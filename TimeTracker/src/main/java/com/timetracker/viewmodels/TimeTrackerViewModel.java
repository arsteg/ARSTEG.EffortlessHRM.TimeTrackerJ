package com.timetracker.viewmodels;

import com.timetracker.models.Project;
import com.timetracker.models.ProjectTask;
import com.timetracker.services.RESTService;
import com.timetracker.utilities.IdleTimeDetector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeTrackerViewModel {
    private final PropertyChangeSupport support;
    private String userFullName = "";
    private List<String> projects = new ArrayList<>();
    private List<String> tasks = new ArrayList<>();
    private String taskName = "";
    private String taskDescription = "";
    private String currentSessionTimeTracked = "0 hrs 00 m";
    private String currentDayTimeTracked = "0 hrs 00 m";
    private String currentWeekTimeTracked = "0 hrs 00 m";
    private String currentMonthTimeTracked = "0 hrs 00 m";
    private String startStopButtonText = "Start";
    private String currentImagePath = "";
    private String errorMessage = "";
    private boolean trackerIsOn = false;
    private final RESTService restService;
    private Timer timer;
    private double minutesTracked = 0;
    private int selectedProjectIndex = -1;
    private int selectedTaskIndex = -1;

    public TimeTrackerViewModel() {
        support = new PropertyChangeSupport(this);
        restService = new RESTService();
        initialize();
    }

    private void initialize() {
        // Simulate fetching user data
        userFullName = "Welcome, John Doe";
        support.firePropertyChange("userFullName", "", userFullName);

        // Load projects
        projects = restService.getProjectList();
        support.firePropertyChange("projects", null, projects);

        // Start idle detection
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkIdleTime();
            }
        }, 0, 120_000); // Every 2 minutes
    }

    // Getters
    public String getUserFullName() {
        return userFullName;
    }

    public String getStartStopButtonText() {
        return startStopButtonText;
    }

    // Setters
    public void setSelectedProject(int index) {
        if (index != selectedProjectIndex && index >= 0 && index < projects.size()) {
            selectedProjectIndex = index;
            tasks = restService.getTaskList(projects.get(index));
            support.firePropertyChange("tasks", null, tasks);
            setTaskName("");
            setTaskDescription("");
            selectedTaskIndex = -1;
        }
    }

    public void setSelectedTask(int index) {
        if (index != selectedTaskIndex && index >= 0 && index < tasks.size()) {
            selectedTaskIndex = index;
            setTaskName(tasks.get(index));
            setTaskDescription(restService.getTaskDescription(tasks.get(index)));
        }
    }

    public void setTaskName(String taskName) {
        String oldValue = this.taskName;
        this.taskName = taskName;
        support.firePropertyChange("taskName", oldValue, taskName);
        // Filter tasks
        List<String> filteredTasks = new ArrayList<>();
        for (String task : tasks) {
            if (task.toLowerCase().contains(taskName.toLowerCase())) {
                filteredTasks.add(task);
            }
        }
        support.firePropertyChange("tasks", null, filteredTasks);
    }

    public void setTaskDescription(String taskDescription) {
        String oldValue = this.taskDescription;
        this.taskDescription = taskDescription;
        support.firePropertyChange("taskDescription", oldValue, taskDescription);
    }

    private void setErrorMessage(String errorMessage) {
        String oldValue = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldValue, errorMessage);
    }

    private void setCurrentImagePath(String path) {
        String oldValue = this.currentImagePath;
        this.currentImagePath = path;
        support.firePropertyChange("currentImagePath", oldValue, path);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Commands
    public void startStop() {
        if (taskName.isEmpty()) {
            setErrorMessage("No task selected");
            return;
        }
        trackerIsOn = !trackerIsOn;
        startStopButtonText = trackerIsOn ? "Stop" : "Start";
        support.firePropertyChange("startStopButtonText", null, startStopButtonText);

        if (trackerIsOn) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    captureScreenshot();
                    updateTime();
                }
            }, 0, 540_000); // Every 9 minutes
        } else {
            timer.cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    checkIdleTime();
                }
            }, 0, 120_000);
        }
    }

    public void logout() {
        if (trackerIsOn) {
            startStop();
        }
        support.firePropertyChange("logout", false, true);
    }

    public void close() {
        if (trackerIsOn) {
            setErrorMessage("Please stop the tracker before closing.");
            return;
        }
        System.exit(0);
    }

    public void refresh() {
        projects = restService.getProjectList();
        support.firePropertyChange("projects", null, projects);
        tasks.clear();
        support.firePropertyChange("tasks", null, tasks);
        setTaskName("");
        setTaskDescription("");
    }

    public void createNewTask() {
        if (taskName.isEmpty()) {
            setErrorMessage("Please specify task details");
            return;
        }
        restService.createTask(taskName, taskDescription, selectedProjectIndex >= 0 ? projects.get(selectedProjectIndex) : "");
        tasks = restService.getTaskList(projects.get(selectedProjectIndex));
        support.firePropertyChange("tasks", null, tasks);
        setErrorMessage("Task created successfully");
    }

    public void completeTask() {
        if (taskName.isEmpty()) {
            setErrorMessage("No task selected");
            return;
        }
        restService.completeTask(taskName);
        tasks = restService.getTaskList(projects.get(selectedProjectIndex));
        support.firePropertyChange("tasks", null, tasks);
        setErrorMessage("Task marked as completed");
    }

    public void openTask() {
        if (taskName.isEmpty()) {
            setErrorMessage("No task selected");
            return;
        }
        restService.openTaskInBrowser(taskName);
    }

    private void captureScreenshot() {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenshot = robot.createScreenCapture(screenRect);
            File file = new File("screenshot_" + System.currentTimeMillis() + ".png");
            ImageIO.write(screenshot, "png", file);
            setCurrentImagePath(file.getAbsolutePath());

            // Simulate save/delete popup
            int choice = JOptionPane.showConfirmDialog(null, "Save screenshot?", "Screenshot", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                restService.saveScreenshot(file);
            } else {
                Files.deleteIfExists(file.toPath());
                setCurrentImagePath("");
            }
        } catch (AWTException | IOException e) {
            e.printStackTrace();
            setErrorMessage("Failed to capture screenshot");
        }
    }

    private void updateTime() {
        minutesTracked += 10;
        currentSessionTimeTracked = formatTime(minutesTracked);
        support.firePropertyChange("currentSessionTimeTracked", null, currentSessionTimeTracked);

        // Simulate fetching other times
        currentDayTimeTracked = restService.getDayTimeTracked();
        currentWeekTimeTracked = restService.getWeekTimeTracked();
        currentMonthTimeTracked = restService.getMonthTimeTracked();
        support.firePropertyChange("currentDayTimeTracked", null, currentDayTimeTracked);
        support.firePropertyChange("currentWeekTimeTracked", null, currentWeekTimeTracked);
        support.firePropertyChange("currentMonthTimeTracked", null, currentMonthTimeTracked);
    }

    private String formatTime(double minutes  {
        int hours = (int) (minutes / 60);
        int minutes = (int) (minutes % 60);
        return String.format("%d hrs %02d m", hours, minutes);
    }

    private void checkIdleTime() {
        IdleTimeDetector.IdleTimeInfo idleInfo = IdleTimeDetector.getIdleTimeInfo();
        if (idleInfo.getIdleTime().toMinutes() >= 4 && trackerIsOn) {
            startStop();
            setErrorMessage("Idle time detected, tracker stopped.");
        }
    }
}