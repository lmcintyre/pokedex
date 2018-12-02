package population;

import database.Category;
import database.Move;
import database.Type;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MovePopulator {

    static final int ID_INDEX = 0;
    static final int INTERNALNAME_INDEX = 1;
    static final int NAME_INDEX = 2;
    static final int POWER_INDEX = 4;
    static final int TYPE_INDEX = 5;
    static final int CATEGORY_INDEX = 6;
    static final int ACCURACY_INDEX = 7;
    static final int DESC_INDEX = 13;

    static final int CAT_SPECIAL = 1;
    static final int CAT_PHYSICAL = 2;
    static final int CAT_STATUS = 3;

    public static void main(String[] args) {

        List<String> lines = Util.getLinesFromResource("moves.txt");

        lines.set(0, lines.get(0).substring(1));

        for (String line : lines) {
            Move m = createMove(line);
            writeToDatabase(m, false);
        }
    }

    private static Move createMove(String line) {

        String[] split = line.split(",");

        StringBuilder descFixer = new StringBuilder();

        for (int i = DESC_INDEX; i < split.length; i++) {
            descFixer.append(split[i].trim());

            if (i < split.length - 1)
                descFixer.append(", ");
        }

        split[DESC_INDEX] = descFixer.toString();

        Move move = new Move();

        for (int i = 0; i < split.length; i++) {

            String tSplit = split[i].trim();

            if (tSplit.isEmpty())
                continue;

            try {
                switch(i) {
                    case ID_INDEX:
                        move.setId(Integer.parseInt(tSplit));
                        break;
                    case INTERNALNAME_INDEX:
                        move.setIntName(tSplit);
                        break;
                    case NAME_INDEX:
                        move.setName(tSplit);
                        break;
                    case POWER_INDEX:
                        move.setPower(Integer.parseInt(tSplit));
                        break;
                    case TYPE_INDEX:
                        move.setType(Type.getIndex(tSplit));
                        break;
                    case CATEGORY_INDEX:
                        move.setCategory(Category.getIndex(tSplit));
                        break;
                    case ACCURACY_INDEX:
                        move.setAccuracy(Integer.parseInt(tSplit));
                        break;
                    case DESC_INDEX:
                        move.setText(tSplit.replace("\"", "").replace("'", "''"));
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }


        return move;
    }

    private static void writeToDatabase(Move move, boolean dry) {

        try {
            Class.forName("org.sqlite.JDBC");

            Connection con = DriverManager.getConnection(Util.dbPath);
            Statement stmt = con.createStatement();

            String statement = String.format("insert into Move (id, name, internalname, type, power, accuracy, category, movetext) " +
                            "values ('%d', '%s', '%s', '%d', '%d', '%d', '%d', '%s')",
                    move.getId(), move.getName(), move.getIntName(), move.getType(), move.getPower(),
                    move.getAccuracy(), move.getCategory(), move.getText());

            System.out.println(statement);

            if (!dry)
                stmt.executeUpdate(statement);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
