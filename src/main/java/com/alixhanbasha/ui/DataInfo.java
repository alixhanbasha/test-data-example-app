package com.alixhanbasha.ui;

import com.alixhanbasha.model.TestData;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class DataInfo {

    private JFrame window;
    private JPanel panel;
    private JButton browserLauncher;
    private JTextArea jTextArea;

    @Getter
    @Setter
    private TestData testData;

    public DataInfo() {
        this.window = new JFrame("Test Data Info");
        this.panel = new JPanel();
        this.browserLauncher = new JButton("Open in browser");
        this.jTextArea = new JTextArea();
        this.testData = new TestData();
    }

    public void display() {
        this.panel.setLayout(new BorderLayout());
        this.jTextArea.append("ID:            " + this.testData.getId() + "\n");
        this.jTextArea.append("Command:       " + this.testData.getCommand() + "\n");
        this.jTextArea.append("Last Updated:  " + this.testData.getDate() + "\n\n\n");

        this.jTextArea.append(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(this.testData.getJson())
        );

        this.jTextArea.setEditable(false);

        var scrollPane = new JScrollPane(this.jTextArea);
        scrollPane.setPreferredSize(new Dimension(this.window.getWidth(), 500));

        this.browserLauncher.addActionListener( (event) -> {
            log.debug("This would open the browser on <FS_HUB_URL>?value={}", this.testData.getId());
        });

        this.panel.add(this.browserLauncher, BorderLayout.PAGE_START);
        this.panel.add(scrollPane, BorderLayout.PAGE_END);


        this.window.add(this.panel);
        this.window.setMinimumSize(new Dimension(400, 500));
        this.window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.window.pack();
        this.window.setVisible(true);
    }

}
