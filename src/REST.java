import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CompletionException;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.net.ssl.SSLContext; // Explicit import
import javax.net.ssl.TrustManager; // Explicit import
import javax.net.ssl.X509TrustManager; // Explicit import
import javax.swing.Timer;
import java.security.SecureRandom; // Explicit import
import java.security.cert.X509Certificate;

public class REST {
    private static final Logger logger = Logger.getLogger(REST.class.getName());
    private final ObjectMapper objectMapper;
    private final HttpProviders httpProvider;

    public REST(HttpProviders httpProvider) {
        this.httpProvider = httpProvider;
        this.objectMapper = JsonMapper.builder()
                .findAndAddModules() // For enums and other features
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .defaultDateFormat(new StdDateFormat().withColonInTimeZone(true))
                .build();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String combineUri(String... uriParts) {
        if (uriParts == null || uriParts.length == 0) return "";
        StringBuilder uri = new StringBuilder(uriParts[0].replaceAll("[\\\\/]+$", ""));
        for (int i = 1; i < uriParts.length; i++) {
            uri.append("/").append(uriParts[i].replaceAll("^[\\\\/]+", ""));
        }
        return uri.toString();
    }

    public CompletableFuture<ApiResponse> signIn(Login login) {
        logger.info("Calling signIn with login: " + login.toString());
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/users/login");
        logger.info("Uri: "+ uri);
        return httpProvider.postAsync(uri, login, ApiResponse.class)
                .thenApply(res -> {
                    logger.info("SignIn response: " + res);
                    return res;
                })
                .exceptionally(throwable -> {
                    logger.log(Level.SEVERE, "SignIn failed", throwable);
                    ApiResponse apiResponse = new ApiResponse();
                    return apiResponse;
                });
    }

    public CompletableFuture<AddTimeLogAPIResult> addTimeLog(TimeLog timeLog) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/timeLogs");
        return httpProvider.postWithTokenAsync(uri, timeLog, AddTimeLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetTimeLogAPIResult> getTimeLogs(TimeLog timeLog) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/timeLogs/getTimeLogs");
        return httpProvider.postWithTokenAsync(uri, timeLog, GetTimeLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetTimeLogAPIResult> getCurrentWeekTotalTime(CurrentWeekTotalTime timeLog) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/timeLogs/getCurrentWeekTotalTime");
        return httpProvider.postWithTokenAsync(uri, timeLog, GetTimeLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetTimeLogAPIResult> getTimeLogsWithImages(TimeLog timeLog) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/timeLogs/getLogsWithImages");
        return httpProvider.postWithTokenAsync(uri, timeLog, GetTimeLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public void addErrorLogs(ErrorLog errorLog) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/errorlogs/new");
        httpProvider.postWithTokenAsync(uri, errorLog, AddErrorLogAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken())
                .join();
    }

    public CompletableFuture<GetErrorLogResult> getErrorLogs(String userId) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/errorlogs/errorloglist/" + userId);
        return httpProvider.getWithTokenAsync(uri, GetErrorLogResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetProjectListAPIResult> getProjectListByUserId(ProjectRequest projectRequest) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/project/projectlistbyuser");
        return httpProvider.postWithTokenAsync(uri, projectRequest, GetProjectListAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<GetTaskListAPIResult> getTaskListByProject(TaskRequest taskRequest) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/task/getUserTaskListByProject");
        return httpProvider.postWithTokenAsync(uri, taskRequest, GetTaskListAPIResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<NewTaskResult> addNewTask(CreateTaskRequest createTaskRequest) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/task/newtask");
        return httpProvider.postWithTokenAsync(uri, createTaskRequest, NewTaskResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<String> addNewProject(ProjectRequest projectRequest) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/project/newproject");
        return httpProvider.postWithTokenAsync(uri, projectRequest, Object.class, GlobalSetting.getInstance().getLoginResult().getToken())
                .thenApply(res -> "");
    }

    public CompletableFuture<NewTaskResult> completeATask(String taskId, Object status) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/task/update/" + taskId);
        return httpProvider.putWithTokenAsync(uri, status, NewTaskResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<ApplicationLogResult> addUsedApplicationLog(ApplicationLog applicationLog) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/appWebsite/create");
        return httpProvider.postWithTokenAsync(uri, applicationLog, ApplicationLogResult.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<Void> sendLiveScreenData(LiveImageRequest timeLog) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/liveTracking/save");
        return httpProvider.postAsyncWithVoid(uri, timeLog, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<Void> sendLiveScreenDataV1(LiveImageRequest timeLog) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/liveTracking/updateUserScreen");
        return httpProvider.postAsyncWithVoid(uri, timeLog, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<CheckLiveScreenResponse> checkLiveScreen(TaskUser user) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/liveTracking/getLiveTrackingByUserId");
        return httpProvider.postWithTokenAsync(uri, user, CheckLiveScreenResponse.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<ProductivityAppResult> getProductivityApps(String url) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), url);
        return httpProvider.getWithTokenAsync(uri, ProductivityAppResult.class, GlobalSetting.getInstance().getLoginResult().getToken())
                .exceptionally(throwable -> {
                    logger.log(Level.SEVERE, "GetProductivityApps failed: " + throwable.getMessage());
                    return new ProductivityAppResult();
                });
    }

    public CompletableFuture<ProductivityAppAddResult> addProductivityApps(String url, ProductivityModel productivityModel) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), url);
        return httpProvider.postWithTokenAsync(uri, productivityModel, ProductivityAppAddResult.class, GlobalSetting.getInstance().getLoginResult().getToken())
                .exceptionally(throwable -> {
                    logger.log(Level.SEVERE, "AddProductivityApps failed: " + throwable.getMessage());
                    return new ProductivityAppAddResult();
                });
    }

    public CompletableFuture<ProductivityAppDeleteResult> deleteProductivityApp(String id) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), id);
        return httpProvider.deleteWithTokenAsync(uri, ProductivityAppDeleteResult.class, GlobalSetting.getInstance().getLoginResult().getToken())
                .exceptionally(throwable -> {
                    logger.log(Level.SEVERE, "DeleteProductivityApp failed: " + throwable.getMessage());
                    return new ProductivityAppDeleteResult();
                });
    }

    public CompletableFuture<Object> addBrowserHistory(HistoryEntry historyEntry) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), "/api/v1/appWebsite/browser-history");
        return httpProvider.postWithTokenAsync(uri, historyEntry, Object.class, GlobalSetting.getInstance().getLoginResult().getToken());
    }

