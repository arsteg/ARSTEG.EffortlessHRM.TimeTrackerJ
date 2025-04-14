package com.timetracker.models;

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

// Singleton