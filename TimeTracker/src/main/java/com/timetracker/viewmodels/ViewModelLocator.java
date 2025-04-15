package com.timetracker.viewmodels;

public class ViewModelLocator {
    private static final LoginViewModel loginViewModel = new LoginViewModel(null);
    private static final TimeTrackerViewModel timeTrackerViewModel = new TimeTrackerViewModel();

    public static LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public static TimeTrackerViewModel getTimeTrackerViewModel() {
        return timeTrackerViewModel;
    }
}