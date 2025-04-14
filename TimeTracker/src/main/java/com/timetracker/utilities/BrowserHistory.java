package com.timetracker.utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BrowserHistory {
    public static List<HistoryEntry> getHistoryEntries(LocalDateTime startDate, LocalDateTime endDate) {
        // Simulate browser history (real implementation requires browser-specific APIs)
        List<HistoryEntry> entries = new ArrayList<>();
        entries.add(new HistoryEntry("https://example.com", "Example", startDate));
        return entries;
    }

    public static class HistoryEntry {
        private String url;
        private String title;
        private LocalDateTime timestamp;

        public HistoryEntry(String url, String title, LocalDateTime timestamp) {
            this.url = url;
            this.title = title;
            this.timestamp = timestamp;
        }

        // Getters
        public String getUrl() { return url; }
        public String getTitle() { return title; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}