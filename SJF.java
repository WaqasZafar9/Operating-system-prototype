import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SJF {

    static class Process {
        int pid;
        int at; // Arrival Time
        int bt; // Burst Time
        int ct; // Completion Time
        int tat; // Turn Around Time
        int wt; // Waiting Time
        int remainingBt; // Remaining Burst Time for preemptive SJF

        public Process(int pid, int at, int bt) {
            this.pid = pid;
            this.at = at;
            this.bt = bt;
            this.remainingBt = bt;
        }
    }

    public static void main(String[] args) {
        try {
            String[] options = {"SJF Preemptive", "SJF Non-Preemptive"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose the scheduling algorithm:",
                    "Scheduling Algorithm Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            ArrayList<Process> processes = getUserInputForProcesses();

            if (choice == 0) {
                calculateTimesSJFPreemptive(processes);
            } else {
                calculateTimesSJFNonPreemptive(processes);
            }

            displayResultsInTable(processes);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
        }
    }

    static ArrayList<Process> getUserInputForProcesses() {
        ArrayList<Process> processes = new ArrayList<>();

        try {
            int n = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of processes:"));

            for (int i = 0; i < n; i++) {
                int pid = i + 1;
                int at = Integer.parseInt(JOptionPane.showInputDialog("Enter Arrival Time for Process " + pid + ":"));
                int bt = Integer.parseInt(JOptionPane.showInputDialog("Enter Burst Time for Process " + pid + ":"));

                processes.add(new Process(pid, at, bt));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
        }

        return processes;
    }

    static void calculateTimesSJFPreemptive(ArrayList<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.at));

        boolean[] completed = new boolean[processes.size()];
        int currentTime = 0;
        int completedCount = 0;

        while (completedCount != processes.size()) {
            int shortest = -1;
            int shortestBurst = Integer.MAX_VALUE;

            for (int i = 0; i < processes.size(); i++) {
                if (!completed[i] && processes.get(i).at <= currentTime && processes.get(i).remainingBt < shortestBurst) {
                    shortestBurst = processes.get(i).remainingBt;
                    shortest = i;
                }
            }

            if (shortest == -1) {
                currentTime++;
            } else {
                processes.get(shortest).remainingBt--;

                if (processes.get(shortest).remainingBt == 0) {
                    processes.get(shortest).ct = currentTime + 1;
                    processes.get(shortest).tat = processes.get(shortest).ct - processes.get(shortest).at;
                    processes.get(shortest).wt = processes.get(shortest).tat - processes.get(shortest).bt;

                    completed[shortest] = true;
                    completedCount++;
                }

                currentTime++;
            }
        }
    }

    static void calculateTimesSJFNonPreemptive(ArrayList<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.at));

        int currentTime = 0;

        for (Process process : processes) {
            if (process.at > currentTime) {
                currentTime = process.at;
            }

            process.ct = currentTime + process.bt;
            process.tat = process.ct - process.at;
            process.wt = process.tat - process.bt;

            currentTime = process.ct;
        }
    }

    static void displayResultsInTable(ArrayList<Process> processes) {
        JFrame frame = new JFrame("SJF Example");

        String[] column = {"ProcessID", "Arrival Time", "Burst Time", "Completion Time", "Turn Around Time", "Waiting Time"};
        DefaultTableModel model = new DefaultTableModel(null, column);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        for (Process p : processes) {
            model.addRow(new Object[]{p.pid, p.at, p.bt, p.ct, p.tat, p.wt});
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                main(null); // Restart the program to get new process details
            }
        });

        JPanel panel = new JPanel();
        panel.add(backButton);

        frame.add(scrollPane);
        frame.add(panel);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setVisible(true);
    }
}