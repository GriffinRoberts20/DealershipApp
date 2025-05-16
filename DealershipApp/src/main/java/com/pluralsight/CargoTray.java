package com.pluralsight;

public class CargoTray extends AddOn{
    public CargoTray() {
        this.setPrice(35);
    }

    @Override
    public String toString(){
        return "Ct";
    }
}
