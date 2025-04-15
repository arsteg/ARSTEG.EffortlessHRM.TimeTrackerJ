import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.timetracker.models.*;
import com.timetracker.models.ErrorLog;
import com.timetracker.models.timeLogResult.*;
import com.timetracker.models.timelog.*;
import com.timetracker.models.user.LoginResult;
import com.timetracker.services.HttpProviders;
import com.timetracker.utilities.BrowserHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class REST {
    private static final Logger logger = LoggerFactory.getLogger(REST.class);
    private final ObjectMapper objectMapper;
    private final HttpProviders httpProvider;

    public REST(HttpProviders httpProvider) {
        this.objectMapper = new ObjectMapper();
        //this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Enum.class, new ToStringSerializer());
        this.objectMapper.registerModule(module);

        this.httpProvider = httpProvider;
    }

    public static String combineUri(String... uriParts) {
        if (uriParts == null || uriParts.length == 0) {
            return "";
        }

        char[] trims = {'\\', '/'};
        StringBuilder uri = new StringBuilder(trimEnd(uriParts[0] != null ? uriParts[0] : "", trims));
        for (int i = 1; i < uriParts.length; i++) {
            String part = uriParts[i] != null ? uriParts[i] : "";
            uri = new StringBuilder(String.format("%s/%s", trimEnd(uri.toString(), trims), trimStart(part, trims)));
        }
        return uri.toString();
    }

    private static String trimEnd(String str, char[] chars) {
        if (str == null || str.isEmpty()) return str;
        int end = str.length();
        while (end > 0 && Arrays.binarySearch(chars, str.charAt(end - 1)) >= 0) {
            end--;
        }
        return str.substring(0, end);
    }

    private static String trimStart(String str, char[] chars) {
        if (str == null || str.isEmpty()) return str;
        int start = 0;
        while (start < str.length() && Arrays.binarySearch(chars, str.charAt(start)) >= 0) {
            start++;
        }
        return str.substring(start);
    }

    public CompletableFuture<LoginResult> signIn(Login login) {
        logger.info("Calling signIn with login={}", login);
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/users/login");
        return httpProvider.postAsync(uri, login, LoginResult.class);
    }

    public CompletableFuture<AddTimeLogAPIResult> addTimeLog(TimeLog timeLog) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/timeLogs");
        return httpProvider.postWithTokenAsync(uri, timeLog, AddTimeLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetTimeLogAPIResult> getTimeLogs(TimeLog timeLog) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/timeLogs/getTimeLogs");
        return httpProvider.postWithTokenAsync(uri, timeLog, GetTimeLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetTimeLogAPIResult> getCurrentWeekTotalTime(CurrentWeekTotalTime timeLog) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/timeLogs/getCurrentWeekTotalTime");
        return httpProvider.postWithTokenAsync(uri, timeLog, GetTimeLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetTimeLogAPIResult> getTimeLogsWithImages(TimeLog timeLog) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/timeLogs/getLogsWithImages");
        return httpProvider.postWithTokenAsync(uri, timeLog, GetTimeLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    // ErrorLog Section
    public void addErrorLogs(ErrorLog errorLog) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/errorlogs/new");
        httpProvider.postWithTokenAsync(uri, errorLog, AddErrorLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken())
                .join(); // Blocking to mimic .Result
    }

    public CompletableFuture<GetErrorLogResult> getErrorLogs(String userId) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/errorlogs/errorloglist/" + userId);
        return httpProvider.getWithTokenAsync(uri, GetErrorLogResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    // Project and Task Section
    public CompletableFuture<GetProjectListAPIResult> getProjectListByUserId(ProjectRequest projectRequest) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/project/projectlistbyuser");
        return httpProvider.postWithTokenAsync(uri, projectRequest, GetProjectListAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetTaskListAPIResult> getTaskListByProject(TaskRequest taskRequest) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/task/getUserTaskListByProject");
        return httpProvider.postWithTokenAsync(uri, taskRequest, GetTaskListAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<NewTaskResult> addNewTask(CreateTaskRequest createTaskRequest) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/task/newtask");
        return httpProvider.postWithTokenAsync(uri, createTaskRequest, NewTaskResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<String> addNewProject(ProjectRequest projectRequest) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/project/newproject");
        return httpProvider.postWithTokenAsync(uri, projectRequest, Object.class, GlobalSetting.getInstance().getLoginResult().getToken())
                .thenApply(res -> "");
    }

    public CompletableFuture<NewTaskResult> completeATask(String taskId, Map<String, Object> status) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/task/update/" + taskId);
        return httpProvider.putWithTokenAsync(uri, status, NewTaskResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    // Track Application Website
    public CompletableFuture<ApplicationLogResult> addUsedApplicationLog(ApplicationLog applicationLog) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/appWebsite/create");
        return httpProvider.postWithTokenAsync(uri, applicationLog, ApplicationLogResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    // Live Screen
    public CompletableFuture<Void> sendLiveScreenData(LiveImageRequest timeLog) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/liveTracking/save");
        return httpProvider.postAsyncWithVoid(uri, timeLog, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<Void> sendLiveScreenDataV1(LiveImageRequest timeLog) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/liveTracking/updateUserScreen");
        return httpProvider.postAsyncWithVoid(uri, timeLog, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<CheckLiveScreenResponse> checkLiveScreen(TaskUser user) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/liveTracking/getLiveTrackingByUserId");
        return httpProvider.postWithTokenAsync(uri, user, CheckLiveScreenResponse.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    // Productivity Applications
    public CompletableFuture<ProductivityAppResult> getProductivityApps(String url) {
        ProductivityAppResult result = new ProductivityAppResult();
        try {
            String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), url);
            return httpProvider.getWithTokenAsync(uri, ProductivityAppResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            return CompletableFuture.completedFuture(result);
        }
    }

    public CompletableFuture<ProductivityAppAddResult> addProductivityApps(String url, ProductivityModel productivityModel) {
        ProductivityAppAddResult result = new ProductivityAppAddResult();
        try {
            String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), url);
            return httpProvider.postWithTokenAsync(uri, productivityModel, ProductivityAppAddResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            return CompletableFuture.completedFuture(result);
        }
    }

    public CompletableFuture<ProductivityAppDeleteResult> deleteProductivityApp(String id) {
        ProductivityAppDeleteResult result = new ProductivityAppDeleteResult();
        try {
            String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), id);
            return httpProvider.deleteWithTokenAsync(uri, ProductivityAppDeleteResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            return CompletableFuture.completedFuture(result);
        }
    }

    public CompletableFuture<Object> addBrowserHistory(BrowserHistory.HistoryEntry historyEntry) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/appWebsite/browser-history");
        return httpProvider.postWithTokenAsync(uri, historyEntry, Object.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    // User Preferences
    public CompletableFuture<EnableBeepSoundResult> getEnableBeepSoundSetting(String url) {
        EnableBeepSoundResult result = new EnableBeepSoundResult();
        try {
            String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), url);
            return httpProvider.getWithTokenAsync(uri, EnableBeepSoundResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            return CompletableFuture.completedFuture(result);
        }
    }

    // Update Online Status
    public CompletableFuture<UpdateOnlineStatusResult> updateOnlineStatus(String userId, String machineId, boolean isOnline, String project, String task) {
        String uri = combineUri(GlobalSetting.getInstance().getApiBaseUrl(), "/api/v1/common/onlineStatus");
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("userId", userId);
        requestData.put("machineId", machineId);
        requestData.put("isOnline", isOnline);
        requestData.put("project", project);
        requestData.put("task", task);

        logger.info("Calling updateOnlineStatus with userId={}, machineId={}, isOnline={}, project={}, task={}",
                userId, machineId, isOnline, project, task);

        return httpProvider.postWithTokenAsync(uri, requestData, UpdateOnlineStatusResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }
}