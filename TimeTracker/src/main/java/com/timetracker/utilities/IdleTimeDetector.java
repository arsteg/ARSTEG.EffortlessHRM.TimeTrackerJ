package com.timetracker.utilities;

import java.time.Duration;
import java.time.Instant;

public class IdleTimeDetector {
    // Placeholder: Requires JNA for Windows
    public static IdleTimeInfo getIdleTimeInfo() {
        // Simulate idle time
        return new IdleTimeInfo(Duration.ofSeconds(0), Instant.now());
    }

    public static class IdleTimeInfo {
        private final Duration idleTime;
        private final Instant lastInputTime;

        public IdleTimeInfo(Duration idleTime, Instant lastInputTime) {
            this.idleTime = idleTime;
            this.lastInputTime = lastInputTime;
        }

        public Duration getIdleTime() {
            return idleTime;
        }

        public Instant getLastInputTime() {
            return lastInputTime;
        }
    }
}