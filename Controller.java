import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//client side
public class Controller implements ActionListener {
    View view;
    Client client;
    private static Controller controller;

    //client data
    String username;
    String hashword;
    String name;
    int money;
    int betAmount;

    public static void main(String argv[]) {
        getInstance().go();
    }

    private Controller() {
        betAmount = 0;
        money = 0;

        view = new View(this);
        client = new Client(this);
    }

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    public void go() {
        username = "";
        hashword = "";
        name = "";
        money = 0;
        betAmount = 0;


        view.navSignin();

        //view.navMain();
    }

    public void close() {
        System.exit(0);
    }

    public void connectionLost() {
        view.setError("Connection lost. Attempting to reconnect...");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client = new Client(this);

        signin();
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
            case ADD_TO_BET: updateBetAmount(5);
                break;
            case REMOVE_FROM_BET: updateBetAmount(-5);
                break;
            case CHOOSE_HEADS: chooseSide(true);
                break;
            case CHOOSE_TAILS: chooseSide(false);
                break;
            case FLIP: flipCoin();
                break;
            case SIGN_OUT: signout();
                break;
            default:
                System.out.println("Unhandled response " + clickedButton.getID().toString());
        }
    }

    public void signin(Button button) {
        username = button.getTextField(TextFieldID.USERNAME);
        String password = button.getTextField(TextFieldID.PASSWORD);

        hashword = Util.sha256(password);

        signin();
    }

    public void signin() {
        ArrayList<Object> req = new ArrayList<Object>();

        req.add(username);
        req.add(hashword);

        Dataflow result = client.serverRequest(Instruct.SIGNIN_ATTEMPT, Instruct.AUTH_RESULT, req);

        if (!result.getResult().isSuccessful()) {
            view.setError(result.getResult().getMessage());
        } else {
            name = (String)result.getNext();
            money = (int)result.getNext();
            view.updateMoney(money);
            view.navMain();
        }
    }

    public void createAccount(Button button) {
        String name = button.getTextField(TextFieldID.NAME);
        username = button.getTextField(TextFieldID.USERNAME);
        String password = button.getTextField(TextFieldID.PASSWORD);
        String confPassword = button.getTextField(TextFieldID.CONFIRM_PASSWORD);

        hashword = Util.sha256(password);
        String confHashword = Util.sha256(confPassword);

        ArrayList<Object> request = new ArrayList<Object>();

        request.add(name);
        request.add(username);
        request.add(hashword);
        request.add(confHashword);

        Dataflow result = client.serverRequest(Instruct.SIGNUP_ATTEMPT, Instruct.AUTH_RESULT, request);

        if (!result.getResult().isSuccessful())
            view.setError(result.getResult().getMessage());
        else {
            this.name = (String)result.getNext();
            money = (int)result.getNext();
            view.updateMoney(money);
            view.navMain();
        }
    }

    public void updateLeaderboard(List<?> list) {
        int leaderboardSize = 3;
        if (list.size() < 3)
            leaderboardSize = list.size();

        for (int i = 0; i < leaderboardSize; i++) {
            view.updateLeader(i, (String)list.get(i));
        }
    }

    public void updateBetAmount(int change) {
        if (betAmount + change <= 0 || betAmount + change > money) {
            return;
        }

        betAmount += change;
        view.updateBetAmount(betAmount);
    }

    public void chooseSide(boolean side) { //effectively does nothing
        //true = heads, false = tails
    }

    public void flipCoin() {
        ArrayList<Object> request = new ArrayList<Object>();

        request.add(betAmount);

        Dataflow response = client.serverRequest(Instruct.FLIP_REQUEST, Instruct.FLIP_RESULT, request);

        if (response.getResult().isSuccessful()) {
            boolean outcome = (boolean)response.getNext();
            money = (int)response.getNext();
            view.updateMoney(money);
        } else {
            System.out.println("No success");
            view.setError(response.getResult().getMessage());
        }
    }


    public void signout() {
        Dataflow df = new Dataflow(Instruct.SIGN_OUT);
        client.sendData(df);

        view.navSignin();
    }
}
