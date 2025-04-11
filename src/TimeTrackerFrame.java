import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

class TimeTrackerFrame extends JFrame {
    private static final Logger logger = Logger.getLogger(TimeTrackerFrame.class.getName());
    private ApiResponse loginResult;
    private Point initialClick;
    private boolean trackerIsOn = false;
    private Timer dispatcherTimer; // For periodic time tracking
    private Timer screenshotTimer; // For screenshot countdown
    private Timer idleDetectionTimer; // For idle detection
    private Date trackingStartedAt;
    private Date trackingStoppedAt;
    private double minutesTracked = 0;
    private int totalMouseClicks = 0;
    private int totalKeysPressed = 0;
    private int totalMouseScrolls = 0;
    private String machineId;
    private String currentImagePath;
    private List<TimeLog> unsavedTimeLogs = new ArrayList<>();
    private long lastActivityTime = System.currentTimeMillis();
    private boolean userIsInactive = false;
    private TimeSpan timeTrackedSaved = new TimeSpan(0); // Simplified TimeSpan class below

    // UI Components
    private JLabel userLabel;
    private JTextField sessionTimeField, dayTimeField, weekTimeField, monthTimeField;
    private JComboBox<String> projectCombo, taskCombo;
    private JTextArea taskDescription;
    private JLabel screenshotLabel;
    private JButton startStopButton, logoutButton;
    private JLabel errorLabel;

