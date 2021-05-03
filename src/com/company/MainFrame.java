package com.company;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class MainFrame extends JFrame{

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private final JMenuItem pauseMenuItem;
    private final JMenuItem resumeMenuItem;
    private final JCheckBox sandpaperMenuItem;

    private final Field field = new Field();

    public MainFrame(){

        super("Programming and threads synchronizing");
        setSize(WIDTH,HEIGHT);

        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);

        setExtendedState(MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Balls");
        Action addBallAction = new AbstractAction("Add ball") {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.addBall();
                if(!pauseMenuItem.isEnabled() &&
                        !resumeMenuItem.isEnabled()){
                    pauseMenuItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);
        JMenu controlMenu = new JMenu("Controls");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("pause moving") {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);
        Action resumeAction = new AbstractAction("resume") {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);
        Action sandpaperAction = new AbstractAction("sandpaper") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sandpaperMenuItem.isSelected()){
                    try {

                        field.sandpaper(Double.parseDouble(JOptionPane.
                                showInputDialog("Enter sandpaper value: ", 1)));
                        sandpaperMenuItem.setSelected(true);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }else if (!sandpaperMenuItem.isSelected()){
                    try {
                        field.sandpaper(1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }

            }
        };
        sandpaperMenuItem = new JCheckBox(sandpaperAction);
        controlMenu.add(sandpaperMenuItem);

        getContentPane().add(field, BorderLayout.CENTER);
    }

    public static void main(String[] args){

        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        int nbThreads =  Thread.getAllStackTraces().keySet().size();
        System.out.println("x=" +nbThreads );
        int nbRunning = 0;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getState()==Thread.State.RUNNABLE) nbRunning++;
            System.out.println("x=" +nbRunning );
        }
    }






}