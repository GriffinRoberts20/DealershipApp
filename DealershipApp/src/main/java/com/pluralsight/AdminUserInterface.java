package com.pluralsight;

import java.util.ArrayList;

public class AdminUserInterface {
    private ArrayList<Contract> contracts;
    private String password;

    public AdminUserInterface(ArrayList<Contract> contracts) {
        this.contracts = contracts;
        this.password = "spaghetti";
    }

    public void display(){
        if(MyUtils.askQuestionGetString("Enter password: ").equals(this.password)){
            boolean adminRunning=true;
            while(adminRunning){
                printMenu();
                switch (MyUtils.askQuestionGetString("Enter Choice: ").toUpperCase()){
                    case "1":{
                        System.out.println("ALL CONTRACTS: ");
                        for(Contract contract:contracts){
                            System.out.println(contract);
                        }
                        MyUtils.pressEnterToContinue();
                        break;
                    }
                    case "2":{
                        System.out.println("LAST 10 CONTRACTS: ");
                        int i=0;
                        for(Contract contract:contracts){
                            System.out.println(contract);
                            i++;
                            if(i==10) break;
                        }
                        MyUtils.pressEnterToContinue();
                        break;
                    }
                    case "3":{
                        System.out.println("ALL SALES: ");
                        for(Contract contract:contracts){
                            if(contract instanceof SalesContract sale){
                                System.out.println(sale);
                            }
                        }
                        MyUtils.pressEnterToContinue();
                        break;
                    }
                    case "4":{
                        System.out.println("ALL LEASES: ");
                        for(Contract contract:contracts){
                            if(contract instanceof LeaseContract lease){
                                System.out.println(lease);
                            }
                        }
                        MyUtils.pressEnterToContinue();
                        break;
                    }
                    case "X":{
                        adminRunning=false;
                        break;
                    }
                }
            }
        }
        else{
            System.out.println("Incorrect password.");
        }
        System.out.println("Returning to home menu.");
        MyUtils.pressEnterToContinue();
    }

    public void printMenu(){
        MyUtils.printDivider(40);
        System.out.println("Menu");
        System.out.println("   1-LIST ALL CONTRACTS");
        System.out.println("   2-LIST LAST 10 CONTRACTS");
        System.out.println("   3-LIST ALL SALES");
        System.out.println("   4-LIST ALL LEASES");
        System.out.println("   X-EXIT");
    }
}
