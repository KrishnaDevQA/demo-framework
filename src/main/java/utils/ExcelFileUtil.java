package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelFileUtil {
    private static final Logger logger = LogManager.getLogger(ExcelFileUtil.class);

    // ------ Helper method to convert Cell to String ---------
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double val = cell.getNumericCellValue();
                    // Remove .0 if integer
                    if (val == Math.floor(val)) {
                        return String.valueOf((long) val);
                    } else {
                        return String.valueOf(val);
                    }
                }
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BLANK:
            case _NONE:
            case ERROR:
            default:      return "";
        }
    }

    // ##### Excel Methods #####
    public static void createExcelFileIfNotExists(String filePath) throws IOException {
        createExcelFileIfNotExists(filePath, "Sheet1");
    }
    public static void createExcelFileIfNotExists(String filePath, String[] headers) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("File already exists: " + filePath);
            return;
        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                System.out.println("Excel file created at: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createExcelFileIfNotExists(String filePath, List<String> headers) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("File already exists: " + filePath);
            return;
        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                System.out.println("Excel file created at: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createExcelFileIfNotExists(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            Workbook workbook = new XSSFWorkbook();
            workbook.createSheet(sheetName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                //logger.info("Excel file created with sheet: {}", sheetName);
            } catch (IOException e) {
                logger.error("Error creating Excel file: {}", e.getMessage(), e);
                throw e;
            } finally {
                try {
                    workbook.close();
                } catch (IOException ignored) {}
            }
        } else {
            //logger.info("File already exists: {}", filePath);
        }
    }
    public static boolean deleteExcelFileIfExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                //logger.info("Deleted file: {}", filePath);
                return true;
            } else {
                logger.error("Failed to delete file: {}", filePath);
                throw new IOException("Failed to delete file: " + filePath);
            }
        } else {
            //logger.info("File does not exist: {}", filePath);
            return false;
        }
    }

    // ##### Sheet Methods #####
    public static void createSheetIfNotExists(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            if (workbook.getSheet(sheetName) != null) {
                //logger.info("Sheet '{}' already exists in file '{}'", sheetName, filePath);
                return;
            }

            workbook.createSheet(sheetName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                //logger.info("Sheet '{}' created in file '{}'", sheetName, filePath);
            }
        } catch (IOException e) {
            logger.error("Error creating sheet '{}': {}", sheetName, e.getMessage(), e);
            throw e;
        }
    }
    public static void deleteSheetIfExists(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            int sheetIndex = workbook.getSheetIndex(sheetName);
            if (sheetIndex == -1) {
                //logger.info("Sheet '{}' does not exist in file '{}'", sheetName, filePath);
                return;
            }

            workbook.removeSheetAt(sheetIndex);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                //logger.info("Sheet '{}' deleted from file '{}'", sheetName, filePath);
            }
        } catch (IOException e) {
            logger.error("Error deleting sheet '{}': {}", sheetName, e.getMessage(), e);
            throw e;
        }
    }
    public static List<String> getSheetNames(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        List<String> sheetNames = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                sheetNames.add(workbook.getSheetName(i));
            }

            //logger.info("Retrieved {} sheets from file '{}'", sheetNames.size(), filePath);

        } catch (IOException e) {
            logger.error("Error reading sheets from file '{}': {}", filePath, e.getMessage(), e);
            throw e;
        }

        return sheetNames;
    }

    // ##### Row Methods #####
    public static List<String> readRow(String filePath, String sheetName, int rowIndex) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        List<String> rowData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                //logger.info("Row {} is empty or does not exist in sheet '{}' in file '{}'", rowIndex, sheetName, filePath);
                return rowData; // empty list
            }

            for (Cell cell : row) {
                rowData.add(getCellValueAsString(cell));
            }

            //logger.info("Read row {} from sheet '{}' in file '{}'", rowIndex, sheetName, filePath);

        } catch (IOException e) {
            logger.error("Error reading row {} from sheet '{}': {}", rowIndex, sheetName, e.getMessage(), e);
            throw e;
        }

        return rowData;
    }
    public static List<List<String>> readRows(String filePath, String sheetName, List<Integer> rowIndices) throws IOException {
        List<List<String>> rowsData = new ArrayList<>();
        for (int rowIndex : rowIndices) {
            rowsData.add(readRow(filePath, sheetName, rowIndex));
        }
        //logger.info("Read {} rows from sheet '{}' in file '{}'", rowIndices.size(), sheetName, filePath);
        return rowsData;
    }
    public static List<List<String>> readAllRows(String filePath, String sheetName, boolean skipHeader) throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
        throw new IOException("Excel file does not exist: " + filePath);
    }

    List<List<String>> allRows = new ArrayList<>();

    try (FileInputStream fis = new FileInputStream(file);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
        }

        DataFormatter formatter = new DataFormatter();

        int startRow = skipHeader ? 1 : 0;
        int lastRowNum = sheet.getLastRowNum();

        for (int i = startRow; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            List<String> rowData = new ArrayList<>();

            if (row != null) {
                int lastCellNum = row.getLastCellNum(); // total columns

                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    String value = formatter.formatCellValue(cell);

                    // Normalize blank values
                    if (value == null || value.trim().isEmpty()) {
                        value = ""; // keep empty but preserve position
                    }

                    rowData.add(value);
                }
            }

            allRows.add(rowData);
        }

    } catch (IOException e) {
        logger.error("Error reading all rows from sheet '{}': {}", sheetName, e.getMessage(), e);
        throw e;
    }

    return allRows;
}
    public static List<String> readRow(String filePath, String sheetName, String columnName, String columnValue) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            // Find index of the columnName
            int colIndex = -1;
            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && columnName.equals(cell.getStringCellValue())) {
                    colIndex = i;
                    break;
                }
            }
            if (colIndex == -1) {
                throw new IOException("Column '" + columnName + "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell cell = row.getCell(colIndex);
                String cellVal = getCellValueAsString(cell);
                if (columnValue.equals(cellVal)) {
                    // Found matching row, read all cells in this row
                    List<String> rowData = new ArrayList<>();
                    int lastCellInRow = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                    for (int j = 0; j < lastCellInRow; j++) {
                        Cell c = row.getCell(j);
                        rowData.add(getCellValueAsString(c));
                    }
                    //logger.info("Read row {} from sheet '{}' where column '{}' == '{}'", i, sheetName, columnName, columnValue);
                    return rowData;
                }
            }

            logger.warn("No row found in sheet '{}' where column '{}' == '{}'", sheetName, columnName, columnValue);
            return new ArrayList<>(); // or throw if you prefer no match found

        } catch (IOException e) {
            logger.error("Error reading row conditionally from sheet '{}': {}", sheetName, e.getMessage(), e);
            throw e;
        }
    }

    // ##### Column Methods #####
    public static void createColumn(String filePath, String sheetName, String columnName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                headerRow = sheet.createRow(0);
            }

            // Check if column already exists
            boolean columnExists = false;
            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && columnName.equals(cell.getStringCellValue())) {
                    columnExists = true;
                    break;
                }
            }

            if (columnExists) {
                //logger.info("Column '{}' already exists in sheet '{}' of file '{}'", columnName, sheetName, filePath);
                return;
            }

            // Add column at the end
            Cell newCell = headerRow.createCell(lastCellNum);
            newCell.setCellValue(columnName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                //logger.info("Column '{}' created in sheet '{}' of file '{}'", columnName, sheetName, filePath);
            }

        } catch (IOException e) {
            logger.error("Error creating column '{}' in sheet '{}': {}", columnName, sheetName, e.getMessage(), e);
            throw e;
        }
    }
    public static List<String> readColumn(String filePath, String sheetName, String columnName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        List<String> columnData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            // Find column index by name
            int colIndex = -1;
            for (Cell cell : headerRow) {
                if (columnName.equals(cell.getStringCellValue())) {
                    colIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (colIndex == -1) {
                throw new IOException("Column '" + columnName + "' not found in sheet '" + sheetName + "'");
            }

            // Read data from column (excluding header row)
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    columnData.add("");
                    continue;
                }
                Cell cell = row.getCell(colIndex);
                columnData.add(getCellValueAsString(cell));
            }

            //logger.info("Read column '{}' ({} rows) from sheet '{}' in file '{}'", columnName, columnData.size(), sheetName, filePath);

        } catch (IOException e) {
            logger.error("Error reading column '{}' from sheet '{}': {}", columnName, sheetName, e.getMessage(), e);
            throw e;
        }

        return columnData;
    }
    public static List<String> readAllColumnConditionally(
            String filePath,
            String sheetName,
            String conditionalColName,
            String conditionalColValue,
            String columnNameToRead) throws IOException {

        List<String> results = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            int condColIndex = -1;
            int readColIndex = -1;

            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();

            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String headerVal = cell.getStringCellValue();

                    if (conditionalColName.equals(headerVal)) {
                        condColIndex = i;
                    }
                    if (columnNameToRead.equals(headerVal)) {
                        readColIndex = i;
                    }
                }
            }

            if (condColIndex == -1) {
                throw new IOException("Conditional column '" + conditionalColName + "' not found in sheet '" + sheetName + "'");
            }
            if (readColIndex == -1) {
                throw new IOException("Column '" + columnNameToRead + "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();

            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell condCell = row.getCell(condColIndex);
                String condVal = getCellValueAsString(condCell);

                if (conditionalColValue.equals(condVal)) {
                    Cell readCell = row.getCell(readColIndex);
                    if (readCell != null) {
                        results.add(getCellValueAsString(readCell));
                    }
                }
            }

            return results;

        } catch (IOException e) {
            logger.error("Error reading column '{}' conditionally in sheet '{}': {}", columnNameToRead, sheetName, e.getMessage(), e);
            throw e;
        }
    }

    public static List<String> readAllColumnConditionallyReverse(
            String filePath,
            String sheetName,
            String conditionalColName,
            String conditionalColValue,
            String columnNameToRead) throws IOException {

        List<String> results = new ArrayList<>();

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IOException(
                        "Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                throw new IOException(
                        "Sheet '" + sheetName + "' does not have a header row");
            }

            int condColIndex = -1;
            int readColIndex = -1;

            int lastCellNum = headerRow.getLastCellNum() == -1
                    ? 0
                    : headerRow.getLastCellNum();

            for (int i = 0; i < lastCellNum; i++) {

                Cell cell = headerRow.getCell(i);

                if (cell != null) {

                    String headerVal = cell.getStringCellValue();

                    if (conditionalColName.equals(headerVal)) {
                        condColIndex = i;
                    }

                    if (columnNameToRead.equals(headerVal)) {
                        readColIndex = i;
                    }
                }
            }

            if (condColIndex == -1) {
                throw new IOException(
                        "Conditional column '" + conditionalColName
                                + "' not found in sheet '" + sheetName + "'");
            }

            if (readColIndex == -1) {
                throw new IOException(
                        "Column '" + columnNameToRead
                                + "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();

            for (int i = 1; i <= lastRowNum; i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                Cell condCell = row.getCell(condColIndex);

                String condVal = getCellValueAsString(condCell);

                // REVERSED CONDITION
                if (!conditionalColValue.equals(condVal)) {

                    Cell readCell = row.getCell(readColIndex);

                    if (readCell != null) {
                        results.add(getCellValueAsString(readCell));
                    }
                }
            }

            return results;

        } catch (IOException e) {

            logger.error(
                    "Error reading column '{}' conditionally in sheet '{}': {}",
                    columnNameToRead,
                    sheetName,
                    e.getMessage(),
                    e);

            throw e;
        }
    }

    public static List<String> readAllEmptyCellConditionally(
            String filePath,
            String sheetName,
            String conditionalColName,
            String conditionalColValue,
            String columnNameToRead) throws IOException {

        List<String> results = new ArrayList<>();

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IOException(
                        "Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                throw new IOException(
                        "Sheet '" + sheetName + "' does not have a header row");
            }

            int condColIndex = -1;
            int readColIndex = -1;

            int lastCellNum = headerRow.getLastCellNum() == -1
                    ? 0
                    : headerRow.getLastCellNum();

            for (int i = 0; i < lastCellNum; i++) {

                Cell cell = headerRow.getCell(i);

                if (cell != null) {

                    String headerVal = getCellValueAsString(cell);

                    if (conditionalColName.equals(headerVal)) {
                        condColIndex = i;
                    }

                    if (columnNameToRead.equals(headerVal)) {
                        readColIndex = i;
                    }
                }
            }

            if (condColIndex == -1) {
                throw new IOException(
                        "Conditional column '" + conditionalColName
                                + "' not found in sheet '" + sheetName + "'");
            }

            if (readColIndex == -1) {
                throw new IOException(
                        "Column '" + columnNameToRead
                                + "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();

            for (int i = 1; i <= lastRowNum; i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                Cell condCell = row.getCell(condColIndex);

                String condVal = getCellValueAsString(condCell);

                boolean isConditionMatched;

                // Match blank/null condition
                if (conditionalColValue == null
                        || conditionalColValue.trim().isEmpty()) {

                    isConditionMatched =
                            (condVal == null || condVal.trim().isEmpty());

                } else {

                    isConditionMatched =
                            conditionalColValue.equals(condVal);
                    // use equalsIgnoreCase(condVal) if needed
                }

                if (isConditionMatched) {

                    Cell readCell = row.getCell(readColIndex);

                    if (readCell != null) {
                        results.add(getCellValueAsString(readCell));
                    } else {
                        results.add("");
                    }
                }
            }

            return results;

        } catch (IOException e) {

            logger.error(
                    "Error reading column '{}' conditionally in sheet '{}': {}",
                    columnNameToRead,
                    sheetName,
                    e.getMessage(),
                    e);

            throw e;
        }
    }

    public static String readColumnConditionally(String filePath, String sheetName, String conditionalColName, String conditionalColValue, String columnNameToRead) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            int condColIndex = -1;
            int readColIndex = -1;
            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();

            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String headerVal = cell.getStringCellValue();
                    if (conditionalColName.equals(headerVal)) {
                        condColIndex = i;
                    }
                    if (columnNameToRead.equals(headerVal)) {
                        readColIndex = i;
                    }
                }
            }

            if (condColIndex == -1) {
                throw new IOException("Conditional column '" + conditionalColName + "' not found in sheet '" + sheetName + "'");
            }
            if (readColIndex == -1) {
                throw new IOException("Column '" + columnNameToRead + "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();

            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell condCell = row.getCell(condColIndex);
                String condVal = getCellValueAsString(condCell);

                if (conditionalColValue.equals(condVal)) {
                    Cell readCell = row.getCell(readColIndex);
                    if (readCell != null) {
                        return getCellValueAsString(readCell);
                    }
                }
            }

            return "";

        } catch (IOException e) {
            logger.error("Error reading column '{}' conditionally in sheet '{}': {}", columnNameToRead, sheetName, e.getMessage(), e);
            throw e;
        }
    }
    public static String readColumnConditionally(String filePath, String sheetName, int headerRowIndex, String conditionalColName, String conditionalColValue, String columnNameToRead) throws IOException {

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IOException(
                        "Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(headerRowIndex);

            if (headerRow == null) {
                throw new IOException(
                        "Header row " + headerRowIndex + " does not exist in sheet '" + sheetName + "'");
            }

            int condColIndex = -1;
            int readColIndex = -1;

            int lastCellNum = headerRow.getLastCellNum() == -1
                    ? 0
                    : headerRow.getLastCellNum();

            // Find column indexes from header row
            for (int i = 0; i < lastCellNum; i++) {

                Cell cell = headerRow.getCell(i);

                if (cell != null) {

                    String headerVal = getCellValueAsString(cell);

                    if (conditionalColName.equals(headerVal)) {
                        condColIndex = i;
                    }

                    if (columnNameToRead.equals(headerVal)) {
                        readColIndex = i;
                    }
                }
            }

            if (condColIndex == -1) {
                throw new IOException(
                        "Conditional column '" + conditionalColName +
                                "' not found in sheet '" + sheetName + "'");
            }

            if (readColIndex == -1) {
                throw new IOException(
                        "Column '" + columnNameToRead +
                                "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();

            // Start reading after header row
            for (int i = headerRowIndex + 1; i <= lastRowNum; i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                Cell condCell = row.getCell(condColIndex);

                String condVal = getCellValueAsString(condCell);

                if (conditionalColValue.equals(condVal)) {

                    Cell readCell = row.getCell(readColIndex);

                    if (readCell != null) {
                        return getCellValueAsString(readCell);
                    }

                    return "";
                }
            }

            return "";

        } catch (IOException e) {

            logger.error(
                    "Error reading column '{}' conditionally in sheet '{}': {}",
                    columnNameToRead,
                    sheetName,
                    e.getMessage(),
                    e);

            throw e;
        }
    }
    public static List<String> readFilledColumn(String filePath, String sheetName, String columnName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        List<String> columnData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            int colIndex = -1;
            for (Cell cell : headerRow) {
                if (columnName.equals(cell.getStringCellValue())) {
                    colIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (colIndex == -1) {
                throw new IOException("Column '" + columnName + "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell cell = row.getCell(colIndex);
                if (cell == null || cell.getCellType() == CellType.BLANK) continue;

                String value = getCellValueAsString(cell).trim();
                if (!value.isEmpty()) {
                    columnData.add(value);
                }
            }

            //logger.info("Read {} filled cells from column '{}' in sheet '{}' in file '{}'", columnData.size(), columnName, sheetName, filePath);

        } catch (IOException e) {
            logger.error("Error reading filled cells from column '{}' in sheet '{}': {}", columnName, sheetName, e.getMessage(), e);
            throw e;
        }

        return columnData;
    }
    public static Map<String, List<String>> readColumns(String filePath, String sheetName, List<String> columnNames) throws IOException {
        Map<String, List<String>> columnsData = new LinkedHashMap<>();

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            // Map each requested column name to its index
            Map<String, Integer> colIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                String name = cell.getStringCellValue();
                if (columnNames.contains(name)) {
                    colIndexMap.put(name, cell.getColumnIndex());
                }
            }

            // Check all columns found
            for (String colName : columnNames) {
                if (!colIndexMap.containsKey(colName)) {
                    throw new IOException("Column '" + colName + "' not found in sheet '" + sheetName + "'");
                }
            }

            int lastRowNum = sheet.getLastRowNum();

            // Initialize lists
            for (String colName : columnNames) {
                columnsData.put(colName, new ArrayList<>());
            }

            // Read data row-wise for each requested column
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);

                for (String colName : columnNames) {
                    int colIndex = colIndexMap.get(colName);
                    String cellValue = "";

                    if (row != null) {
                        Cell cell = row.getCell(colIndex);
                        cellValue = getCellValueAsString(cell);
                    }

                    columnsData.get(colName).add(cellValue);
                }
            }

            //logger.info("Read {} columns ({} rows each) from sheet '{}' in file '{}'", columnNames.size(), lastRowNum, sheetName, filePath);

        } catch (IOException e) {
            logger.error("Error reading columns '{}' from sheet '{}': {}", columnNames, sheetName, e.getMessage(), e);
            throw e;
        }

        return columnsData;
    }
    public static Map<String, String> readColumnsAsMap(String filePath, String sheetName, List<String> columnNamesToRead) throws IOException {
        Map<String, String> columnData = new LinkedHashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException(
                        "Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException(
                        "Sheet '" + sheetName + "' does not have a header row");
            }

            // Map column names to indexes
            Map<String, Integer> colIndexMap = new HashMap<>();

            for (Cell cell : headerRow) {
                String columnName = cell.getStringCellValue().trim();

                if (columnNamesToRead.contains(columnName)) {
                    colIndexMap.put(columnName, cell.getColumnIndex());
                }
            }

            // Validate all requested columns exist
            for (String colName : columnNamesToRead) {
                if (!colIndexMap.containsKey(colName)) {
                    throw new IOException(
                            "Column '" + colName + "' not found in sheet '" + sheetName + "'");
                }
            }

            // Read only first data row (row index 1)
            Row dataRow = sheet.getRow(1);

            for (String colName : columnNamesToRead) {

                String cellValue = "";

                if (dataRow != null) {
                    int colIndex = colIndexMap.get(colName);

                    Cell cell = dataRow.getCell(colIndex);

                    cellValue = getCellValueAsString(cell);
                }

                columnData.put(colName, cellValue);
            }

        } catch (IOException e) {
            logger.error(
                    "Error reading columns '{}' from sheet '{}': {}",
                    columnNamesToRead,
                    sheetName,
                    e.getMessage(),
                    e);

            throw e;
        }

        return columnData;
    }
    public static Map<String, List<String>> readColumns(String filePath, String sheetName, String columnNameAsKey, List<String> columnNames) throws IOException {

        Map<String, List<String>> result = new LinkedHashMap<>();

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            // Map column names to indexes
            Map<String, Integer> colIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                String name = cell.getStringCellValue();
                if (columnNames.contains(name) || name.equals(columnNameAsKey)) {
                    colIndexMap.put(name, cell.getColumnIndex());
                }
            }

            // Validate key column
            if (!colIndexMap.containsKey(columnNameAsKey)) {
                throw new IOException("Key column '" + columnNameAsKey + "' not found in sheet '" + sheetName + "'");
            }

            // Validate other columns
            for (String colName : columnNames) {
                if (!colIndexMap.containsKey(colName)) {
                    throw new IOException("Column '" + colName + "' not found in sheet '" + sheetName + "'");
                }
            }

            int lastRowNum = sheet.getLastRowNum();

            // Iterate rows
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Read key
                Cell keyCell = row.getCell(colIndexMap.get(columnNameAsKey));
                String key = getCellValueAsString(keyCell);

                // Read values for requested columns
                List<String> values = new ArrayList<>();
                for (String colName : columnNames) {
                    Cell cell = row.getCell(colIndexMap.get(colName));
                    values.add(getCellValueAsString(cell));
                }

                result.put(key, values);
            }

        } catch (IOException e) {
            logger.error("Error reading columns '{}' with key '{}' from sheet '{}': {}",
                    columnNames, columnNameAsKey, sheetName, e.getMessage(), e);
            throw e;
        }

        return result;
    }
    public static Map<String, String> readFilledColumns(String filePath, String sheetName, String columnNameHavingKeys, String columnNameHavingValues) throws IOException {
        Map<String, String> resultMap = new LinkedHashMap<>();

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            int keyColIndex = -1;
            int valueColIndex = -1;

            for (Cell cell : headerRow) {
                String headerValue = getCellValueAsString(cell).trim();
                if (columnNameHavingKeys.equals(headerValue)) {
                    keyColIndex = cell.getColumnIndex();
                }
                if (columnNameHavingValues.equals(headerValue)) {
                    valueColIndex = cell.getColumnIndex();
                }
            }

            if (keyColIndex == -1) {
                throw new IOException("Key column '" + columnNameHavingKeys + "' not found in sheet '" + sheetName + "'");
            }
            if (valueColIndex == -1) {
                throw new IOException("Value column '" + columnNameHavingValues + "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell keyCell = row.getCell(keyColIndex);
                Cell valueCell = row.getCell(valueColIndex);

                String key = getCellValueAsString(keyCell).trim();
                String value = getCellValueAsString(valueCell).trim();

                if (!key.isEmpty() && !value.isEmpty()) {
                    resultMap.put(key, value);
                }
            }

            //logger.info("Read {} key-value pairs from columns '{}' and '{}' in sheet '{}' in file '{}'",resultMap.size(), columnNameHavingKeys, columnNameHavingValues, sheetName, filePath);

        } catch (IOException e) {
            logger.error("Error reading key-value pairs from columns '{}' and '{}' in sheet '{}': {}",
                    columnNameHavingKeys, columnNameHavingValues, sheetName, e.getMessage(), e);
            throw e;
        }

        return resultMap;
    }
    public static void writeColumn(String filePath, String sheetName, String columnName, List<String> data) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                headerRow = sheet.createRow(0);
            }

            int colIndex = -1;
            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();

            // Find existing column or create new
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && columnName.equals(cell.getStringCellValue())) {
                    colIndex = i;
                    break;
                }
            }
            if (colIndex == -1) {
                colIndex = lastCellNum;
                Cell newCell = headerRow.createCell(colIndex);
                newCell.setCellValue(columnName);
            }

            // Write data starting from row 1 (below header)
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.getRow(i + 1);
                if (row == null) {
                    row = sheet.createRow(i + 1);
                }
                Cell cell = row.getCell(colIndex);
                if (cell == null) {
                    cell = row.createCell(colIndex);
                }
                cell.setCellValue(data.get(i));
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                //logger.info("Written {} entries to column '{}' in sheet '{}' of file '{}'", data.size(), columnName, sheetName, filePath);
            }

        } catch (IOException e) {
            logger.error("Error writing to column '{}' in sheet '{}': {}", columnName, sheetName, e.getMessage(), e);
            throw e;
        }
    }
    public static void writeColumn(String filePath, String sheetName, String columnName, List<String> data, int headerRowIndex) throws IOException {

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IOException(
                        "Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            // Get/Create header row
            Row headerRow = sheet.getRow(headerRowIndex);

            if (headerRow == null) {
                headerRow = sheet.createRow(headerRowIndex);
            }

            int colIndex = -1;

            int lastCellNum = headerRow.getLastCellNum() == -1
                    ? 0
                    : headerRow.getLastCellNum();

            // Find existing column
            for (int i = 0; i < lastCellNum; i++) {

                Cell cell = headerRow.getCell(i);

                if (cell != null &&
                        columnName.equals(getCellValueAsString(cell))) {

                    colIndex = i;
                    break;
                }
            }

            // Create new column if not found
            if (colIndex == -1) {

                colIndex = lastCellNum;

                Cell newCell = headerRow.createCell(colIndex);

                newCell.setCellValue(columnName);
            }

            // Write data below header row
            for (int i = 0; i < data.size(); i++) {

                int rowIndex = headerRowIndex + 1 + i;

                Row row = sheet.getRow(rowIndex);

                if (row == null) {
                    row = sheet.createRow(rowIndex);
                }

                Cell cell = row.getCell(colIndex);

                if (cell == null) {
                    cell = row.createCell(colIndex);
                }

                cell.setCellValue(data.get(i));
            }

            // Save workbook
            try (FileOutputStream fos = new FileOutputStream(file)) {

                workbook.write(fos);

                //logger.info(
                //        "Written {} entries to column '{}' in sheet '{}'",
                //        data.size(),
                //        columnName,
                //        sheetName);
            }

        } catch (IOException e) {

            logger.error(
                    "Error writing to column '{}' in sheet '{}': {}",
                    columnName,
                    sheetName,
                    e.getMessage(),
                    e);

            throw e;
        }
    }
    public static void writeColumn(String filePath, String sheetName, String columnName, String data) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                headerRow = sheet.createRow(0);
            }

            int colIndex = -1;
            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();

            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && columnName.equals(cell.getStringCellValue())) {
                    colIndex = i;
                    break;
                }
            }

            if (colIndex == -1) {
                colIndex = lastCellNum;
                Cell newCell = headerRow.createCell(colIndex);
                newCell.setCellValue(columnName);
            }

            int rowIndex = 1;
            while (true) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) break;

                Cell cell = row.getCell(colIndex);
                if (cell == null || cell.getCellType() == CellType.BLANK || cell.toString().isEmpty()) break;

                rowIndex++;
            }

            Row targetRow = sheet.getRow(rowIndex);
            if (targetRow == null) {
                targetRow = sheet.createRow(rowIndex);
            }

            Cell targetCell = targetRow.getCell(colIndex);
            if (targetCell == null) {
                targetCell = targetRow.createCell(colIndex);
            }

            targetCell.setCellValue(data);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                //logger.info("Appended '{}' to column '{}' in sheet '{}' of file '{}'", data, columnName, sheetName, filePath);
            }

        } catch (IOException e) {
            logger.error("Error appending to column '{}' in sheet '{}': {}", columnName, sheetName, e.getMessage(), e);
            throw e;
        }
    }
    public static void writeColumns(String filePath, String sheetName, Map<String, List<String>> columnsData) throws IOException {
        for (Map.Entry<String, List<String>> entry : columnsData.entrySet()) {
            writeColumn(filePath, sheetName, entry.getKey(), entry.getValue());
        }
    }
    public static void writeColumnConditionally(String filePath, String sheetName, String conditionalColName, String conditionalColValue, String columnNameToWrite, String columnValueToWrite) throws IOException {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            // Find indexes of conditional and target columns
            int condColIndex = -1;
            int writeColIndex = -1;
            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();

            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String headerVal = cell.getStringCellValue();
                    if (conditionalColName.equals(headerVal)) {
                        condColIndex = i;
                    }
                    if (columnNameToWrite.equals(headerVal)) {
                        writeColIndex = i;
                    }
                }
            }

            if (condColIndex == -1) {
                throw new IOException("Conditional column '" + conditionalColName + "' not found in sheet '" + sheetName + "'");
            }
            if (writeColIndex == -1) {
                // create target column if missing
                writeColIndex = lastCellNum;
                Cell newCell = headerRow.createCell(writeColIndex);
                newCell.setCellValue(columnNameToWrite);
            }

            int lastRowNum = sheet.getLastRowNum();
            int rowsUpdated = 0;

            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell condCell = row.getCell(condColIndex);
                String condVal = getCellValueAsString(condCell);

                if (conditionalColValue.equals(condVal)) {
                    Cell writeCell = row.getCell(writeColIndex);
                    if (writeCell == null) {
                        writeCell = row.createCell(writeColIndex);
                    }
                    writeCell.setCellValue(columnValueToWrite);
                    rowsUpdated++;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                //logger.info("Updated {} rows in column '{}' where '{}' == '{}' in sheet '{}' of file '{}'", rowsUpdated, columnNameToWrite, conditionalColName, conditionalColValue, sheetName, filePath);
            }

        } catch (IOException e) {
            logger.error("Error updating column '{}' conditionally in sheet '{}': {}", columnNameToWrite, sheetName, e.getMessage(), e);
            throw e;
        }
    }
    public static void updateColumn(String filePath, String sheetName, String columnName, List<String> updatedData) throws IOException {
        // Essentially same as writeColumn but can log differently if needed
        writeColumn(filePath, sheetName, columnName, updatedData);
        //logger.info("Updated column '{}' in sheet '{}' of file '{}'", columnName, sheetName, filePath);
    }
    public static void updateColumns(String filePath, String sheetName, Map<String, List<String>> updatedColumnsData) throws IOException {
        for (Map.Entry<String, List<String>> entry : updatedColumnsData.entrySet()) {
            updateColumn(filePath, sheetName, entry.getKey(), entry.getValue());
        }
    }
    public static void writeDataToExcel(String fileName, String[][] data) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("File does not exist. Please create the file first.");
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            for (int i = 0; i < data.length; i++) {
                Row row = sheet.createRow(lastRowNum + i + 1);
                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(data[i][j]);
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                System.out.println("Data written to Excel file successfully.");
            }

        } catch (IOException e) {
            System.err.println("Error writing data to Excel file: " + e.getMessage());
        }
    }
    public static void writeDataToExcel(String fileName, String sheetName, List<String> columnNames, List<String> columnValues) {

        if (columnNames.size() != columnValues.size()) {
            System.err.println("Column names and values must have the same size.");
            return;
        }

        try {
            File file = new File(fileName);
            Workbook workbook;
            Sheet sheet;

            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
            }

            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            // Create header row if not exist
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                headerRow = sheet.createRow(0);
            }

            // Map of existing column names -> index
            Map<String, Integer> headerIndexMap = new HashMap<>();

            // Read existing headers
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    headerIndexMap.put(cell.getStringCellValue(), i);
                }
            }

            // Ensure all required headers exist
            for (String header : columnNames) {
                if (!headerIndexMap.containsKey(header)) {
                    int newIndex = headerIndexMap.size();
                    Cell cell = headerRow.createCell(newIndex);
                    cell.setCellValue(header);
                    headerIndexMap.put(header, newIndex);
                }
            }

            // Append new row
            int newRowNum = sheet.getLastRowNum() + 1;
            Row newRow = sheet.createRow(newRowNum);

            for (int i = 0; i < columnNames.size(); i++) {
                String header = columnNames.get(i);
                String value = columnValues.get(i);

                int colIndex = headerIndexMap.get(header);
                newRow.createCell(colIndex).setCellValue(value);
            }

            FileOutputStream fos = new FileOutputStream(fileName);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("Row added successfully.");

        } catch (Exception e) {
            System.err.println("Error writing to Excel: " + e.getMessage());
        }
    }
    public static void writeDataToExcel(String fileName, String sheetName, Map<String, String> rowData) {
        try {
            File file = new File(fileName);
            Workbook workbook;
            Sheet sheet;

            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                }
            } else {
                System.err.println("File does not exist: " + fileName);
                return;  // or throw exception
            }

            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("Sheet does not exist: " + sheetName);
                workbook.close();
                return; // or throw exception
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.err.println("Header row is missing in sheet: " + sheetName);
                workbook.close();
                return; // or throw exception
            }

            Map<String, Integer> headerIndexMap = new HashMap<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String header = cell.getStringCellValue().trim();
                    headerIndexMap.put(header, i);
                }
            }

            // Check if all headers in rowData exist
            for (String header : rowData.keySet()) {
                if (!headerIndexMap.containsKey(header)) {
                    System.err.println("Missing header in Excel sheet: " + header);
                    workbook.close();
                    return;  // or throw exception, depending on your design
                }
            }

            int newRowNum = sheet.getLastRowNum() + 1;
            Row newRow = sheet.createRow(newRowNum);

            for (Map.Entry<String, String> entry : rowData.entrySet()) {
                String header = entry.getKey();
                String value = entry.getValue();
                int colIndex = headerIndexMap.get(header);
                newRow.createCell(colIndex).setCellValue(value);
            }

            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                workbook.write(fos);
            }
            workbook.close();

            System.out.println("Row added successfully.");

        } catch (Exception e) {
            System.err.println("Error writing to Excel: " + e.getMessage());
        }
    }
    public static String readCellValueOf(String filePath, String sheetName,
                                         String conditionColumnName, String conditionColumnValue,
                                         String columnNameToGetCellValue) throws IOException {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row");
            }

            int conditionColIndex = -1;
            int valueColIndex = -1;
            int lastCellNum = headerRow.getLastCellNum();

            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String header = getCellValueAsString(cell);
                    if (header.equals(conditionColumnName)) {
                        conditionColIndex = i;
                    }
                    if (header.equals(columnNameToGetCellValue)) {
                        valueColIndex = i;
                    }
                }
            }

            if (conditionColIndex == -1) {
                throw new IOException("Condition column '" + conditionColumnName + "' not found in sheet '" + sheetName + "'");
            }
            if (valueColIndex == -1) {
                throw new IOException("Target column '" + columnNameToGetCellValue + "' not found in sheet '" + sheetName + "'");
            }

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell conditionCell = row.getCell(conditionColIndex);
                String conditionValue = getCellValueAsString(conditionCell);
                if (conditionColumnValue.equals(conditionValue)) {
                    Cell targetCell = row.getCell(valueColIndex);
                    String cellValue = getCellValueAsString(targetCell);
                    //logger.info("Found matching row {} in sheet '{}', returning value '{}' from column '{}'",i, sheetName, cellValue, columnNameToGetCellValue);
                    return cellValue;
                }
            }

            logger.warn("No matching row found where column '{}' == '{}' in sheet '{}'", conditionColumnName, conditionColumnValue, sheetName);
            return ""; // or null, depending on design choice

        } catch (IOException e) {
            logger.error("Error in readCellValueOf(): {}", e.getMessage(), e);
            throw e;
        }
    }
    // reads only UTI Tube ID's
    public static List<Map<String, String>> readAllRowsAsMap(String filePath, String sheetName, boolean skipHeader) throws IOException {
        List<Map<String, String>> allRows = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in " + filePath);
            }

            Iterator<Row> rowIterator = sheet.iterator();
            List<String> headers = new ArrayList<>();

            // If sheet has rows
            if (rowIterator.hasNext()) {
                Row firstRow = rowIterator.next();
                for (Cell cell : firstRow) {
                    headers.add(cell.getStringCellValue().trim());
                }

                // Skip header if requested
                if (!skipHeader) {
                    Map<String, String> headerMap = new LinkedHashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        String value = getCellValueAsString(firstRow.getCell(i));
                        headerMap.put(headers.get(i), value);
                    }
                    allRows.add(headerMap);
                }
            }

            // Read remaining rows
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> rowMap = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i);
                    String cellValue = getCellValueAsString(cell);
                    rowMap.put(headers.get(i), cellValue);
                }
                allRows.add(rowMap);
            }
        }

        return allRows;
    }
    public static List<Map<String, String>> readColumnsConditionallyAll(
            String filePath,
            String sheetName,
            String conditionalColName,
            String conditionalColValue,
            List<String> columnsToRead,
            int headerRowIndex) throws IOException {

        if (columnsToRead == null || columnsToRead.isEmpty()) {
            throw new IllegalArgumentException("Columns to read cannot be null or empty");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        List<Map<String, String>> results = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(headerRowIndex);
            if (headerRow == null) {
                throw new IOException("Sheet '" + sheetName + "' does not have a header row at index " + headerRowIndex);
            }

            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();

            int condColIndex = -1;
            Map<String, Integer> columnsIndexMap = new HashMap<>();

            // Map headers
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell == null) continue;

                String headerVal = cell.getStringCellValue().trim();

                if (conditionalColName.equalsIgnoreCase(headerVal)) {
                    condColIndex = i;
                }

                for (String colName : columnsToRead) {
                    if (colName.equalsIgnoreCase(headerVal)) {
                        columnsIndexMap.put(colName, i);
                    }
                }
            }

            if (condColIndex == -1) {
                throw new IOException("Conditional column '" + conditionalColName + "' not found in sheet '" + sheetName + "'");
            }

            // Validate requested columns
            for (String colName : columnsToRead) {
                if (!columnsIndexMap.containsKey(colName)) {
                    throw new IOException("Column '" + colName + "' not found in sheet '" + sheetName + "'");
                }
            }

            // Iterate rows
            int lastRowNum = sheet.getLastRowNum();
            for (int i = headerRowIndex + 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell condCell = row.getCell(condColIndex);
                String condVal = getCellValueAsString(condCell);

                if (conditionalColValue.equalsIgnoreCase(condVal)) {
                    Map<String, String> rowMap = new HashMap<>();
                    for (Map.Entry<String, Integer> entry : columnsIndexMap.entrySet()) {
                        String colName = entry.getKey();
                        int colIndex = entry.getValue();
                        Cell cell = row.getCell(colIndex);
                        rowMap.put(colName, getCellValueAsString(cell));
                    }
                    results.add(rowMap);
                }
            }

            return results;

        } catch (IOException e) {
            logger.error("Error reading columns conditionally in sheet '{}': {}", sheetName, e.getMessage(), e);
            throw e;
        }
    }

    public static void writeColumnConditionally(
            String filePath,
            String sheetName,
            String conditionalColName,
            String conditionalColValue,
            String columnNameToWrite,
            String columnValueToWrite,
            int headerRowIndex) throws IOException {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            Row headerRow = sheet.getRow(headerRowIndex);
            if (headerRow == null) {
                throw new IOException("Header row not found at index: " + headerRowIndex);
            }

            int condColIndex = -1;
            int writeColIndex = -1;
            int lastCellNum = headerRow.getLastCellNum() == -1 ? 0 : headerRow.getLastCellNum();

            // Find column indexes
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String headerVal = cell.getStringCellValue().trim();

                    if (conditionalColName.equalsIgnoreCase(headerVal)) {
                        condColIndex = i;
                    }
                    if (columnNameToWrite.equalsIgnoreCase(headerVal)) {
                        writeColIndex = i;
                    }
                }
            }

            // Validate condition column
            if (condColIndex == -1) {
                throw new IOException("Conditional column '" + conditionalColName + "' not found");
            }

            // Create column if missing
            if (writeColIndex == -1) {
                writeColIndex = lastCellNum;
                Cell newHeaderCell = headerRow.createCell(writeColIndex);
                newHeaderCell.setCellValue(columnNameToWrite);
            }

            int lastRowNum = sheet.getLastRowNum();
            int rowsUpdated = 0;

            // Iterate rows AFTER header
            for (int i = headerRowIndex + 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell condCell = row.getCell(condColIndex);
                String condVal = getCellValueAsString(condCell);

                if (conditionalColValue.equalsIgnoreCase(condVal)) {
                    Cell writeCell = row.getCell(writeColIndex);

                    if (writeCell == null) {
                        writeCell = row.createCell(writeColIndex);
                    }

                    writeCell.setCellValue(columnValueToWrite);
                    rowsUpdated++;
                }
            }

            // Write back to file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            // Optional logging
            // logger.info("Updated {} rows in column '{}' where '{}'='{}'",
            //        rowsUpdated, columnNameToWrite, conditionalColName, conditionalColValue);

        } catch (IOException e) {
            logger.error("Error updating column '{}' conditionally in sheet '{}': {}",
                    columnNameToWrite, sheetName, e.getMessage(), e);
            throw e;
        }
    }

    public static void copyExcelFile(String sourceFilePath, String destFilePath) {
        Path sourcePath = Paths.get(sourceFilePath);
        Path destPath = Paths.get(destFilePath);
        try {
            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
            // logger.info("Excel file copied from {} to {}", sourceFilePath, destFilePath);
        } catch (IOException e) {
            logger.error("Error copying Excel file from {} to {}: {}", sourceFilePath, destFilePath, e.getMessage(), e);
            throw new RuntimeException("Failed to copy Excel file from " + sourceFilePath + " to " + destFilePath, e);
        }
    }
    public static void renameExcelFile(String oldFilePath, String newFilePath) {
        Path oldPath = Paths.get(oldFilePath);
        Path newPath = Paths.get(newFilePath);

        try {
            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
            // logger.info("Excel file renamed from {} to {}", oldFilePath, newFilePath);
        } catch (IOException e) {
            logger.error("Error renaming Excel file from {} to {}: {}", oldFilePath, newFilePath, e.getMessage(), e);
            throw new RuntimeException("Failed to rename Excel file from " + oldFilePath + " to " + newFilePath, e);
        }
    }

    public static void writeAllRows(String filePath, String sheetName, List<List<String>> allRows, boolean skipHeader) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            int startRow = skipHeader ? 1 : 0;

            // Write data
            for (int i = 0; i < allRows.size(); i++) {
                int rowIndex = startRow + i;

                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    row = sheet.createRow(rowIndex);
                }

                List<String> rowData = allRows.get(i);

                for (int j = 0; j < rowData.size(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        cell = row.createCell(j);
                    }

                    cell.setCellValue(rowData.get(j) == null ? "" : rowData.get(j));
                }
            }

            // Save changes
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            logger.error("Error writing all rows to sheet '{}': {}", sheetName, e.getMessage(), e);
            throw e;
        }
    }
    public static int getRowCount(String filePath, String sheetName, boolean skipHeader) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' does not exist in file: " + filePath);
            }

            int rowCount = sheet.getPhysicalNumberOfRows();

            if (skipHeader && rowCount > 0) {
                rowCount--;
            }

            return rowCount;
        } catch (IOException e) {
            logger.error("Error getting row count from sheet '{}': {}", sheetName, e.getMessage(), e);
            throw e;
        }
    }

}
