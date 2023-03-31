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

        TextField username = new TextField("Username", 15);
        TextField password = new TextField("Password", 15);


        Button signin = new Button("Sign in", ButtonID.SIGN_IN);
        Button signup = new Button("Sign up", ButtonID.SIGN_UP);

        signin.addActionListener(controller);
        signup.addActionListener(controller);

        signin.setAlignmentX(Component.CENTER_ALIGNMENT);
        signup.setAlignmentX(Component.CENTER_ALIGNMENT);

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
}
