import java.net.Socket;

//server side!!
//maintains connection with client and account
public class SocketThread extends DataSender implements Runnable {
    Server server;

    AccountManager accountManager;

    Game game;

    public SocketThread(Socket conn, Server s) {
        super(conn);
        server = s;
        accountManager = AccountManager.getInstance();
        game = new Game();
    }

    @Override
    public void run() {
        System.out.println("new server thread to handle new client");
        //must thread to handle data

        commThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Dataflow line;
                boolean cont = true;
                while ((line = receiveData()) != null && cont) { //communication logic
                    switch(line.getInstruct()) {
                        case SUCCESSFUL_CONNECTION:
                            System.out.println("Successful connection with client!");
                            break;
                        case QUIT:
                            cont = false;
                            break;
                        case SIGNIN_ATTEMPT:
                            signinAttempt(line);
                            break;
                        case SIGNUP_ATTEMPT:
                            signupAttempt(line);
                            break;
                        case FLIP_REQUEST:
                            flipCoin((int)line.getNext());
                            break;
                        default:
                            System.out.println("Unhandled request: " + line.getInstruct());
                            break;
                    }
                }
            }
        });
        commThread.start();

        sendData(new Dataflow(Instruct.SUCCESSFUL_CONNECTION));

        sendLeaderboard();

        try {
            commThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //closing down actions
        accountManager.signout();
    }


    public void signinAttempt(Dataflow data) {
        String username = (String)data.getNext();
        String password = (String)data.getNext();

        ReqResult result = accountManager.loadAccount(username, password);

        Dataflow df = new Dataflow(Instruct.AUTH_RESULT);

        df.setResult(result);

        if (result.isSuccessful()) {
            Entry entry = accountManager.getActiveEntry();
            df.add(entry.getField(Field.NAME));
            df.add(entry.getField(Field.MONEY));
        }

        sendData(df);
    }

    public void signupAttempt(Dataflow data) {
        String name = (String)data.getNext();
        String username = (String)data.getNext();
        String password = (String)data.getNext();
        String confPassword = (String)data.getNext();

        ReqResult result = accountManager.createAccount(name, username, password, confPassword);

        Dataflow df = new Dataflow(Instruct.AUTH_RESULT);

        df.setResult(result);

        if (result.isSuccessful()) {
            Entry entry = accountManager.getActiveEntry();
            df.add(entry.getField(Field.NAME));
            df.add(entry.getField(Field.MONEY));
        }

        sendData(df);
    }

    public void sendLeaderboard() {
        Dataflow df = new Dataflow(Instruct.LEADERBOARD_UPDATE);
        df.add(accountManager.getLeaderboard());
        sendData(df);
    }

    public void flipCoin(int betAmount) {
        Entry entry = accountManager.getActiveEntry();

        int money = (int)entry.getField(Field.MONEY);

        Dataflow df = new Dataflow(Instruct.FLIP_RESULT);

        if (betAmount > money || betAmount <= 0) {
            df.setResult(ReqResult.NOT_ENOUGH_MONEY);
        } else {
            df.setResult(ReqResult.SUCCESS);
            boolean outcome = game.flipCoin();

            if (outcome) { //win coin toss
                money += betAmount;
            } else { //lose coin toss
                money -= betAmount;
            }
            accountManager.getLeaderboard(true); //to save output 
            entry.setField(Field.MONEY, money);
            if (accountManager.leaderboardUpdated()) {
                server.updateAllLeaderboards();
            }

            df.add(outcome);
            df.add(money);
        }

        sendData(df);
    }
}