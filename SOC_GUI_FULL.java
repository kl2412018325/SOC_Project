import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SOC_GUI_FULL extends JFrame {

    LinkedList<AnalystInfo> analystList = new LinkedList<>();
    Queue<AnalystInfo> internalQ = new LinkedList<>();
    Queue<AnalystInfo> externalQ = new LinkedList<>();
    Queue<AnalystInfo> criticalQ = new LinkedList<>();
    Stack<AnalystInfo> stack = new Stack<>();

    JTextArea area;

    public SOC_GUI_FULL() {
        setTitle("SOC Incident Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JButton load = new JButton("Load File");
        JButton assign = new JButton("Assign Queues");
        JButton showQ = new JButton("Show Queues");
        JButton process = new JButton("Process");
        JButton stackBtn = new JButton("Show Stack");
        JButton reset = new JButton("Reset");

        panel.add(load);
        panel.add(assign);
        panel.add(showQ);
        panel.add(process);
        panel.add(stackBtn);
        panel.add(reset);

        add(panel, BorderLayout.SOUTH);

        load.addActionListener(e -> loadFile());
        assign.addActionListener(e -> assignQueues());
        showQ.addActionListener(e -> showQueues());
        process.addActionListener(e -> processQueues());
        stackBtn.addActionListener(e -> showStack());
        reset.addActionListener(e -> resetAll());
    }

    // ================= LOAD =================
    void loadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("cyber_incidents.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split("\\|");

                IncidentInfo inc = new IncidentInfo(
                        d[3], d[4], Integer.parseInt(d[5]),
                        d[6], Integer.parseInt(d[7]),
                        Double.parseDouble(d[8])
                );

                AnalystInfo a = find(d[0]);

                if (a == null) {
                    a = new AnalystInfo(d[0], d[1], d[2]);
                    analystList.add(a);
                }

                a.addIncident(inc);
            }

            area.setText("File Loaded Successfully!\n");

        } catch (Exception e) {
            area.setText("Error loading file.\n");
        }
    }

    AnalystInfo find(String id) {
        for (AnalystInfo a : analystList)
            if (a.id.equals(id)) return a;
        return null;
    }

    // ================= ASSIGN =================
    void assignQueues() {
        int toggle = 0;

        for (AnalystInfo a : analystList) {
            if (a.incidents.size() <= 3) {
                if (toggle % 2 == 0) internalQ.add(a);
                else externalQ.add(a);
                toggle++;
            } else {
                criticalQ.add(a);
            }
        }

        area.setText("Queues Assigned!\n");
    }

    // ================= SHOW QUEUES =================
    void showQueues() {
        area.setText("=== INTERNAL QUEUE ===\n");
        for (AnalystInfo a : internalQ) area.append(a.fullDisplay());

        area.append("=== EXTERNAL QUEUE ===\n");
        for (AnalystInfo a : externalQ) area.append(a.fullDisplay());

        area.append("=== CRITICAL QUEUE ===\n");
        for (AnalystInfo a : criticalQ) area.append(a.fullDisplay());
    }

    // ================= PROCESS =================
    void processQueues() {
        while (!internalQ.isEmpty() || !externalQ.isEmpty() || !criticalQ.isEmpty()) {
            process(internalQ);
            process(externalQ);
            process(criticalQ);
        }

        area.setText("Processing Completed!\n");
    }

    void process(Queue<AnalystInfo> q) {
        int count = 0;
        while (!q.isEmpty() && count < 5) {
            stack.push(q.poll());
            count++;
        }
    }

    // ================= STACK =================
    void showStack() {
        area.setText("=== RESOLVED STACK ===\n");

        Stack<AnalystInfo> temp = (Stack<AnalystInfo>) stack.clone();

        while (!temp.isEmpty()) {
            area.append(temp.pop().fullDisplay());
        }
    }

    // ================= RESET =================
    void resetAll() {
        analystList.clear();
        internalQ.clear();
        externalQ.clear();
        criticalQ.clear();
        stack.clear();
        area.setText("System Reset\n");
    }

    public static void main(String[] args) {
        new SOC_GUI_FULL().setVisible(true);
    }
}