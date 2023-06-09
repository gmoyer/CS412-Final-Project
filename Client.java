import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

//import custom.communication.*;
//import custom.Util;

public class Client extends DataSender {
    Socket socket;
    Controller controller;

    int waitUntilNextConnectionAttempt;

    public Client(Controller c) {
        super();
        controller = c;
        waitUntilNextConnectionAttempt = 1;
        go();
    }

    public void go() {

        // server connection
        try {
            init(new Socket("127.0.0.1", 5000));
        } catch (IOException e) {
            System.out.println("Connection refused");
        }
        commThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Dataflow data;
                boolean cont = true;
                while ((data = receiveData()) != null && cont) { // communication logic
                    //System.out.println("New Data: " + data.getInstruct().toString());
                    switch (data.getInstruct()) {
                        case SUCCESSFUL_CONNECTION:
                            System.out.println("Successful connection with server!");
                            break;
                        case QUIT:
                            cont = false;
                            break;
                        case AUTH_RESULT:
                            System.out.println("Recieved auth result");
                            break;
                        case LEADERBOARD_UPDATE:
                            controller.updateLeaderboard(Util.convertObjectToList(data.getNext()));
                            break;
                        default:
                            //System.out.println("Unhandled " + data.getInstruct().toString());
                            break;
                    }
                }
                controller.connectionLost();
            }
        });
        commThread.start();
    }
    public Dataflow serverRequest(Instruct req, Instruct resp, ArrayList<Object> data) {
        Dataflow df = new Dataflow(req);

        for (Object d : data) {
            df.add(d);
        }

        Dataflow response = query(df, resp);

        if (response == null) {
            response = new Dataflow(resp);
            response.setResult(ReqResult.CONN_FAIL);
        }

        return response;
    }



    /*
    public ReqResult signinreq(String username, String password) {
        Dataflow df = new Dataflow(Instruct.SIGNIN_ATTEMPT);
        df.add(username);
        df.add(password);

        Dataflow lastRequest = query(df, Instruct.AUTH_RESULT);

        if (lastRequest == null)
            return ReqResult.CONN_FAIL;

        return (ReqResult) lastRequest.getNext();
    }

    public ReqResult signupreq(String name, String username, String password, String confPassword) {
        Dataflow df = new Dataflow(Instruct.SIGNUP_ATTEMPT);
        df.add(name);
        df.add(username);
        df.add(password);
        df.add(confPassword);

        lastRequest = query(df, Instruct.AUTH_RESULT);

        if (lastRequest == null)
            return ReqResult.CONN_FAIL;

        return (ReqResult) lastRequest.getNext();
    }

    public Object requestNext() {
        return lastRequest.getNext();
    }
    */
}