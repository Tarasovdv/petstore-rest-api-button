package ru.buttonone.petstore.constans;

import java.util.List;

import static java.lang.Long.parseLong;
import static ru.buttonone.petstore.utils.Props.getProperty;

public class TestValue {
    public static final long PET_ID = parseLong(getProperty("pet-id"));
    public final static long CATEGORY_ID = parseLong(getProperty("category-id"));
    public final static long UPDATE_CATEGORY_ID = parseLong(getProperty("update-category-id"));
    public final static String CATEGORY_NAME = getProperty("category-name");
    public final static String UPDATE_CATEGORY_NAME = getProperty("update-category-name");
    public final static List<String> PHOTO_URL_LIST = List.of("photo_1", "photo_2", "photo_3");
    public final static List<String> UPDATE_PHOTO_URL_LIST = List.of("photo_4", "photo_5", "photo_6");
    public static final long TAG_ID = parseLong(getProperty("tag-id"));
    public static final long UPDATE_TAG_ID = parseLong(getProperty("update-tag-id"));
    public static final String TAG_NAME = getProperty("tag-name");
    public static final String UPDATE_TAG_NAME = getProperty("update-tag-name");
    public static final String PET_NAME = getProperty("pet-name");
    public static final String UPDATE_PET_NAME = getProperty("update-pet-name");
    public static final String PET_SCHEMA_PATH = "src/test/resources/pet_schema.json";
}