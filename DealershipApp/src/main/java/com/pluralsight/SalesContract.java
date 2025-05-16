package com.pluralsight;

import java.util.ArrayList;

public class SalesContract extends Contract{
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private boolean finance;
    private ArrayList<AddOn> addOns;

    public SalesContract(String date, String name, String email, Vehicle vehicle, boolean finance) {
        super(date, name, email, vehicle);
        this.finance = finance;
        this.salesTax=0.05*this.getVehicle().getPrice();
        this.recordingFee=100;
        this.processingFee=(this.getVehicle().getPrice() <10000)?295:495;
        this.addOns=new ArrayList<>();
    }

    public SalesContract(String date, String name, String email, Vehicle vehicle, boolean finance, ArrayList<AddOn> addOns) {
        super(date, name, email, vehicle);
        this.finance = finance;
        this.salesTax=0.05*this.getVehicle().getPrice();
        this.recordingFee=100;
        this.processingFee=(this.getVehicle().getPrice() <10000)?295:495;
        this.addOns = addOns;
    }

    @Override
    public String toString(){
        StringBuilder output= new StringBuilder(String.format("SALE|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                this.getDate(), this.getName(), this.getEmail(), this.getVehicle().getVin(), this.getVehicle().getYear(),
                this.getVehicle().getMake(), this.getVehicle().getModel(), this.getVehicle().getVehicleType(),
                this.getVehicle().getColor(), this.getVehicle().getOdometer(), this.getVehicle().getPrice(),
                this.getSalesTax(), this.getRecordingFee(), this.getProcessingFee(), this.getTotalPrice(),
                (isFinanced()) ? "YES" : "NO", this.getMonthlyPayment()));
        if(!(this.addOns.isEmpty())){
            output.append("|");
            for(AddOn addOn:this.addOns){
                output.append(addOn.toString());
            }
        }
        return output.toString();
    }

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinanced() {
        return finance;
    }

    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    public ArrayList<AddOn> getAddOns() {
        return addOns;
    }

    @Override
    public double getTotalPrice(){
        double totalPrice=this.getVehicle().getPrice() + this.getSalesTax() + this.getRecordingFee() + this.getProcessingFee() + this.getAddOnPrice();
        if(isFinanced()) {
            double totalInterest = (this.getVehicle().getPrice() < 10000) ? (2*0.0525) : (4*0.0424);
            return totalPrice * (1+ totalInterest);
        }
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        return (!isFinanced())?0:
                (this.getVehicle().getPrice() <10000)?this.getTotalPrice()/24:
                        this.getTotalPrice()/48;
    }

    public double getAddOnPrice(){
        double total=0;
        for(AddOn addOn:this.addOns){
            total+=addOn.getPrice();
        }
        return total;
    }
}
