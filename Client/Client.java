import java.io.IOException;
import java.net.Socket;


public class Client extends DataSender {
    Socket socket;
    Controller controller;

    public Client(Controller c) {
        super();
        controller = c;
        go();
    }

    public void go() {

        //server connection
        try {
            init(new Socket("127.0.0.1", 5000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        commThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Dataflow data;
                boolean cont = true;
                while ((data = receiveData()) != null && cont) { //communication logic
                    System.out.println("New Data: " + data.getInstruct().toString());
                    switch(data.getInstruct()) {
                        case SUCCESSFUL_CONNECTION:
                            System.out.println("Successful connection with server!");
                            break;
                        case QUIT:
                            cont = false;
                            break;
                        case AUTH_RESULT:
                            System.out.println("Recieved auth result");
                            break;
                        default:
                            System.out.println("Unhandled " + data.getInstruct().toString());
                            break;
                    }
                }
                System.out.println("Quitting...");
            }
        });
        commThread.start();
    }

    public ReqResult signinreq(String username, String password) {
        Dataflow df = new Dataflow(Instruct.SIGNIN_ATTEMPT);
        df.add(username);
        df.add(password);

        Dataflow response = query(df, Instruct.AUTH_RESULT);

        if (response == null)
            return ReqResult.CONN_FAIL;

        return (ReqResult)response.getNext();
    }

    public ReqResult signupreq(String name, String username, String password, String confPassword) {
        Dataflow df = new Dataflow(Instruct.SIGNUP_ATTEMPT);
        df.add(name);
        df.add(username);
        df.add(password);
        df.add(confPassword);

        Dataflow response = query(df, Instruct.AUTH_RESULT);

        if (response == null)
            return ReqResult.CONN_FAIL;

        return (ReqResult)response.getNext();
    }
}