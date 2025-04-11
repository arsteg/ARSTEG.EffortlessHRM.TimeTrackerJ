import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;


    class ProjectRequest {
        private String userId;
        private Date startDate; // Using Date instead of DateTime; nullable handled as null
        private String projectName;
        private Date endDate; // Nullable handled as null
        private String notes;
        private String estimatedTime;

        public String getUserId() { return userId; }
        public Date getStartDate() { return startDate; }
        public String getProjectName() { return projectName; }
        public Date getEndDate() { return endDate; }
        public String getNotes() { return notes; }
        public String getEstimatedTime() { return estimatedTime; }

        public void setUserId(String userId) { this.userId = userId; }
        public void setStartDate(Date startDate) { this.startDate = startDate; }
        public void setProjectName(String projectName) { this.projectName = projectName; }
        public void setEndDate(Date endDate) { this.endDate = endDate; }
        public void setNotes(String notes) { this.notes = notes; }
        public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }
    }

@JsonIgnoreProperties(ignoreUnknown = true)
    class TimeLogBase {
        private String id;
        private String user;
        private Date date;
        private Date startTime;
        private Date endTime;
        private String filePath;
        private int keysPressed;
        private String allKeysPressed;
        private int clicks;
        private String url;
        private int scrolls;

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }
        public Date getStartTime() { return startTime; }
        public void setStartTime(Date startTime) { this.startTime = startTime; }
        public Date getEndTime() { return endTime; }
        public void setEndTime(Date endTime) { this.endTime = endTime; }
        public String getFilePath() { return filePath; }
        public void setFilePath(String filePath) { this.filePath = filePath; }
        public int getKeysPressed() { return keysPressed; }
        public void setKeysPressed(int keysPressed) { this.keysPressed = keysPressed; }
        public String getAllKeysPressed() { return allKeysPressed; }
        public void setAllKeysPressed(String allKeysPressed) { this.allKeysPressed = allKeysPressed; }
        public int getClicks() { return clicks; }
        public void setClicks(int clicks) { this.clicks = clicks; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public int getScrolls() { return scrolls; }
        public void setScrolls(int scrolls) { this.scrolls = scrolls; }
    }

