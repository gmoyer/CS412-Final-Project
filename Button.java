import java.util.ArrayList;

import javax.swing.JButton;

//so that I can identify the button in Controller and stores text fields associated with box
public class Button extends JButton implements Element {
    private ElementID bid;
    private ElementType type;
    private ArrayList<TextField> fields;

    public Button() {
        super();
        init();
    }
    public Button(String name) {
        super(name);
        init();
    }

    public Button(String name, ElementID i) {
        super(name);
        bid = i;
        type = i.getType();
        init();
    }

    private void init() {
        fields = new ArrayList<TextField>();
        addActionListener(Controller.getInstance());
    }

    public void setID(ElementID i) {
        bid = i;
    }

    public ElementID getID() {
        return bid;
    }
    public ElementType getType() {
        return type;
    }

    public void addTextField(TextField f) {
        fields.add(f);
    }

    public String getTextField(ElementID id) {
        for (TextField tf : fields) {
            if (tf.getID() == id) {
                return tf.getText();
            }
        }
        return "FIELD NOT FOUND";
    }
}
