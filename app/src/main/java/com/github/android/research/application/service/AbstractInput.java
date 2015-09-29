package com.github.android.research.application.service;

import android.location.Location;
import android.location.LocationManager;

public class AbstractInput {
    protected String batteryLevel;
    protected String connectionType;
    protected Location location;
    protected String deviceId;

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(LocationManager locationManager) {
        if (locationManager == null)
            return;

        Location location;

        for (String provider : locationManager.getAllProviders()) {
            location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                this.location = location;
                return;
            }
        }
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
