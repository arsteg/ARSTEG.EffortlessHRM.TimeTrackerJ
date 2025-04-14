package com.timetracker;

import com.timetracker.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}