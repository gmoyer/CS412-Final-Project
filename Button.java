import javax.swing.JButton;

//so that I can identify the button in Controller
public class Button extends JButton {
    private ButtonID bid;

    public Button() {super();};
    public Button(String name) {super(name);};

    public Button(String name, ButtonID i) {
        super(name);
        bid = i;
    }

    public void setID(ButtonID i) {
        bid = i;
    }

    public ButtonID getID() {
        return bid;
    }
}
