package com.pluralsight;

public class SalesContract extends Contract{
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private boolean finance;

    public SalesContract(String date, String name, String email, Vehicle vehicle, boolean finance) {
        super(date, name, email, vehicle);
        this.finance = finance;
        this.salesTax=0.05*this.getVehicle().getPrice();
        this.recordingFee=100;
        this.processingFee=(this.getVehicle().getPrice() <10000)?295:495;
    }

    @Override
    public String toString(){
        return String.format("SALE|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                this.getDate(),this.getName(),this.getEmail(),this.getVehicle().getVin(),this.getVehicle().getYear(),
                this.getVehicle().getMake(),this.getVehicle().getModel(),this.getVehicle().getVehicleType(),
                this.getVehicle().getColor(),this.getVehicle().getOdometer(),this.getVehicle().getPrice(),
                this.getSalesTax(),this.getRecordingFee(),this.getProcessingFee(),this.getTotalPrice(),
                (isFinanced())?"YES":"NO",this.getMonthlyPayment());
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

    @Override
    public double getTotalPrice(){
        if(isFinanced()) {
            double totalInterest = (this.getVehicle().getPrice() < 10000) ? (2*0.0525) : (4*0.0424);
            return (this.getVehicle().getPrice() * (1+ totalInterest)) + this.getSalesTax() + this.getRecordingFee() + this.getProcessingFee();
        }
        return this.getVehicle().getPrice() + this.getSalesTax() + this.getRecordingFee() + this.getProcessingFee();
    }

    @Override
    public double getMonthlyPayment() {
        return (!isFinanced())?0:
                (this.getVehicle().getPrice() <10000)?this.getTotalPrice()/24:
                        this.getTotalPrice()/48;
    }
}
