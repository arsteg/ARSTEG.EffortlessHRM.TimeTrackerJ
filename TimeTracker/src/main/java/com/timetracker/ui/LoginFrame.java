package com.timetracker.ui;

import com.timetracker.viewmodels.LoginViewModel;
import com.timetracker.viewmodels.TimeTrackerViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginFrame extends JFrame implements PropertyChangeListener {
    private final LoginViewModel viewModel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckBox;
    private JButton loginButton;
    private JLabel errorLabel;
    private JProgressBar progressBar;

    public LoginFrame(LoginViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Time Tracker - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout(10, 10));

        // Main panel with GridBagLayout for form-like structure
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setText(viewModel.getUsername());
        usernameField.getDocument().addDocumentListener(new SimpleDocumentListener(e -> viewModel.setUsername(usernameField.getText())));
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setText(viewModel.getPassword());
        passwordField.getDocument().addDocumentListener(new SimpleDocumentListener(e -> viewModel.setPassword(new String(passwordField.getPassword()))));
        mainPanel.add(passwordField, gbc);

        // Remember Me
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        rememberMeCheckBox = new JCheckBox("Remember Me");
        rememberMeCheckBox.setSelected(viewModel.isRememberMe());
        rememberMeCheckBox.addActionListener(e -> viewModel.setRememberMe(rememberMeCheckBox.isSelected()));
        mainPanel.add(rememberMeCheckBox, gbc);

        // Login Button
        gbc.gridy = 3;
        loginButton = new JButton("Login");
        loginButton.setEnabled(viewModel.isEnableLoginButton());
        loginButton.addActionListener(e -> viewModel.login());
        mainPanel.add(loginButton, gbc);

        // Error Label
        gbc.gridy = 4;
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        mainPanel.add(errorLabel, gbc);

        // Progress Bar
        gbc.gridy = 5;
        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        mainPanel.add(progressBar, gbc);

        // Links Panel (Forgot Password, Sign Up, Social Media)
        JPanel linksPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.addActionListener(e -> viewModel.openForgotPassword());
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> viewModel.openSignUpPage());
        linksPanel.add(forgotPasswordButton);
        linksPanel.add(signUpButton);

        add(mainPanel, BorderLayout.CENTER);
        add(linksPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "username":
                usernameField.setText((String) evt.getNewValue());
                break;
            case "password":
                passwordField.setText((String) evt.getNewValue());
                break;
            case "rememberMe":
                rememberMeCheckBox.setSelected((Boolean) evt.getNewValue());
                break;
            case "enableLoginButton":
                loginButton.setEnabled((Boolean) evt.getNewValue());
                break;
            case "errorMessage":
                errorLabel.setText((String) evt.getNewValue());
                break;
            case "progressWidth":
                int value = (Integer) evt.getNewValue();
                progressBar.setVisible(value > 0);
                progressBar.setValue(value);
                break;
            case "loginSuccess":
                // Open TimeTrackerFrame
                SwingUtilities.invokeLater(() -> {
                    new TimeTrackerFrame(new TimeTrackerViewModel()).setVisible(true);
                    dispose(); // Close login window
                });
                break;
        }
    }
}

// Helper class for text field changes
class SimpleDocumentListener implements javax.swing.event.DocumentListener {
    private final Runnable action;

    public SimpleDocumentListener(Runnable action) {
        this.action = action;
    }

    @Override
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        action.run();
    }

    @Override
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        action.run();
    }

    @Override
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        action.run();
    }
}