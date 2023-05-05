import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

//client side
public class Controller implements ActionListener, KeyListener {
    View view;
    Client client;
    private static Controller controller;

    boolean locked;

    //client data
    String username;
    String hashword;
    String name;
    int money;
    int betAmount;
    boolean coinSide;


    Dataflow response;
    final int flipTime = 1000;

    public static void main(String argv[]) {
        getInstance().go();
    }

    private Controller() {
        locked = false;
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
        betAmount = 5;

        view.navSignin();

        //view.navMain();
    }

    public void close() {
        System.exit(0);
    }

    public void connectionLost() {
        view.setError("Connection lost. Attempting to reconnect...");

        Util.sleep(5);

        client = new Client(this);

        signin();
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        handleAction((Element)e.getSource());
    }
    @Override
    public void keyTyped(KeyEvent e) {
        handleAction((Element)e.getSource());
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    

    public void handleAction(Element clickedElement) {
        if (locked)
            return;
        locked = true;

        Thread buttonHandler = new Thread(new Runnable() {
            public void run() {
                switch (clickedElement.getID()) {
                    case SIGN_IN: signin((Button)clickedElement);
                        break;
                    case SIGN_UP: view.navSignup();
                        break;
                    case CREATE_ACCOUNT: createAccount((Button)clickedElement);
                        break;
                    case BACK_SIGN_IN: view.navSignin();
                        break;
                    case ADD_TO_BET: setBetAmount(betAmount+5);
                        break;
                    case REMOVE_FROM_BET: setBetAmount(betAmount-5);
                        break;
                    case CHOOSE_HEADS: chooseSide(true);
                        break;
                    case CHOOSE_TAILS: chooseSide(false);
                        break;
                    case FLIP: flipCoin();
                        break;
                    case SIGN_OUT: signout();
                        break;
                    case BET_AMOUNT: setBetAmount((TextField)clickedElement);
                        break;
                    default:
                        System.out.println("Unhandled response " + clickedElement.getID().toString());
                }
                locked = false;
            }
        });

        buttonHandler.start();
    }

    public void signin(Button button) {
        username = button.getTextField(ElementID.USERNAME);
        String password = button.getTextField(ElementID.PASSWORD);

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
            betAmount = 5;
            view.navMain();
        }
    }

    public void createAccount(Button button) {
        String name = button.getTextField(ElementID.NAME);
        username = button.getTextField(ElementID.USERNAME);
        String password = button.getTextField(ElementID.PASSWORD);
        String confPassword = button.getTextField(ElementID.CONFIRM_PASSWORD);

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
            betAmount = 5;
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

    public void setBetAmount(TextField tf) {
        String input = tf.getText();

        if (Util.validNumber(input)) {
            if (input.length()==0)
                return;
            int num = Integer.parseInt(input);
            if (num > money) {
                setBetAmount(money);
            } else {
                setBetAmount(num, false);
            }
        } else {
            setBetAmount(betAmount);
        }
    }
    public void setBetAmount(int change) {
        setBetAmount(change, true);
    }
    public void setBetAmount(int change, boolean updateView) {
        if (change < 0 || change > money) {
            return;
        }

        betAmount = change;
        if (updateView)
            view.updateBetAmount(betAmount);
    }

    public void chooseSide(boolean side) {
        coinSide = side;
        view.updateCoin(side);
        view.updateSide(side);
    }
    public void visualChooseSide(boolean side, double scale) {
        view.updateCoin(side, scale);
    }

    public void flipCoin() {
        ArrayList<Object> request = new ArrayList<Object>();

        request.add(betAmount);

        Thread requestThread = new Thread(new Runnable() {
            public void run() {
                response = client.serverRequest(Instruct.FLIP_REQUEST, Instruct.FLIP_RESULT, request);
            }
        });
        requestThread.start();

        //coin flip animation while waiting for server response
        long baseTime = System.currentTimeMillis();
        double scale = 1;
        boolean flipSide = true;
        while ((System.currentTimeMillis() < baseTime + flipTime)) {
            double x = ((double)(System.currentTimeMillis()-baseTime))/flipTime;
            scale = 1 - 0.8*(x*x - x);
            if (flipSide) {
                flipSide = false;
            } else {
                flipSide = true;
            }
            visualChooseSide(flipSide, scale);
            Util.sleep(0.05);
        }

        try {
            requestThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response.getResult().isSuccessful()) {
            boolean outcome = (boolean)response.getNext();
            if (outcome) {
                visualChooseSide(coinSide, 1);
            } else {
                visualChooseSide(!coinSide, 1);
            }
            int newMoney = (int)response.getNext();
            view.updateMoneyEarned(newMoney-money);
            money = newMoney;
            if (money < betAmount) {
                setBetAmount(money);
            }
            view.updateMoney(money);
        } else {
            view.setError(response.getResult().getMessage());
        }
    }


    public void signout() {
        Dataflow df = new Dataflow(Instruct.SIGN_OUT);
        client.sendData(df);

        view.navSignin();
    }
}
