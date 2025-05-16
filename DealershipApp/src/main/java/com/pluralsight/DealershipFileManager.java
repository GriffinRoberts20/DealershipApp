package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class DealershipFileManager {
    static String dealershipFilePath="src/main/resources/dealership.csv";

    public static Dealership getDealership() {
        // Initialize the transaction list
        Dealership dealership;

        try {
            // Open file and buffer the reader
            FileReader fr = new FileReader(dealershipFilePath);
            BufferedReader readDealership = new BufferedReader(fr);

            // Read the header line
            String line=readDealership.readLine();
            String[] dealershipInfo=line.split("\\|");
            dealership=new Dealership(dealershipInfo[0].trim(),dealershipInfo[1].trim(),dealershipInfo[2].trim());

            // Read each subsequent line
            while ((line = readDealership.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines

                // Split transaction details by pipe character
                String[] vehicleInfo = line.split("\\|");

                // Parse line into a Vehicle object and add to inventory
                Vehicle vehicle = new Vehicle(
                        Integer.parseInt(vehicleInfo[0]), Integer.parseInt(vehicleInfo[1]), vehicleInfo[2], vehicleInfo[3],
                        vehicleInfo[4],vehicleInfo[5],Integer.parseInt(vehicleInfo[6]),Double.parseDouble(vehicleInfo[7]));
                dealership.addVehicle(vehicle);
            }

            // Close reader
            readDealership.close();

        } catch (Exception e) {
            // Wrap and rethrow exceptions as runtime
            throw new RuntimeException(e);
        }

        // Return the parsed list of transactions
        return dealership;
    }

    public static void saveDealership(Dealership dealership) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dealershipFilePath))) {
            // Write header row
            String header = String.format("%s|%s|%s",dealership.getName(),dealership.getAddress(),dealership.getPhone());
            writer.write(header);

            // Write each transaction in the correct format
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                String line = String.format("%d|%d|%s|%s|%s|%s|%d|%.2f\n",
                        vehicle.getVin(),vehicle.getYear(),vehicle.getMake(),
                        vehicle.getModel(),vehicle.getVehicleType(),vehicle.getColor(),
                        vehicle.getOdometer(),vehicle.getPrice());
                writer.write(line);
            }

            // Close the writer and confirm update
            writer.close();
            System.out.println("Dealership successfully updated.");

        } catch (Exception e) {
            // Wrap and rethrow exceptions as runtime
            throw new RuntimeException(e);
        }
    }
}
