package org.team7.server.server;

import org.team7.server.message.robotmessage.RobotMessageMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class for controlling the server. Define commands that can be input to the console.
 */
class ServerControl {

    private Server server;

    private JFrame frame;

    private JTextArea text;
    private JButton forward;
    private JButton backward;
    private JButton right;
    private JButton left;
    private JButton followLine;

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
        forward = new JButton("Forward");
        backward = new JButton("Backward");
        right = new JButton("Right");
        left = new JButton("Left");
        followLine = new JButton("FollowLine");
        frame.setLayout(new GridLayout(0, 2));
        frame.add(text);
        frame.add(forward);
        frame.add(backward);
        frame.add(right);
        frame.add(left);
        frame.add(followLine);
        forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot(0).sendMessage(new RobotMessageMove(0, 1).encodeMessage());
            }
        });
        backward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot(0).sendMessage(new RobotMessageMove(0, 2).encodeMessage());
            }
        });
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot(0).sendMessage(new RobotMessageMove(0, 3).encodeMessage());
            }
        });
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot(0).sendMessage(new RobotMessageMove(0, 4).encodeMessage());
            }
        });
        followLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot(0).sendMessage(new RobotMessageMove(0, 5).encodeMessage());
            }
        });

        frame.pack();
    }

    public void setText(String s) {
        text.setText(text.getText() + "\n" + s);
    }
}
