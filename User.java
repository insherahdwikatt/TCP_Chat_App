/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Net1Part2_Chatting;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
/**
 *
 * @author asus
 */

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class User {
    private String name;
    private LocalDateTime lastActiveTime;
    private String status; // active or away
        private LocalDateTime lastActiveDate;

    private Timer activityTimer;

    public User(String name) {
        this.name = name;
        this.lastActiveTime = LocalDateTime.now();
        this.status = "active";
        startInactivityTimer();
    }

      public LocalDateTime getLastActiveDate() {
        return lastActiveDate;
    }
      
    public User(String name, LocalDateTime lastActiveDate) {
        this.name = name;
        this.lastActiveDate = lastActiveDate;
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

    public LocalDateTime getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(LocalDateTime lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    private void startInactivityTimer() {
        activityTimer = new Timer(true);
        activityTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (lastActiveTime.plusSeconds(30).isBefore(LocalDateTime.now())) {
                    setStatus("away");
                }
            }
        }, 0, 5000); // Check every 5 seconds
    }

    public void resetInactivityTimer() {
        setLastActiveTime(LocalDateTime.now());
        setStatus("active");
    }

}

