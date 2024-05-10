package library;

import java.awt.List;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginForm extends JFrame {
    private JLabel usernameLabel, passwordLabel, messageLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public LoginForm() {
        setTitle("STAFF");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 20, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 50, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 160, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 80, 25);
        add(loginButton);

        messageLabel = new JLabel();
        messageLabel.setBounds(20, 110, 250, 25);
        add(messageLabel);

        //  action listener to sa login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("admin")) {
            messageLabel.setText("Login successful");

            Dashboard dashboard = new Dashboard();
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            dashboard.setVisible(false);

            ArrayList<Book> books = dashboard.listOfBooks();
            ArrayList<BorrowedBook> theLastOne = new ArrayList<>();
            
            StaffForm staffForm = new StaffForm(books, dashboard, theLastOne);
            staffForm.setVisible(true);
            this.setVisible(false);
        } else {
            messageLabel.setText("Login failed");
        }
    }
}