package com.example.carshowcaseapp.data;

public class Bookmark extends Model {
    private int adminId;
    private Vehicle vehicle;

    public Bookmark(int adminId, Vehicle vehicle) {
        this.adminId = adminId;
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "adminId=" + adminId +
                ", vehicle=" + vehicle +
                ", id=" + id +
                '}';
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
