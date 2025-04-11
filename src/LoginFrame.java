import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private boolean automaticLogin = true;
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

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel(new ImageIcon("logo.png"));
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(logoLabel);
        leftPanel.add(Box.createVerticalStrut(50));

        JLabel signUpTitle = new JLabel("Sign Up");
        signUpTitle.setFont(new Font("Arial", Font.BOLD, 32));
        signUpTitle.setForeground(Color.WHITE);
        signUpTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(signUpTitle);

        JLabel signUpText = new JLabel("<html>Enter your personal info and create new<br>account to connect us</html>");
        signUpText.setFont(new Font("Arial", Font.PLAIN, 16));
        signUpText.setForeground(Color.WHITE);
        signUpText.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(signUpText);
        leftPanel.add(Box.createVerticalStrut(30));

        JButton signUpButton = createStyledButton("Sign Up", "button");
        signUpButton.addActionListener(e -> viewModel.openSignUpPageCommandExecute());
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(signUpButton);

        JPanel versionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        versionPanel.setOpaque(false);
        versionPanel.add(new JLabel("Version"));
        JLabel currentVersion = new JLabel("1.0.0");
        versionPanel.add(currentVersion);
        leftPanel.add(Box.createVerticalGlue());
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

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        JLabel signInTitle = new JLabel("Sign in to App");
        signInTitle.setFont(new Font("Arial", Font.BOLD, 32));
        signInTitle.setForeground(new Color(0x3AB19B));
        titlePanel.add(signInTitle);
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(30, 25));
        progressBar.setVisible(false);
        titlePanel.add(progressBar);
        rightPanel.add(titlePanel);

        JPanel socialPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        socialPanel.setBackground(Color.WHITE);
        String[] socialIcons = {"google", "facebook", "linkedin", "x", "youtube", "instagram"};
        for (String icon : socialIcons) {
            JButton btn = createCircleButton(icon);
            final String pageName = icon + "PageURL";
            btn.addActionListener(e -> viewModel.openSocialMediaPageCommandExecute(pageName));
            socialPanel.add(btn);
        }
        rightPanel.add(socialPanel);

        JLabel emailHint = new JLabel("or use your email info :");
        emailHint.setFont(new Font("Arial", Font.PLAIN, 16));
        emailHint.setForeground(new Color(0x878787));
        emailHint.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(emailHint);

        JPanel usernamePanel = createInputPanel("Email", "email.png");
        txtUsername = (JTextField) usernamePanel.getComponent(2);
        textEmail = (JLabel) usernamePanel.getComponent(1);
        rightPanel.add(usernamePanel);

        JPanel passwordPanel = createInputPanel("Password", "lock.png");
        txtPassword = (JPasswordField) passwordPanel.getComponent(2);
        passwordHint = (JLabel) passwordPanel.getComponent(1);
        rightPanel.add(passwordPanel);

        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkPanel.setBackground(Color.WHITE);
        checkPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xACB0AF), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        chkRememberMe = new JCheckBox("Remember Me");
        chkRememberMe.setFont(new Font("Arial", Font.PLAIN, 14));
        chkRememberMe.setForeground(new Color(0x878787));
        checkPanel.add(chkRememberMe);
        rightPanel.add(checkPanel);

        errorMessageLabel = new JLabel("");
        errorMessageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(errorMessageLabel);

        btnLogOn = createStyledButton("Sign In", "mainButton");
        btnLogOn.addActionListener(e -> viewModel.loginCommandExecute());
        btnLogOn.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(btnLogOn);

        gbc.gridx = 1;
        gbc.weightx = 1.5;
        mainPanel.add(rightPanel, gbc);

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        topPanel.setOpaque(false);
        JButton minimizeBtn = new JButton(new ImageIcon("minimize.png"));
        minimizeBtn.setPreferredSize(new Dimension(20, 20));
        minimizeBtn.addActionListener(e -> setState(Frame.ICONIFIED));
        topPanel.add(minimizeBtn);
        JButton closeBtn = new JButton(new ImageIcon("close.png"));
        closeBtn.setPreferredSize(new Dimension(20, 20));
        closeBtn.addActionListener(e -> viewModel.closeCommandExecute());
        topPanel.add(closeBtn);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(topPanel, gbc);

        add(mainPanel);

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
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xACB0AF), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel icon = new JLabel(new ImageIcon(iconPath));
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
        gbc.gridx = 1;
        panel.add(input, gbc);

        return panel;
    }

    private JButton createStyledButton(String text, String style) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        if (style.equals("mainButton")) {
            button.setBackground(new Color(0x3AB19B));
            button.setForeground(new Color(0xFDFEFE));
            button.setPreferredSize(new Dimension(200, 50));
        } else {
            button.setBackground(new Color(0, 0, 0, 0));
            button.setForeground(new Color(0xFDFEFE));
            button.setPreferredSize(new Dimension(170, 50));
        }
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        button.setUI(new RoundedButtonUI());
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0xE8E8E8));
                button.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(style.equals("mainButton") ? new Color(0x3AB19B) : new Color(0, 0, 0, 0));
                button.setForeground(new Color(0xFDFEFE));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(0x2D7A6C));
                button.setForeground(Color.WHITE);
            }
        });
        return button;
    }

    private JButton createCircleButton(String iconName) {
        JButton button = new JButton(new ImageIcon(iconName + ".png"));
        button.setPreferredSize(new Dimension(60, 60));
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(BorderFactory.createLineBorder(new Color(0x878787), 1));
        button.setUI(new RoundedButtonUI(30));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0xD9D9D9));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 0));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(0xC4C4C4));
            }
        });
        return button;
    }

    private void addListeners() {
        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                viewModel.setUserName(txtUsername.getText());
                textEmail.setVisible(txtUsername.getText().isEmpty());
            }
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                viewModel.setPassword(new String(txtPassword.getPassword()));
                passwordHint.setVisible(txtPassword.getPassword().length == 0);
            }
        });

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
        }
        if (!savedPassword.isEmpty()) {
            txtPassword.setText(savedPassword);
            viewModel.setPassword(savedPassword);
            chkRememberMe.setSelected(true);
            viewModel.setRememberMe(true);
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
    }
}

class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
    private int radius;

    public RoundedButtonUI() {
        this(25);
    }

    public RoundedButtonUI(int radius) {
        this.radius = radius;
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, AbstractButton b, int offset) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(b.getBackground());
        g2.fillRoundRect(offset, offset, b.getWidth() - offset * 2, b.getHeight() - offset * 2, radius, radius);
        g2.dispose();
    }
}