package com.hcl.ecommerce.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.io.*;
import java.util.Map;

public class LocalStorageUtils {

    private static final String STORAGE_FILE_PATH = "src/test/resources/LocalStorage.data";

    public static void saveStorageToFile(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            // 1. Get all Local Storage data as a Map
            Map<String, String> storageData = (Map<String, String>) js.executeScript(
                    "var items = {}; " +
                            "for (var i = 0, len = localStorage.length; i < len; ++i) { " +
                            "  items[localStorage.key(i)] = localStorage.getItem(localStorage.key(i)); " +
                            "} " +
                            "return items;");

            // 2. Write to file
            File file = new File(STORAGE_FILE_PATH);
            file.delete();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, String> entry : storageData.entrySet()) {
                // Format: Key || Value (Using || as separator to avoid confusion with json
                // content)
                writer.write(entry.getKey() + "||" + entry.getValue());
                writer.newLine();
            }
            writer.close();
            System.out.println("Local Storage saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadStorageFromFile(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            File file = new File(STORAGE_FILE_PATH);
            if (!file.exists())
                return;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                // Split by the custom separator
                String[] parts = line.split("\\|\\|", 2);
                if (parts.length >= 2) {
                    String key = parts[0];
                    String value = parts[1];
                    // Inject back into browser
                    js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);", key, value);
                }
            }
            reader.close();
            System.out.println("Local Storage loaded successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}