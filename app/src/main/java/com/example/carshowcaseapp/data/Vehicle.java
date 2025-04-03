package com.example.carshowcaseapp.data;

public class Vehicle extends Model {
    private String model, brand, features, imageName;
    private double price;

    public Vehicle(String model, String brand, String features, String imageName, double price) {
        this.model = model;
        this.brand = brand;
        this.features = features;
        this.imageName = imageName;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", features='" + features + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