    // Constructor remains unchanged
    public TimeTrackerFrame(ApiResponse loginResult) {
        this.loginResult = loginResult;
        setTitle("EffortlessHRM - Time Tracker");
        setSize(800, 500);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        machineId = generateMachineId();

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, getWidth(), getHeight(), new Color(58, 173, 161));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(78, 177, 182));
                GeneralPath polygon1 = new GeneralPath();
                polygon1.moveTo(0, 20);
                polygon1.lineTo(230, 140);
                polygon1.lineTo(0, 270);
                polygon1.closePath();
                g2d.fill(polygon1);

                GeneralPath polygon2 = new GeneralPath();
                polygon2.moveTo(100, 400);
                polygon2.lineTo(200, 370);
                polygon2.lineTo(180, 470);
                polygon2.closePath();
                g2d.fill(polygon2);

                g2d.fill(new Ellipse2D.Double(250, 450, 40, 40));
                g2d.fill(new Ellipse2D.Double(50, 400, 20, 20));
            }
        };
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(400, 500));

        JLabel titleLabel = new JLabel("Time Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(58, 177, 155));
        JPanel topSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topSection.setOpaque(false);
        topSection.add(titleLabel);
        leftPanel.add(topSection);

        JTextArea description = new JTextArea(
                "Experience seamless time-tracking and employee monitoring with our cutting-edge software designed specifically for companies and remote teams. Effortless HRM is a comprehensive solution equipped with all the essential features.");
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setOpaque(false);
        description.setFont(new Font("Arial", Font.PLAIN, 12));
        description.setMaximumSize(new Dimension(350, 200));
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(description);

        JPanel versionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        versionPanel.setOpaque(false);
        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setForeground(new Color(58, 177, 155));
        versionPanel.add(versionLabel);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(versionPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        topBar.setOpaque(false);
        JButton minimizeButton = new JButton(new ImageIcon("minimize.png"));
        minimizeButton.setBorderPainted(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.addActionListener(e -> minimizeToTray());

        JButton closeButton = new JButton(new ImageIcon("close.png"));
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.addActionListener(e -> closeApplication());

        topBar.add(minimizeButton);
        topBar.add(closeButton);
        rightPanel.add(topBar);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        rightPanel.add(errorLabel);

        String userFullName = loginResult.getData().getUser().getFirstName() + " " + loginResult.getData().getUser().getLastName();
        userLabel = new JLabel(userFullName);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setForeground(new Color(58, 177, 155));
        rightPanel.add(userLabel);
        rightPanel.add(Box.createVerticalStrut(10));

        JPanel timePanel = new JPanel(new GridLayout(4, 2, 5, 5));
        timePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        timePanel.setBackground(Color.WHITE);
        timePanel.add(new JLabel("Current Session:"));
        sessionTimeField = new JTextField("00:00");
        sessionTimeField.setEditable(false);
        timePanel.add(sessionTimeField);
        timePanel.add(new JLabel("Today:"));
        dayTimeField = new JTextField("00:00");
        dayTimeField.setEditable(false);
        timePanel.add(dayTimeField);
        timePanel.add(new JLabel("This week:"));
        weekTimeField = new JTextField("00:00");
        weekTimeField.setEditable(false);
        timePanel.add(weekTimeField);
        timePanel.add(new JLabel("This month:"));
        monthTimeField = new JTextField("00:00");
        monthTimeField.setEditable(false);
        timePanel.add(monthTimeField);
        rightPanel.add(timePanel);
        rightPanel.add(Box.createVerticalStrut(10));

        JPanel projectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        projectPanel.setOpaque(false);
        projectPanel.add(new JLabel("Project:"));
        projectCombo = new JComboBox<>();
        projectCombo.setPreferredSize(new Dimension(330, 25));
        projectPanel.add(projectCombo);
        rightPanel.add(projectPanel);

        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskPanel.setOpaque(false);
        taskPanel.add(new JLabel("Task:"));
        taskCombo = new JComboBox<>();
        taskCombo.setPreferredSize(new Dimension(330, 25));
        taskPanel.add(taskCombo);
        rightPanel.add(taskPanel);

        taskDescription = new JTextArea("Task Description", 2, 30);
        taskDescription.setLineWrap(true);
        taskDescription.setWrapStyleWord(true);
        taskDescription.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        rightPanel.add(taskDescription);
        rightPanel.add(Box.createVerticalStrut(10));

        screenshotLabel = new JLabel();
        screenshotLabel.setPreferredSize(new Dimension(400, 130));
        screenshotLabel.setBorder(BorderFactory.createLineBorder(new Color(172, 176, 175)));
        rightPanel.add(screenshotLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(58, 177, 155));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout());

        startStopButton = new JButton("Start");
        startStopButton.setBackground(new Color(58, 177, 155));
        startStopButton.setForeground(Color.WHITE);
        startStopButton.addActionListener(e -> startStopTracker());

        buttonPanel.add(logoutButton);
        buttonPanel.add(startStopButton);
        rightPanel.add(buttonPanel);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        add(mainPanel);

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - initialClick.x;
                int y = getLocation().y + e.getY() - initialClick.y;
                setLocation(x, y);
            }
        });

        // Initialize JNativeHook for system-wide event tracking
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            logger.log(Level.SEVERE, "Failed to register JNativeHook", ex);
            showErrorMessage("Failed to initialize global event tracking: " + ex.getMessage());
            System.exit(1); // Exit if critical functionality fails
        }

        // Add global mouse and wheel listener
        GlobalScreen.addNativeMouseListener(new NativeMouseInputListener() {
            @Override
            public void nativeMouseClicked(NativeMouseEvent e) {
                if (trackerIsOn) {
                    totalMouseClicks++;
                    lastActivityTime = System.currentTimeMillis();
                    System.out.println("System-wide mouse clicked at (" + e.getX() + ", " + e.getY() + "), totalMouseClicks: " + totalMouseClicks);
                }
            }

            @Override
            public void nativeMousePressed(NativeMouseEvent e) {
                // Optional: Track presses if needed
                System.out.println("Mouse pressed at (" + e.getX() + ", " + e.getY() + ")");
            }

            @Override
            public void nativeMouseReleased(NativeMouseEvent e) {
                // Optional: Track releases if needed
            }

            @Override
            public void nativeMouseMoved(NativeMouseEvent e) {
                // Optional: Track movement if needed
            }

            @Override
            public void nativeMouseDragged(NativeMouseEvent e) {
                // Optional: Track dragging if needed
            }
        });

        GlobalScreen.addNativeMouseWheelListener(new NativeMouseWheelListener() {
            @Override
            public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
                if (trackerIsOn) {
                    totalMouseScrolls++;
                    lastActivityTime = System.currentTimeMillis();
                    System.out.println("System-wide mouse scrolled, direction: " + e.getWheelRotation() + ", totalMouseScrolls: " + totalMouseScrolls);
                }
            }
        });

        // Add global key listener (already works system-wide via AWT, but can be moved here if needed)
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (trackerIsOn && event instanceof KeyEvent) {
                KeyEvent ke = (KeyEvent) event;
                if (ke.getID() == KeyEvent.KEY_PRESSED && !ke.isControlDown() && !ke.isAltDown() && !ke.isShiftDown()) {
                    totalKeysPressed++;
                    lastActivityTime = System.currentTimeMillis();
                    System.out.println("Global key pressed, totalKeysPressed: " + totalKeysPressed);
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);

        setFocusable(true);
        requestFocus();

        initializeUI();
        setupTimers();
        setupSystemTray();

        // Ensure JNativeHook shuts down cleanly
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException ex) {
                    logger.log(Level.SEVERE, "Failed to unregister JNativeHook", ex);
                }
            }
        });
    }

    // Existing methods unchanged
    private void initializeUI() {
        userLabel.setText("Welcome, " + loginResult.getData().getUser().getFirstName() + " " + loginResult.getData().getUser().getLastName());
        bindProjectList();
        fetchInitialTimeTracked();
    }

    private void setupTimers() {
        dispatcherTimer = new Timer(1000, e -> dispatcherTimerTick()); // Update every second
        idleDetectionTimer = new Timer(120000, e -> idleDetectionTimerTick()); // Check every 2 minutes
        screenshotTimer = new Timer(10 * 60 * 1000, e -> saveTimeSlot()); // Every 10 minutes
        screenshotTimer.setInitialDelay(0);
    }

    private String captureAndUploadScreenshot() {
        String resultPath = null;
        try {
            Robot robot = new Robot();

            // Get total dimensions of all screens combined
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] screens = ge.getScreenDevices();
            Rectangle virtualBounds = new Rectangle();
            for (GraphicsDevice screen : screens) {
                Rectangle bounds = screen.getDefaultConfiguration().getBounds();
                virtualBounds = virtualBounds.union(bounds);
            }

            // Capture the screenshot
            BufferedImage screenshot = robot.createScreenCapture(virtualBounds);

            // Create directory structure similar to C# version
            String baseDir = System.getProperty("user.dir"); // Current working directory
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String screenshotsDir = baseDir + File.separator + "Screenshots" + File.separator + dateStr;

            File directory = new File(screenshotsDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate filename with timestamp
            String timeStr = new SimpleDateFormat("HH-mm").format(new Date(System.currentTimeMillis()));
            String fileName = timeStr + ".jpg";
            resultPath = screenshotsDir + File.separator + fileName;

            // Save with JPEG compression similar to C# quality setting (75%)
            File outputFile = new File(resultPath);
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.75f); // 75% quality matching C# version

            try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(screenshot, null, null), param);
            }

            // Update UI (keeping original functionality)
            screenshotLabel.setIcon(new ImageIcon(
                    screenshot.getScaledInstance(400, 130, Image.SCALE_SMOOTH)));
            startScreenshotCountdown(10);

            currentImagePath=resultPath;
            return resultPath;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error capturing screenshot", ex);
            showErrorMessage("Screenshot capture failed: " + ex.getMessage());
            return null;
        } finally {
            // Unlike C#, Java handles resource cleanup automatically via try-with-resources
        }
    }

    private void setupSystemTray() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image trayImage = Toolkit.getDefaultToolkit().getImage("smallLogo.png");
            TrayIcon trayIcon = new TrayIcon(trayImage, "EffortlessHRM - Time Tracker");
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(e -> {
                setVisible(true);
                setState(Frame.NORMAL);
                tray.remove(trayIcon);
            });
            try {
                tray.add(trayIcon);
            } catch (AWTException ex) {
                logger.log(Level.SEVERE, "Failed to add tray icon", ex);
            }
        }
    }

    private void minimizeToTray() {
        if (SystemTray.isSupported()) {
            setVisible(false);
            setupSystemTray();
        } else {
            setState(Frame.ICONIFIED);
        }
    }

    private void closeApplication() {
        if (trackerIsOn) {
            showErrorMessage("Please stop the tracker before closing the application.");
        } else {
            checkForUnsavedLogs();
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void logout() {
        if (trackerIsOn) {
            startStopTracker();
        }
        checkForUnsavedLogs();
        GlobalSetting.getInstance().setLoginResult(null);
        GlobalSetting.getInstance().setTimeTracker(null);
        dispose();
        new LoginFrame().setVisible(true);
    }

    private void startStopTracker() {
//        if (taskCombo.getSelectedItem() == null || taskCombo.getSelectedItem().toString().isEmpty()) {
//            showErrorMessage("No task selected");
//            return;
//        }

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    if (trackerIsOn) {
                        startStopButton.setText("Start");
                        dispatcherTimer.stop();
                        idleDetectionTimer.stop();
                        screenshotTimer.stop();
                        trackingStoppedAt = new Date();
                        saveTimeLog();
                    } else {
                        startStopButton.setText("Stop");
                        trackingStartedAt = new Date();
                        minutesTracked = 0;
                        totalMouseClicks = 0;
                        totalKeysPressed = 0;
                        totalMouseScrolls = 0;
                        dispatcherTimer.start();
                        idleDetectionTimer.start();
                        screenshotTimer.start();
                    }
                    trackerIsOn = !trackerIsOn;
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error in start/stop tracker", ex);
                    showErrorMessage("Error: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    private void saveTimeLog() {
        if (minutesTracked > 0) {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    try {
                        TimeLog timeLog = createTimeLog();
                        REST rest = new REST(new HttpProviders());
                        AddTimeLogAPIResult result = rest.addTimeLog(timeLog).join();
                        if (result.getStatusCode() == 401) {
                            logout();
                            showErrorMessage("Unauthorized access detected. Logging out.");
                        } else if (result.getData() != null && result.getData().getMessage() != null &&
                                result.getData().getMessage().contains("User is logged in on another device")) {
                            handleDeviceSwitch(timeLog);
                        } else {
                            //processUnsavedLogs(rest);
                        }
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Error saving time log", ex);
                        unsavedTimeLogs.add(createTimeLog());
                        showErrorMessage("Error saving time: " + ex.getMessage());
                    }
                    return null;
                }
            }.execute();
        }
    }

    private void dispatcherTimerTick() {
        if (trackerIsOn && trackingStartedAt != null) {
            long currentTime = new Date().getTime();
            long elapsedMillis = currentTime - trackingStartedAt.getTime();
            minutesTracked = elapsedMillis / (1000.0 * 60.0);
            System.out.println("Dispatcher tick - minutesTracked: " + minutesTracked + ", trackingStartedAt: " + trackingStartedAt);
            updateTimeFields();

        } else {
            System.out.println("Dispatcher tick skipped - trackerIsOn: " + trackerIsOn + ", trackingStartedAt: " + trackingStartedAt);
        }
    }

    private void updateTimeFields() {
        SwingUtilities.invokeLater(() -> {
            TimeSpan sessionTime = new TimeSpan((long) (minutesTracked * 60 * 1000));
            sessionTimeField.setText(sessionTime.toString());

            TimeSpan totalDayTime = timeTrackedSaved.add(new TimeSpan((long) (minutesTracked * 60 * 1000)));
            dayTimeField.setText(totalDayTime.toString());

            // Week and month times should be fetched from the server periodically
            // For simplicity, we'll update them in fetchInitialTimeTracked()
        });
    }

    private void bindProjectList() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    REST rest = new REST(new HttpProviders());
                    ProjectRequest request = new ProjectRequest();
                    request.setUserId(loginResult.getData().getUser().getId());
                    GetProjectListAPIResult result = rest.getProjectListByUserId(request).join();

                    if ("success".equals(result.getStatus())) {
                        DefaultComboBoxModel<String> projectModel = new DefaultComboBoxModel<>();
                        for (Project project : result.getData().getProjectList()) {
                            projectModel.addElement(project.getProjectName());
                        }
                        projectCombo.setModel(projectModel);
                        projectCombo.addActionListener(e -> {
                            if (projectCombo.getSelectedIndex() >= 0) {
                                String projectId = result.getData().getProjectList().get(projectCombo.getSelectedIndex()).getId();
                                bindTaskList(projectId);
                            }
                        });
                    } else {
                        showErrorMessage("Failed to load projects: " + result.getStatus());
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error binding project list", ex);
                    showErrorMessage("Error loading projects: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    private void bindTaskList(String projectId) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    if (projectId != null && !projectId.isEmpty()) {
                        REST rest = new REST(new HttpProviders());
                        TaskRequest request = new TaskRequest();
                        request.setProjectId(projectId);
                        request.setUserId(loginResult.getData().getUser().getId());
                        GetTaskListAPIResult taskList = rest.getTaskListByProject(request).join();

                        if ("success".equals(taskList.getStatus()) && taskList.getTaskList() != null) {
                            DefaultComboBoxModel<String> taskModel = new DefaultComboBoxModel<>();
                            for (TaskList task : taskList.getTaskList()) {
                                if (!"closed".equalsIgnoreCase(task.getStatus()) &&
                                        !"done".equalsIgnoreCase(task.getStatus())) {
                                    taskModel.addElement(task.getTaskName());
                                }
                            }
                            SwingUtilities.invokeLater(() -> {
                                taskCombo.setModel(taskModel);
                                if (taskModel.getSize() > 0) {
                                    taskCombo.setSelectedIndex(0);
                                    taskDescription.setText(taskList.getTaskList().get(0).getDescription());
                                }
                            });
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error binding task list", ex);
                    showErrorMessage("Error loading tasks: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setForeground(Color.RED);
        new Timer(10000, e -> errorLabel.setText("")).start();
    }

    private String generateMachineId() {
        StringBuilder id = new StringBuilder();
        try {
            id.append(System.getProperty("os.name"));
            id.append(System.getProperty("user.name"));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error generating machine ID", ex);
        }
        return id.toString();
    }

    // New methods for time tracking functionality

//    private void saveTimeSlot() {
//        captureAndUploadScreenshot();
//        TimeLog timeLog = createTimeLog();
//
//        // Execute in background using CompletableFuture
//        CompletableFuture.supplyAsync(() -> {
//            try {
//                REST rest = new REST(new HttpProviders());
//                AddTimeLogAPIResult result = rest.addTimeLog(timeLog).join();
//                logger.info("API call result: status=" + result.getStatusCode() + ", data=" + result.getData());
//                //logger.info("API call result: " + result); // Log immediately after API call
//                if (result.getStatusCode() == 401) {
//                    logout();
//                    showErrorMessage("Unauthorized access detected. Logging out.");
//                    return null;
//                } else if (result.getStatusCode() == 200) {
//                    String message = result.getData() != null ? result.getData().getMessage() : "";
//                    logger.info("Success - Message: " + message); // Log success case
//                    processUnsavedLogs(rest);
//                    minutesTracked = 0;
//                    totalMouseClicks = 0;
//                    totalKeysPressed = 0;
//                    totalMouseScrolls = 0;
//                    trackingStartedAt = new Date();
//                    fetchInitialTimeTracked();
//                    logger.info("Successfully saved time slot for machineId: " + machineId);
//                    return result.getData();
//                } else {
//                    logger.warning("Unexpected status code: " + result.getStatusCode());
//                    unsavedTimeLogs.add(timeLog);
//                    showErrorMessage("Failed to save time slot. Status: " + result.getStatusCode());
//                    return null;
//                }
//            } catch (Exception ex) {
//                logger.log(Level.SEVERE, "Error saving time slot", ex);
//                unsavedTimeLogs.add(timeLog);
//                showErrorMessage("Error saving time slot: " + ex.getMessage());
//                return null;
//            }
//        }).thenAccept(result -> {
//            logger.info("Final result in thenAccept: " + result); // Log in thenAccept
//            if (result != null) {
//                System.out.println("Time slot saved: " + result);
//            }
//        });
//    }

    private void saveTimeSlot() {
        captureAndUploadScreenshot();
        TimeLog timeLog = createTimeLog();

        try {
            REST rest = new REST(new HttpProviders());
            AddTimeLogAPIResult result = rest.addTimeLog(timeLog).join(); // Synchronous execution
            if (result.getStatusCode() == 401) {
                logout();
                showErrorMessage("Unauthorized access detected. Logging out.");
            } else if (result.getStatusCode() == 200) {
                String message = result.getData() != null ? result.getData().getMessage() : "";
                //processUnsavedLogs(rest);
                minutesTracked = 0;
                totalMouseClicks = 0;
                totalKeysPressed = 0;
                totalMouseScrolls = 0;
                trackingStartedAt = new Date();
                fetchInitialTimeTracked();
                logger.info("Successfully saved time slot for machineId: " + machineId);
                System.out.println("Time slot saved: " + result.getData()); // Inspect here
            } else {
                logger.warning("Unexpected status code: " + result.getStatusCode());
                unsavedTimeLogs.add(timeLog);
                showErrorMessage("Failed to save time slot. Status: " + result.getStatusCode());
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error saving time slot", ex);
            unsavedTimeLogs.add(timeLog);
            showErrorMessage("Error saving time slot: " + ex.getMessage());
        }
    }

    private TimeLog createTimeLog() {
        TimeLog timeLog = new TimeLog();
        timeLog.setUser(loginResult.getData().getUser().getId());
        timeLog.setDate(new Date());
        timeLog.setTask("67ec145c5ff9b7f9a03a94b3");
        timeLog.setStartTime(new Date(trackingStartedAt.getTime()));
        timeLog.setEndTime(new Date());
        if (currentImagePath != null && !currentImagePath.isEmpty()) {
            try {
                byte[] fileBytes = Files.readAllBytes(new File(currentImagePath).toPath());
                timeLog.setFileString(Base64.getEncoder().encodeToString(fileBytes));
                timeLog.setFilePath(new File(currentImagePath).getName());
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error reading screenshot file", ex);
            }
        }
        timeLog.setKeysPressed(totalKeysPressed);
        timeLog.setClicks(totalMouseClicks);
        timeLog.setScrolls(totalMouseScrolls);
        timeLog.setProject(getSelectedProjectId());
        timeLog.setMachineId(machineId);
        timeLog.setMakeThisDeviceActive(false);
        return timeLog;
    }

    private void startScreenshotCountdown(int seconds) {
        Timer countdownTimer = new Timer(1000, new ActionListener() {
            int count = seconds;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count > 0) {
                    screenshotLabel.setText("(" + count + ")");
                    count--;
                } else {
                    screenshotLabel.setIcon(null);
                    screenshotLabel.setText("");
                    deleteScreenshot();
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        countdownTimer.start();
    }

    private void deleteScreenshot() {
        if (currentImagePath != null && !currentImagePath.isEmpty()) {
            File file = new File(currentImagePath);
            if (file.exists()) {
                file.delete();
            }
            currentImagePath = null;
        }
    }

    private void resetTrackingData() {
        minutesTracked = 0;
        totalMouseClicks = 0;
        totalKeysPressed = 0;
        totalMouseScrolls = 0;
        trackingStartedAt = new Date();
    }

    private void idleDetectionTimerTick() {
        if (trackerIsOn) {
            long idleTime = (System.currentTimeMillis() - lastActivityTime) / (1000 * 60); // Minutes
            if (idleTime >= 4) {
                startStopTracker();
                userIsInactive = true;
                showErrorMessage("Idle detected. Tracker stopped.");
            } else if (userIsInactive && idleTime < 4) {
                startStopTracker();
                userIsInactive = false;
                fetchInitialTimeTracked();
            }
        }
    }

    private void fetchInitialTimeTracked() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    REST rest = new REST(new HttpProviders());
                    TimeLog request = new TimeLog();
                    request.setUser(loginResult.getData().getUser().getId());
                    request.setDate(new Date());
                    GetTimeLogAPIResult result = rest.getTimeLogs(request).join();
                    TimeSpan totalDay = new TimeSpan(0);
                    if (result.getData() != null) {
                        for (TimeLogBaseResponse log : result.getData()) {
                            long diff = log.getEndTime().getTime() - log.getStartTime().getTime();
                            totalDay = totalDay.add(new TimeSpan(diff));
                        }
                    }
                    timeTrackedSaved = totalDay;

                    // Fetch week and month data
                    CurrentWeekTotalTime weekRequest = new CurrentWeekTotalTime();
                    weekRequest.setUser(loginResult.getData().getUser().getId());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                    weekRequest.setStartDate(cal.getTime());
                    weekRequest.setEndDate(new Date());
                    GetTimeLogAPIResult weekResult = rest.getCurrentWeekTotalTime(weekRequest).join();
                    TimeSpan weekTime = new TimeSpan(weekResult.getData().size() * 10 * 60 * 1000L);

                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    CurrentWeekTotalTime monthRequest = new CurrentWeekTotalTime();
                    monthRequest.setUser(loginResult.getData().getUser().getId());
                    monthRequest.setStartDate(cal.getTime());
                    monthRequest.setEndDate(new Date());
                    GetTimeLogAPIResult monthResult = rest.getCurrentWeekTotalTime(monthRequest).join();
                    TimeSpan monthTime = new TimeSpan(monthResult.getData().size() * 10 * 60 * 1000L);

                    TimeSpan finalTotalDay = totalDay;
                    SwingUtilities.invokeLater(() -> {
                        dayTimeField.setText(finalTotalDay.toString());
                        weekTimeField.setText(weekTime.toString());
                        monthTimeField.setText(monthTime.toString());
                    });
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error fetching tracked time", ex);
                }
                return null;
            }
        }.execute();
    }

    private void processUnsavedLogs(REST rest) {
        if (!unsavedTimeLogs.isEmpty()) {
            List<TimeLog> tempLogs = new ArrayList<>(unsavedTimeLogs);
            for (TimeLog log : tempLogs) {
                try {
                    rest.addTimeLog(log).join();
                    unsavedTimeLogs.remove(log);
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error processing unsaved log", ex);
                }
            }
        }
    }

    private void checkForUnsavedLogs() {
        if (!unsavedTimeLogs.isEmpty()) {
            int result = JOptionPane.showConfirmDialog(this,
                    "There are unsaved logs. Do you want to save them before closing?",
                    "Unsaved Logs", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                REST rest = new REST(new HttpProviders());
                //processUnsavedLogs(rest);
            }
        }
    }

    private void handleDeviceSwitch(TimeLog timeLog) {
        int result = JOptionPane.showConfirmDialog(this,
                "User is logged in on another device. Make this device active?",
                "Device Switch", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            timeLog.setMakeThisDeviceActive(true);
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    try {
                        REST rest = new REST(new HttpProviders());
                        rest.addTimeLog(timeLog).join();
                        minutesTracked = 0;
                        totalMouseClicks = 0;
                        totalKeysPressed = 0;
                        totalMouseScrolls = 0;
                        fetchInitialTimeTracked();
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Error switching device", ex);
                        unsavedTimeLogs.add(timeLog);
                    }
                    return null;
                }
            }.execute();
        } else {
            startStopTracker();
        }
    }

    private String getSelectedProjectId() {
        // Assuming Project class has getId() method
        REST rest = new REST(new HttpProviders());
        ProjectRequest request = new ProjectRequest();
        request.setUserId(loginResult.getData().getUser().getId());
        try {
            GetProjectListAPIResult result = rest.getProjectListByUserId(request).join();
            int index = projectCombo.getSelectedIndex();
            if (index >= 0) {
                return result.getData().getProjectList().get(index).getId();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error getting project ID", ex);
        }
        return null;
    }

    private String getSelectedTaskId() {
        // Assuming TaskList class has getId() method
        String projectId = getSelectedProjectId();
        if (projectId != null) {
            REST rest = new REST(new HttpProviders());
            TaskRequest request = new TaskRequest();
            request.setProjectId(projectId);
            request.setUserId(loginResult.getData().getUser().getId());
            try {
                GetTaskListAPIResult taskList = rest.getTaskListByProject(request).join();
                int index = taskCombo.getSelectedIndex();
                if (index >= 0) {
                    return taskList.getTaskList().get(index).getId();
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error getting task ID", ex);
            }
        }
        return null;
    }
}

// Simplified TimeSpan class for Java
class TimeSpan {
    private long milliseconds;

    public TimeSpan(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public TimeSpan add(TimeSpan other) {
        return new TimeSpan(this.milliseconds + other.milliseconds);
    }

    public int getHours() {
        return (int) (milliseconds / (1000 * 60 * 60));
    }

    public int getMinutes() {
        return (int) ((milliseconds / (1000 * 60)) % 60);
    }

    public int getSeconds() {
        return (int) ((milliseconds / 1000) % 60);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds());
    }
}