    public CompletableFuture<EnableBeepSoundResult> getEnableBeepSoundSetting(String url) {
        String uri = combineUri(GlobalSetting.getApiBaseUrl(), url);
        return httpProvider.getWithTokenAsync(uri, EnableBeepSoundResult.class, GlobalSetting.getInstance().getLoginResult().getToken())
                .exceptionally(throwable -> {
                    logger.log(Level.SEVERE, "GetEnableBeepSoundSetting failed: " + throwable.getMessage());
                    return new EnableBeepSoundResult();
                });
    }
}

class HttpProviders {
    private HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(HttpProviders.class.getName());

    public HttpProviders() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            }, new SecureRandom());
            client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize HttpClient", e);
        }
    }

    public <T> CompletableFuture<T> postAsync(String uri, Object request, Class<T> responseType) {
        try {
            String jsonBody = mapper.writeValueAsString(request);
            System.out.println("Sending JSON: " + jsonBody);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenCompose(response -> {
                        int code = response.statusCode();
                        String body = response.body();

                        System.out.println("Status: " + code);
                        System.out.println("Body: " + body);

                        if (code >= 200 && code < 300) {
                            try {
                                return CompletableFuture.completedFuture(mapper.readValue(body, responseType));
                            } catch (Exception e) {
                                return CompletableFuture.failedFuture(e);
                            }
                        } else {
                            return CompletableFuture.failedFuture(new RuntimeException("HTTP " + code + ": " + body));
                        }
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    public <T, R> CompletableFuture<T> postWithTokenAsync(String uri, R request, Class<T> responseType, String token) {
        try {
            String jsonBody = mapper.writeValueAsString(request);
            String cookies = "companyId=" + GlobalSetting.getInstance().getLoginResult().getData().getUser().getCompany().getId() +
                    "; jwt=" + token +
                    "; userId=" + GlobalSetting.getInstance().getLoginResult().getData().getUser().getId();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .header("Cookie", cookies)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            return mapper.readValue(response.body(), responseType);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    public <T> CompletableFuture<T> getWithTokenAsync(String uri, Class<T> responseType, String token) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        return mapper.readValue(response.body(), responseType);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public <T, R> CompletableFuture<T> putWithTokenAsync(String uri, R request, Class<T> responseType, String token) {
        try {
            String jsonBody = mapper.writeValueAsString(request);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            return mapper.readValue(response.body(), responseType);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    public <T> CompletableFuture<T> deleteWithTokenAsync(String uri, Class<T> responseType, String token) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Authorization", "Bearer " + token)
                .DELETE()
                .build();
        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        return mapper.readValue(response.body(), responseType);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public <R> CompletableFuture<Void> postAsyncWithVoid(String uri, R request, String token) {
        try {
            String jsonBody = mapper.writeValueAsString(request);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {});
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}

// Placeholder model classes
class Login {
    private String email;
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Login{email='" + email + "', password='[hidden]'}";
    }
}







class GetErrorLogResult {}

class NewTaskResult {}
class ApplicationLog {}
class ApplicationLogResult {}

class ProductivityAppResult {}

class ProductivityAppAddResult {}
class ProductivityAppDeleteResult {}
class HistoryEntry {}
class EnableBeepSoundResult {}

class GlobalSetting {
    private static GlobalSetting instance = new GlobalSetting();
    private static String apiBaseUrl = "http://localhost:8080/";
    private ApiResponse loginResult; // Changed from LoginResult
    private TimeTrackerFrame timeTracker;

    private GlobalSetting() {}
    public static GlobalSetting getInstance() { return instance; }
    public static String getApiBaseUrl() { return apiBaseUrl; }
    public static void setApiBaseUrl(String url) { apiBaseUrl = url; }
    public ApiResponse getLoginResult() { return loginResult; } // Changed from LoginResult
    public void setLoginResult(ApiResponse result) { this.loginResult = result; } // Changed from LoginResult
    public TimeTrackerFrame getTimeTracker() { return timeTracker; }
    public void setTimeTracker(TimeTrackerFrame timeTracker) { this.timeTracker = timeTracker; }
}



class ServiceAuthenticationException extends Exception {
    public ServiceAuthenticationException(String message) { super(message); }
}



