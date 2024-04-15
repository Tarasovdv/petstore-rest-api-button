package ru.buttonone.petstore.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProp {
    public String getProperty(String key) {
        Properties properties;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.prop");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке файла!");
            throw new RuntimeException();
        }
        return properties.getProperty(key);
    }
}