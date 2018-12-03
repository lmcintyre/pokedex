package database;

import javafx.scene.image.Image;
import population.DBPokemon;
import population.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    public static List<DBPokemon> getDBPokeList() {

        Connection con;
        List<DBPokemon> pokes = new ArrayList<>();

        try {
            con = Util.getConnection();

            String statement = "select * from Pokemon";
            PreparedStatement pStmt = con.prepareStatement(statement);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);

                pokes.add(new DBPokemon(id, name));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pokes;
    }

    public static Pokemon getPokemon(int id) {

        Pokemon poke = new Pokemon();
        poke.setDexNum(id);

        Connection con;

        try {
            con = Util.getConnection();

            String statement = "select name, internalname, pokedextext, icon from Pokemon where id = (?)";
            PreparedStatement pStmt = con.prepareStatement(statement);
            pStmt.setInt(1, id);

            ResultSet rs = pStmt.executeQuery();

            poke.setName(rs.getString(1));
            poke.setInternalName(rs.getString(2));
            poke.setPokedexText(rs.getString(3));
            poke.setIcon(new Image(rs.getBinaryStream(4)));

            poke.setTypes(getTypes(id));
            poke.setAbilities(getAbilities(id));
            poke.setMoves(getMoves(id));
            poke.setEvolutions(getEvolutions(id));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return poke;
    }

    private static List<Type> getTypes(int id) {

        List<Type> types = new ArrayList<>();
        //6 columns
        //3 per type
        //id, name, icon
        String statement = "select type1.id, type1.name, type1.icon, type2.id, type2.name, type2.icon " +
                "from Pokemon left outer join Type as type1, Type as type2 on Pokemon.typeid1 = type1.id and " +
                "Pokemon.typeid2 = type2.id " +
                "where Pokemon.id = (?)";

        try {
            Connection con = Util.getConnection();

            PreparedStatement pStmt = con.prepareStatement(statement);
            pStmt.setInt(1, id);

            ResultSet rs = pStmt.executeQuery();

            for (int i = 1; i <= 6; i += 3) {
                if (i == 4 && rs.getInt(i) == 0) //only one type
                    break;

                String tName = rs.getString(i + 1);
                Image tImg = new Image(rs.getBinaryStream(i + 2));

                Type t = new Type(tName, i == 1 ? Type.Kind.PRIMARY : Type.Kind.SECONDARY, tImg);
                types.add(t);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return types;
    }

    private static List<Ability> getAbilities(int id) {

        List<Ability> abilities = new ArrayList<>();
        //12 columns total, 4-8, 9-12 might be null/none check 5, 9 before creating an ability
        //1, 2, 3, 4 per ability
        //id, name, internalname, description
        String statement = "select ab1.id, ab1.name, ab1.internalname, ab1.description, " +
                                "ab2.id, ab2.name, ab2.internalname, ab2.description, " +
                                "abh.id, abh.name, abh.internalname, abh.description " +
                            "from Pokemon left outer join Ability as ab1, Ability as ab2, Ability as abh " +
                            "on Pokemon.ability1 = ab1.id and Pokemon.ability2 = ab2.id and Pokemon.abilityh = abh.id " +
                            "where Pokemon.id = (?)";

        try {
            Connection con = Util.getConnection();

            PreparedStatement pStmt = con.prepareStatement(statement);
            pStmt.setInt(1, id);

            ResultSet rs = pStmt.executeQuery();

            for (int i = 1; i <= 12; i += 4) {

                if (rs.getInt(i) != 0) {
                    String abilityName = rs.getString(i + 1);
                    String abilityIntName = rs.getString(i + 2);
                    String abilityText = rs.getString(i + 3);
                    boolean isHidden = i == 9;

                    Ability a = new Ability(abilityName, abilityIntName, abilityText, isHidden);
                    abilities.add(a);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return abilities;
    }

    private static List<Move> getMoves(int id) {

        List<Move> moves = new ArrayList<>();
        //9 columns per move
        //many moves
        //level, name, power, accuracy, text, type, icon, category, icon
        String statement = "select Learns.level, Move.name, Move.power, Move.accuracy, Move.movetext, Type.name, Type.icon, Category.name, Category.icon " +
                            "from Pokemon left outer join Learns, Move, Type, Category " +
                            "on Pokemon.id = Learns.pokemonid and Learns.moveid = Move.id and Move.type = Type.id and Move.category = Category.id " +
                            "where Pokemon.id = (?) " +
                            "order by Learns.level";

        try {
            Connection con = Util.getConnection();

            PreparedStatement pStmt = con.prepareStatement(statement);
            pStmt.setInt(1, id);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {

                Move m = new Move();

                m.setLevelLearned(rs.getInt(1));
                m.setMoveName(rs.getString(2));
                m.setPower(rs.getInt(3));
                m.setAccuracy(rs.getInt(4));
                m.setMoveText(rs.getString(5));

                String typeName = rs.getString(6);
                Image typeImage = new Image(rs.getBinaryStream(7));
                m.setType(new Type(typeName, Type.Kind.MOVE, typeImage));

                String categoryName = rs.getString(8);
                Image categoryImage = new Image(rs.getBinaryStream(9));
                m.setCategory(new Category(categoryName, categoryImage));

                moves.add(m);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return moves;
    }

    private static List<Evolution> getEvolutions(int id) {

        List<Evolution> evolutions = new ArrayList<>();
        //5 columns per rec, 0 or more recs
        //basename, evol name, evol id, evol icon, method, criteria
        String statement = "select base.name, evolved.name, evolved.id, evolved.icon, Evolves.method, Evolves.criteria " +
                "from Pokemon as base left outer join Evolves, Pokemon as evolved on base.id = Evolves.baseid and Evolves.evolvedid = evolved.id " +
                "where base.id = (?)";

        try {
            Connection con = Util.getConnection();

            PreparedStatement pStmt = con.prepareStatement(statement);
            pStmt.setInt(1, id);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {

                Evolution e = new Evolution();

                e.setEvolvedFrom(rs.getString(1));
                e.setEvolvesTo(rs.getString(2));
                e.setEvolvesId(rs.getInt(3));
                e.setEvolvedIcon(new Image(rs.getBinaryStream(4)));
                e.setEvolutionMethod(rs.getString(5));
                e.setEvolutionCriteria(rs.getString(6));

                evolutions.add(e);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return evolutions;
    }





}
