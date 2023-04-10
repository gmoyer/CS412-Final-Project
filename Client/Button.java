import java.util.ArrayList;

import javax.swing.JButton;

//so that I can identify the button in Controller and stores text fields associated with box
public class Button extends JButton {
    private ButtonID bid;
    private ArrayList<TextField> fields;

    public Button() {
        super();
        init();
    }
    public Button(String name) {
        super(name);
        init();
    }

    public Button(String name, ButtonID i) {
        super(name);
        bid = i;
        init();
    }

    private void init() {
        fields = new ArrayList<TextField>();
        addActionListener(Controller.getInstance());
    }

    public void setID(ButtonID i) {
        bid = i;
    }

    public ButtonID getID() {
        return bid;
    }

    public void addTextField(TextField f) {
        fields.add(f);
    }

    public String getTextField(TextFieldID id) {
        for (TextField tf : fields) {
            if (tf.getID() == id) {
                return tf.getText();
            }
        }
        return "FIELD NOT FOUND";
    }
}
