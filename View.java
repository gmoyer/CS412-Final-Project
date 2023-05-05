import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class View {
    Controller controller;
    JFrame jFrame;

    //specific elements that need to be updated
    JLabel errorLabel;
    TextField betAmount;
    JLabel coinImage;
    JLabel[] leaderboard;
    JLabel currentMoney;

    JLabel currentSide;
    JLabel moneyEarned;

    BufferedImage headsCoin;
    BufferedImage tailsCoin;

    public View(Controller c) {
        controller = c;
        jFrame = new JFrame();
        jFrame.setSize(800, 620);

        //init elements that need to be updated
        leaderboard = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            leaderboard[i] = new JLabel("");
        }
        currentMoney = new JLabel();

        try {
            headsCoin = ImageIO.read(new File("images/Heads.png"));
            tailsCoin = ImageIO.read(new File("images/Tails.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //on exit
        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                controller.close();
            }
        });

        errorLabel = new JLabel();
    }

    public void setError(String err) {
        errorLabel.setText(err);
    }

    public void newWindow(JPanel p) {
        jFrame.getContentPane().removeAll();
        jFrame.add(p);
        jFrame.setVisible(true);
    }

    public void navSignin() {
        setError("");

        JPanel container = new JPanel(new GridBagLayout());
        JPanel centerPanel = new JPanel();

        JLabel label = new JLabel();
        label.setText("Coin flip!");
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setMinimumSize(new Dimension(0, 200));

        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        TextField username = new TextField("Username", 15, ElementID.USERNAME);
        TextField password = new TextField("Password", 15, ElementID.PASSWORD);

        Button signin = new Button("Sign in", ElementID.SIGN_IN);
        Button signup = new Button("Sign up", ElementID.SIGN_UP);

        signin.setAlignmentX(Component.CENTER_ALIGNMENT);
        signup.setAlignmentX(Component.CENTER_ALIGNMENT);

        signin.addTextField(username);
        signin.addTextField(password);

        //panel.setLayout(new BorderLayout());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));


        JPanel errorLabelPanel = new JPanel();
        errorLabelPanel.add(errorLabel);
        errorLabelPanel.setMinimumSize(new Dimension(0, 50));

        centerPanel.add(label);
        centerPanel.add(username.getPanel());
        centerPanel.add(password.getPanel());
        centerPanel.add(errorLabelPanel);
        centerPanel.add(signin);
        centerPanel.add(signup);
    
        centerPanel.setMaximumSize(new Dimension(300, 400));

        centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(centerPanel);

        //centerPanel.setBackground(new Color(255, 0, 0));
        //container.setBackground(new Color(0, 255, 0));

        newWindow(container);
    }

    public void navSignup() {
        setError("");
        
        JPanel container = new JPanel(new GridBagLayout());
        JPanel centerPanel = new JPanel();

        TextField name = new TextField("Full name", 15, ElementID.NAME);
        TextField username = new TextField("Username", 15, ElementID.USERNAME);
        TextField password = new TextField("Password", 15, ElementID.PASSWORD);
        TextField confPassword = new TextField("Confirm Password", 15, ElementID.CONFIRM_PASSWORD);

        Button createAccount = new Button("Create Account", ElementID.CREATE_ACCOUNT);
        Button back = new Button("Back", ElementID.BACK_SIGN_IN);

        createAccount.setAlignmentX(Component.CENTER_ALIGNMENT);

        createAccount.addTextField(name);
        createAccount.addTextField(username);
        createAccount.addTextField(password);
        createAccount.addTextField(confPassword);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));


        JPanel errorLabelPanel = new JPanel();
        errorLabelPanel.add(errorLabel);
        errorLabelPanel.setMinimumSize(new Dimension(0, 50));

        centerPanel.add(name.getPanel());
        centerPanel.add(username.getPanel());
        centerPanel.add(password.getPanel());
        centerPanel.add(confPassword.getPanel());
        centerPanel.add(errorLabelPanel);
        centerPanel.add(createAccount);
        centerPanel.add(back);
    
        centerPanel.setMaximumSize(new Dimension(300, 500));

        //centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        //centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(centerPanel);

        newWindow(container);
    }

    public void navMain() {
        setError("");
        
        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel leaderboardLabel = new JLabel("Leaderboard");
        c.gridx = 0;
        c.gridy = 0;
        container.add(leaderboardLabel, c);

        for (int i = 0; i < 3; i++) {
            c.gridx = 0;
            c.gridy = i+1;
            container.add(leaderboard[i], c);
        }


        int pady = 11; //columns
        int padx = 5; //rows
        c.ipady = pady;
        c.ipadx = padx;
        c.insets = new Insets(pady/2, padx/2, pady/2, padx/2);

        Button signoutButton = new Button("Sign out", ElementID.SIGN_OUT);
        c.gridx = 8;
        c.gridy = 0;
        container.add(signoutButton, c);

        Button addToBet = new Button("+", ElementID.ADD_TO_BET);
        c.gridx = 6;
        c.gridy = 8;
        container.add(addToBet, c);

        Button removeFromBet = new Button("-", ElementID.REMOVE_FROM_BET);
        c.gridx = 2;
        c.gridy = 8;
        container.add(removeFromBet, c);

        Button chooseHeads = new Button("Heads", ElementID.CHOOSE_HEADS);
        c.gridx = 3;
        c.gridy = 9;
        container.add(chooseHeads, c);

        Button chooseTails = new Button("Tails", ElementID.CHOOSE_TAILS);
        c.gridx = 5;
        c.gridy = 9;
        container.add(chooseTails, c);

        Button flip = new Button("Flip!", ElementID.FLIP);
        c.gridx = 4;
        c.gridy = 10;
        container.add(flip, c);


        //add 2 wide elements
        c.gridwidth = 2;

        moneyEarned = new JLabel("");
        c.gridx = 6;
        c.gridy = 5;
        container.add(moneyEarned, c);


        //all centered, 3 wide elements
        c.gridwidth = 3;
        betAmount = new TextField("Current bet: ", 3, ElementID.BET_AMOUNT);
        betAmount.addKeyListener(controller);
        c.gridx = 3;
        c.gridy = 8;
        container.add(betAmount.getPanel(), c);

        controller.setBetAmount(5);

        c.gridy = 5;
        setTextSize(currentMoney, 27);
        container.add(currentMoney, c);

        c.gridy = 4;
        container.add(errorLabel, c);

        currentSide = new JLabel("Chosen: Heads");
        c.gridy = 7;
        container.add(currentSide, c);

        //image
        coinImage = new JLabel();
        controller.chooseSide(true);
        c.gridy = 6;
        container.add(coinImage, c);


        newWindow(container);
    }

    public void updateLeader(int index, String text) {
        if (index >= 0 && index <= 2) {
            leaderboard[index].setText(text);
        }
    }

    public void updateMoney(int amount) {
        currentMoney.setText("$" + amount);
    }

    public void updateCoin(boolean side) {
        if (side) {
            coinImage.setIcon(new ImageIcon(headsCoin));
        } else {
            coinImage.setIcon(new ImageIcon(tailsCoin));
        }
    }

    public void updateCoin(boolean side, double scale) {
        BufferedImage newCoin;
        if (side) {
            newCoin = headsCoin;
        } else {
            newCoin = tailsCoin;
        }
        int newWidth = (int)(newCoin.getWidth() * scale);
        int newHeight = (int)(newCoin.getHeight() * scale);
        coinImage.setIcon(new ImageIcon(newCoin.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT)));
    }

    public void updateBetAmount(int amount) {
        //System.out.println("Setting bet to " + amount);
        betAmount.setText("" + amount);
    }

    public void updateSide(boolean side) {
        if (side)
            currentSide.setText("Chosen: heads");
        else
            currentSide.setText("Chosen: tails");
    }

    public void updateMoneyEarned(int money) {
        String txt;
        if (money < 0) {
            txt = "- $" + (-money);
            moneyEarned.setForeground(Color.RED);
        } else {
            txt = "+ $" + money;
            moneyEarned.setForeground(Color.GREEN);
        }

        Thread removeText = new Thread(new Runnable() {
            public void run() {
                Font font = moneyEarned.getFont();
                for (int i = 0; i < 10; i++) {
                    moneyEarned.setFont(new Font(font.getName(), font.getStyle(), (i*3)));
                    moneyEarned.setText(txt);
                    Util.sleep(0.03);
                }
                Util.sleep(1);
                moneyEarned.setText("");
                //moneyEarned.setFont(new Font(font.getName(), font.getStyle(), 0));
            }
        });

        removeText.start();
    }

    public void setTextSize(JLabel tf, int size) {
        Font font = tf.getFont();
        tf.setFont(new Font(font.getName(), font.getStyle(), size));
    }
}
