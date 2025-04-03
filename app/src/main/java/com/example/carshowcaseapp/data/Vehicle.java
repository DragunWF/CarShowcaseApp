package com.example.carshowcaseapp.data;

public class Vehicle extends Model {
    private String model, brand, features, category, imageName;
    private double price;

    public Vehicle(String model, String brand, String features, String category, String imageName, double price) {
        this.model = model;
        this.brand = brand;
        this.features = features;
        this.category = category;
        this.imageName = imageName;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", features='" + features + '\'' +
                ", category='" + category + '\'' +
                ", imageName='" + imageName + '\'' +
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
