package com.timetracker.models;

import com.timetracker.models.user.LoginResult;

import javax.swing.JFrame;
import java.net.URI;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class GlobalSetting {
    // Singleton instance
    private static final GlobalSetting INSTANCE = new GlobalSetting();

    // Fields
    private LoginResult loginResult;
    private JFrame timeTracker;
    private JFrame loginView;
    private String machineId;
    private JFrame productivityAppsSettings;

    // Constants
    public static final String PORTAL_BASE_URL = "https://www.effortlesshrm.com/";
    public static final String EMAIL_RECEIVER = "info@arsteg.com";
    public static final String API_KEY = "ec86b9ecfee30654";

    // API base URL (loaded from config or default)
    private String apiBaseUrl;

    // Private constructor for singleton
    private GlobalSetting() {
        // Load apiBaseUrl from config.properties or use default
        Properties props = loadConfig();
        this.apiBaseUrl = props.getProperty("apiBaseUrl", "https://effortlesshrm-e029cd6a5095.herokuapp.com");
    }

    // Singleton getter
    public static GlobalSetting getInstance() {
        return INSTANCE;
    }

    // Load configuration from resources
    private Properties loadConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            // Fallback to default if config file is missing
            System.err.println("Could not load config.properties: " + e.getMessage());
        }
        return props;
    }

    // Getters
    public LoginResult getLoginResult() {
        return loginResult;
    }

    public JFrame getTimeTracker() {
        return timeTracker;
    }

    public JFrame getLoginView() {
        return loginView;
    }

    public String getMachineId() {
        return machineId;
    }

    public JFrame getProductivityAppsSettings() {
        return productivityAppsSettings;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    // Setters
    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    public void setTimeTracker(JFrame timeTracker) {
        this.timeTracker = timeTracker;
    }

    public void setLoginView(JFrame loginView) {
        this.loginView = loginView;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setProductivityAppsSettings(JFrame productivityAppsSettings) {
        this.productivityAppsSettings = productivityAppsSettings;
    }

    // Extract base URI
    public String extractBaseUri(String endpoint) {
        try {
            URI uri = new URI(endpoint);
            return uri.getScheme() + "://" + uri.getAuthority();
        } catch (Exception e) {
            e.printStackTrace();
            return endpoint; // Fallback to original if parsing fails
        }
    }
}