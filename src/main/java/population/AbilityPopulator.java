package population;

import database.Ability;
import database.Category;
import database.Move;
import database.Type;

import java.sql.*;
import java.util.List;

public class AbilityPopulator {

    static final int ID_INDEX = 0;
    static final int INTERNALNAME_INDEX = 1;
    static final int NAME_INDEX = 2;
    static final int DESC_INDEX = 3;

    public static void main(String[] args) {

        List<String> lines = Util.getLinesFromResource("abilities.txt");

        lines.set(0, lines.get(0).substring(1));

        for (String line : lines) {
            Ability m = createAbility(line);
            writeToDatabase(m, false);
        }
    }

    private static Ability createAbility(String line) {

        String[] split = line.split(",");

        StringBuilder descFixer = new StringBuilder();

        for (int i = DESC_INDEX; i < split.length; i++) {
            descFixer.append(split[i].trim());

            if (i < split.length - 1)
                descFixer.append(", ");
        }

        split[DESC_INDEX] = descFixer.toString();

        Ability ability = new Ability();

        for (int i = 0; i < split.length; i++) {

            String tSplit = split[i].trim();

            if (tSplit.isEmpty())
                continue;

            try {
                switch(i) {
                    case ID_INDEX:
                        ability.setId(Integer.parseInt(tSplit));
                        break;
                    case INTERNALNAME_INDEX:
                        ability.setIntName(tSplit);
                        break;
                    case NAME_INDEX:
                        ability.setName(tSplit);
                        break;
                    case DESC_INDEX:
                        ability.setText(tSplit.replace("\"", ""));
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }


        return ability;
    }

    private static void writeToDatabase(Ability ability, boolean dry) {

        try {
            Class.forName("org.sqlite.JDBC");

            Connection con = DriverManager.getConnection(Util.dbPath);

            String statement = "insert into Ability (id, name, internalname, description) values (?, ?, ?, ?)";
            PreparedStatement pStmt = con.prepareStatement(statement);

            pStmt.setInt(1, ability.getId());
            pStmt.setString(2, ability.getName());
            pStmt.setString(3, ability.getIntName());
            pStmt.setString(4, ability.getText());

            if (!dry)
                pStmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
