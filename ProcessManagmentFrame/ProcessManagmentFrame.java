package ProcessManagmentFrame;

import MenuFrames.MenuFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ProcessManagmentFrame  {

    private JButton createProcessButton;
    private JFrame frame;
    private JPanel mainPanel;
    private JButton destroyProcessButton;
    private JButton suspendProcessButton;
    private JButton ProcessScheduling;
    private JButton resumeProcessButton;
    private JButton blockProcessButton;
    private JButton changeProcessPriorityButton;
    private JButton dispatchProcessButton;
    private JButton wakeupProcessButton;
    private JButton displayProcessButton;

    private JLabel heading;

    // For creating processes
    private JLabel numberOfProcesses;
    private JTextField numberOfProcessInput;
    private JButton create;
    private JButton backButtonMenu;
    private JLabel processesArrivalTime;
    private JLabel processesBurstTime;
    private JLabel processesPriority;
    private JTextField[] processesArrivalTimeInput;
    private JTextField[] processesBurstTimeInput;
    private JTextField[] processesPriorityInput;
    private int processesInput;
    private int i = 0;
    private int ids = 1;
    private int idShow = 1;
    private JButton next;
    ArrayList<Process> processes = new ArrayList<>();
    ArrayList<Process> schedulingQueue = new ArrayList<>();
    private Process runningProcess;
    ///For Suspend Process

    private JLabel suspendInputLabel;
    private JTextField suspendIDInput;
    private JButton suspend;
    private JButton back;
    private JLabel idLabel;

    ////For Priority Panel

    JLabel processIDLabelPriority;
    JLabel priorityLabelPriority ;
    JTextField processIDInputPriority;
    JTextField priorityInputPriority;
    JButton changePriority;

    /// For Scheduling Panel

    private JButton fcfsButton;
    private JButton sjfButton;
    private JButton backButton;

    // Process class with ID
    private static class Process {
        private int id;
        private final int arrivalTime;
        private final int burstTime;
        private String status;
        private int priority;
        private int ct;
        private int wt;
        private int tat;
        private int remainingTime;

        public Process(int id, int arrivalTime, int burstTime,int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.status="Ready";
            this.priority=priority;
            this.ct=-1;
            this.wt=-1;
            this.tat=-1;
            this.remainingTime=burstTime;

        }
    }

    public ProcessManagmentFrame () {
        initiallize();
        initGUI();
    }

    public void initGUI() {
        mainPanel = addContent();
        frame = new JFrame("Process Management Frame");
        frame.setSize(784, 449);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    JPanel createProcessPanel() {
        JPanel temp = new JPanel(null);
        numberOfProcesses = new JLabel("Enter Number of Processes");
        numberOfProcessInput = new JTextField(5);
        create = new JButton("Create");

        temp.add(numberOfProcesses);
        temp.add(numberOfProcessInput);
        temp.add(create);

        numberOfProcesses.setBounds(130, 75, 195, 45);
        numberOfProcessInput.setBounds(335, 85, 185, 30);
        create.setBounds(250, 130, 150, 35);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isNumber()){
                    JOptionPane.showMessageDialog(temp,"Please Number of Processes in Number","Wrong Input",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                processesInput = Integer.parseInt(numberOfProcessInput.getText());
                processesArrivalTimeInput = new JTextField[processesInput];
                processesBurstTimeInput = new JTextField[processesInput];
                processesPriorityInput = new JTextField[processesInput];
                idShow=ids;
                frame.remove(temp);
                frame.add(inputProcesses());
                frame.revalidate();
                frame.repaint();
            }

            private boolean isNumber() {
                try{
                    Integer.parseInt(numberOfProcessInput.getText());
                    return true;
                }
                catch (Exception e){
                    return false;
                }
            }
        });

        return temp;
    }JPanel inputProcesses() {
        JPanel temp = new JPanel(null);
        processesArrivalTime = new JLabel("Enter Arrival Time of Process " + (i + 1) + " :");
        temp.add(processesArrivalTime);
        processesBurstTime = new JLabel("Enter Burst Time of Process " + (i + 1) + " :");
        temp.add(processesBurstTime);
        processesPriority=new JLabel("Enter Priority of Process " + (i + 1) );
        temp.add(processesPriority);
        idLabel=new JLabel("Process Id  : " +idShow++);
        temp.add(idLabel);

        processesArrivalTimeInput[i] = new JTextField();
        processesBurstTimeInput[i] = new JTextField();
        processesPriorityInput[i]=new JTextField();

        temp.add(processesArrivalTimeInput[i]);
        temp.add(processesBurstTimeInput[i]);
        temp.add(processesPriorityInput[i]);

        if (i == processesInput - 1) {
            next = new JButton("Submit");
        } else {
            next = new JButton("Next");
        }

        idLabel.setBounds(200,70,205,30);
        processesArrivalTime.setBounds(155, 100, 205, 30);
        next.setBounds(280, 240, 135, 30);
        processesBurstTime.setBounds(155, 145, 205, 30);
        processesPriority.setBounds(155, 190, 205, 30);

        processesArrivalTimeInput[i].setBounds(375, 100, 130, 30);
        processesBurstTimeInput[i].setBounds(375, 145, 130, 30);
        processesPriorityInput[i].setBounds(375, 190, 130, 30);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isNumber()){
                    JOptionPane.showMessageDialog(temp,"Please Arrival/Burst Time in Number Format","Wrong Input",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(processesBurstTimeInput[i].getText().equals("0")){
                    JOptionPane.showMessageDialog(temp,"Burst Time Cannot be Zero","Wrong Input",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                i++;
                if (i < processesInput) {
                    frame.remove(temp);
                    frame.add(inputProcesses());
                    frame.revalidate();
                    frame.repaint();
                }

                if (e.getActionCommand().equals("Submit")) {
                    i=0;
                    frame.remove(temp);
                    frame.add(addContent());
                    frame.revalidate();
                    frame.repaint();
                    createPCB();
                }
            }

            private boolean isNumber() {
                try{
                    Integer.parseInt(processesArrivalTimeInput[i].getText());
                    Integer.parseInt(processesBurstTimeInput[i].getText());
                    return true;
                }
                catch (Exception e){
                    return false;
                }
            }
        });

        temp.add(next);
        return temp;
    }
    JPanel addContent(){
        JPanel temp=new JPanel(null);

        createProcessButton = new JButton("Create a process");
        destroyProcessButton = new JButton("Destroy a process");
        suspendProcessButton = new JButton("Suspend a Process");
        ProcessScheduling = new JButton("Process Scheduling");
        resumeProcessButton = new JButton("Resume a Process");
        blockProcessButton = new JButton("Block a Process");
        changeProcessPriorityButton = new JButton("Change process Priority");
        dispatchProcessButton = new JButton("Dispatch a Process");
        wakeupProcessButton = new JButton("Wakeup a Process");
        displayProcessButton=new JButton("Display Processes");
        backButtonMenu=new JButton("Main Menu");

        heading = new JLabel("Operating System");
        Font boldFont = new Font(heading.getFont().getName(), Font.BOLD, 28);
        heading.setFont(boldFont);
        heading.setHorizontalAlignment(SwingConstants.CENTER);


        // Add components to the temp
        temp.add(createProcessButton);
        temp.add(destroyProcessButton);
        temp.add(suspendProcessButton);
        temp.add(ProcessScheduling);
        temp.add(resumeProcessButton);
        temp.add(blockProcessButton);
        temp.add(changeProcessPriorityButton);
        temp.add(dispatchProcessButton);
        temp.add(wakeupProcessButton);
        temp.add(displayProcessButton);
        temp.add(backButtonMenu);
        temp.add(heading);


        createProcessButton.setBounds(80, 100, 170, 45);
        destroyProcessButton.setBounds(300, 100, 170, 45);
        suspendProcessButton.setBounds(80, 170, 170, 45);
        ProcessScheduling.setBounds(225, 310, 250, 50);
        resumeProcessButton.setBounds(510, 100, 170, 45);
        blockProcessButton.setBounds(300, 170, 170, 45);
        changeProcessPriorityButton.setBounds(515, 170, 170, 45);
        dispatchProcessButton.setBounds(80 ,240, 170, 45);
        wakeupProcessButton.setBounds(300, 240, 170, 45);
        displayProcessButton.setBounds(515,240,170,45);
        heading.setBounds(245, 20, 305, 50);
        backButtonMenu.setBounds(490,310,150,50);

        backButtonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MenuFrame();
            }
        });

        createProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                frame.remove(mainPanel);
                frame.getContentPane().removeAll();
                frame.add(createProcessPanel());
                frame.revalidate();
                frame.repaint();
            }
        });

        destroyProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                frame.remove(temp);
                frame.getContentPane().removeAll();
                frame.add(suspendProcessPanel("Destroy"));
                frame.setSize(907,632);
                frame.revalidate();
                frame.repaint();
            }
        });

        resumeProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(suspendProcessPanel("Resume"));
                frame.setSize(907,632);
                frame.revalidate();
                frame.repaint();
            }
        });
        dispatchProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(suspendProcessPanel("Running"));
                frame.setSize(907,632);
                frame.revalidate();
                frame.repaint();
            }
        });

        wakeupProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                frame.remove(temp);
                frame.getContentPane().removeAll();
                frame.add(suspendProcessPanel("WakeUp"));
                frame.setSize(907,632);
                frame.revalidate();
                frame.repaint();
            }
        });

        blockProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                frame.remove(temp);
                frame.getContentPane().removeAll();
                frame.add(suspendProcessPanel("Block"));
                frame.setSize(907,632);
                frame.revalidate();
                frame.repaint();
            }
        });

        suspendProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                frame.remove(temp);
                frame.getContentPane().removeAll();
                frame.add(suspendProcessPanel("Suspend"));
                frame.revalidate();
                frame.setSize(907,632);
                frame.repaint();

            }
        });

        displayProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                frame.remove(temp);
                frame.getContentPane().removeAll();
                frame.add(displayAllProcesses());
                frame.revalidate();
                frame.setSize(907,632);
                frame.repaint();
            }


        });


        ProcessScheduling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schedulingQueue=new ArrayList<>();
                createQueue();
                frame.getContentPane().removeAll();
                frame.add(schedulingPanel());
                frame.revalidate();
                frame.setSize(907,632);
                frame.repaint();

            }
        });

        changeProcessPriorityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(changePriorityPanel());
                frame.revalidate();
                frame.setSize(907,632);
                frame.repaint();
            }
        });
        return temp;
    }

    private JPanel changePriorityPanel() {

        JPanel temp=new JPanel(null);
        backButton = new JButton ("Back");
        changePriority = new JButton ("Change Priority");
        processIDLabelPriority = new JLabel("Enter Process ID : ");
        priorityLabelPriority = new JLabel("Enter Priority to Change : ");
        processIDInputPriority=new JTextField();
        priorityInputPriority=new JTextField();


        changePriority.setBounds (365, 175, 125, 30);
        backButton.setBounds(215, 175, 125, 30);
        processIDLabelPriority.setBounds(220, 80, 200, 30);
        priorityLabelPriority.setBounds(220, 110, 200, 30);
        processIDInputPriority.setBounds(440, 80, 130, 30);
        priorityInputPriority.setBounds(440, 110, 130, 30);

        temp.add(displayPriority());
        temp.add(backButton);
        temp.add(changePriority);
        temp.add(priorityLabelPriority);
        temp.add(processIDLabelPriority);
        temp.add(priorityInputPriority);
        temp.add(processIDInputPriority);



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(addContent());
                frame.revalidate();
                frame.repaint();
            }
        });

        changePriority.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!checkNumber()){
                    JOptionPane.showMessageDialog(temp,"Please Enter Valid Process ID / Priority  ","Input Not Valid",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for(Process process : processes){
                    if(process.id==Integer.parseInt(processIDInputPriority.getText())) {
                        process.priority = Integer.parseInt(priorityInputPriority.getText());
                        JOptionPane.showMessageDialog(temp, "Process Priority Changed Successfully.", "Priority Changed", JOptionPane.INFORMATION_MESSAGE);
                        temp.add(displayPriority());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(temp,"Process Priority Not Changed Successfully . No Process Found","Process Not FOund",JOptionPane.ERROR_MESSAGE);
            }

            private boolean checkNumber() {
                try{
                    Integer.parseInt(processIDInputPriority.getText());
                    Integer.parseInt(priorityInputPriority.getText());

                    return true;
                }
                catch (Exception e){
                    return false;
                }
            }
        });

        return temp;

    }

    private JScrollPane displayPriority() {


        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(100,250,750,300);
        model.addColumn("Process ID");
        model.addColumn("Arrival Time");
        model.addColumn("Burst Time");
        model.addColumn("Process Status");
        model.addColumn("Process Priority");



        for (Process process:processes) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(process.id));
            row.add(String.valueOf(process.arrivalTime));
            row.add(String.valueOf(process.burstTime));
            row.add(String.valueOf(process.status));
            row.add(String.valueOf(process.priority));
            model.addRow(row);
        }

        return scrollBar;
    }

    private JPanel schedulingPanel() {
        JPanel temp=new JPanel(null);

        fcfsButton = new JButton ("FCFS");
        sjfButton = new JButton ("SJF");
        backButton = new JButton ("Back");

        temp.add (fcfsButton);

        temp.add (sjfButton);
        temp.add (backButton);

        fcfsButton.setBounds (110, 60, 120, 30);
        sjfButton.setBounds (500, 60, 120, 30);
        backButton.setBounds (255, 100, 225, 30);

        temp.add(displaySchedulingProcesses(schedulingQueue));


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(addContent());
                frame.revalidate();
                frame.repaint();
            }
        });

        fcfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Process> t = new ArrayList<>();
                t=performFCFS();
                if(t==null){
                    JOptionPane.showMessageDialog(mainPanel,"No Proceses To Schdule","Empty Processes",JOptionPane.ERROR_MESSAGE);
                }else {
                    temp.add(displaySchedulingProcesses(t));
                }
            }
        });
        sjfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result=JOptionPane.showConfirmDialog(null, "Do you want to non-Permitive Scheduling ?", "Permitive or Not", JOptionPane.YES_NO_OPTION);
                ArrayList<Process> t = new ArrayList<>();
                if (result == JOptionPane.YES_OPTION) {
                    t=performSJF();
                } else {
                    t=performSJFPreemptive();
                }
                if(t==null){
                    JOptionPane.showMessageDialog(mainPanel,"No Proceses To Schdule","Empty Processes",JOptionPane.ERROR_MESSAGE);
                }else {
                    temp.add(displaySchedulingProcesses(t));
                }
            }
        });


        return temp;
    }

    private ArrayList<Process> performSJFPreemptive() {

        ArrayList<Process> temp=schedulingQueue;
        temp.sort(Comparator.comparingInt(p -> p.arrivalTime));

        boolean[] completed = new boolean[temp.size()];
        int currentTime = 0;
        int completedCount = 0;

        while (completedCount != temp.size()) {
            int shortest = -1;



            int shortestBurst = Integer.MAX_VALUE;

            for (int i = 0; i < temp.size(); i++) {
                if (!completed[i] && temp.get(i).arrivalTime <= currentTime && temp.get(i).remainingTime < shortestBurst) {
                    shortestBurst = temp.get(i).remainingTime;
                    shortest = i;
                }
            }

            if (shortest == -1) {
                currentTime++;
            } else {
                temp.get(shortest).remainingTime--;

                if (temp.get(shortest).remainingTime == 0) {
                    temp.get(shortest).ct = currentTime + 1;
                    temp.get(shortest).tat = temp.get(shortest).ct - temp.get(shortest).arrivalTime;
                    temp.get(shortest).wt = temp.get(shortest).tat - temp.get(shortest).burstTime;

                    completed[shortest] = true;
                    completedCount++;
                }

                currentTime++;
            }
        }
        temp.sort(Comparator.comparingInt(p -> p.id));

        return temp;
    }



    private ArrayList<Process> performSJF() {
        ArrayList<Process> temp=schedulingQueue;

        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.size() - i - 1; j++) {
                if (processes.get(j).burstTime > processes.get(j + 1).burstTime) {
                    // Swap processes
                    Process tem = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, tem);
                }
            }
        }

        int sum = 0;
        for (Process process : processes) {
            if (sum < process.arrivalTime) {
                sum = process.arrivalTime;
            }
            sum += process.burstTime;
            process.ct = sum;
            process.tat = process.ct - process.arrivalTime;
            process.wt = process.tat - process.burstTime;
        }

        return temp;
    }

    private ArrayList<Process> performFCFS() {

        ArrayList<Process> temp=schedulingQueue;

        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.size() - i - 1; j++) {
                if (temp.get(j).arrivalTime > temp.get(j + 1).arrivalTime) {
                    // Swap processes
                    Process tem = temp.get(j);
                    temp.set(j, temp.get(j + 1));
                    temp.set(j + 1, tem);
                }
            }
        }

        int sum = 0;
        for (Process process : temp) {
            if (sum < process.arrivalTime) {
                sum = process.arrivalTime;
            }
            sum += process.burstTime;
            process.ct = sum;
            process.tat = process.ct - process.arrivalTime;
            process.wt = process.tat - process.burstTime;
        }


        return temp;
    }

    private JScrollPane displaySchedulingProcesses(ArrayList<Process> t) {


        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(100,250,750,300);
        model.addColumn("Process ID");
        model.addColumn("Arrival Time");
        model.addColumn("Burst Time");
        model.addColumn("Priority");
        model.addColumn("Waiting Time");
        model.addColumn("TAT");
        model.addColumn("CT");

        for (Process process:t) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(process.id));
            row.add(String.valueOf(process.arrivalTime));
            row.add(String.valueOf(process.burstTime));
            row.add(String.valueOf(process.priority));
            if(process.wt!=-1 && process.tat!=-1 && process.ct!=-1) {
                row.add(String.valueOf(process.wt));
                row.add(String.valueOf(process.tat));
                row.add(String.valueOf(process.ct));
            }
            model.addRow(row);
        }


        return scrollBar;
    }

    private void createQueue() {
        for(Process process : processes){
            if(process.status.equals("Running")) {
                schedulingQueue.add(process);
            }
        }
    }

    private JPanel displayAllProcesses() {

        JPanel temp=new JPanel(new BorderLayout(2,2));
        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);
        scrollBar.setBounds(100,250,750,300);
        model.addColumn("Process ID");
        model.addColumn("Arrival Time");
        model.addColumn("Burst Time");
        model.addColumn("Process Status");
        back=new JButton("Back");
        temp.add(back,BorderLayout.SOUTH);

        for (Process process:processes) {
            Vector<String> row = new Vector<>();
                row.add(String.valueOf(process.id));
                row.add(String.valueOf(process.arrivalTime));
                row.add(String.valueOf(process.burstTime));
                row.add(String.valueOf(process.status));
                model.addRow(row);
        }

        temp.add(scrollBar,BorderLayout.CENTER);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(addContent());
                frame.revalidate();
                frame.repaint();
            }
        });
        return temp;
    }

    private JScrollPane displayProcesses(String toDisplay) {
        if(toDisplay.equals("Resume")){
            toDisplay="Suspend";
        }
        else if(toDisplay.equals("Suspend") || toDisplay.equals("Block") ){
            return  displayAllProcessesTwoArgs("Ready","Running");
        }
        else if(toDisplay.equals("Running")){
            toDisplay="Ready";
        }
        else if(toDisplay.equals("Destroy")){
            return displayAllProcessesDestroy();
        }
        else if(toDisplay.equals("WakeUp")){
            toDisplay="Block";
        }


        JPanel temp=new JPanel(null);

        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(100,250,750,300);
        model.addColumn("Process ID");
        model.addColumn("Arrival Time");
        model.addColumn("Burst Time");
        model.addColumn("Process Status");



        for (Process process:processes) {
            Vector<String> row = new Vector<>();
            if(process.status.equals(toDisplay)) {
                row.add(String.valueOf(process.id));
                row.add(String.valueOf(process.arrivalTime));
                row.add(String.valueOf(process.burstTime));
                row.add(String.valueOf(process.status));
                model.addRow(row);
            }
        }


        return scrollBar;
    }

    private JScrollPane displayAllProcessesTwoArgs(String toDisplayOne,String toDisplayTwo) {

        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(100,250,750,300);
        model.addColumn("Process ID");
        model.addColumn("Arrival Time");
        model.addColumn("Burst Time");
        model.addColumn("Process Status");



        for (Process process:processes) {
            Vector<String> row = new Vector<>();
            if(process.status.equals(toDisplayOne) || process.status.equals(toDisplayTwo) ) {
                row.add(String.valueOf(process.id));
                row.add(String.valueOf(process.arrivalTime));
                row.add(String.valueOf(process.burstTime));
                row.add(String.valueOf(process.status));
                model.addRow(row);
            }
        }


        return scrollBar;
    }

    private JScrollPane displayAllProcessesDestroy() {


        DefaultTableModel model=new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane scrollBar=new JScrollPane(table);

        scrollBar.setBounds(100,250,750,300);
        model.addColumn("Process ID");
        model.addColumn("Arrival Time");
        model.addColumn("Burst Time");
        model.addColumn("Process Status");



        for (Process process:processes) {
            Vector<String> row = new Vector<>();
                row.add(String.valueOf(process.id));
                row.add(String.valueOf(process.arrivalTime));
                row.add(String.valueOf(process.burstTime));
                row.add(String.valueOf(process.status));
                model.addRow(row);
        }


        return scrollBar;
    }
    ////Suspend Procss
    private JPanel suspendProcessPanel(String stateChange) {
        JPanel temp=new JPanel(null);

        suspendInputLabel = new JLabel ("Enter Process Id To "+stateChange+" : ");
        suspendIDInput = new JTextField (5);
        suspend = new JButton (stateChange);
        back=new JButton("Back");
//        JScrollPane scrollBar=displayProcesses(stateChange);

        temp.add (suspendInputLabel);
        temp.add (suspendIDInput);
        temp.add (suspend);
        temp.add(back);
        temp.add(displayProcesses(stateChange));


        suspendInputLabel.setBounds (200, 110, 200, 35);
        suspendIDInput.setBounds (430, 115, 100, 25);
        suspend.setBounds (365, 175, 125, 30);
        back.setBounds(215, 175, 125, 30);

        suspend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!checkNumber()){
                    JOptionPane.showMessageDialog(temp,"Please Enter Process ID only to "+stateChange,stateChange+" Process",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for(Process process : processes){
                    if(process.id==Integer.parseInt(suspendIDInput.getText())) {
                        if(stateChange.equals("Destroy")) {
                            processes.remove(process);
                            JOptionPane.showMessageDialog(temp, "Process " + stateChange + " Successfully.", stateChange + " Process", JOptionPane.INFORMATION_MESSAGE);
                            temp.add(displayProcesses(stateChange));
                            return;
                        }
                        else if(stateChange.equals("Resume") && !process.status.equals("Suspend")){
                            JOptionPane.showMessageDialog(temp,"Process "+stateChange+" Not Successfully . No Process Found",stateChange+" Process",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        else if(process.status.equals(stateChange)){
                            JOptionPane.showMessageDialog(temp,"Processs "+stateChange+" Not Successfully . Process Already "+stateChange,stateChange+" Process",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        else if (stateChange.equals("WakeUp") && !process.status.equals("Block")) {
                            JOptionPane.showMessageDialog(temp,"Processss "+stateChange+" Not Successfully . No Process Found",stateChange+" Process",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        else if(stateChange.equals("Resume") || stateChange.equals("WakeUp")){
                            process.status = "Ready";
                        }
                        else if(stateChange.equals("Running") && !process.status.equals("Ready")){
                            JOptionPane.showMessageDialog(temp,"Processss "+stateChange+" Not Successfully . Process is not in Ready State",stateChange+" Process",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        else {
                                process.status = stateChange;
                        }
                            JOptionPane.showMessageDialog(temp, "Process " + stateChange + " Successfully.", stateChange + " Process", JOptionPane.INFORMATION_MESSAGE);
                            temp.add(displayProcesses(stateChange));
//                        frame.remove(temp);
//                        frame.add(addContent());
//                        frame.revalidate();
//                        frame.repaint();
                            return;
                    }
                    print();
                }
                    JOptionPane.showMessageDialog(temp,"Process "+stateChange+" Not Successfully . No Process Found",stateChange+" Process",JOptionPane.ERROR_MESSAGE);
            }

            private boolean checkNumber() {
                try{
                    Integer.parseInt(suspendIDInput.getText());
                    return true;
                }
                catch (Exception e){
                    return false;
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(temp);
                frame.add(addContent());
                frame.revalidate();
                frame.repaint();
            }
        });
        return temp;

    }
    void createPCB(){
//        processes = new ArrayList<>();
        for (int j = 0; j < processesInput; j++) {
            processes.add(new Process(ids++, Integer.parseInt(processesArrivalTimeInput[j].getText()),
                    Integer.parseInt(processesBurstTimeInput[j].getText()),Integer.parseInt(processesPriorityInput[j].getText())));
        }

    }
    void initiallize(){
//        for (int j = 0; j < 6; j++) {
//            processes.add(new Process(ids++, j + 2,
//                    j + 4,0));
//        }
        processes.add(new Process(ids++, 1,
                1,0));
                processes.add(new Process(ids++, 3,
                    2,0));
                        processes.add(new Process(ids++, 2,
                    1,0));
                                processes.add(new Process(ids++, 0,
                    4,0));

//        processes.add(new Process(ids++, 0,
//                1,0));
//        processes.add(new Process(ids++, 0,
//                2,0));
//        processes.add(new Process(ids++, 0,
//                1,0));
//        processes.add(new Process(ids++, 0,
//                4,0));

    }
    void print(){
        for (Process process : processes) {
            System.out.println(process.id + "Arrival : " + process.arrivalTime+" Burst Time " + process.burstTime+ " Status " + process.status );
        }
    }
    public static void main(String[] args) {
        new ProcessManagmentFrame ();
    }
}
