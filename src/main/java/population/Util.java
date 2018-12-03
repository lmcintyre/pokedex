package population;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    final static String dbPath = "jdbc:sqlite::resource:pokedex.sqlite";
    static Connection conn;

    public static Connection getConnection() throws ClassNotFoundException, SQLException{

        if (conn != null || (conn != null && !conn.isClosed()))
           return conn;
        else {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbPath);
        }

        return conn;
    }

    static List<String> getLinesFromResource(String fileName) {
        InputStream resourceStream = MovePopulator.class.getResourceAsStream("/" + fileName);
        InputStreamReader reader = new InputStreamReader(resourceStream);

        StringBuilder text = new StringBuilder();

        try {
            while (reader.ready())
                text.append((char) reader.read());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(Arrays.asList(text.toString().split("\n")));
    }

    private static int getIndexFromIntNameForTable(String tableName, String internalName) {

        int id = -1;

        try {
            Connection con = getConnection();

            String statement = "select id from " + tableName + " where internalname = ?";

            PreparedStatement pStmt = con.prepareStatement(statement);
            pStmt.setString(1, internalName);

            ResultSet rs = pStmt.executeQuery();

            id = rs.getInt(1);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Exception occurred obtaining " + internalName + " from " + tableName + "!");
            e.printStackTrace();
        }

        return id;
    }

    static int getMoveIndexFromIntName(String internalName) {
        return getIndexFromIntNameForTable("Move", internalName);
    }

    static int getAbilityIndexFromIntName(String internalName) {
        return getIndexFromIntNameForTable("Ability", internalName);
    }

    static int getPokemonIndexFromIntName(String internalName) {
        return getIndexFromIntNameForTable("Pokemon", internalName);
    }
}
