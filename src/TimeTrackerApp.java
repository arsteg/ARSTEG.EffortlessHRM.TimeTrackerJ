import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class TimeTrackerApp {
    private static FileLock lock;
    private static FileChannel channel;

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        });

        if (!createMutex()) {
            JOptionPane.showMessageDialog(null, "An instance is already running...", "Time Tracker", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                // Fallback to default
            }
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

    private static boolean createMutex() {
        try {
            Path lockFile = Paths.get(System.getProperty("user.home"), ".timeTrackerLock");
            if (!Files.exists(lockFile)) {
                Files.createFile(lockFile);
            }
            channel = FileChannel.open(lockFile, StandardOpenOption.WRITE);
            lock = channel.tryLock();
            return lock != null;
        } catch (IOException e) {
            return false;
        }
    }

    public static void releaseMutex() {
        try {
            if (lock != null) lock.release();
            if (channel != null) channel.close();
        } catch (IOException e) {
            // Log error if needed
        }
    }
}