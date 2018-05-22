package org.team7.server.server;

import org.team7.server.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * Class for controlling the server. Define commands that can be input to the console.
 */
class ServerControl {

    private Server server;

    private JFrame frame;

    private JTextArea text;

    ServerControl(Server server) {
        this.server = server;
        frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setMinimumSize(new Dimension(400, 400));
        text = new JTextArea();
        frame.add(text);
        frame.pack();
    }

    public void setText(String s) {
        text.setText(text.getText() + "\n" + s);
    }
}
