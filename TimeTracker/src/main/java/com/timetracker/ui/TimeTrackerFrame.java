package com.timetracker.ui;

import com.timetracker.viewmodels.LoginViewModel;
import com.timetracker.viewmodels.TimeTrackerViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TimeTrackerFrame extends JFrame implements PropertyChangeListener {
    private final TimeTrackerViewModel viewModel;
    private JLabel userFullNameLabel;
    private JComboBox<String> projectComboBox;
    private JComboBox<String> taskComboBox;
    private JTextArea taskDescriptionArea;
    private JLabel sessionTimeLabel;
    private JLabel dayTimeLabel;
    private JLabel weekTimeLabel;
    private JLabel monthTimeLabel;
    private JLabel errorLabel;
    private JButton startStopButton;
    private JLabel screenshotLabel;
    private JPopupMenu loggingMenu;

    public TimeTrackerFrame(TimeTrackerViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);
        initializeUI();
        initializeSystemTray();
    }

    private void initializeUI() {
        setTitle("Time Tracker");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left Panel (Logo, Description)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(58, 173, 161)); // Mimic gradient
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Time Tracker", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea(
                "Experience seamless time-tracking and employee monitoring with our cutting-edge software..."
        );
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(leftPanel.getBackground());
        descriptionArea.setForeground(Color.WHITE);
        leftPanel.add(descriptionArea, BorderLayout.CENTER);

        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setForeground(Color.WHITE);
        leftPanel.add(versionLabel, BorderLayout.SOUTH);

        // Right Panel (Controls)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top: User Info and Buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        userFullNameLabel = new JLabel(viewModel.getUserFullName());
        userFullNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(userFullNameLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton newTaskButton = new JButton("New Task");
        newTaskButton.addActionListener(e -> viewModel.createNewTask());
        JButton completeTaskButton = new JButton("Complete Task");
        completeTaskButton.addActionListener(e -> viewModel.completeTask());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> viewModel.refresh());
        JButton openTaskButton = new JButton("Open Task");
        openTaskButton.addActionListener(e -> viewModel.openTask());
        buttonPanel.add(newTaskButton);
        buttonPanel.add(completeTaskButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(openTaskButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        rightPanel.add(topPanel, BorderLayout.NORTH);

        // Center: Time and Task Info
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Time Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Current Session:"), gbc);
        gbc.gridx = 1;
        sessionTimeLabel = new JLabel("0 hrs 00 m");
        centerPanel.add(sessionTimeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Today:"), gbc);
        gbc.gridx = 1;
        dayTimeLabel = new JLabel("0 hrs 00 m");
        centerPanel.add(dayTimeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(new JLabel("This Week:"), gbc);
        gbc.gridx = 1;
        weekTimeLabel = new JLabel("0 hrs 00 m");
        centerPanel.add(weekTimeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(new JLabel("This Month:"), gbc);
        gbc.gridx = 1;
        monthTimeLabel = new JLabel("0 hrs 00 m");
        centerPanel.add(monthTimeLabel, gbc);

        // Project and Task
        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(new JLabel("Project:"), gbc);
        gbc.gridx = 1;
        projectComboBox = new JComboBox<>();
        projectComboBox.addActionListener(e -> {
            if (projectComboBox.getSelectedIndex() >= 0) {
                viewModel.setSelectedProject(projectComboBox.getSelectedIndex());
            }
        });
        centerPanel.add(projectComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        centerPanel.add(new JLabel("Task:"), gbc);
        gbc.gridx = 1;
        taskComboBox = new JComboBox<>();
        taskComboBox.setEditable(true);
        taskComboBox.addActionListener(e -> {
            if (taskComboBox.getSelectedIndex() >= 0) {
                viewModel.setSelectedTask(taskComboBox.getSelectedIndex());
            }
        });
        taskComboBox.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                viewModel.setTaskName(taskComboBox.getEditor().getItem().toString());
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    viewModel.createNewTask();
                }
            }
        });
        centerPanel.add(taskComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        centerPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        taskDescriptionArea = new JTextArea(3, 20);
        taskDescriptionArea.setLineWrap(true);
        taskDescriptionArea.setWrapStyleWord(true);
        taskDescriptionArea.getDocument().addDocumentListener(new SimpleDocumentListener(() -> viewModel.setTaskDescription(taskDescriptionArea.getText())));
        centerPanel.add(new JScrollPane(taskDescriptionArea), gbc);

        rightPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom: Screenshot and Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        screenshotLabel = new JLabel();
        screenshotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(screenshotLabel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> viewModel.logout());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> viewModel.close());
        startStopButton = new JButton(viewModel.getStartStopButtonText());
        startStopButton.addActionListener(e -> viewModel.startStop());
        actionPanel.add(logoutButton);
        actionPanel.add(closeButton);
        actionPanel.add(startStopButton);

        // Logging Menu
        loggingMenu = new JPopupMenu();
        JCheckBoxMenuItem infoLog = new JCheckBoxMenuItem("Enable Info Logging", true);
        JCheckBoxMenuItem warnLog = new JCheckBoxMenuItem("Enable Warning Logging", true);
        JCheckBoxMenuItem errorLog = new JCheckBoxMenuItem("Enable Error Logging", true);
        loggingMenu.add(infoLog);
        loggingMenu.add(warnLog);
        loggingMenu.add(errorLog);
        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> loggingMenu.show(menuButton, menuButton.getWidth(), 0));
        actionPanel.add(menuButton);

        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        rightPanel.add(errorLabel, BorderLayout.NORTH);

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // Draggable Window
        final Point[] dragStart = {null};
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart[0] = e.getPoint();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStart[0] != null) {
                    Point dragEnd = e.getLocationOnScreen();
                    setLocation(dragEnd.x - dragStart[0].x, dragEnd.y - dragStart[0].y);
                }
            }
        });
    }

    private void initializeSystemTray() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("path/to/smallLogo.png");
            PopupMenu popup = new PopupMenu();
            MenuItem restoreItem = new MenuItem("Restore");
            restoreItem.addActionListener(e -> {
                setVisible(true);
                setExtendedState(JFrame.NORMAL);
            });
            popup.add(restoreItem);

            TrayIcon trayIcon = new TrayIcon(image, "Time Tracker", popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(e -> {
                setVisible(true);
                setExtendedState(JFrame.NORMAL);
            });

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }

            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowIconified(java.awt.event.WindowEvent e) {
                    setVisible(false);
                }
            });
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "userFullName":
                userFullNameLabel.setText((String) evt.getNewValue());
                break;
            case "projects":
                projectComboBox.removeAllItems();
                for (String project : (java.util.List<String>) evt.getNewValue()) {
                    projectComboBox.addItem(project);
                }
                break;
            case "tasks":
                taskComboBox.removeAllItems();
                for (String task : (java.util.List<String>) evt.getNewValue()) {
                    taskComboBox.addItem(task);
                }
                break;
            case "taskName":
                taskComboBox.getEditor().setItem(evt.getNewValue());
                break;
            case "taskDescription":
                taskDescriptionArea.setText((String) evt.getNewValue());
                break;
            case "currentSessionTimeTracked":
                sessionTimeLabel.setText((String) evt.getNewValue());
                break;
            case "currentDayTimeTracked":
                dayTimeLabel.setText((String) evt.getNewValue());
                break;
            case "currentWeekTimeTracked":
                weekTimeLabel.setText((String) evt.getNewValue());
                break;
            case "currentMonthTimeTracked":
                monthTimeLabel.setText((String) evt.getNewValue());
                break;
            case "startStopButtonText":
                startStopButton.setText((String) evt.getNewValue());
                break;
            case "currentImagePath":
                String path = (String) evt.getNewValue();
                if (path != null && !path.isEmpty()) {
                    ImageIcon icon = new ImageIcon(path);
                    Image scaled = icon.getImage().getScaledInstance(400, 130, Image.SCALE_SMOOTH);
                    screenshotLabel.setIcon(new ImageIcon(scaled));
                } else {
                    screenshotLabel.setIcon(null);
                }
                break;
            case "errorMessage":
                errorLabel.setText((String) evt.getNewValue());
                break;
            case "logout":
                SwingUtilities.invokeLater(() -> {
                    new LoginFrame(new LoginViewModel()).setVisible(true);
                    dispose();
                });
                break;
        }
    }
}