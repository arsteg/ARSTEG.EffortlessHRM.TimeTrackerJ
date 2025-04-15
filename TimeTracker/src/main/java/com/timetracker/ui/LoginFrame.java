package com.timetracker.ui;

import com.timetracker.viewmodels.LoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.prefs.Preferences;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkRememberMe;
    private JLabel passwordHint;
    private JLabel textEmail;
    private JButton btnLogOn;
    private JLabel errorMessageLabel;
    private JProgressBar progressBar;
    private LoginViewModel viewModel;
    private boolean automaticLogin;
    private Preferences prefs = Preferences.userNodeForPackage(LoginFrame.class);

    public LoginFrame() {
        this(true);
    }

    public LoginFrame(boolean automaticLogin) {
        this.automaticLogin = automaticLogin;
        viewModel = new LoginViewModel(this);
        initUI();
        addListeners();
        loadSettings();
    }

    private void initUI() {
        setTitle("Time Tracker - Login");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, getWidth(), getHeight(),
                        new Color(0x3AADA1), true);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Left panel with decorations
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Decorative shapes in #4EB1B6
                g2d.setColor(new Color(0x4EB1B6));

                // Polygon 1: 0,20,230,140,0,270
                GeneralPath polygon1 = new GeneralPath();
                polygon1.moveTo(0, 20);
                polygon1.lineTo(230, 140);
                polygon1.lineTo(0, 270);
                polygon1.closePath();
                g2d.fill(polygon1);

                // Polygon 2: 100,400,200,370,180,470
                GeneralPath polygon2 = new GeneralPath();
                polygon2.moveTo(100, 400);
                polygon2.lineTo(200, 370);
                polygon2.lineTo(180, 470);
                polygon2.closePath();
                g2d.fill(polygon2);

                // Ellipse 1: 250,450, 40x40
                g2d.fill(new Ellipse2D.Double(250, 450, 40, 40));

                // Ellipse 2: 50,400, 20x20
                g2d.fill(new Ellipse2D.Double(50, 400, 20, 20));
            }
        };
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Logo
//        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
//        Image scaledImage = logoIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
//        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
//        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
//        logoLabel.setPreferredSize(new Dimension(25, 25)); // Optional, for safety
//        leftPanel.add(logoLabel);
//        leftPanel.add(Box.createVerticalStrut(50));
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
        System.out.println("Logo resource: " + getClass().getResource("/images/logo.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(50, 25, Image.SCALE_SMOOTH); // Width 50px
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0)); // 5px padding
        logoLabel.setPreferredSize(new Dimension(50, 25)); // Match new width

// Wrap in a left-aligned panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoPanel.setOpaque(false);
        logoPanel.add(logoLabel);

        leftPanel.add(logoPanel);
        leftPanel.add(Box.createVerticalStrut(50));

        // Sign-up section
        JLabel signUpTitle = new JLabel("Sign Up", SwingConstants.CENTER);
        signUpTitle.setFont(new Font("Arial", Font.BOLD, 32));
        signUpTitle.setForeground(Color.WHITE);
        signUpTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(signUpTitle);

        JLabel signUpText = new JLabel("<html>Enter your personal info and create new<br>account to connect us</html>", SwingConstants.CENTER);
        signUpText.setFont(new Font("Arial", Font.PLAIN, 16));
        signUpText.setForeground(new Color(0xFFFFFF));
        signUpText.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpText.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        leftPanel.add(signUpText);

        JButton signUpButton = createStyledButton("Sign Up", "button");
        signUpButton.addActionListener(e -> viewModel.openSignUpPageCommandExecute());
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(signUpButton);

