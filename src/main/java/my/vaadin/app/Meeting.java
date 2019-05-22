package my.vaadin.app;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Meeting implements Serializable, Cloneable {

    private Long id;
    private Long customerId;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() { return endTime; }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getCustomerId() { return customerId; }

    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }

        if (obj instanceof Meeting && obj.getClass().equals(getClass())) {
            return this.id.equals(((Meeting) obj).id);
        }

        return false;
    }

    @Override
    public Meeting clone() throws CloneNotSupportedException {
        return (Meeting) super.clone();
    }
}
