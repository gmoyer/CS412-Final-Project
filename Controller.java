import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//client side
public class Controller implements ActionListener {
    View view;
    public Controller() {
        view = new View(this);

        view.navSignin();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button)e.getSource();

        switch (clickedButton.getID()) {
            case SIGN_IN: signinClicked(clickedButton);
                break;
            case SIGN_UP: view.navSignup();
                break;
            case CREATE_ACCOUNT:
                break;
            case BACK_SIGN_IN: view.navSignin();
                break;
        }
    }

    public void signinClicked(Button button) {
        String username = button.getTextField(TextFieldID.USERNAME);
        String password = button.getTextField(TextFieldID.PASSWORD);

        String hashword = AccountManager.sha256(password);
    }
}
