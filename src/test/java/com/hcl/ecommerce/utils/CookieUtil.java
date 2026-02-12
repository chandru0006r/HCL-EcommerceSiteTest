package com.hcl.ecommerce.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Date;
import java.util.Set;
import java.util.StringTokenizer;

public class CookieUtil {

    private static final String COOKIE_FILE_PATH = "src/test/resources/Cookies.data";

    public static void saveCookiesToFile(WebDriver driver) {
        File file = new File(COOKIE_FILE_PATH);
        try {
            file.delete();
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Cookie cookie : driver.manage().getCookies()) {
                bufferedWriter.write((cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";"
                        + cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure()));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Session Cookies saved successfully!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void loadCookiesFromFile(WebDriver driver) {
        try {
            File file = new File(COOKIE_FILE_PATH);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String strline;

            while ((strline = bufferedReader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(strline, ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;

                    String val;
                    if (!(val = token.nextToken()).equals("null")) {
                    }
                    Boolean isSecure = Boolean.parseBoolean(token.nextToken());

                    Cookie cookie = new Cookie.Builder(name, value)
                            .domain(domain)
                            .path(path)
                            .expiresOn(expiry)
                            .isSecure(isSecure)
                            .build();

                    driver.manage().addCookie(cookie);
                }
            }
            bufferedReader.close();
            fileReader.close();
            System.out.println("Cookies loaded. You are now logged in.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}