// Add glue to push versionPanel to the bottom
        leftPanel.add(Box.createVerticalGlue());




        // Version info
        JPanel versionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        versionPanel.setOpaque(false);
        JLabel versionLabel = new JLabel("Version");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        versionLabel.setForeground(Color.WHITE);
        versionPanel.add(versionLabel);
        JLabel currentVersion = new JLabel("1.0.0");
        currentVersion.setFont(new Font("Arial", Font.PLAIN, 16));
        currentVersion.setForeground(Color.WHITE);
        versionPanel.add(currentVersion);
        leftPanel.add(versionPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(leftPanel, gbc);

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 70, 20, 70));
        rightPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        // Title with progress
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        JLabel signInTitle = new JLabel("Sign in to App");
        signInTitle.setFont(new Font("Arial", Font.BOLD, 32));
        signInTitle.setForeground(new Color(0x3AB19B));
        titlePanel.add(signInTitle);
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(25, 25));
        progressBar.setVisible(false);
        progressBar.setForeground(new Color(0x3AB19B));
        titlePanel.add(Box.createHorizontalStrut(5));
        titlePanel.add(progressBar);
        rightPanel.add(titlePanel);

        // Social media buttons
        JPanel socialPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        socialPanel.setBackground(Color.WHITE);
        String[] socialIcons = {"google", "facebook", "linkedin", "x", "youtube", "instagram"};
        for (String icon : socialIcons) {
            JButton btn = createCircleButton(icon);
            final String pageName = icon + "PageURL";
            btn.addActionListener(e -> viewModel.openSocialMediaPageCommandExecute(pageName));
            socialPanel.add(btn);
        }
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(socialPanel);

        // Email hint
        JLabel emailHint = new JLabel("or use your email info :", SwingConstants.CENTER);
        emailHint.setFont(new Font("Arial", Font.PLAIN, 16));
        emailHint.setForeground(new Color(0x878787));
        emailHint.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(emailHint);

        // Username input
        JPanel usernamePanel = createInputPanel("Email", "email.png");
        txtUsername = (JTextField) usernamePanel.getComponent(2);
        textEmail = (JLabel) usernamePanel.getComponent(1);
        rightPanel.add(Box.createVerticalStrut(7));
        rightPanel.add(usernamePanel);

        // Password input
        JPanel passwordPanel = createInputPanel("Password", "lock.png");
        txtPassword = (JPasswordField) passwordPanel.getComponent(2);
        passwordHint = (JLabel) passwordPanel.getComponent(1);
        rightPanel.add(Box.createVerticalStrut(7));
        rightPanel.add(passwordPanel);

        // Remember Me checkbox
        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkPanel.setBackground(Color.WHITE);
        checkPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xACB0AF), 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        chkRememberMe = new JCheckBox("Remember Me") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g);
            }
        };
        chkRememberMe.setFont(new Font("Arial", Font.PLAIN, 14));
        chkRememberMe.setForeground(new Color(0x878787));
        chkRememberMe.setOpaque(false);
        chkRememberMe.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDAE2EA), 1, true),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        chkRememberMe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                chkRememberMe.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0x9575CD), 1, true),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chkRememberMe.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0xDAE2EA), 1, true),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)));
            }
        });
        checkPanel.add(chkRememberMe);
        rightPanel.add(Box.createVerticalStrut(7));
        rightPanel.add(checkPanel);

        // Error message
        errorMessageLabel = new JLabel("", SwingConstants.CENTER);
        errorMessageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(errorMessageLabel);

        // Sign-in button
        btnLogOn = createStyledButton("Sign In", "mainButton");
        btnLogOn.addActionListener(e -> viewModel.loginCommandExecute());
        btnLogOn.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(btnLogOn);

        gbc.gridx = 1;
        gbc.weightx = 1.5;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(rightPanel, gbc);

        // Top panel (minimize/close)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        topPanel.setOpaque(false);
        JButton minimizeBtn = new JButton(new ImageIcon("minimize.png")) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2d.scale(1.1, 1.1);
                    g2d.translate(-1, -1);
                }
                super.paintComponent(g2d);
            }
        };
        minimizeBtn.setPreferredSize(new Dimension(20, 20));
        minimizeBtn.setContentAreaFilled(false);
        minimizeBtn.setBorderPainted(false);
        minimizeBtn.addActionListener(e -> setState(Frame.ICONIFIED));
        topPanel.add(minimizeBtn);

        JButton closeBtn = new JButton(new ImageIcon("close.png")) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2d.scale(1.1, 1.1);
                    g2d.translate(-1, -1);
                }
                super.paintComponent(g2d);
            }
        };
        closeBtn.setPreferredSize(new Dimension(20, 20));
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.addActionListener(e -> viewModel.closeCommandExecute());
        topPanel.add(closeBtn);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(topPanel, gbc);

        add(mainPanel);

        // Dragging support
        Point origin = new Point();
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
    }

    private JPanel createInputPanel(String hintText, String iconPath) {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
            }
        };
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xACB0AF), 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel icon = new JLabel(new ImageIcon(iconPath));
        icon.setPreferredSize(new Dimension(20, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        panel.add(icon, gbc);

        JLabel hint = new JLabel(hintText);
        hint.setFont(new Font("Arial", Font.PLAIN, 14));
        hint.setForeground(new Color(0xACB0AF));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(hint, gbc);

        JComponent input = hintText.equals("Password") ? new JPasswordField(20) : new JTextField(20);
        input.setFont(new Font("Arial", Font.PLAIN, 14));
        input.setForeground(new Color(0x878787));
        input.setBorder(BorderFactory.createEmptyBorder());
        input.setOpaque(false);
        gbc.gridx = 1;
        panel.add(input, gbc);

        return panel;
    }

    private JButton createStyledButton(String text, String style) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(0x2D7A6C));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(0xE8E8E8));
                } else {
                    g2d.setColor(style.equals("mainButton") ? new Color(0x3AB19B) : new Color(0, 0, 0, 0));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2d.setColor(Color.WHITE);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(new Color(0xFDFEFE));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        if (style.equals("mainButton")) {
            button.setPreferredSize(new Dimension(200, 50));
        } else {
            button.setPreferredSize(new Dimension(170, 50));
        }
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.DARK_GRAY);
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(0xFDFEFE));
                button.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setForeground(Color.WHITE);
                button.repaint();
            }
        });
        return button;
    }

    private JButton createCircleButton(String iconName) {
        JButton button = new JButton(new ImageIcon(iconName + ".png")) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(0xC4C4C4));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(0xD9D9D9));
                } else {
                    g2d.setColor(new Color(0, 0, 0, 0));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 60, 60);
                g2d.setColor(new Color(0x878787));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 60, 60);
                super.paintComponent(g);
            }
        };
        button.setPreferredSize(new Dimension(60, 60));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    private void addListeners() {
        txtUsername.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            viewModel.setUserName(txtUsername.getText());
            textEmail.setVisible(txtUsername.getText().isEmpty());
        }));

        txtPassword.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            viewModel.setPassword(new String(txtPassword.getPassword()));
            passwordHint.setVisible(txtPassword.getPassword().length == 0);
        }));

        txtUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textEmail.setVisible(txtUsername.getText().isEmpty());
            }
        });

        txtPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordHint.setVisible(txtPassword.getPassword().length == 0);
            }
        });

        chkRememberMe.addActionListener(e -> viewModel.setRememberMe(chkRememberMe.isSelected()));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                if (automaticLogin) {
                    viewModel.loginCommandExecute();
                }
            }
        });
    }

    private void loadSettings() {
        String savedUsername = prefs.get("username", "");
        String savedPassword = prefs.get("password", "");
        if (!savedUsername.isEmpty()) {
            txtUsername.setText(savedUsername);
            viewModel.setUserName(savedUsername);
            textEmail.setVisible(false);
        }
        if (!savedPassword.isEmpty()) {
            txtPassword.setText(savedPassword);
            viewModel.setPassword(savedPassword);
            chkRememberMe.setSelected(true);
            viewModel.setRememberMe(true);
            passwordHint.setVisible(false);
        }
    }

    public void updateUI() {
        txtUsername.setText(viewModel.getUserName());
        txtPassword.setText(viewModel.getPassword());
        chkRememberMe.setSelected(viewModel.isRememberMe());
        btnLogOn.setEnabled(viewModel.isEnableLoginButton());
        progressBar.setValue(viewModel.getProgressWidth());
        progressBar.setVisible(viewModel.getProgressWidth() > 0);
        errorMessageLabel.setText(viewModel.getErrorMessage());
        textEmail.setVisible(txtUsername.getText().isEmpty());
        passwordHint.setVisible(txtPassword.getPassword().length == 0);
    }
}

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