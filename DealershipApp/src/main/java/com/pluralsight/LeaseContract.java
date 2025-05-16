package com.pluralsight;

public class LeaseContract extends Contract{
    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(String date, String name, String email, Vehicle vehicle) {
        super(date, name, email, vehicle);
        this.expectedEndingValue=this.getVehicle().getPrice()/2;
        this.leaseFee=this.getVehicle().getPrice()*0.07;
    }

    @Override
    public String toString(){
        return String.format("LEASE|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f",
                this.getDate(),this.getName(),this.getEmail(),this.getVehicle().getVin(),this.getVehicle().getYear(),
                this.getVehicle().getMake(),this.getVehicle().getModel(),this.getVehicle().getVehicleType(),
                this.getVehicle().getColor(),this.getVehicle().getOdometer(),this.getVehicle().getPrice(),
                this.getExpectedEndingValue(),this.getLeaseFee(),this.getTotalPrice(),this.getMonthlyPayment());
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public void setExpectedEndingValue(double expectedEndingValue) {
        this.expectedEndingValue = expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public void setLeaseFee(double leaseFee) {
        this.leaseFee = leaseFee;
    }

    @Override
    public double getTotalPrice(){
        return (this.getExpectedEndingValue()+this.getLeaseFee())*(1+(0.04*3));
    }

    @Override
    public double getMonthlyPayment(){
        return this.getTotalPrice()/36;
    }

}
