import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.SwingWorker;

public class LoginViewModel {
    private static final Logger logger = Logger.getLogger(LoginViewModel.class.getName());
    private Map<String, String> configuration;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String userName = "";
    private String password = "";
    private boolean rememberMe = false;
    private boolean enableLoginButton = false;
    private int progressWidth = 0;
    private String errorMessage = "";
    private LoginFrame view;

    public LoginViewModel(LoginFrame view) {
        this.view = view;
        try {
            logger.info("Constructor starts");
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("appsettings.json")) {
                if (inputStream == null) {
                    throw new IOException("appsettings.json not found in classpath");
                }
                configuration = objectMapper.readValue(inputStream, Map.class);
                String apiBaseUrl = configuration.get("ApiBaseUrl");
                if (apiBaseUrl != null) {
                    GlobalSetting.setApiBaseUrl(apiBaseUrl);
                }
            }
            logger.info("Constructor ends");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load appsettings.json", e);
            configuration = Map.of();
        }
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; view.updateUI(); }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; view.updateUI(); }
    public boolean isRememberMe() { return rememberMe; }
    public void setRememberMe(boolean rememberMe) {
        logger.info("RememberMe setter starts");
        this.rememberMe = rememberMe;
        saveUserCredentials(rememberMe, rememberMe ? userName : "", rememberMe ? password : "");
        view.updateUI();
        logger.info("RememberMe setter ends");
    }
    public boolean isEnableLoginButton() { return enableLoginButton; }
    public void setEnableLoginButton(boolean enableLoginButton) { this.enableLoginButton = enableLoginButton; view.updateUI(); }
    public int getProgressWidth() { return progressWidth; }
    public void setProgressWidth(int progressWidth) { this.progressWidth = progressWidth; view.updateUI(); }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; view.updateUI(); }

    public void loginCommandExecute() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    logger.info("Login command execution starts");
                    setErrorMessage("");
                    if (userName.isEmpty() || password.isEmpty()) {
                        setErrorMessage("Invalid credentials, Please try again");
                        return null;
                    }
                    setProgressWidth(30);
                    setErrorMessage("");

                    REST rest = new REST(new HttpProviders());
                    Login login = new Login(userName, password);
                    ApiResponse result = rest.signIn(login).join();

                    if ("success".equals(result.getStatus())) {
                        logger.info("SignIn is successful");
                        GlobalSetting.getInstance().setLoginResult(result);
                        if (GlobalSetting.getInstance().getTimeTracker() == null) {
                            logger.info("Creating the instance of TimeTracker");
                            GlobalSetting.getInstance().setTimeTracker(new TimeTrackerFrame(result)); // Pass ApiResponse
                        }
                        logger.info("Showing the instance of TimeTracker");
                        GlobalSetting.getInstance().getTimeTracker().setVisible(true);
                        view.dispose();
                        saveUserCredentials(rememberMe, rememberMe ? userName : "", rememberMe ? password : "");
                    } else {
                        setErrorMessage("Login failed: Invalid credentials or server error");
                        logger.info("Login failed with status: " + result.getStatus());
                    }
                    logger.info("Login command execution ends");
                } catch (Exception e) {
                    setErrorMessage("Something went wrong: " + e.getMessage());
                    logger.log(Level.SEVERE, "Login error", e);
                } finally {
                    setProgressWidth(0);
                    setEnableLoginButton(true);
                }
                return null;
            }
        }.execute();
    }

    public void closeCommandExecute() {
        TimeTrackerApp.releaseMutex();
        System.exit(0);
    }

    public void openForgotPasswordCommandExecute() {
        String url = configuration.getOrDefault("ApplicationBaseUrl", "http://example.com") + "#/forgotPassword";
        openUrl(url);
    }

    public void openSignUpPageCommandExecute() {
        String url = configuration.getOrDefault("SignUpUrl", "http://example.com/signup");
        openUrl(url);
    }

    public void openSocialMediaPageCommandExecute(String pageName) {
        String url = configuration.getOrDefault(pageName, "http://example.com");
        openUrl(url);
    }

    private void saveUserCredentials(boolean rememberMe, String userName, String password) {
        if (rememberMe) {
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LoginViewModel.class);
            prefs.put("username", userName);
            prefs.put("password", password);
        }
    }

    private void openUrl(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to open URL: " + url, e);
        }
    }
}