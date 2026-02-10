package com.hcl.ecommerce.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class CookieUtil {
    private static final String COOKIE_FILE_PATH = "src/test/resources/config/sessionCookies.json";

    public static void saveCookies(WebDriver driver) {
        Set<Cookie> cookies = driver.manage().getCookies();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<CookieDTO> cookieDTOs = new ArrayList<>();
            for (Cookie cookie : cookies) {
                CookieDTO dto = new CookieDTO();
                dto.setName(cookie.getName());
                dto.setValue(cookie.getValue());
                dto.setDomain(cookie.getDomain());
                dto.setPath(cookie.getPath());
                dto.setExpiry(cookie.getExpiry());
                dto.setSecure(cookie.isSecure());
                dto.setHttpOnly(cookie.isHttpOnly());
                cookieDTOs.add(dto);
            }

            File file = new File(COOKIE_FILE_PATH);
            file.getParentFile().mkdirs();
            mapper.writeValue(file, cookieDTOs);
            System.out.println("Cookies saved successfully to " + COOKIE_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to save cookies: " + e.getMessage());
        }
    }

    public static boolean loadCookies(WebDriver driver) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(COOKIE_FILE_PATH);
        if (!file.exists()) {
            System.out.println("No cookie file found at " + COOKIE_FILE_PATH);
            return false;
        }

        try {
            List<CookieDTO> cookieDTOs = mapper.readValue(file, new TypeReference<List<CookieDTO>>() {
            });
            for (CookieDTO dto : cookieDTOs) {
                Cookie cookie = new Cookie.Builder(dto.getName(), dto.getValue())
                        .domain(dto.getDomain())
                        .path(dto.getPath())
                        .expiresOn(dto.getExpiry())
                        .isSecure(dto.isSecure())
                        .isHttpOnly(dto.isHttpOnly())
                        .build();
                try {
                    driver.manage().addCookie(cookie);
                } catch (Exception e) {
                    System.out.println("Could not add cookie: " + dto.getName() + " - " + e.getMessage());
                }
            }
            System.out.println("Cookies loaded successfully.");
            return true;
        } catch (IOException e) {
            System.err.println("Failed to load cookies: " + e.getMessage());
            return false;
        }
    }

    public static class CookieDTO {
        private String name;
        private String value;
        private String domain;
        private String path;
        private Date expiry;
        private boolean isSecure;
        private boolean isHttpOnly;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Date getExpiry() {
            return expiry;
        }

        public void setExpiry(Date expiry) {
            this.expiry = expiry;
        }

        public boolean isSecure() {
            return isSecure;
        }

        public void setSecure(boolean secure) {
            isSecure = secure;
        }

        public boolean isHttpOnly() {
            return isHttpOnly;
        }

        public void setHttpOnly(boolean httpOnly) {
            isHttpOnly = httpOnly;
        }
    }
}
