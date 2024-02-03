package MemoryManagment;

import MenuFrames.MenuFrame;
import ProcessManagmentFrame.ProcessManagmentFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class MemoryManagmentFrame {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTextField noOfFramesInput;
    private JTextField stringInput;
    private JLabel noOfFramesLabel;
    private JLabel stringLabel;
    private JButton LRUButton;
    private JButton FIFO;
    private JButton backButton;
    Socket s;
    ObjectOutputStream out;

    private JLabel pageFaultHeading;
    private String[] framesRecord;
    private int frame = 0;

    public MemoryManagmentFrame(){

        initGUI();

    }

    private void initGUI() {

        mainFrame=new JFrame();

        mainPanel=new JPanel(null);

        noOfFramesInput = new JTextField (5);
        stringInput = new JTextField (5);
        noOfFramesLabel = new JLabel ("No of Frames");
        stringLabel = new JLabel ("Reference String");
        LRUButton = new JButton ("LRU");
        FIFO = new JButton ("FIFO");
        backButton=new JButton("Back");
        pageFaultHeading = new JLabel ("Page ");

        Font f1=new Font("Arial",Font.BOLD,16);
        pageFaultHeading.setFont(f1);
        pageFaultHeading.setVisible(false);

        mainPanel.add (noOfFramesInput);
        mainPanel.add (stringInput);
        mainPanel.add (noOfFramesLabel);
        mainPanel.add (stringLabel);
        mainPanel.add (LRUButton);
        mainPanel.add (FIFO);
        mainPanel.add (pageFaultHeading);
        mainPanel.add(backButton);

        noOfFramesInput.setBounds (230, 60, 100, 25);
        stringInput.setBounds (230, 120, 200, 25);
        noOfFramesLabel.setBounds (80, 60, 100, 25);
        stringLabel.setBounds (80, 120, 100, 25);
        LRUButton.setBounds (120, 175, 100, 25);
        FIFO.setBounds (245, 175, 100, 25);
        backButton.setBounds(365,175,100,25);
        pageFaultHeading.setBounds (130, 210, 390, 35);

//        mainPanel.add(displaySchedulingProcesses(schedulingQueue));

        mainFrame.add(mainPanel);
        mainFrame.setSize(873,603);
        mainFrame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new SelectionFrame();
            }
        });


        LRUButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

            if(isFormValid()) {


                int faults=0;
                try {
                    s = new Socket("127.0.0.1", 8005);
                    out = new ObjectOutputStream(s.getOutputStream());
                    Pages pg=new Pages(Integer.parseInt(noOfFramesInput.getText().trim()),stringInput.getText().split("\\s+"),false);
                    out.writeObject(pg);
                    ObjectInputStream in =new ObjectInputStream(s.getInputStream());
                    Pages finalPage= (Pages) in.readObject();
                    framesRecord=finalPage.framesRecord;
                    faults=finalPage.faults;


                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(mainFrame,"Unable to Connect to Server");
                }

                pageFaultHeading.setText("The number of page faults using LRU are: " + faults);
                pageFaultHeading.setVisible(true);
                System.out.println("The number of page faults using LRU are: " + faults);
                mainPanel.add(displayLRUFrames());
                mainPanel.repaint();
                mainPanel.validate();


            }
            }

        });

        FIFO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isFormValid()) {
                    int faults=0;
                    try {
                        s = new Socket("127.0.0.1", 8005);
                        out = new ObjectOutputStream(s.getOutputStream());
                        Pages pg=new Pages(Integer.parseInt(noOfFramesInput.getText().trim()),stringInput.getText().split("\\s+"),true);
                        out.writeObject(pg);
                        ObjectInputStream in =new ObjectInputStream(s.getInputStream());
                        Pages finalPage= (Pages) in.readObject();
                        framesRecord=finalPage.framesRecord;
                        faults=finalPage.faults;


                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(mainFrame,"Unable to Connect to Server");
                    }

                    pageFaultHeading.setText("The number of page faults using FIFO are: " + faults);
                    pageFaultHeading.setVisible(true);
                    System.out.println("The number of page faults using FIFO are: " + faults);
                    mainPanel.add(displayFIFOFrames());
                    mainPanel.repaint();
                    mainPanel.validate();
//                    performFIFO();

                }
            }
        });
    }



    private boolean isFormValid() {

        try{
            Integer.parseInt(noOfFramesInput.getText().trim());

            String[] inputStrings = stringInput.getText().split("\\s+");
            int[] inputNumbers = new int[inputStrings.length];

            for (int l = 0; l < inputStrings.length; l++) {
                inputNumbers[l] = Integer.parseInt(inputStrings[l]);
            }
            return true;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(mainFrame,"Input Data is not in Numerical Form");
            return false;
        }


    }

    private JScrollPane displayFIFOFrames() {

        for (String a:framesRecord){
            System.out.println(a);
        }

        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(20,250,820,310);
        model.addColumn("Ref String");

        for (int i=0;i<Integer.parseInt(noOfFramesInput.getText().trim());i++){
            model.addColumn("Frame "+i);
        }
        model.addColumn("Fault No");


        for (String eachFrame:framesRecord) {
//            JOptionPane.showMessageDialog(null,eachFrame);
            Vector<String> row = new Vector<>();
            String temp="";
            for(int j=4;j<eachFrame.length();j++) {
                if(String.valueOf(eachFrame.charAt(j)).equals(",")){
                    row.add(temp);
                    temp="";
                }
                else{
                    temp+=String.valueOf(eachFrame.charAt(j));
                }
            }
            model.addRow(row);

        }


        return scrollBar;
    }

    private JScrollPane displayLRUFrames() {

        for (String a:framesRecord){
            System.out.println(a);
        }

        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(20,250,820,310);
        model.addColumn("Ref String");

        for (int i=0;i<Integer.parseInt(noOfFramesInput.getText().trim());i++){
            model.addColumn("Frame "+i);
        }
        model.addColumn("Fault No");


        for (String eachFrame:framesRecord) {
//            JOptionPane.showMessageDialog(null,eachFrame);
            Vector<String> row = new Vector<>();
            String temp="";
            for(int j=4;j<eachFrame.length();j++) {
                if(String.valueOf(eachFrame.charAt(j)).equals(",")){
                    row.add(temp);
                    temp="";
                }
                else{
                    temp+=String.valueOf(eachFrame.charAt(j));
                }
            }
            model.addRow(row);

        }


        return scrollBar;
    }


    public static void main(String[] args) {
        new MemoryManagmentFrame();
    }

}
