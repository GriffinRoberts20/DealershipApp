package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ContractFileManager {
    static String contractsFilePath ="src/main/resources/contracts.csv";

    public static ArrayList<Contract> getContracts() {
        // Initialize the contract list
        ArrayList<Contract> contracts=new ArrayList<>();

        try {
            // Open file and buffer the reader
            FileReader fr = new FileReader(contractsFilePath);
            BufferedReader readContracts = new BufferedReader(fr);

            // Read the header line
            //readContracts.readLine(); //no header

            String line;
            // Read each subsequent line
            while ((line = readContracts.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines

                // Split transaction details by pipe character
                String[] contractInfo=line.split("\\|");

                // Parse line into a Vehicle object and add to inventory
                Vehicle vehicle=new Vehicle(Integer.parseInt(contractInfo[4]),Integer.parseInt(contractInfo[5]),contractInfo[6],
                        contractInfo[7],contractInfo[8],contractInfo[9],Integer.parseInt(contractInfo[10]),Double.parseDouble(contractInfo[11]));
                if(contractInfo[0].equalsIgnoreCase("SALE")){
                    boolean finance= contractInfo[16].equalsIgnoreCase("YES");
                    contracts.add(new SalesContract(contractInfo[1],contractInfo[2],contractInfo[3], vehicle,finance));
                }
                else{
                    contracts.add(new LeaseContract(contractInfo[1],contractInfo[2],contractInfo[3],vehicle));
                }
            }

            // Close reader
            readContracts.close();

        } catch (Exception e) {
            // Wrap and rethrow exceptions as runtime
            throw new RuntimeException(e);
        }

        // Return the parsed list of transactions
        return contracts;
    }

    public static void saveContracts(ArrayList<Contract> contracts) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(contractsFilePath))) {
            // Write header row
            //String header = "CONTRACT TYPE|DATE|NAME|EMAIL|VIN|YEAR|MAKE|MODEL|VEHICLE TYPE|COLOR|MILEAGE|STICKER PRICE|CONTRACT DETAILS";
            //writer.write(header);

            // Write each transaction in the correct format
            for (Contract contract : contracts) {
                if(contract instanceof SalesContract sale){
                    writer.write(sale.toString());
                }
                if(contract instanceof LeaseContract lease){
                    writer.write(lease.toString());
                }
                writer.write("\n");
            }

            // Close the writer and confirm update
            writer.close();
            System.out.println("Contracts successfully updated.");

        } catch (Exception e) {
            // Wrap and rethrow exceptions as runtime
            throw new RuntimeException(e);
        }
    }
}
