package com.pluralsight;

public abstract class AddOn {
    private double price;

    public AddOn() {

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public abstract String toString();
}
