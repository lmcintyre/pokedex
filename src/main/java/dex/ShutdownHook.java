package dex;

import population.Util;

import java.sql.SQLException;

public class ShutdownHook extends Thread {

    @Override
    public void run() {
        try {
            Util.getConnection().close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database could not be closed.");
            e.printStackTrace();
        }
    }
}
