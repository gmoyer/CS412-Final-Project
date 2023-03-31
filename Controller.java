import javax.swing.*;
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
            case SIGN_IN: signinClicked();
                break;
            case SIGN_UP: signupClicked();
                break;
        }
    }

    public void signinClicked() {
        System.out.println("Sign in clicked!");
    }
    public void signupClicked() {
        System.out.println("Sign up clicked!");
    }
}
