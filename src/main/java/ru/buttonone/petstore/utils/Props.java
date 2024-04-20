package ru.buttonone.petstore.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class Props {

    private final static Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(Props.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            log.error("Error load config file 'application.properties'");
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}