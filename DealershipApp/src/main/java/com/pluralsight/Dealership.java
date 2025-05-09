package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dealership {
    private String name;
    private String address;
    private String phone;

    private ArrayList<Vehicle> inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        inventory=new ArrayList<>();
    }

    public List<Vehicle> getVehiclesByPrice(double min,double max){
        return inventory.stream()
                .filter(vehicle -> vehicle.getPrice()>=min&&vehicle.getPrice()<=max)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByMakeModel(String make,String model){
        return inventory.stream()
                .filter(vehicle -> make.isEmpty()||vehicle.getMake().toLowerCase().contains(make.toLowerCase()))
                .filter(vehicle -> model.isEmpty()||vehicle.getModel().toLowerCase().contains(model.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByYear(int min,int max){
        if(max<min){
            max=min;
        }
        int finalMax = max;
        return inventory.stream()
                .filter(vehicle -> vehicle.getYear()>=min)
                .filter(vehicle -> vehicle.getYear()<= finalMax)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByColor(String color){
        return inventory.stream()
                .filter(vehicle -> color.isEmpty()||vehicle.getColor().toLowerCase().contains(color.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByMileage(int min,int max){
        if(min<0){
            min=0;
        }
        if(max<min){
            max=min;
        }
        int finalMin=min;
        int finalMax = max;
        return inventory.stream()
                .filter(vehicle -> vehicle.getYear()>=finalMin)
                .filter(vehicle -> vehicle.getYear()<= finalMax)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByType(String vehicleType){
        return inventory.stream()
                .filter(vehicle -> vehicleType.isEmpty()||vehicle.getColor().toLowerCase().contains(vehicleType.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getAllVehicles(){
        return new ArrayList<>(inventory);
    }

    public void addVehicle(Vehicle vehicle){
        inventory.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle){
        inventory.remove(vehicle);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
