package com.util;

public abstract class AbstractDataLibrary {
    public static String configFilePath = System.getProperty("user.dir") + "/src/main/java/com/config/config.properties";
    public static String featureFilePath = System.getProperty("user.dir") + "/src/test/java/com/testcases/";

    public static String CarInputFile="src/main/resources/car_input - V6.txt";
    public static String CarOutputFile="src/main/resources/car_output - V6.txt";
}
