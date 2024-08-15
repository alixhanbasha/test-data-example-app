package com.alixhanbasha.db;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Getter
public class DatabaseController {

    private Connection dbConnection;
    private Statement statement;

    public DatabaseController() {
        try {
            Class.forName("org.sqlite.JDBC");

            this.dbConnection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/contracts.db");
            Statement statement = this.dbConnection.createStatement();

            log.info("Connected to database.");

            String sql = "CREATE TABLE IF NOT EXISTS CONTRACTS( " +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "COMMAND        TEXT    NOT NULL, " +
                    "JSON           TEXT    NOT NULL, " +
                    "UPDATED        TEXT    NOT NULL) ";

            statement.executeUpdate(sql);
            statement.close();

            log.debug("Table created successfully");
        } catch (Exception e) {
            log.error("{}: {}", e.getClass().getName(), e.getMessage());
            System.exit(0);
        }
    }

    public Optional<ResultSet> executeStatement(String sqlStatement) {
        try {
            this.statement = this.dbConnection.createStatement();
            var rs = this.statement.executeQuery(sqlStatement);
            return Optional.of(rs);
        } catch (java.sql.SQLException sqlException) {
            log.error("An error occurred while executing a SQL statement -> {}: {}", sqlException.getClass().getName(), sqlException.getMessage());
        }

        return Optional.empty();
    }

    public void insert(String command, String json) {
        try {
            this.statement = this.dbConnection.createStatement();
            this.statement.execute("INSERT INTO contracts (command, json, updated) VALUES ('" + command + "', '" + json + "', '" + Instant.now() + "')");
        } catch (java.sql.SQLException sqlException) {
            log.error("An error occurred while inserting values to the database -> {}: {}", sqlException.getClass().getName(), sqlException.getMessage());
        }
    }

    public void closeConnection() throws SQLException {
        this.statement.close();
        this.dbConnection.close();
    }
}
