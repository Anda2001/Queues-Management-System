package ro.tuc.pt.GUI;

import javax.swing.*;
import java.util.List;

public class Worker extends SwingWorker<String, String> {

    private final JTextArea textArea;
    private final String data;

    public Worker(String data, JTextArea textArea) {
        this.textArea = textArea;
        this.data = data;
    }

    @Override
    protected void process(List<String> chunks) {

        String txt = chunks.get(chunks.size()-1);
        textArea.setText(txt);
    }
    @Override
    protected String doInBackground() {
        publish(data);
        return data;
    }

}
