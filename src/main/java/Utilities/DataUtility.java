package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

// this class concerns with the Data like handling data from external files
public class DataUtility {

    public static final String TestData_Path = "src/test/resources/TestData/";


    /* Reading From Json File */
    public static String GetJsonDataFromFile(String jsonfilename, String field) {

        try {
            FileReader reader = new FileReader(TestData_Path + jsonfilename + ".json");

            JsonElement jsonElement = JsonParser.parseReader(reader);

            return jsonElement.getAsJsonObject().get(field).getAsString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String GetJsonDataFromFilePC(String filePath, String key) {

        try {
            FileReader reader = new FileReader(filePath);

            JsonElement jsonElement = JsonParser.parseReader(reader);

            return jsonElement.getAsJsonObject().get(key).getAsString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /* Reading From Properties File */
    public static String GetPropertiesDataFromFile(String propertiesfilename, String key) {

        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream(TestData_Path + propertiesfilename + ".properties"));
            return properties.getProperty(key);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void writePropertiesDataToFile(String propertiesFilename, String key, String value) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(TestData_Path + propertiesFilename + ".properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (FileOutputStream outputStream = new FileOutputStream(TestData_Path + propertiesFilename + ".properties")) {
            properties.setProperty(key, value);
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileInputStream getInputStream(String filename){

        String fileName = (TestData_Path + filename + ".xlsx");
        FileInputStream file = null;
        File srcfile = new File(fileName);
        try {
            file = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public static Object[][] GetExcelDataFromFile(String excelfilename , String sheetname){

        FileInputStream file = getInputStream(excelfilename);
        String[][] strArray;
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheet(sheetname);

            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = sheet.getRow(0).getLastCellNum();

            strArray = new String[noOfRows][noOfCols];

            for (int i = 0; i < noOfRows; i++) {

                for (int j = 0; j < noOfCols; j++) {
                    XSSFRow row = sheet.getRow(i);
                    strArray[i][j] = row.getCell(j).toString();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return strArray;
    }

    public static void writeDataToExcelFile(String excelFilename, String sheetName, int x, int y, String value) {
        String filePath = TestData_Path + excelFilename + ".xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            Row row = sheet.getRow(x);
            if (row == null) {
                row = sheet.createRow(x);
            }

            Cell cell = row.getCell(y);
            if (cell == null) {
                cell = row.createCell(y);
            }

            cell.setCellValue(value);

            // Write changes back to the file
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Creates an Excel file with configurable parameters.
     *
     * @param filename  The file name of the Excel file.
     * @param sheetName The name of the sheet to be created.
     * @param headers   Array of header values to be written to the first row.
     * @param data      2D array of data to be written to the subsequent rows.
     */
    public static void createExcelFile(String filename, String sheetName, String[] headers, String[][] data) {
        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet with the specified name
        Sheet sheet = workbook.createSheet(sheetName);

        // Create the header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill in the data rows
        for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
            Row row = sheet.createRow(rowIndex + 1); // Start from the second row
            for (int colIndex = 0; colIndex < data[rowIndex].length; colIndex++) {
                Cell cell = row.createCell(colIndex);
                cell.setCellValue(data[rowIndex][colIndex]);
            }
        }

        // Save the workbook to the specified file path
        try  {
            FileOutputStream fileOut = new FileOutputStream(TestData_Path + filename + ".xlsx");
            workbook.write(fileOut);
        } catch (IOException e) {
            System.err.println("Error while creating Excel file: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                System.err.println("Error while closing the workbook: " + e.getMessage());
            }
        }
    }


    public static String GetSpecificFieldFromExcelFile(String excelfilename , String sheetname,int x, int y){

        Object[][] colsRow = (String[][]) GetExcelDataFromFile(excelfilename,sheetname);

        return (String) colsRow[x][y];


    }

    public static void writeDataIntoJsonFile(String filename, String key, String value) {
        File file = new File(TestData_Path + filename + ".json");
        JSONObject obj;

        // Check if file exists
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                JSONParser parser = new JSONParser();
                obj = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                obj = new JSONObject();
            }
        } else {
            // Create new JSON object if file doesn't exist
            obj = new JSONObject();
        }

        // Update JSON with the new key-value pair
        obj.put(key, value);

        // Write updated JSON back to the file
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDataIntoJsonFileCheckFileExists(String filename, String key, String value) {
        File file = new File(TestData_Path + filename + ".json");

        // ✅ Check if the file exists (by name only)
        if (!file.exists()) {
            System.out.println("File does not exist. Creating new file and writing data...");
            createJsonFile(file, key, value);
        } else {
            System.out.println("File exists. Writing new data...");
            appendToJsonFile(file, key, value);
        }
    }

    // ✅ Create a new file and write the first key-value pair
    private static void createJsonFile(File file, String key, String value) {
        JSONObject obj = new JSONObject();
        obj.put(key, value);

        try (FileWriter writer = new FileWriter(file)) { // No append mode (overwrite)
            writer.write(obj.toJSONString());
            writer.flush();
            System.out.println("New JSON file created and data added.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToJsonFileString(String filename, String key, String value) {
        File file = new File(TestData_Path + filename + ".json");
        JSONParser parser = new JSONParser();
        JSONObject obj;

        // Check if file exists and is non-empty
        if (file.exists() && file.length() > 0) {
            try (FileReader reader = new FileReader(file)) {
                obj = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                // File exists but content is invalid, start fresh
                System.out.println("Existing file is empty or invalid JSON. Starting with new object.");
                obj = new JSONObject();
            }
        } else {
            // File doesn't exist or is empty
            System.out.println("File is empty or doesn't exist. Starting with new object.");
            obj = new JSONObject();
        }

        // Add or update key-value
        obj.put(key, value);

        // Write updated content back to file
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(obj.toJSONString());
            writer.flush();
            System.out.println("Data written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void appendToJsonFile(File file, String key, String value) {
        JSONParser parser = new JSONParser();
        JSONObject obj;

        try (FileReader reader = new FileReader(file)) {
            obj = (JSONObject) parser.parse(reader); // Read existing content

            // Add new key-value
            obj.put(key, value);

            // Overwrite file with updated JSON
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(obj.toJSONString());
                writer.flush();
                System.out.println("Data appended successfully.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static int getAndIncrementCounter(String filename) {
        File file = new File(TestData_Path + filename + ".json");
        int counter = 0;

        try {
            if (file.exists()) {
                List<String> lines = Files.readAllLines(file.toPath());
                if (!lines.isEmpty()) {
                    counter = Integer.parseInt(lines.get(0));
                }
            }

            counter++;

            Files.write(file.toPath(), String.valueOf(counter).getBytes());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error handling the counter file: " + e.getMessage());
            // You can log the error or handle it in another way
        }

        return counter;
    }



//    public static String getProjectRoot() {
//        return Paths.get(".").toAbsolutePath().normalize().toString();
//    }
//
//    public static String getTestDataFolder(String fileNameWithExtention) {
//        return getProjectRoot() + fileSeparator() + TestData_Path + fileNameWithExtention;
//    }
//
//    public static String fileSeparator() {
//        return System.getProperty("file.separator");
//    }

}
