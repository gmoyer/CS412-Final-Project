import java.awt.*;
import javax.swing.*;

public class View {
    Controller controller;
    JFrame jFrame;
    public View(Controller c) {
        controller = c;
        jFrame = new JFrame();
        jFrame.setSize(500, 500);
    }

    public void newWindow(JPanel p) {
        jFrame.setVisible(false);
        jFrame.getContentPane().removeAll();
        jFrame.add(p);
        //jFrame.repaint();
        jFrame.setVisible(true);
    }

    public void navSignin() {
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

        signin.addActionListener(controller);
        signup.addActionListener(controller);

        signin.setAlignmentX(Component.CENTER_ALIGNMENT);
        signup.setAlignmentX(Component.CENTER_ALIGNMENT);

        signin.addTextField(username);
        signin.addTextField(password);

        //panel.setLayout(new BorderLayout());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.add(label);
        centerPanel.add(username.getPanel());
        centerPanel.add(password.getPanel());
        centerPanel.add(signin);
        centerPanel.add(signup);
    
        centerPanel.setMaximumSize(new Dimension(300, 400));

        centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(centerPanel);

        centerPanel.setBackground(new Color(255, 0, 0));
        container.setBackground(new Color(0, 255, 0));

        newWindow(container);
    }

    public void navSignup() {
        JPanel container = new JPanel(new GridBagLayout());
        JPanel centerPanel = new JPanel();

        TextField name = new TextField("Full name", 15, TextFieldID.NAME);
        TextField username = new TextField("Username", 15, TextFieldID.USERNAME);
        TextField password = new TextField("Password", 15, TextFieldID.PASSWORD);
        TextField confPassword = new TextField("Confirm Password", 15, TextFieldID.CONFIRM_PASSWORD);

        Button createAccount = new Button("Create Account", ButtonID.CREATE_ACCOUNT);
        Button back = new Button("Back", ButtonID.BACK_SIGN_IN);

        createAccount.addActionListener(controller);
        back.addActionListener(controller);

        createAccount.setAlignmentX(Component.CENTER_ALIGNMENT);

        createAccount.addTextField(name);
        createAccount.addTextField(username);
        createAccount.addTextField(password);
        createAccount.addTextField(confPassword);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.add(name.getPanel());
        centerPanel.add(username.getPanel());
        centerPanel.add(password.getPanel());
        centerPanel.add(confPassword.getPanel());
        centerPanel.add(createAccount);
        centerPanel.add(back);
    
        centerPanel.setMaximumSize(new Dimension(300, 400));

        centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(centerPanel);

        centerPanel.setBackground(new Color(255, 0, 0));
        container.setBackground(new Color(0, 255, 0));

        newWindow(container);
    }
}
