package example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import cartago.*;
import cartago.tools.GUIArtifact;

public class Counter extends GUIArtifact {
    
    private MyFrame frame;

    public void setup() {
        frame = new MyFrame();
        linkActionEventToOp(frame.init, "init");
        linkActionEventToOp(frame.kill_process, "kill_process");
        linkActionEventToOp(frame.revive, "revive");
        linkActionEventToOp(frame.who_perceived, "who_perceived");
        linkWindowClosingEventToOp(frame, "closed");
        defineObsProperty("killed", getValue());
        defineObsProperty("perceived", getValue());
        frame.setVisible(true);
    }

    @OPERATION
    void init(ActionEvent ev){
        signal("init");
    }

    @OPERATION 
    void revive(ActionEvent ev) throws InterruptedException {
        signal("revive");
        Thread.sleep(2000);
        getObsProperty("killed").updateValue(0);
    }

    @OPERATION
    void who_perceived(ActionEvent ev){
        getObsProperty("perceived").updateValue(getValue());
        signal("perceived");
    }

    @OPERATION 
    void kill_process(ActionEvent ev){
        getObsProperty("killed").updateValue(getValue());
        signal("killed");
    }

    @OPERATION 
    void closed(WindowEvent ev) {
        signal("closed");
    }

    @OPERATION 
    void setValue(int value) {
        frame.setText("" + value);
        getObsProperty("killed").updateValue(getValue());
    }

    @OPERATION
    void setTableMessage(int processNumber, String message) {
        DefaultTableModel model = (DefaultTableModel) frame.table.getModel();
        if (processNumber >= 1 && processNumber <= model.getRowCount()) {
            model.setValueAt(message, processNumber - 1, 1);  // Update the "Mensagem" column
        } else {
            System.err.println("Invalid process number: " + processNumber);
        }
    }

    private int getValue() {
        try {
            return Integer.parseInt(frame.getText());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input, setting value to 0.");
            return 0;
        }
    }

    class MyFrame extends JFrame {
        private JButton init;
        private JButton kill_process;
        private JButton revive;
        private JButton who_perceived;
        private JTextField text;
        private JTable table;

        public MyFrame(){
            setTitle("Simple GUI");
            setSize(300,200);
            JPanel panel = new JPanel();
            setContentPane(panel);

            init = new JButton("Iniciar");
            init.setSize(80, 50);

            kill_process = new JButton("Matar Processo");
            kill_process.setSize(80, 50);

            revive = new JButton("Reviver processo");
            revive.setSize(80, 50);

            who_perceived = new JButton("Quem vai percerber");
            who_perceived.setSize(80, 50);

            text = new JTextField(10);
            text.setText("0");
            text.setEditable(true);

            String[] columnNames = {"Processo", "Mensagem"};
            Object[][] data = {
                {1, ""},
                {2, ""},
                {3, ""},
                {4, ""},
                {5, ""},
                {6, ""}
            };
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            panel.add(text);
            panel.add(init);
            panel.add(kill_process);
            panel.add(revive);
            panel.add(who_perceived);
            panel.add(scrollPane);
        }

        public String getText(){
            return text.getText();
        }

        public void setText(String s){
            text.setText(s);
        }
    }
}
