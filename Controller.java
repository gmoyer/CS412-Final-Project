import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//client side
public class Controller implements ActionListener {
    View view;
    Client client;
    private static Controller controller;

    public static void main(String argv[]) {
        getInstance().go();
    }

    private Controller() {
        view = new View(this);
        client = new Client(this);
    }

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    public void go() {
        view.navSignin();

        //view.navMain();
    }

    public void close() {
        System.exit(0);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button)e.getSource();

        switch (clickedButton.getID()) {
            case SIGN_IN: signin(clickedButton);
                break;
            case SIGN_UP: view.navSignup();
                break;
            case CREATE_ACCOUNT: createAccount(clickedButton);
                break;
            case BACK_SIGN_IN: view.navSignin();
                break;
            default:
                System.out.println("Unhandled response " + clickedButton.getID().toString());
        }
    }

    public void signin(Button button) {
        String username = button.getTextField(TextFieldID.USERNAME);
        String password = button.getTextField(TextFieldID.PASSWORD);

        String hashword = Util.sha256(password);

        ReqResult result = client.signinreq(username, hashword);


        if (!result.isSuccessful()) {
            view.setError(result.getMessage());
        } else {
            view.navMain();
        }
    }

    public void createAccount(Button button) {
        String name = button.getTextField(TextFieldID.NAME);
        String username = button.getTextField(TextFieldID.USERNAME);
        String password = button.getTextField(TextFieldID.PASSWORD);
        String confPassword = button.getTextField(TextFieldID.CONFIRM_PASSWORD);

        String hashword = Util.sha256(password);
        String confHashword = Util.sha256(confPassword);

        ReqResult result = client.signupreq(name, username, hashword, confHashword);

        if (!result.isSuccessful())
            view.setError(result.getMessage());
        else
            view.navMain();
    }

    public void updateLeaderboard(List<?> list) {
        int leaderboardSize = 3;
        if (list.size() < 3)
            leaderboardSize = list.size();

        for (int i = 0; i < leaderboardSize; i++) {
            view.updateLeader(i, (String)list.get(i));
        }
    }
}
