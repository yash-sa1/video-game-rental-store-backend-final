package com.example.demo.service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    public static void savedata(String targetinput, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(targetinput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> loadData(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return Files.readAllLines(file.toPath())
                    .stream().filter(line -> line != null && !line.equals(""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeStock(String filePath, String gameTitle, int newStock) throws IOException {
        File fileToBeModified = new File(filePath);
        List<String> filecontent = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(gameTitle + ",")) {
                    String[] parts = line.split(",");
                    parts[4] = String.valueOf(newStock);
                    line = String.join(",", parts);
                }
                filecontent.add(line);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToBeModified))) {
            for (String line : filecontent) {
                writer.write(line + "\n");
            }
        }
    }

    public static void changeReturnStatus(String filePath, String enteredCharacters, boolean newStatus) throws IOException {
        File fileToBeModified = new File(filePath);
        List<String> filecontent = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(enteredCharacters + ",false")) {
                    String[] parts = line.split(",");
                    parts[5] = String.valueOf(newStatus);
                    line = String.join(",", parts);
                }
                filecontent.add(line);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToBeModified))) {
            for (String line : filecontent) {
                writer.write(line + "\n");
            }
        }
    }

    public static List<String> readAndPrintRentalsFile(String filePath, String customerID) {
        List<String> allRentalsFromFile = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2) {
                    String stored_customerID = parts[2];
                    if (stored_customerID.equals(customerID)) {
                        allRentalsFromFile.add(line);
                        System.out.println(line);
                    }
                }
            }
            return allRentalsFromFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean check_rentalID(String filePath, String enteredSearch, String entryToCheck) throws IOException {
        File fileToBeModified = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(enteredSearch)) {
                    String[] parts = line.split(",");
                    String correctCodes = parts[4];
                    List<String> uuidList = Arrays.asList(correctCodes.split(","));
                    boolean isReturned = Boolean.parseBoolean(parts[5]);

                    List<String> filteredUUIDs = uuidList.stream()
                            .filter(uuid -> uuid.equals(entryToCheck))
                            .collect(Collectors.toList());

                    if (!filteredUUIDs.isEmpty() && !isReturned) {
                        System.out.println("successfully returned!");
                        changeReturnStatus("src/main/rentals.txt", enteredSearch, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static int create_next_id(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int count = 1;
            while (reader.readLine() != null) {
                count++;
            }
            int id = count;
            return id;
        }
    }
}