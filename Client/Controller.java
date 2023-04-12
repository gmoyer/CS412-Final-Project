import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;

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

        String hashword = sha256(password);

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

        String hashword = sha256(password);
        String confHashword = sha256(confPassword);

        ReqResult result = client.signupreq(name, username, hashword, confHashword);

        if (!result.isSuccessful())
            view.setError(result.getMessage());
        else
            view.navMain();
    }


    //Client side. Pulled from https://stackoverflow.com/a/11009612
    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) 
                  hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
}
