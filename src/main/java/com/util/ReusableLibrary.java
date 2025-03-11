package com.util;

import com.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.util.AbstractDataLibrary.*;


public class ReusableLibrary extends TestBase {



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
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(fileAsString)) {
            properties.load(reader);
            String carInputFile = properties.getProperty("CarInputFile");
            String fileContent = readTextFile(carInputFile);

            Pattern pattern = Pattern.compile("\\b[A-Z]{2}\\d{2}\\s?[A-Z]{3}\\b");
            Matcher matcher = pattern.matcher(fileContent);

            while (matcher.find()) {
                carRegList.add(matcher.group());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carRegList;
    }
    public String inputFileString = readTextFile(CarInputFile);
    public ArrayList<String> carRegList = getCarRegList(inputFileString);
}
