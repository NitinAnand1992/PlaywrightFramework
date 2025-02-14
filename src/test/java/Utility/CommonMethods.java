package Utility;

//import com.fasterxml.jackson.core.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.GZIPOutputStream;

import org.json.JSONString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import testcases.api.us.pojo.Credentials;

public class CommonMethods {

    private static final List<String> firstNames = new ArrayList<>(List.of(
            "Alice", "Bob", "Charlie", "David", "Emma",
            "Frank", "Grace", "Hannah", "Isaac", "Jack",
            "Katie", "Liam", "Mia", "Noah", "Olivia",
            "Paul", "Quinn", "Ryan", "Sophia", "Tom"
    ));

    private static final List<String> lastNames = new ArrayList<>(List.of(
            "Smith", "Johnson", "Williams", "Brown", "Jones",
            "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
            "Thomas", "Taylor", "Moore", "Jackson", "Martin"
    ));

    public static String getSuiteConfiguration(String key) {
        String sValue = null;
        File file = null;
        FileInputStream fis = null;
        String SFilePath = System.getProperty("user.dir") + "/SuiteConfiguration.properties";
        Properties prop = new Properties();
        try {
            file = new File(SFilePath);
            fis = new FileInputStream(file);
            prop.load(fis);
            sValue = prop.getProperty(key.toUpperCase().trim());
        } catch (Exception e) {
            System.out.println("Not able to retrieve the value for key:::" + key + "due to " + e.getMessage());
        }
        return sValue;
    }

    public static String jsonParse(String key) {
        String sCountry = getSuiteConfiguration("APPLICATION_COUNTRY").trim().toUpperCase();
        String sEnvironment = getSuiteConfiguration("APPLICATION_ENVIRONMENT").trim().toUpperCase();
        String stype = getSuiteConfiguration("TYPE").trim().toUpperCase();
        String sValue = null;
        try {
            JSONParser parser = new JSONParser();
            String FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/"
                    + getSuiteConfiguration("APPLICATION_NAME").toLowerCase() + ".json";
            FileReader fileread = new FileReader(FILE_PATH);
            Object obj = parser.parse(fileread);
            JSONObject JsonData = (JSONObject) obj;
            JSONObject JSONCountry = (JSONObject) JsonData.get(sCountry);
            // get Environment Object
            JSONObject JSONEnv = (JSONObject) JSONCountry.get(sEnvironment);
            JSONObject JSONType = (JSONObject) JSONEnv.get(stype);
            sValue = (String) JSONType.get(key);

        } catch (Exception e) {
            System.out.println("Not Able to retieve the data from JSON file due to " + e.getMessage());
        }
        return sValue;
    }

    public static Credentials parsetokenjson(String key) {
        String sUserName = null;
        String sPassword = null;
        try {
            JSONParser parser = new JSONParser();
            String FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/token.json";
            FileReader fileread = new FileReader(FILE_PATH);
            Object obj = parser.parse(fileread);
            JSONObject JsonData = (JSONObject) obj;
            JSONArray JsonPartner = (JSONArray) JsonData.get("partners");
            for (Object partnerObj : JsonPartner) {
                JSONObject partner = (JSONObject) partnerObj;
                if (partner.containsKey(key)) {
                    JSONObject JsonType = (JSONObject) partner.get(key);
                    sUserName = (String) JsonType.get("username");
                    sPassword = (String) JsonType.get("password");
                    break;
                }
            }
            if (sUserName != null && sPassword != null) {
                System.out.println("Username: " + sUserName);
                System.out.println("Password: " + sPassword);
            } else {
                System.out.println("Key not found: " + key);
            }
        } catch (Exception e) {
            System.out.println("Not Able to retieve the data from JSON file due to " + e.getMessage());
        }
        return new Credentials(sUserName, sPassword);
    }


    public static String update_json_payload(String path, String key, String value) {


        try {
            // Parse the JSON file
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(path);
            Object obj = parser.parse(fileReader);

            // Cast the object to a JSONObject
            JSONObject jsonData = (JSONObject) obj;

            // Update the key with the new value
            jsonData.put(key, value);

            // Write the updated JSON back to the file
            try (FileWriter fileWriter = new FileWriter(path)) {
                fileWriter.write(jsonData.toJSONString());
            }

            // Return the updated JSON as a string
            return jsonData.toJSONString();
        } catch (Exception e) {
            System.out.println("Error updating JSON payload: " + e.getMessage());
            return null;
        }
    }

