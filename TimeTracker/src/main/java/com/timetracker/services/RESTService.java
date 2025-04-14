package com.timetracker.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timetracker.models.Login;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RESTService {
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public RESTService() {
        client = new OkHttpClient();
        mapper = new ObjectMapper();
    }

    public boolean signIn(Login login) {
        // Simulate API call
        try {
            RequestBody body = RequestBody.create(
                    mapper.writeValueAsString(login),
                    MediaType.parse("application/json")
            );
            Request request = new Request.Builder()
                    .url("https://api.example.com/signin")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.isSuccessful();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getProjectList() {
        // Simulate
        List<String> projects = new ArrayList<>();
        projects.add("Project A");
        projects.add("Project B");
        return projects;
    }

    public List<String> getTaskList(String project) {
        // Simulate
        List<String> tasks = new ArrayList<>();
        tasks.add("Task 1");
        tasks.add("Task 2");
        return tasks;
    }

    public String getTaskDescription(String task) {
        return "Description for " + task;
    }

    public void createTask(String taskName, String description, String project) {
        // Simulate
        System.out.println("Task created: " + taskName);
    }

    public void completeTask(String task) {
        // Simulate
        System.out.println("Task completed: " + task);
    }

    public void openTaskInBrowser(String task) {
        // Simulate
        System.out.println("Opening task: " + task);
    }

    public void saveScreenshot(File file) {
        // Simulate
        System.out.println("Screenshot saved: " + file.getAbsolutePath());
    }

    public String getDayTimeTracked() {
        return "2 hrs 30 m";
    }

    public String getWeekTimeTracked() {
        return "10 hrs 45 m";
    }

    public String getMonthTimeTracked() {
        return "50 hrs 15 m";
    }
}