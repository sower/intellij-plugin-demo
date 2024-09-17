package me.ylem.intellij.demo.dialog;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * HighlightDialogPanel
 *
 * @since 2024/09/16
 **/
public class HighlightDialogPanel extends JPanel {

    private JTextField textField;

    private JTextArea textArea;

    public HighlightDialogPanel(String title, String content) {
        textField = new JTextField(title);
        textArea = new JTextArea(content);
    }
}
