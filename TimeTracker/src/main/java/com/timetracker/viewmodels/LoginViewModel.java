package com.timetracker.viewmodels;

import com.timetracker.services.RESTService;
import com.timetracker.models.Login;
import com.timetracker.ui.LoginFrame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.awt.Desktop;
import java.net.URI;
import com.timetracker.models.GlobalSetting;

import javax.swing.*;

public class LoginViewModel {
    private Map<String, String> configuration;
    private final PropertyChangeSupport support;
    private String userName = "";
    private String password = "";
    private boolean rememberMe = false;
    private boolean enableLoginButton = false;
    private String errorMessage = "";
    private int progressWidth = 0;
    private final RESTService restService;
    private final Properties config;
    private LoginFrame view;



//    public LoginViewModel() {
//        support = new PropertyChangeSupport(this);
//        restService = new RESTService();
//        config = loadConfig();
//        loadSavedCredentials();
//    }
    public LoginViewModel(LoginFrame view) {
        this.view = view;
        try {
            //logger.info("Constructor starts");
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("appsettings.json")) {
                if (inputStream == null) {
                    throw new IOException("appsettings.json not found in classpath");
                }
                //configuration = objectMapper.readValue(inputStream, Map.class);
                //String apiBaseUrl = configuration.get("ApiBaseUrl");
                //if (apiBaseUrl != null) {
                    //GlobalSetting.setApiBaseUrl(apiBaseUrl);
               // }
            }
            //logger.info("Constructor ends");
        } catch (IOException e) {
            //logger.log(Level.SEVERE, "Failed to load appsettings.json", e);
            //configuration = Map.of();
        }

        support = new PropertyChangeSupport(this);
        restService = new RESTService();
        config = loadConfig();
        loadSavedCredentials();
    }

    // Getters
    public String getUsername() {
        return userName;
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
        String oldValue = this.userName;
        this.userName = username;
        support.firePropertyChange("username", oldValue, username);
        updateLoginButtonState();
    }

    public void setPassword(String password) {
        if (!this.password.equals(password)) {
            this.password = password;
            // Only trigger UI updates if really needed
            updateLoginButtonState();
        }
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
            props.setProperty("username", userName);
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
            setRememberMe(!userName.isEmpty() || !password.isEmpty());
        } catch (IOException e) {
            // File doesn't exist yet, ignore
        }
    }

    private void updateLoginButtonState() {
        setEnableLoginButton(!userName.isEmpty() && !password.isEmpty());
    }

    // Commands
    public void login() {
        if (userName.isEmpty() || password.isEmpty()) {
            setErrorMessage("Invalid credentials, Please try again");
            return;
        }
        setProgressWidth(30);
        setEnableLoginButton(false);

        // Simulate async REST call
        new Thread(() -> {
            try {
                Login login = new Login(userName, password);
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

    public void openSignUpPageCommandExecute() {
        String url = configuration.getOrDefault("SignUpUrl", "http://example.com/signup");
        openUrl(url);
    }
    public void openSocialMediaPageCommandExecute(String pageName) {
        String url = configuration.getOrDefault(pageName, "http://example.com");
        openUrl(url);
    }
    public void loginCommandExecute() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    //logger.info("Login command execution starts");
                    setErrorMessage("");
                    if (userName.isEmpty() || password.isEmpty()) {
                        setErrorMessage("Invalid credentials, Please try again");
                        return null;
                    }
                    setProgressWidth(30);
                    setErrorMessage("");

//                    REST rest = new REST(new HttpProviders());
//                    Login login = new Login(userName, password);
//                    ApiResponse result = rest.signIn(login).join();
//
//                    if ("success".equals(result.getStatus())) {
//                       //logger.info("SignIn is successful");
//                        GlobalSetting.getInstance().setLoginResult(result);
//                        if (GlobalSetting.getInstance().getTimeTracker() == null) {
//                            //logger.info("Creating the instance of TimeTracker");
//                            GlobalSetting.getInstance().setTimeTracker(new TimeTrackerFrame(result)); // Pass ApiResponse
//                        }
//                        //logger.info("Showing the instance of TimeTracker");
//                        GlobalSetting.getInstance().getTimeTracker().setVisible(true);
//                        view.dispose();
//                        //saveUserCredentials(rememberMe, rememberMe ? userName : "", rememberMe ? password : "");
//                    } else {
//                        setErrorMessage("Login failed: Invalid credentials or server error");
//                        //logger.info("Login failed with status: " + result.getStatus());
//                    }
                    //logger.info("Login command execution ends");
                } catch (Exception e) {
                    setErrorMessage("Something went wrong: " + e.getMessage());
                    //logger.log(Level.SEVERE, "Login error", e);
                } finally {
                    setProgressWidth(0);
                    setEnableLoginButton(true);
                }
                return null;
            }
        }.execute();
    }

    public void closeCommandExecute() {
        //TimeTrackerApp.releaseMutex();
        System.exit(0);
    }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; view.updateUI(); }




    private void saveUserCredentials(boolean rememberMe, String userName, String password) {
        if (rememberMe) {
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LoginViewModel.class);
            prefs.put("username", userName);
            prefs.put("password", password);
        }
    }
    public int getProgressWidth() { return progressWidth; }
    public String getErrorMessage() { return errorMessage; }
}