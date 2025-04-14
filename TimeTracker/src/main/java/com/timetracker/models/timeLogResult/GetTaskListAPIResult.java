package com.timetracker.models.timeLogResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timetracker.models.BaseResponse;

import java.util.List;

public class GetTaskListAPIResult extends BaseResponse {
    @JsonProperty("taskList")
    private List<TaskList> taskList;

    @JsonProperty("taskCount")
    private int taskCount;

    // Default constructor
    public GetTaskListAPIResult() {
    }

    // Getters
    public List<TaskList> getTaskList() {
        return taskList;
    }

    public int getTaskCount() {
        return taskCount;
    }

    // Setters
    public void setTaskList(List<TaskList> taskList) {
        this.taskList = taskList;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}