    public static String create_random_phone_number() {

        Random rn = new Random();
        //int length=rn.nextInt(10)+1;

        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = rn.nextInt(10);
            phoneNumber.append(digit);
        }
        System.out.println(phoneNumber);
        return phoneNumber.toString();
    }

    public static String create_random_email() {
        String emailId = "testpwautomation" + System.currentTimeMillis() + "@qa-copart-test.com";
        return emailId;
    }

    public static String random_first_name() {
        List<String> firstNames = new ArrayList<>(List.of(
                "Alice", "Bob", "Charlie", "David", "Emma",
                "Frank", "Grace", "Hannah", "Isaac", "Jack",
                "Katie", "Liam", "Mia", "Noah", "Olivia",
                "Paul", "Quinn", "Ryan", "Sophia", "Tom"
        ));

        int currentIndex = 0;
        if (currentIndex == 0) {
            Collections.shuffle(firstNames); // Shuffle only when all names are used
        }
        String name = firstNames.get(currentIndex++);
        if (currentIndex >= firstNames.size()) {
            currentIndex = 0; // Reset index after all names are used
        }
        return name;

    }

    public static String random_last_name() {

        List<String> lastNames = new ArrayList<>(List.of(
                "Smith", "Johnson", "Williams", "Brown", "Jones",
                "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
                "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
                "Thomas", "Taylor", "Moore", "Jackson", "Martin"
        ));

        int lastNameIndex = 0;
        if (lastNameIndex == 0) {
            Collections.shuffle(lastNames); // Shuffle only when all names are used
        }
        String lastName = lastNames.get(lastNameIndex++);
        if (lastNameIndex >= lastNames.size()) {
            lastNameIndex = 0; // Reset index after all names are used
        }
        return lastName;
    }

    public static String getUpdatedPayload(String FILE_PATH, HashMap<String, String> Map_POV) {


        String sUpdatedPayload = null;
        try {
            JSONParser parser = new JSONParser();
            FileReader fileread = new FileReader(FILE_PATH);
            // Get Object for the JSON file
            Object obj = parser.parse(fileread);
            // get JSON Object
            JSONObject JsonData = (JSONObject) obj;
            Iterator<String> Keys = Map_POV.keySet().iterator();
            while (Keys.hasNext()) {
                String key = (String) Keys.next();
                // Update the JSON Data
                if (key.contains("~")) {
                    String[] JSONTraverse = key.split("~");
                    int iLastIndex = JSONTraverse.length;
                    // Update the Login
                    JSONObject jsonUpdate = null;
                    for (int i = 0; i < JSONTraverse.length - 1; i++) {
                        jsonUpdate = (JSONObject) JsonData.get(JSONTraverse[i]);
                    }
                    jsonUpdate.put(JSONTraverse[iLastIndex - 1], Map_POV.get(key));
                } else {
                    // JSONObject jsonUpdate = (JSONObject) JsonData.get(key);
                    JsonData.put(key, Map_POV.get(key));
                    System.out.println();
                }

            }
            // Get a updated Payload
            System.out.println(JsonData.toString());
            sUpdatedPayload = JsonData.toString();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return sUpdatedPayload;
    }

    public static boolean compressHtmlFile(String inputFilePath, String outputFilePath) {
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath);
             GZIPOutputStream gzipOS = new GZIPOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                gzipOS.write(buffer, 0, bytesRead);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteDirectory(String dirPath) {
        try {
            Path directory = Paths.get(dirPath);
            if (Files.exists(directory)) {
                Files.walk(directory)
                        .sorted((p1, p2) -> p2.compareTo(p1)) // Start from deepest file
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                System.out.println("Failed to delete: " + path);
                            }
                        });
                System.out.println("Deleted directory: " + dirPath);
            }
        } catch (IOException e) {
            System.out.println("Error deleting directory: " + dirPath);
            e.printStackTrace();
        }
    }

    public static boolean directoryExists(String dirPath) {
        return new File(dirPath).exists();
    }

    public static void generateAllureReport() {
        String[] command = {"cmd", "/c", "allure generate --single-file allure-results"};

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(System.getProperty("user.dir")));

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Allure Report Generation Completed with Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



}

