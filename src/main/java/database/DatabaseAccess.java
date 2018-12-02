package database;

import javafx.scene.image.Image;
import population.DBPokemon;
import population.Util;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    public static void nothing() {

//        Blob b;
//
//        InputStream is = b.getBinaryStream();
//
//        Image img = new Image(is);

    }

    public static List<DBPokemon> getDBPokeList() {

        Connection con;
        List<DBPokemon> pokes = new ArrayList<>();

        try {
            con = Util.getConnection();

            String statement = "select * from Pokemon";
            PreparedStatement pStmt = con.prepareStatement(statement);
            ResultSet rs = pStmt.executeQuery();

            //TODO expland this if necessary
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
}
