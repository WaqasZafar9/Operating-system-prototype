package MemoryManagment;

import MenuFrames.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionFrame {

    JFrame mainFrame;
    JPanel mainPanel;
    private JLabel heading;
    JButton pagginButton;
    JButton pageReplacement;
    JButton backButton;

    public SelectionFrame(){
        initGUI();
    }

    private void initGUI() {
        mainFrame=new JFrame();
        mainPanel=new JPanel(null);
        pagginButton=new JButton("Pagging");
        pageReplacement=new JButton("Page Replacement");
        backButton=new JButton("Back");
        Font f=new Font("Arial",Font.BOLD,18);
        heading=new JLabel("Memory Managment");
        heading.setFont(f);

        mainPanel.add(pagginButton);
        mainPanel.add(backButton);
        mainPanel.add(pageReplacement);
        mainPanel.add(heading);


        heading.setBounds(280, 35, 180, 35 );
        pagginButton.setBounds(305, 120, 155, 40);
        pageReplacement.setBounds(305, 170, 155, 40);
        backButton.setBounds(255, 245, 270, 40);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        mainFrame.setSize(720,480);

        pageReplacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new MemoryManagmentFrame();
            }
        });

        pagginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new Pagging();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new MenuFrame();
            }
        });

    }


}
