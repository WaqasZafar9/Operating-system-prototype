package MemoryManagment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Pagging extends JFrame {

    JFrame DisplayFrame;
    JTextField memorysizetf, pagesizetf,noPagestf,PTEsizetf;

    JLabel memorysize, pagesize,noPages,PTEsize,Title,BytetfnoPages,Pageequal,Memoryequal;

    JButton Calculate, Back;

    Pagging() {
        JPanel p = new JPanel();
        p.setLayout(null);
        Title = new JLabel("Pagging");
        Title.setFont(new Font("Arial",Font.BOLD,25));
        Title.setBounds(300,10,200,30);
        memorysize = new JLabel("Memory Size");
        pagesize = new JLabel("Page Size");
        noPages = new JLabel("Total Number of Pages");
        PTEsize = new JLabel("Per Entry Possible Size");
        Pageequal = new JLabel("");
        Memoryequal = new JLabel("");

        memorysizetf = new JTextField(100);
        pagesizetf = new JTextField(100);
        noPagestf = new JTextField(100);
        BytetfnoPages = new JLabel("");
        PTEsizetf = new JTextField();


        Calculate = new JButton("Calculate");
        Back = new JButton("Back");

        noPagestf.setEditable(false);
        PTEsizetf.setEditable(false);

        noPages.setVisible(false);
        PTEsize.setVisible(false);

        noPagestf.setVisible(false);
        BytetfnoPages.setVisible(false);
        PTEsizetf.setVisible(false);
        Pageequal.setVisible(false);
        Memoryequal.setVisible(false);

        memorysize.setBounds(10, 55, 200, 35);
        memorysizetf.setBounds(210, 60, 120, 25);
        pagesize.setBounds(10, 95, 200, 35);
        pagesizetf.setBounds(210, 100, 120, 25);
        noPages.setBounds(10, 135, 145, 35);
        noPagestf.setBounds(210, 140, 120, 25);
        PTEsize.setBounds(10, 170, 145, 35);
        PTEsizetf.setBounds(210, 175, 120, 25);
        Calculate.setBounds(35, 250, 110, 30);
        Back.setBounds(175, 250, 110, 30);

        Pageequal.setBounds(400, 60, 100, 25);
        Memoryequal.setBounds(400, 100, 100, 25);
        BytetfnoPages.setBounds(400, 140, 120, 25);


        Calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!pagesizetf.getText().isEmpty() && !memorysizetf.getText().isEmpty())
                {
                    try {
                        long memorySize = calculateMemorySize();
                        long pageSize = calculatePageSize();

                        Memoryequal.setText("2^" + (long) Math.ceil(Math.log(memorySize) / Math.log(2)));
                        Pageequal.setText("2^" + (long) Math.ceil(Math.log(pageSize) / Math.log(2)));


                        BytetfnoPages.setText(String.valueOf(memorySize / pageSize));

                        long numberOfPages = memorySize / pageSize;
                        if (numberOfPages != 0) {
                            long PTESize = pageSize / numberOfPages;

                            ;
                            BytetfnoPages.setText(String.valueOf(numberOfPages));

                            noPagestf.setText("2^" + (long) Math.ceil(Math.log(numberOfPages) / Math.log(2)));

                            PTEsizetf.setText("2^" + (long) Math.ceil(Math.log(PTESize) / Math.log(2)));
                        } else {
                            // Handle the case when numberOfPages is zero
                            JOptionPane.showMessageDialog(null, "Number of Pages cannot be zero");
                        }
                        noPages.setVisible(true);
                        PTEsize.setVisible(true);

                        noPagestf.setVisible(true);
                        BytetfnoPages.setVisible(true);
                        PTEsizetf.setVisible(true);
                        Pageequal.setVisible(true);
                        Memoryequal.setVisible(true);

                    }
                    catch (Exception exx){
                        JOptionPane.showMessageDialog(null,"Please Enter Memory Unit");
                    }
                }
                else if(pagesizetf.getText().isEmpty() && memorysizetf.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Enter Page Size or Memory Size");
                }
                else if(pagesizetf.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Enter Page Size");
                }
                else if(memorysizetf.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Enter Memory Size");
                }
                DisplayFrame=new JFrame();

                DisplayFrame.add(DisplayFrames());
                DisplayFrame.setSize(480,720);
                DisplayFrame.setVisible(true);


            }

            private JScrollPane DisplayFrames() {
                DefaultTableModel model=new DefaultTableModel();
                JTable table=new JTable(model);
                JScrollPane scrollBar=new JScrollPane(table);

                scrollBar.setBounds(100,250,750,300);
                model.addColumn("Frame No");
                model.addColumn("Frame Size");
                for (int i=0;i<Integer.parseInt(BytetfnoPages.getText());i++) {
                    Vector<String> row = new Vector<>();
                    row.add(String.valueOf(i));
                    row.add(String.valueOf(pagesizetf.getText()));

                    model.addRow(row);
                }


                return scrollBar;
            }

        });


        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SelectionFrame();
            }
        });

        p.add(Title);
        p.add(memorysize);
        p.add(pagesize);
        p.add(memorysizetf);
        p.add(pagesizetf);
        p.add(Calculate);
        p.add(Back);
        p.add(noPages);
        p.add(noPagestf);
        p.add(BytetfnoPages);
        p.add(PTEsize);
        p.add(PTEsizetf);
        p.add(Pageequal);
        p.add(Memoryequal);

        add(p);
        setSize(669, 368);
        setVisible(true);
    }

    long calculateMemorySize()
    {
        long resultBytes = 0;
        if (!memorysizetf.getText().isEmpty()) {
            int memorySize = Integer.parseInt(memorysizetf.getText().replaceAll("[^0-9]", ""));

            if (memorysizetf.getText().toUpperCase().contains("KB")) {
                resultBytes = changeIntoKB(memorySize);
                System.out.println("Memory Size is equal to " + resultBytes + " bytes of memory.");
            } else if (memorysizetf.getText().toUpperCase().contains("MB")) {
                resultBytes = MchangeIntoMB(memorySize);
                System.out.println("Memory Size is equal to " + resultBytes + " bytes of memory.");
            } else if (memorysizetf.getText().toUpperCase().contains("GB")) {
                resultBytes = (long) changeIntoGB(memorySize);
                System.out.println("Memory Size is equal to " + resultBytes + " bytes of memory.");
            } else if (memorysizetf.getText().toUpperCase().contains("BYTES")) {
                resultBytes = memorySize;
                System.out.println("Memory Size is equal to " + resultBytes + " bytes of memory.");
            }  else {
                System.out.println("Please enter a valid value for Memory Size.");
            }
        }

        if (memorysizetf.getText().isEmpty()) {
            System.out.println("Please enter a valid value for Memory Size.");
        }
        return resultBytes;
    }
    long calculatePageSize()
    {
        long resultBytes = 0;
        if (!pagesizetf.getText().isEmpty()) {
            int pageSize = Integer.parseInt(pagesizetf.getText().replaceAll("[^0-9]", ""));

            if (pagesizetf.getText().toUpperCase().contains("KB")) {
                resultBytes = changeIntoKB(pageSize);
                System.out.println("Page Size is equal to " + resultBytes + " bytes of memory.");
            } else if (pagesizetf.getText().toUpperCase().contains("MB")) {
                resultBytes = MchangeIntoMB(pageSize);
                System.out.println("Page Size is equal to " + resultBytes + " bytes of memory.");
            } else if (pagesizetf.getText().toUpperCase().contains("GB")) {
                resultBytes = (long) changeIntoGB(pageSize);
                System.out.println("Page Size is equal to " + resultBytes + " bytes of memory.");
            } else if (pagesizetf.getText().toUpperCase().contains("BYTES")) {
                resultBytes = pageSize;
                System.out.println("Page Size is equal to " + resultBytes + " bytes of memory.");
            } else if (pagesizetf.getText().toUpperCase().contains("B")) {
                resultBytes = pageSize;
                System.out.println("Page Size is equal to " + resultBytes + " bytes of memory.");
            } else {
                System.out.println("Please enter a valid value for Page Size.");
            }
        }
        if (pagesizetf.getText().isEmpty()) {
            System.out.println("Please enter a valid value for Page Size.");
        }
        return resultBytes;
    }

    int changeIntoKB(int input) {
        return (int) Math.pow(2, 10) * input;
    }

    int MchangeIntoMB(int input) {
        return (int) Math.pow(2, 20) * input;
    }

    long changeIntoGB(int input) {
        return (long) Math.pow(2, 30) * input;
    }

    public static void main(String args[]) {
        new Pagging();
    }
}