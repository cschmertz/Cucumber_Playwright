package com.managers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestDataManager {
    private static final String TEST_DATA_FILE = "src/test/resources/testdata.json";
    private static Map<String, Object> testData;

    static {
        loadTestData();
    }

    private static void loadTestData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            testData = mapper.readValue(new File(TEST_DATA_FILE), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
            testData = new HashMap<>();
        }
    }

    public static Object getTestData(String key) {
        return testData.get(key);
    }

    public static void setTestData(String key, Object value) {
        testData.put(key, value);
    }

    public static void saveTestData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(TEST_DATA_FILE), testData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
