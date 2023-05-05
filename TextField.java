import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

public class TextField extends JTextField implements Element {
    JLabel label;
    JPanel panel;
    ElementID id;
    ElementType type;

    public TextField(String name, int columns, ElementID tfID) {
        super(columns);

        label = new JLabel(name);
        panel = new JPanel();
        panel.add(label);
        panel.add(this);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(300, 50));

        id = tfID;
        type = tfID.getType();
    }

    public JPanel getPanel() {
        return panel;
    }

    public ElementID getID() {
        return id;
    }
    public ElementType getType() {
        return type;
    }
}
