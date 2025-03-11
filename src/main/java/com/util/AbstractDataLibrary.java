package com.util;

import java.util.HashMap;

public abstract class AbstractDataLibrary {
    public static String configFilePath = System.getProperty("user.dir") + "/src/main/java/com/config/config.properties";
    public static String featureFilePath = System.getProperty("user.dir") + "/src/test/java/com/testcases/";

    public static String CarInputFile="src/main/resources/car_input - V6.txt";
    public static String CarOutputFile="src/main/resources/car_output - V6.txt";
    public static HashMap<String, HashMap<CarData, String>> carDetailsMap = new HashMap<>();

    public enum CarData {
        MAKE("Make"),
        MODEL("Model"),
        YEAR("Year of manufacture");

        private final String label;

        CarData(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
    public static String getFilePath(String fileName) {
        switch (fileName) {
            case "CarInputFile":
                return CarInputFile;
            case "CarOutputFile":
                return CarOutputFile;
            default:
                throw new IllegalArgumentException("Invalid file name: " + fileName);
        }
    }
}
