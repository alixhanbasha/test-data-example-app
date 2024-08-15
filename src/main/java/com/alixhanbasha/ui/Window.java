package com.alixhanbasha.ui;

import com.alixhanbasha.MultiDimensionalArray;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.IntelliJTheme;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class Window {

    private JFrame window;
    private JPanel panel;
    private JSplitPane splitPane;
    private JTable table;
    private JButton button;
    private JButton showAll;

    private JButton comboButton;
    private JComboBox accountComboBox;
    private JComboBox solutionComboBox;
    private JTextField textField;

    private MultiDimensionalArray multiDimensionalArray = new MultiDimensionalArray();

    private String[] columns;
    private String[][] data;

    public Window() {
        this.window = new JFrame("SQLite Test");
        this.panel = new JPanel();
        this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.button = new JButton("Search");
        this.showAll = new JButton("Show All");
        this.textField = new JTextField("", 30);
        this.comboButton = new JButton("Find");
        this.accountComboBox = new JComboBox(new String[]{ "loan", "mortgage", "credit card", "current account" });
        this.solutionComboBox = new JComboBox(new String[]{
                "no solution in place", "is eligible",
                "solution summary pending",
                "promise to pay",
                "broken arrangement", "non-fs"
        });

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            log.error("Cannot change app look and feel -> {}: {}", e.getClass().getName(), e.getMessage());
            log.warn("Using default look and feel.");
        }

        IntelliJTheme.setup( Window.class.getResourceAsStream(
                "/themes/GitHubDark.theme.json" ) );

    }

    public void tableHasColumns(String[] columns) {
        this.columns = columns;
    }

    public void hasData(ResultSet queryResult) throws SQLException {
        while (queryResult.next()) {
            this.multiDimensionalArray.add(
                    new String[]{
                            queryResult.getString("ID"),
                            queryResult.getString("COMMAND"),
                            queryResult.getString("JSON"),
                            queryResult.getString("UPDATED")
                    }
            );
        }
        this.data = this.multiDimensionalArray.getArrayOfArrays();
    }

    public void initWindow() {
        this.splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.splitPane.setDividerLocation(450);

        DefaultTableModel model = new DefaultTableModel();
        for (String col : columns) model.addColumn(col);
        for (String[] tData : data) model.addRow(tData);

        this.table = new JTable(model);

        var scrollPane = new JScrollPane(this.table);

        this.table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());

                String columnName = table.getColumnName(col);

                if (columnName.equals("Key")) {
                    DataInfo dataInfoPage = new DataInfo();
                    dataInfoPage.getTestData().setId(table.getModel().getValueAt(row, col - 1).toString());
                    dataInfoPage.getTestData().setCommand(table.getModel().getValueAt(row, col).toString());
                    dataInfoPage.getTestData().setJson(table.getModel().getValueAt(row, col + 1).toString());
                    dataInfoPage.getTestData().setDate(table.getModel().getValueAt(row, col + 2).toString());

                    dataInfoPage.display();
                }
            }
        });

        this.button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String textInputData = textField.getText();

                if (!textInputData.isBlank() && !textInputData.isEmpty()) {

                    data = multiDimensionalArray.searchCandidates(textInputData);
                    if (data.length > 0) {
                        model.setRowCount(0);
                        model.fireTableDataChanged();

                        for (int i = 0; i < data.length; i++) {
                            model.addRow(data[i]);
                        }
                        model.fireTableDataChanged();
                        table.repaint();
                    } else {
                        JOptionPane.showMessageDialog(window, "Could not find what you were looking for!",
                                "Found nothing", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(window, "Please provide a search keyword!",
                            "Cannot run an empty search", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.comboButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String account = accountComboBox.getSelectedItem().toString();
                String solution = solutionComboBox.getSelectedItem().toString();

                if (!account.isBlank() && !account.isEmpty() && !solution.isBlank() && !solution.isEmpty()) {

                    String key = "account:\\(" + account + "\\) & solution:\\(" + solution + "\\)";
                    data = multiDimensionalArray.searchCandidates(key);
                    if (data.length > 0) {
                        model.setRowCount(0);
                        model.fireTableDataChanged();

                        for (int i = 0; i < data.length; i++) {
                            model.addRow(data[i]);
                        }
                        model.fireTableDataChanged();
                        table.repaint();
                    } else {
                        JOptionPane.showMessageDialog(window, "Could not find what you were looking for!",
                                "Found nothing", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(window, "Please provide a search keyword!",
                            "Cannot run an empty search", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.showAll.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                model.setRowCount(0);
                model.fireTableDataChanged();

                data = multiDimensionalArray.getArrayOfArrays();
                for (int i = 0; i < data.length; i++) {
                    model.addRow(data[i]);
                }

                model.fireTableDataChanged();
                table.repaint();
            }
        });

        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
        this.panel.setMinimumSize(new Dimension(this.window.getWidth(), 400));
        this.textField.setPreferredSize(new Dimension(150, 30));

        this.panel.add(new JPanel());

        var searchPanel = new JPanel();
        searchPanel.add(this.textField);
        searchPanel.add(this.button);
        searchPanel.add(this.showAll);
        this.panel.add(searchPanel);

        var filterPanel = new JPanel();
        filterPanel.add(this.accountComboBox);
        filterPanel.add(this.solutionComboBox);
        filterPanel.add(this.comboButton);
        this.panel.add(filterPanel);

        this.table.setMinimumSize(new Dimension(this.window.getWidth(), 400));

        this.splitPane.add(this.panel);
        this.splitPane.add(scrollPane);
        this.window.add(this.splitPane);

        this.window.setMinimumSize(new Dimension(1280, 720));
        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.window.pack();
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
    }

}
