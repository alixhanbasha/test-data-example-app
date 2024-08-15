package com.alixhanbasha;

import com.alixhanbasha.db.DatabaseController;
import com.alixhanbasha.ui.Window;
import com.formdev.flatlaf.FlatDarkLaf;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class Main {
    public static void main(String[] args) throws SQLException {
        log.debug("Starting app...");

        FlatDarkLaf.setup();

        var database = new DatabaseController();
//        database.insert("account:(loan) & solution:(is eligible)", "[{ \"productCode\": \"L\", \"arrears\":{ \"amount\": \"1500.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(mortgage) & solution:(is eligible)", "[{ \"productCode\": \"M\", \"arrears\":{ \"amount\": \"300.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(current account) & solution:(is eligible)", "[{ \"productCode\": \"BA\", \"arrears\":{ \"amount\": \"0.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(credit card) & solution:(is eligible)", "[{ \"productCode\": \"CC\", \"arrears\":{ \"amount\": \"100.00\", \"currency\": \"GBP\" } }]");
//
//        database.insert("account:(loan) & solution:(broken arrangement)", "[{ \"productCode\": \"L\", \"arrears\":{ \"amount\": \"1500.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(mortgage) & solution:(broken arrangement)", "[{ \"productCode\": \"BA\", \"arrears\":{ \"amount\": \"300.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(current account) & solution:(broken arrangement)", "[{ \"productCode\": \"M\", \"arrears\":{ \"amount\": \"0.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(credit card) & solution:(broken arrangement)", "[{ \"productCode\": \"CC\", \"arrears\":{ \"amount\": \"100.00\", \"currency\": \"GBP\" } }]");
//
//        database.insert("account:(loan) & solution:(promise to pay)", "[{ \"productCode\": \"L\", \"arrears\":{ \"amount\": \"1500.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(mortgage) & solution:(promise to pay)", "[{ \"productCode\": \"BA\", \"arrears\":{ \"amount\": \"300.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(current account) & solution:(promise to pay)", "[{ \"productCode\": \"M\", \"arrears\":{ \"amount\": \"0.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(credit card) & solution:(promise to pay)", "[{ \"productCode\": \"CC\", \"arrears\":{ \"amount\": \"100.00\", \"currency\": \"GBP\" } }]");
//
//        database.insert("account:(loan) & solution:(solution summary pending)", "[{ \"productCode\": \"L\", \"arrears\":{ \"amount\": \"1500.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(mortgage) & solution:(solution summary pending)", "[{ \"productCode\": \"BA\", \"arrears\":{ \"amount\": \"300.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(current account) & solution:(solution summary pending)", "[{ \"productCode\": \"M\", \"arrears\":{ \"amount\": \"0.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(credit card) & solution:(solution summary pending)", "[{ \"productCode\": \"CC\", \"arrears\":{ \"amount\": \"100.00\", \"currency\": \"GBP\" } }]");
//
//        database.insert("account:(loan) & solution:(non-fs)", "[{ \"productCode\": \"L\", \"arrears\":{ \"amount\": \"1500.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(mortgage) & solution:(non-fs)", "[{ \"productCode\": \"BA\", \"arrears\":{ \"amount\": \"300.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(current account) & solution:(non-fs)", "[{ \"productCode\": \"M\", \"arrears\":{ \"amount\": \"0.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(credit card) & solution:(non-fs)", "[{ \"productCode\": \"CC\", \"arrears\":{ \"amount\": \"100.00\", \"currency\": \"GBP\" } }]");
//
//        database.insert("account:(loan) & solution:(no solution in place)", "[{ \"productCode\": \"L\", \"arrears\":{ \"amount\": \"1500.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(mortgage) & solution:(no solution in place)", "[{ \"productCode\": \"BA\", \"arrears\":{ \"amount\": \"300.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(current account) & solution:(no solution in place)", "[{ \"productCode\": \"M\", \"arrears\":{ \"amount\": \"0.00\", \"currency\": \"GBP\" } }]");
//        database.insert("account:(credit card) & solution:(no solution in place)", "[{ \"productCode\": \"CC\", \"arrears\":{ \"amount\": \"100.00\", \"currency\": \"GBP\" } }]");

//        database.insert("account:(credit card) & solution:(solution summary pending) | account:(loan) & solution:(non-fs)", "[{ \"productCode\": \"CC\", \"arrears\":{ \"amount\": \"100.00\", \"currency\": \"GBP\" } }, { \"productCode\": \"L\", \"arrears\":{ \"amount\": \"0.00\", \"currency\": \"GBP\" } }]");

        // \(loan\) & [a-z]+:\(no
        Window app = new Window();
        app.tableHasColumns(
                new String[]{"ID", "Key", "JSON", "Last Updated"}
        );
        app.hasData(
                database.executeStatement("SELECT * FROM contracts").get()
        );
        app.initWindow();

        database.closeConnection();
    }
}