@JsonIgnoreProperties(ignoreUnknown = true)
    class TimeLog extends TimeLogBase {
        private String task;
        private Date startDate;
        private String fileString;
        private String project;
        private String machineId;
        private boolean makeThisDeviceActive;
        private String message;

        // Getters and Setters
        public String getTask() { return task; }
        public void setTask(String task) { this.task = task; }
        public Date getStartDate() { return startDate; }
        public void setStartDate(Date startDate) { this.startDate = startDate; }
        public String getFileString() { return fileString; }
        public void setFileString(String fileString) { this.fileString = fileString; }
        public String getProject() { return project; }
        public void setProject(String project) { this.project = project; }
        public String getMachineId() { return machineId; }
        public void setMachineId(String machineId) { this.machineId = machineId; }
        public boolean isMakeThisDeviceActive() { return makeThisDeviceActive; }
        public void setMakeThisDeviceActive(boolean makeThisDeviceActive) { this.makeThisDeviceActive = makeThisDeviceActive; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    class CurrentWeekTotalTime {
        private String user;
        private Date startDate;
        private Date endDate;

        // Getters and Setters
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
        public Date getStartDate() { return startDate; }
        public void setStartDate(Date startDate) { this.startDate = startDate; }
        public Date getEndDate() { return endDate; }
        public void setEndDate(Date endDate) { this.endDate = endDate; }
    }

    class ErrorLog {
        private String id;
        private String error;
        private String details;
        private Date createdOn;
        private Date updatedOn;
        private String status;

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
        public String getDetails() { return details; }
        public void setDetails(String details) { this.details = details; }
        public Date getCreatedOn() { return createdOn; }
        public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }
        public Date getUpdatedOn() { return updatedOn; }
        public void setUpdatedOn(Date updatedOn) { this.updatedOn = updatedOn; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }



    class CreateTaskRequest {
        private String taskName;
        private Date startDate;
        private Date endDate;
        private Date startTime;
        private String description;
        private String comment;
        private String priority;
        private String project;
        private String[] taskUsers;
        private String user;
        private List<TaskAttachment> taskAttachments;
        private String title;
        private String status;

        // Getters and Setters
        public String getTaskName() { return taskName; }
        public void setTaskName(String taskName) { this.taskName = taskName; }
        public Date getStartDate() { return startDate; }
        public void setStartDate(Date startDate) { this.startDate = startDate; }
        public Date getEndDate() { return endDate; }
        public void setEndDate(Date endDate) { this.endDate = endDate; }
        public Date getStartTime() { return startTime; }
        public void setStartTime(Date startTime) { this.startTime = startTime; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        public String getProject() { return project; }
        public void setProject(String project) { this.project = project; }
        public String[] getTaskUsers() { return taskUsers; }
        public void setTaskUsers(String[] taskUsers) { this.taskUsers = taskUsers; }
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
        public List<TaskAttachment> getTaskAttachments() { return taskAttachments; }
        public void setTaskAttachments(List<TaskAttachment> taskAttachments) { this.taskAttachments = taskAttachments; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    class TaskAttachment {
        private String attachmentType;
        private String attachmentName;
        private String attachmentSize;
        private String file;

        // Getters and Setters
        public String getAttachmentType() { return attachmentType; }
        public void setAttachmentType(String attachmentType) { this.attachmentType = attachmentType; }
        public String getAttachmentName() { return attachmentName; }
        public void setAttachmentName(String attachmentName) { this.attachmentName = attachmentName; }
        public String getAttachmentSize() { return attachmentSize; }
        public void setAttachmentSize(String attachmentSize) { this.attachmentSize = attachmentSize; }
        public String getFile() { return file; }
        public void setFile(String file) { this.file = file; }
    }

    class TaskUser {
        private String user;

        // Getters and Setters
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
    }

    class TaskRequest {
        private String userId;
        private String projectId;
        private String skip;
        private String next;

        // Getters and Setters
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getProjectId() { return projectId; }
        public void setProjectId(String projectId) { this.projectId = projectId; }
        public String getSkip() { return skip; }
        public void setSkip(String skip) { this.skip = skip; }
        public String getNext() { return next; }
        public void setNext(String next) { this.next = next; }
    }

    class LiveImageRequest {
        private String fileString;

        // Getters and Setters
        public String getFileString() { return fileString; }
        public void setFileString(String fileString) { this.fileString = fileString; }
    }

    class LiveImageResponse {
        private String status;
        private String data;

        // Getters and Setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
    }

    class ApiResponse {
        private String status;
        private String token;
        private Data data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

@JsonIgnoreProperties(ignoreUnknown = true)
    class GetTaskListAPIResult extends BaseResponse {
        private List<TaskList> taskList;
        private int taskCount;

        public List<TaskList> getTaskList() { return taskList; }
        public int getTaskCount() { return taskCount; }
        public void setTaskList(List<TaskList> taskList) { this.taskList = taskList; }
        public void setTaskCount(int taskCount) { this.taskCount = taskCount; }
    }

    // TaskList
    @JsonIgnoreProperties(ignoreUnknown = true)
    class TaskList {
        private String id;
        private String taskName;
        private String description;
        private String status;

        public String getId() { return id; }
        public String getTaskName() { return taskName; }
        public String getDescription() { return description; }
        public String getStatus() { return status; }
        public void setId(String id) { this.id = id; }
        public void setTaskName(String taskName) { this.taskName = taskName; }
        public void setDescription(String description) { this.description = description; }
        public void setStatus(String status) { this.status = status; }
    }

    class ProductivityModel {
        private String id;
        private String icon;
        private String key;
        private String name;
        private String status;
        private Boolean isApproved;  // Using wrapper Boolean instead of primitive boolean to allow null
        private Date createdOn;
        private Date updatedOn;
        private String createdBy;
        private String updatedBy;
        private String company;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Boolean getIsApproved() {
            return isApproved;
        }

        public void setIsApproved(Boolean isApproved) {
            this.isApproved = isApproved;
        }

        public Date getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
        }

        public Date getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(Date updatedOn) {
            this.updatedOn = updatedOn;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }

     class Data {
        private User user;
        private CompanySubscription companySubscription;

        public Data (){}
        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public CompanySubscription getCompanySubscription() {
            return companySubscription;
        }

        public void setCompanySubscription(CompanySubscription companySubscription) {
            this.companySubscription = companySubscription;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class User {
        private List<String> appointment;
        private String _id;
        private String firstName;
        private String lastName;
        private String email;
        private boolean isSuperAdmin;
        private String status;
        private Company company;
        private Role role;
        private String createdOn;
        private String updatedOn;
        private int __v;
        private String jobTitle;
        private String id;

        public List<String> getAppointment() {
            return appointment;
        }

        public void setAppointment(List<String> appointment) {
            this.appointment = appointment;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isSuperAdmin() {
            return isSuperAdmin;
        }

        public void setSuperAdmin(boolean superAdmin) {
            isSuperAdmin = superAdmin;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    class Company {
        private String _id;
        private String companyName;
        private boolean freeCompany;
        private String id;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public boolean isFreeCompany() {
            return freeCompany;
        }

        public void setFreeCompany(boolean freeCompany) {
            this.freeCompany = freeCompany;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Role {
        private String _id;
        private String id;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    class CompanySubscription {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    class BaseResponse {
        private int statusCode;
        private String status;

        public int getStatusCode() { return statusCode; }
        public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

@JsonIgnoreProperties(ignoreUnknown = true)
    class GetTimeLogAPIResult extends BaseResponse {
        private List<TimeLogBaseResponse> data;

        public List<TimeLogBaseResponse> getData() { return data; }
        public void setData(List<TimeLogBaseResponse> data) { this.data = data; }
    }

    class AddTimeLogAPIResult extends BaseResponse {
        private TimeLog data;

        public TimeLog getData() { return data; }
        public void setData(TimeLog data) { this.data = data; }
    }

    class AddErrorLogAPIResult extends BaseResponse {
        private AddErrorLog data;

        public AddErrorLog getData() { return data; }
        public void setData(AddErrorLog data) { this.data = data; }
    }

    class AddErrorLog {
        private ErrorLog errorLog;

        public ErrorLog getErrorLog() { return errorLog; }
        public void setErrorLog(ErrorLog errorLog) { this.errorLog = errorLog; }
    }

    class GetProjectListAPIResult extends BaseResponse {
        private ProjectList data;

        public ProjectList getData() { return data; }
        public void setData(ProjectList data) { this.data = data; }
    }

    class ProjectList {
        private List<Project> projectList;

        public List<Project> getProjectList() { return projectList; }
        public void setProjectList(List<Project> projectList) { this.projectList = projectList; }
    }

@JsonIgnoreProperties(ignoreUnknown = true)
    class Project {
        private String _id;
        private String projectName;

        public String getId() { return _id; }
        public void setId(String _id) { this._id = _id; }

        public String getProjectName() { return projectName; }
        public void setProjectName(String projectName) { this.projectName = projectName; }
    }


@JsonIgnoreProperties(ignoreUnknown = true)
    class TimeLogBaseResponse {
        private String _id;
        private User user;
        private Date date;
        private Date startTime;
        private Date endTime;
        private String filePath;
        private int keysPressed;
        private int clicks;
        private String url;
        private int scrolls;

        public String getId() { return _id; }
        public void setId(String _id) { this._id = _id; }

        public User getUser() { return user; }
        public void setUser(User user) { this.user = user; }

        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }

        public Date getStartTime() { return startTime; }
        public void setStartTime(Date startTime) { this.startTime = startTime; }

        public Date getEndTime() { return endTime; }
        public void setEndTime(Date endTime) { this.endTime = endTime; }

        public String getFilePath() { return filePath; }
        public void setFilePath(String filePath) { this.filePath = filePath; }

        public int getKeysPressed() { return keysPressed; }
        public void setKeysPressed(int keysPressed) { this.keysPressed = keysPressed; }

        public int getClicks() { return clicks; }
        public void setClicks(int clicks) { this.clicks = clicks; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public int getScrolls() { return scrolls; }
        public void setScrolls(int scrolls) { this.scrolls = scrolls; }
    }



    class CheckLiveScreenResponse {
        private boolean success;

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }

