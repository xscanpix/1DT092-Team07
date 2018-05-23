package org.team7.server.server;

import org.team7.server.message.robotmessage.RobotMessageMove;
import org.team7.server.message.Message;
import org.team7.server.warehouse.Warehouse;

import java.util.List;
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

    private JTextField text;
    private JButton pickup;
    private JButton leave;
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
        text = new JTextField();
        pickup = new JButton("Pickup");
        leave = new JButton("Leave");
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
        frame.add(pickup);
        frame.add(leave);
        //frame.add(forward);
        //frame.add(backward);
        //frame.add(right);
        //frame.add(left);
        //frame.add(followLine);
        //frame.add(right90);
        //frame.add(left90);
        //frame.add(right180);
        //frame.add(forwardintersect);
        //frame.add(trolleyUp);
        //frame.add(trolleyDown);
        //frame.add(takeLoad);
        //frame.add(leaveLoad);
        frame.add(stop);

        pickup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int zone = Integer.parseInt(text.getText());
                System.out.println(zone);
                List<Message> moves = Warehouse.generateMovesTo(zone, true);
                for(Message msg : moves) {
                    server.getRobot().sendMessage(msg.encodeMessage());
                }
            }
        });
        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int zone = Integer.parseInt(text.getText());
                System.out.println(zone);
                List<Message> moves = Warehouse.generateMovesTo(zone, false);
                for(Message msg : moves) {
                    server.getRobot().sendMessage(msg.encodeMessage());
                }
            }
        });
        forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.FORWARD).encodeMessage());
            }
        });
        backward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.BACKWARD).encodeMessage());
            }
        });
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.RIGHT).encodeMessage());
            }
        });
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.LEFT).encodeMessage());
            }
        });
        followLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE).encodeMessage());
            }
        });
        right90.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.RIGHT90).encodeMessage());
            }
        });
        left90.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.LEFT90).encodeMessage());
            }
        });
        right180.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.RIGHT180).encodeMessage());
            }
        });
        forwardintersect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.FORWARDINTERSECTION).encodeMessage());
            }
        });
        trolleyUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.TROLLEYUP).encodeMessage());
            }
        });
        trolleyDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.TROLLEYDOWN).encodeMessage());
            }
        });
        takeLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.TAKELOAD).encodeMessage());
            }
        });
        leaveLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD).encodeMessage());
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.getRobot().sendMessage(new RobotMessageMove(0, RobotMessageMove.STOP).encodeMessage());
            }
        });

        frame.pack();
    }

    public void setText(String s) {
        text.setText(text.getText() + "\n" + s);
    }
}
