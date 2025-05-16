package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    private Dealership dealership;
    private ArrayList<Contract> contracts;

    private void init() {
        dealership = DealershipFileManager.getDealership();
        contracts=ContractFileManager.getContracts();
    }

    public void display() {
        init();
        System.out.printf("Welcome to %s\n", this.dealership.getName());
        boolean menuRunning = true;
        while (menuRunning) {
            printMenu();
            switch (MyUtils.askQuestionGetString("What would you like to do?\n")) {
                case "1": {
                    processGetByPriceRequest();
                    break;
                }
                case "2": {
                    processGetByMakeModelRequest();
                    break;
                }
                case "3": {
                    processGetByYearRequest();
                    break;
                }
                case "4": {
                    processGetByColorRequest();
                    break;
                }
                case "5": {
                    processGetByMileageRequest();
                    break;
                }
                case "6": {
                    processGetByVehicleTypeRequest();
                    break;
                }
                case "7": {
                    processGetAllVehiclesRequest();
                    break;
                }
                case "8": {
                    processAddVehicleRequest();
                    break;
                }
                case "9": {
                    processRemoveVehicleRequest();
                    break;
                }
                case "10": {
                    sellLeaseVehicle();
                    break;
                }
                case "11": {
                    AdminUserInterface adminUserInterface=new AdminUserInterface(this.contracts);
                    adminUserInterface.display();
                    break;
                }
                case "99": {
                    menuRunning = false;
                    break;
                }
                default: {
                    System.out.println("Invalid choice.");
                    MyUtils.pressEnterToContinue();
                    break;
                }
            }
        }
    }

    public void sellLeaseVehicle(){
        System.out.println("New Contract Menu");
        boolean gettingContract=true;
        while (gettingContract){
            switch (MyUtils.askQuestionGetString("Would you like to sell or lease a vehicle?\nEnter S or L, or X to return home: ").toUpperCase()) {
                case "S": {
                    processSellVehicle();
                    break;
                }
                case "L": {
                    processLeaseVehicle();
                    break;
                }
                case "X": {
                    gettingContract=false;
                    break;
                }
                default:{
                    System.out.println("Must enter valid option.");
                }
            }
        }
    }

    public void processSellVehicle(){
        System.out.println("You're selling a car.");
        boolean gettingSale=true;
        while(gettingSale){
            System.out.println("Available cars:");
            processGetAllVehiclesRequest();
            int vin = MyUtils.askQuestionGetInt("Please enter the VIN of the car to sell: ");
            Vehicle toSell = null;
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                if (vin == vehicle.getVin()) {
                    toSell = vehicle;
                    break;
                }
            }
            if (toSell==null) {
                System.out.println("VIN does not match.");
                if(!MyUtils.askQuestionGetString("Enter Y to try a new VIN: ").equalsIgnoreCase("Y")){
                    gettingSale=false;
                }
                continue;
            }
            String date = MyUtils.askQuestionGetString("Enter the date(YYYYMMDD): ");
            String name = MyUtils.askQuestionGetString("Enter the buyers name: ");
            String email = MyUtils.askQuestionGetString("Enter the buyers email: ");
            boolean financing = MyUtils.askQuestionGetString("Enter Y to finance the vehicle: ").equalsIgnoreCase("Y");
            SalesContract sale=new SalesContract(date,name,email,toSell,financing);
            System.out.println("This is the current sale info:");
            System.out.println(sale);
            if(MyUtils.askQuestionGetString("Enter Y if this is correct: ").equalsIgnoreCase("Y")){
                this.contracts.add(sale);
                this.dealership.removeVehicle(toSell);
                ContractFileManager.saveContracts(this.contracts);
                DealershipFileManager.saveDealership(this.dealership);
                System.out.println("Successfully added sale contract and removed vehicle from inventory.");
            }
            else{
                System.out.println("Did not add contract.");
            }
            if(MyUtils.askQuestionGetString("Enter Y to sell another vehicle: ").equalsIgnoreCase("Y")){
                continue;
            }
            gettingSale=false;
        }
        System.out.println("Returning to new contract menu.");
        MyUtils.pressEnterToContinue();
    }

    public void processLeaseVehicle(){
        System.out.println("You're leasing a car.");
        boolean gettingSale=true;
        while(gettingSale){
            System.out.println("Available cars:");
            List<Vehicle> canLease=this.dealership.getVehiclesByYear(LocalDate.now().getYear()-3,LocalDate.now().getYear());
            displayVehicles(canLease);
            int vin = MyUtils.askQuestionGetInt("Please enter the VIN of the car to lease: ");
            Vehicle toLease = null;
            for (Vehicle vehicle : canLease) {
                if (vin == vehicle.getVin()) {
                    toLease = vehicle;
                    break;
                }
            }
            if (toLease==null) {
                System.out.println("VIN does not match.");
                if(!MyUtils.askQuestionGetString("Enter Y to try a new VIN: ").equalsIgnoreCase("Y")){
                    gettingSale=false;
                }
                continue;
            }
            String date = MyUtils.askQuestionGetString("Enter the date(YYYYMMDD): ");
            String name = MyUtils.askQuestionGetString("Enter the buyers name: ");
            String email = MyUtils.askQuestionGetString("Enter the buyers email: ");
            LeaseContract lease=new LeaseContract(date,name,email,toLease);
            System.out.println("This is the current lease info:");
            System.out.println(lease);
            if(MyUtils.askQuestionGetString("Enter Y if this is correct: ").equalsIgnoreCase("Y")){
                this.contracts.add(lease);
                this.dealership.removeVehicle(toLease);
                ContractFileManager.saveContracts(this.contracts);
                DealershipFileManager.saveDealership(this.dealership);
                System.out.println("Successfully added lease contract and removed vehicle from inventory.");
            }
            else{
                System.out.println("Did not add contract.");
            }
            if(MyUtils.askQuestionGetString("Enter Y to lease another vehicle: ").equalsIgnoreCase("Y")){
                continue;
            }
            gettingSale=false;
        }
        System.out.println("Returning to new contract menu.");
        MyUtils.pressEnterToContinue();
    }

    public void processGetByPriceRequest() {
        List<Vehicle> filteredByPrice = this.dealership
                .getVehiclesByPrice(MyUtils.askQuestionGetDouble("Enter the minimum price: $")
                        , MyUtils.askQuestionGetDouble("Enter the maximum price: $"));
        displayVehicles(filteredByPrice);
    }

    public void processGetByMakeModelRequest() {
        List<Vehicle> filteredByMakeModel = this.dealership
                .getVehiclesByMakeModel(MyUtils.askQuestionGetString("Enter the make: "),
                        MyUtils.askQuestionGetString("Enter the model: "));
        displayVehicles(filteredByMakeModel);
    }

    public void processGetByYearRequest() {
        List<Vehicle> filteredByYear = this.dealership
                .getVehiclesByYear(MyUtils.askQuestionGetInt("Enter the earliest year: "),
                        MyUtils.askQuestionGetInt("Enter the latest year: "));
        displayVehicles(filteredByYear);
    }

    public void processGetByColorRequest() {
        List<Vehicle> filteredByColor = this.dealership
                .getVehiclesByColor(MyUtils.askQuestionGetString("Enter the color: "));
        displayVehicles(filteredByColor);
    }

    public void processGetByMileageRequest() {
        List<Vehicle> filteredByMileage = this.dealership
                .getVehiclesByMileage(MyUtils.askQuestionGetInt("Enter the minimum mileage: "),
                        MyUtils.askQuestionGetInt("Enter the maximum mileage: "));
        displayVehicles(filteredByMileage);
    }

    public void processGetByVehicleTypeRequest() {
        List<Vehicle> filteredByType = this.dealership
                .getVehiclesByType(MyUtils.askQuestionGetString("Enter the vehicle type: "));

        displayVehicles(filteredByType);
    }

    public void processGetAllVehiclesRequest() {
        displayVehicles(this.dealership.getAllVehicles());
    }

    public void processAddVehicleRequest() {
        Vehicle newVehicle= new Vehicle(MyUtils.askQuestionGetInt("Enter the VIN: "),
                MyUtils.askQuestionGetInt("Enter the year: "),
                MyUtils.askQuestionGetStringFull("Enter the make: "),
                MyUtils.askQuestionGetStringFull("Enter the model: "),
                MyUtils.askQuestionGetStringFull("Enter the vehicle type: "),
                MyUtils.askQuestionGetStringFull("Enter the color: "),
                MyUtils.askQuestionGetInt("Enter the mileage: "),
                MyUtils.askQuestionGetDouble("Enter the price: $"));
        System.out.println("Successfully added:");
        newVehicle.printVehicleInfo();
        this.dealership.addVehicle(newVehicle);
        DealershipFileManager.saveDealership(this.dealership);
        MyUtils.pressEnterToContinue();
    }

    public void processRemoveVehicleRequest() {
        int vin = MyUtils.askQuestionGetInt("Enter the VIN of the car you'd like to remove");
        boolean found=false;
        for (Vehicle vehicle : this.dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                System.out.println("Successfully removed:");
                vehicle.printVehicleInfo();
                this.dealership.removeVehicle(vehicle);
                DealershipFileManager.saveDealership(this.dealership);
                found=true;
                break;
            }
        }
        if(!found){
            System.out.println("No matching vehicle found.");
        }
        MyUtils.pressEnterToContinue();
    }

    public void displayVehicles(List<Vehicle> filteredVehicleList) {
        MyUtils.printDivider(40);
        for (Vehicle vehicle : filteredVehicleList) {
            vehicle.printVehicleInfo();
        }
        MyUtils.pressEnterToContinue();
    }

    private static void printMenu() {
        MyUtils.printDivider(40);
        System.out.println("Menu");
        System.out.println("   1-Find vehicles within a price range");
        System.out.println("   2-Find vehicles by make / model");
        System.out.println("   3-Find vehicles by year range");
        System.out.println("   4-Find vehicles by color");
        System.out.println("   5-Find vehicles by mileage range");
        System.out.println("   6-Find vehicles by type (car,truck,SUV,van)");
        System.out.println("   7-List ALL vehicles");
        System.out.println("   8-Add a vehicle");
        System.out.println("   9-Remove a vehicle");
        System.out.println("   10-Sell/Lease a vehicle");
        System.out.println("   11-Admin Menu");
        System.out.println("   99-Quit");
    }
}
