import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

public class TextField {
    JTextField textField;
    JLabel label;
    JPanel panel;
    TextFieldID id;

    public TextField(String name, int columns, TextFieldID tfID) {
        textField = new JTextField(columns);
        label = new JLabel(name);
        panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(300, 50));

        id = tfID;
    }

    public JPanel getPanel() {
        return panel;
    }
    public String getText() {
        return textField.getText();
    }

    public TextFieldID getID() {
        return id;
    }
}
