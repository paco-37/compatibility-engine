package com.gatekeeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String URL = "jdbc:postgresql://localhost:5432/gatekeeper_db";
    private static final String USER = "devuser";


    public static Connection connect() {
        Connection conn = null;

        String password = System.getenv("DB_PASSWORD");
        if(password == null){
            System.err.println("WARNUNG: 'DB_PASSWORD' nicht gesetzt. Nutze Standard-Passwort.");
            password = "geheim";
        }

        try {
            conn = DriverManager.getConnection(URL,USER,password);
            // System.out.println("Verbindung zur Datenbank erfolgreich hergestellt!");
        } catch (SQLException e) {
            System.out.println("Verbindungsfehler: " + e.getMessage());
            e.printStackTrace();
        }

        return conn;
    }

    
}
