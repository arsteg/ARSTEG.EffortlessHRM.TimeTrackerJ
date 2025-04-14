package com.timetracker.viewmodels;

import com.timetracker.services.RESTService;
import com.timetracker.models.Login;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.awt.Desktop;
import java.net.URI;

public class LoginViewModel {
    private final PropertyChangeSupport support;
    private String username = "";
    private String password = "";
    private boolean rememberMe = false;
    private boolean enableLoginButton = false;
    private String errorMessage = "";
    private int progressWidth = 0;
    private final RESTService restService;
    private final Properties config;

    public LoginViewModel() {
        support = new PropertyChangeSupport(this);
        restService = new RESTService();
        config = loadConfig();
        loadSavedCredentials();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public boolean isEnableLoginButton() {
        return enableLoginButton;
    }

    // Setters
    public void setUsername(String username) {
        String oldValue = this.username;
        this.username = username;
        support.firePropertyChange("username", oldValue, username);
        updateLoginButtonState();
    }

    public void setPassword(String password) {
        String oldValue = this.password;
        this.password = password;
        support.firePropertyChange("password", oldValue, password);
        updateLoginButtonState();
    }

    public void setRememberMe(boolean rememberMe) {
        boolean oldValue = this.rememberMe;
        this.rememberMe = rememberMe;
        support.firePropertyChange("rememberMe", oldValue, rememberMe);
        saveCredentials();
    }

    private void setErrorMessage(String errorMessage) {
        String oldValue = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldValue, errorMessage);
    }

    private void setProgressWidth(int progressWidth) {
        int oldValue = this.progressWidth;
        this.progressWidth = progressWidth;
        support.firePropertyChange("progressWidth", oldValue, progressWidth);
    }

    private void setEnableLoginButton(boolean enableLoginButton) {
        boolean oldValue = this.enableLoginButton;
        this.enableLoginButton = enableLoginButton;
        support.firePropertyChange("enableLoginButton", oldValue, enableLoginButton);
    }

    // PropertyChangeListener support
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Load configuration
    private Properties loadConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    // Save/load credentials
    private void saveCredentials() {
        Properties props = new Properties();
        if (rememberMe) {
            props.setProperty("username", username);
            props.setProperty("password", password);
        }
        try (FileOutputStream fos = new FileOutputStream("user.properties")) {
            props.store(fos, "User Credentials");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSavedCredentials() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("user.properties")) {
            props.load(fis);
            setUsername(props.getProperty("username", ""));
            setPassword(props.getProperty("password", ""));
            setRememberMe(!username.isEmpty() || !password.isEmpty());
        } catch (IOException e) {
            // File doesn't exist yet, ignore
        }
    }

    private void updateLoginButtonState() {
        setEnableLoginButton(!username.isEmpty() && !password.isEmpty());
    }

    // Commands
    public void login() {
        if (username.isEmpty() || password.isEmpty()) {
            setErrorMessage("Invalid credentials, Please try again");
            return;
        }
        setProgressWidth(30);
        setEnableLoginButton(false);

        // Simulate async REST call
        new Thread(() -> {
            try {
                Login login = new Login(username, password);
                boolean success = restService.signIn(login);
                if (success) {
                    setErrorMessage("");
                    support.firePropertyChange("loginSuccess", false, true);
                } else {
                    setErrorMessage("Invalid credentials, Please try again");
                }
            } catch (Exception e) {
                setErrorMessage("Something went wrong: " + e.getMessage());
            } finally {
                setProgressWidth(0);
                setEnableLoginButton(true);
            }
        }).start();
    }

    public void openForgotPassword() {
        String url = config.getProperty("applicationBaseUrl") + "#/forgotPassword";
        openUrl(url);
    }

    public void openSignUpPage() {
        String url = config.getProperty("signUpUrl");
        openUrl(url);
    }

    private void openUrl(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}