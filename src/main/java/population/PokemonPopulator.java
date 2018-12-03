package population;

import database.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonPopulator {

    static Connection con;

    public static void main(String[] args) {

        try {
            con = Util.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> lines = Util.getLinesFromResource("pokemon.txt");
        List<String> input = new ArrayList<>();
        List<DBPokemon> pkmn = new ArrayList<>();

        lines.set(0, lines.get(0).substring(1));

        int i = 1;
        input.add(lines.get(0));
        while (i < lines.size()) {

            if (lines.get(i).startsWith("["))
            {
                pkmn.add(parsePokemon(input));
                input.clear();
            }
            input.add(lines.get(i));
            i++;
        }

        for (DBPokemon poke : pkmn) {
            writePokemonToDatabase(poke);
        }

        for (DBPokemon poke : pkmn) {
            getEvolutions(poke.getId(), poke.getEvolveText());
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static DBPokemon parsePokemon(List<String> lines) {

        Map<String, String> values = getMappedValues(lines);

        DBPokemon pkmn = new DBPokemon();

        pkmn.setId(Integer.parseInt(values.get("Id")));
        pkmn.setName(values.get("Name"));
        pkmn.setIntName(values.get("InternalName"));

        pkmn.setType1(Type.getIndex(values.get("Type1")));

        String type2 = values.getOrDefault("Type2", "");
        if (!type2.isEmpty())
            pkmn.setType2(Type.getIndex(type2));
        else
            pkmn.setType2(0);

        String abilities = values.get("Abilities");
        String[] theAbilities = abilities.split(",");

        pkmn.setAbility1(Util.getAbilityIndexFromIntName(theAbilities[0]));

        if (theAbilities.length > 1)
            pkmn.setAbility2(Util.getAbilityIndexFromIntName(theAbilities[1]));
        else
            pkmn.setAbility2(0);

        String hiddenAbility = values.getOrDefault("HiddenAbility", "");
        if (!hiddenAbility.isEmpty())
            pkmn.setAbilityh(Util.getAbilityIndexFromIntName(hiddenAbility));
        else
            pkmn.setAbilityh(0);

        //DBMove parse for 7 and 8
//        getMovesLearned(pkmn.getId(), values.getOrDefault("EggMoves", ""), values.get("Moves"));

        pkmn.setDexText(values.get("Pokedex"));

        pkmn.setEvolveText(values.get("Evolutions") + " ");

        pkmn.setIconResourcePath();
        return pkmn;
    }

    private static void getEvolutions(int pokemonId, String evolutions) {

        List<DBEvolution> evols = new ArrayList<>();

        String[] evolSplit = evolutions.split(",");

        if (evolSplit.length >= 3) {
            for (int i = 0; i < evolSplit.length; i += 3) {

                int eForm = Util.getPokemonIndexFromIntName(evolSplit[i].trim());
                String method = evolSplit[i + 1].trim();
                String criteria = evolSplit[i + 2].trim();

                evols.add(new DBEvolution(pokemonId, eForm, method, criteria));
            }
        }


        for (DBEvolution e : evols)
            System.out.printf("DBPokemon %d evolves from %d by %s : %s\n", e.base, e.evolved, e.method, e.criteria);

        writeEvolvesToDatabase(evols);
    }

    private static Map<String, String> getMappedValues(List<String> lines) {

        Map<String, String> valueMap = new HashMap<>();

        for (String line : lines) {
            String[] splits = line.split("=");
            if (splits.length > 1)
                valueMap.put(splits[0].trim(), splits[1].trim());
            if (splits[0].startsWith("["))
                valueMap.put("Id", splits[0].replace("[", "").replace("]", "").trim());
        }
        return valueMap;
    }

    private static void getMovesLearned(int pokemonId, String eggMoves, String moves) {

        List<Integer> levelsLearned = new ArrayList<>();
        List<Integer> movesLearned = new ArrayList<>();

        String[] eggMovesArr = eggMoves.split(",");

        if (!eggMovesArr[0].isEmpty()) {
            for (String move : eggMovesArr) {
                levelsLearned.add(0);
                movesLearned.add(Util.getMoveIndexFromIntName(move));
            }
        }

        String[] levelUpMoves = moves.split(",");

        for (int i = 0; i < levelUpMoves.length; i += 2) {
            levelsLearned.add(Integer.parseInt(levelUpMoves[i]));
            movesLearned.add(Util.getMoveIndexFromIntName(levelUpMoves[i + 1]));
        }

        for (int i = 0; i < levelsLearned.size(); i++)
            System.out.printf("Poke: %d |\t %d\t%d\n", pokemonId, levelsLearned.get(i), movesLearned.get(i));

        writeMovesLearnedToDatabase(pokemonId, levelsLearned, movesLearned);
    }

    private static void writePokemonToDatabase(DBPokemon pkmn) {

        try {
            String statement = "insert into Pokemon (id, name, internalname, typeid1, typeid2, ability1, ability2, " +
                                "abilityh, pokedextext, icon) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pStmt = con.prepareStatement(statement);

            pStmt.setInt(1, pkmn.getId());
            pStmt.setString(2, pkmn.getName());
            pStmt.setString(3, pkmn.getIntName());
            pStmt.setInt(4, pkmn.getType1());
            pStmt.setInt(5, pkmn.getType2());
            pStmt.setInt(6, pkmn.getAbility1());
            pStmt.setInt(7, pkmn.getAbility2());
            pStmt.setInt(8, pkmn.getAbilityh());
            pStmt.setString(9, pkmn.getDexText());
            pStmt.setBytes(10, pkmn.getBytesFromIcon());

            pStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void writeMovesLearnedToDatabase(int pokemonId, List<Integer> levels, List<Integer> moveIds) {

        int i = 0;
        try {
            for (i = 0; i < levels.size(); i++) {
                String statement = "insert into Learns (pokemonid, moveid, level) values (?, ?, ?)";
                PreparedStatement pStmt = con.prepareStatement(statement);

                pStmt.setInt(1, pokemonId);
                pStmt.setInt(2, moveIds.get(i));
                pStmt.setInt(3, levels.get(i));

                pStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.printf("Error writing pokemon %d move %d level %d to db", pokemonId, moveIds.get(i), levels.get(i));
            e.printStackTrace();
        }
    }

    private static void writeEvolvesToDatabase(List<DBEvolution> evols) {

        DBEvolution thisEvol = null;

        try {
            for (DBEvolution evol : evols) {
                thisEvol = evol;
                String statement = "insert into Evolves (baseid, evolvedid, method, criteria) values (?, ?, ?, ?)";
                PreparedStatement pStmt = con.prepareStatement(statement);

                pStmt.setInt(1, evol.base);
                pStmt.setInt(2, evol.evolved);
                pStmt.setString(3, evol.method);
                pStmt.setString(4, evol.criteria);

                pStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.printf("Error writing evolve from %d to %d by %s with %s to db\n", thisEvol.base, thisEvol.evolved, thisEvol.method, thisEvol.criteria);
            e.printStackTrace();
        }
    }
}

class DBEvolution {

    public int base;
    public int evolved;
    public String method;
    public String criteria;

    public DBEvolution(int base, int evolved, String method, String criteria) {
        this.base = base;
        this.evolved = evolved;
        this.method = method;
        this.criteria = criteria;
    }
}