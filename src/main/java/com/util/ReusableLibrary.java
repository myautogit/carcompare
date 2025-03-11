package com.util;

import com.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.util.AbstractDataLibrary.*;


public class ReusableLibrary extends TestBase {


    private HashMap<String, HashMap<String, String>> carOutputDataMap = new HashMap<>();
    public ArrayList<String> carRegList = new ArrayList<>();
    public String carOutputFileString= null;

    public ReusableLibrary(WebDriver driver) {
        this.driver = driver;
    }

    public static String readTextFile(String filepath) {
        if (filepath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static ArrayList<String> getCarRegList(String fileAsString) {
        ArrayList<String> carRegList = new ArrayList<>();

            Pattern pattern = Pattern.compile("\\b[A-Z]{2}\\d{2}\\s?[A-Z]{3}\\b");
            Matcher matcher = pattern.matcher(fileAsString);

            while (matcher.find()) {
                carRegList.add(matcher.group().replaceAll("\\s", ""));
            }
        return carRegList;
    }


    public ArrayList<String> getcarRegListArray(String filename) {
        String filePath = AbstractDataLibrary.getFilePath(filename);
        String inputFileString = readTextFile(filePath);
        return carRegList = getCarRegList(inputFileString);
    }

    public void getCarOutputDataAsString(String fileName) {
        String filePath = AbstractDataLibrary.getFilePath(fileName);
        carOutputFileString = readTextFile(filePath);
    }


    public HashMap<String, HashMap<String, String>> parseCarOutputFile() {
        if (carOutputFileString == null) {
            throw new IllegalArgumentException("carOutputFileString cannot be null");
        }
//        HashMap<String, HashMap<String, String>> carOutputDataMap = new HashMap<>();
        String[] lines = carOutputFileString.split("\n");


        if (lines.length > 0) {
            String[] keys = lines[0].split(",");
            int variantRegIndex = -1;

            // Find the index of the VARIANT_REG column
            for (int i = 0; i < keys.length; i++) {
                if (keys[i].equals("VARIANT_REG")) {
                    variantRegIndex = i;
                    break;
                }
            }

            if (variantRegIndex == -1) {
                throw new IllegalArgumentException("VARIANT_REG column not found in the file");
            }

            for (int i = 1; i < lines.length; i++) {
                String[] values = lines[i].split(",");
                HashMap<String, String> valueMap = new HashMap<>();
                for (int j = 0; j < keys.length; j++) {
                    valueMap.put(keys[j], values[j]);
                }
                String variantReg = values[variantRegIndex].replaceAll("\\s", "");
                carOutputDataMap.put(variantReg, valueMap);
            }
        }

        return carOutputDataMap;
    }

public void compareCarData() {
    parseCarOutputFile();
    if (carOutputDataMap == null || carOutputDataMap.isEmpty()) {
        throw new IllegalArgumentException("carOutputDataMap cannot be null or empty");
    }
    for (String carReg : carDetailsMap.keySet()) {
        System.out.println(">>Car registration: " + carReg);
        HashMap<AbstractDataLibrary.CarData, String> carDetails = carDetailsMap.get(carReg);
        if (carDetails == null) {
            System.out.println("No car details found for car registration: " + carReg);
            continue;
        }
        HashMap<String, String> carOutputDetails = carOutputDataMap.get(carReg);

        if (carOutputDetails == null) {
            System.out.println("No output data found for car registration: " + carReg);
            continue;
        }

        for (AbstractDataLibrary.CarData carData : carDetails.keySet()) {
            String detailValue = carDetails.get(carData).toLowerCase();
            String outputValue = carOutputDetails.get(carData.name()).toLowerCase();

            if (outputValue == null) {
                System.out.println("No output value found for " + carData.getLabel());
                continue;
            }

            if (detailValue.equals(outputValue.toLowerCase())) {
                System.out.println("Match for " + carData.getLabel() + ": " + detailValue);
            } else {
                System.out.println("Mismatch for " + carData.getLabel() + ": expected " + outputValue + " but found " + detailValue);
            }
        }
    }
}

}
