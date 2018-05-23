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
    private JButton right90;
    private JButton left90;
    private JButton right180;
    private JButton forwardintersect;
    private JButton trolleyUp;
    private JButton trolleyDown;
    private JButton leaveLoad;
    private JButton takeLoad;
    private JButton stop;

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
        right90 = new JButton("Right90");
        left90 = new JButton("Left90");
        right180 = new JButton("Right180");
        forwardintersect = new JButton("ForwardInter");
        trolleyUp = new JButton("TrolleyUp");
        trolleyDown = new JButton("TrolleyDown");
        takeLoad = new JButton("TakeLoad");
        leaveLoad = new JButton("LeaveLoad");
        stop = new JButton("Stop");

        frame.setLayout(new GridLayout(0, 4));
        frame.add(text);
        frame.add(forward);
        frame.add(backward);
        frame.add(right);
        frame.add(left);
        frame.add(followLine);
        frame.add(right90);
        frame.add(left90);
        frame.add(right180);
        frame.add(forwardintersect);
        frame.add(trolleyUp);
        frame.add(trolleyDown);
        frame.add(takeLoad);
        frame.add(leaveLoad);
        frame.add(stop);

        forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 1).encodeMessage());
            }
        });
        backward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 2).encodeMessage());
            }
        });
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 3).encodeMessage());
            }
        });
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 4).encodeMessage());
            }
        });
        followLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 5).encodeMessage());
            }
        });
        right90.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 6).encodeMessage());
            }
        });
        left90.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 7).encodeMessage());
            }
        });
        right180.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 8).encodeMessage());
            }
        });
        forwardintersect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 9).encodeMessage());
            }
        });
        trolleyUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 10).encodeMessage());
            }
        });
        trolleyDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 11).encodeMessage());
            }
        });
        takeLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 12).encodeMessage());
            }
        });
        leaveLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 13).encodeMessage());
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, 14).encodeMessage());
            }
        });

        frame.pack();
    }

    public void setText(String s) {
        text.setText(text.getText() + "\n" + s);
    }
}
