import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class View {
    Controller controller;
    JFrame jFrame;
    JLabel errorLabel;

    public View(Controller c) {
        controller = c;
        jFrame = new JFrame();
        jFrame.setSize(500, 500);

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
        jFrame.setVisible(false);
        jFrame.getContentPane().removeAll();
        jFrame.add(p);
        //jFrame.repaint();
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

        TextField username = new TextField("Username", 15, TextFieldID.USERNAME);
        TextField password = new TextField("Password", 15, TextFieldID.PASSWORD);

        Button signin = new Button("Sign in", ButtonID.SIGN_IN);
        Button signup = new Button("Sign up", ButtonID.SIGN_UP);

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

        TextField name = new TextField("Full name", 15, TextFieldID.NAME);
        TextField username = new TextField("Username", 15, TextFieldID.USERNAME);
        TextField password = new TextField("Password", 15, TextFieldID.PASSWORD);
        TextField confPassword = new TextField("Confirm Password", 15, TextFieldID.CONFIRM_PASSWORD);

        Button createAccount = new Button("Create Account", ButtonID.CREATE_ACCOUNT);
        Button back = new Button("Back", ButtonID.BACK_SIGN_IN);

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

        centerPanel.setBackground(new Color(255, 0, 0));
        container.setBackground(new Color(0, 255, 0));

        newWindow(container);
    }

    public void navMain() {
        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Columns: 7
        //Rows: 7

        Button signoutButton = new Button("Sign out", ButtonID.SIGN_OUT);
        c.gridx = 6;
        c.gridy = 0;
        container.add(signoutButton, c);

        Button addToBet = new Button("+", ButtonID.ADD_TO_BET);
        c.gridx = 1;
        c.gridy = 4;
        container.add(addToBet, c);

        Button removeFromBet = new Button("-", ButtonID.REMOVE_FROM_BET);
        c.gridx = 5;
        c.gridy = 4;
        container.add(removeFromBet, c);

        Button chooseHeads = new Button("Heads", ButtonID.CHOOSE_HEADS);
        c.gridx = 2;
        c.gridy = 5;
        container.add(chooseHeads, c);

        Button chooseTails = new Button("Tails", ButtonID.CHOOSE_TAILS);
        c.gridx = 4;
        c.gridy = 5;
        container.add(chooseTails, c);

        Button flip = new Button("Flip!", ButtonID.FLIP);
        c.gridx = 3;
        c.gridy = 6;
        container.add(flip, c);

        //images
        try {
            BufferedImage headsCoin = ImageIO.read(new File("images/Heads.png"));
            JLabel headsLabel = new JLabel(new ImageIcon(headsCoin));
            c.gridx = 3;
            c.gridy = 3;
            container.add(headsLabel, c);
        } catch (IOException e) {
            e.printStackTrace();
        }


        newWindow(container);
    }
}
