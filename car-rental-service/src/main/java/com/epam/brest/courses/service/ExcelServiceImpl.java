package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarRepository;
import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.ExcelService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class ExcelServiceImpl implements ExcelService {

    private final CarRepository carRepository;
    Object[][] data = null;

    @Autowired
    public ExcelServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void exportFromDB() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Car sheet");
        sheet.protectSheet("password");

        List<Car> cars = carRepository.findAll();

        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle lockStyle = workbook.createCellStyle();
        lockStyle.setLocked(true);
        XSSFCellStyle unlockStyle = workbook.createCellStyle();
        unlockStyle.setLocked(false);

        row = sheet.createRow(rownum);

        // Id
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("id");
        cell.setCellStyle(unlockStyle);
        // Brand
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("brand");
        cell.setCellStyle(unlockStyle);
        // Registration number
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("registerNumber");
        cell.setCellStyle(unlockStyle);
        // Price
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("price");
        cell.setCellStyle(unlockStyle);

        // Data
        for (Car car : cars) {
            rownum++;
            row = sheet.createRow(rownum);

            // Brand (A)
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(car.getId());
            cell.setCellStyle(lockStyle);
            // Brand (B)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(car.getBrand());
            cell.setCellStyle(unlockStyle);
            // Registration number (C)
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(car.getRegisterNumber());
            cell.setCellStyle(unlockStyle);
            // Price (D)
            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue(car.getPrice().toString());
            cell.setCellStyle(unlockStyle);

        }
        File file = new File("cars.xlsx");
      //  file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());

    }

    @Override
    public void importInDB(String fileName) {
        final DataFormatter df = new DataFormatter();
        try {

            FileInputStream file = new FileInputStream(fileName);
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            int rownum = 0;
            int colnum = 0;
            Row r = rowIterator.next();

            int rowcount = sheet.getLastRowNum();
            int colcount = r.getPhysicalNumberOfCells();
            data = new Object[rowcount][colcount];
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                colnum = 0;
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    data[rownum][colnum] = df.formatCellValue(cell);
                    System.out.print(df.formatCellValue(cell));
                    colnum++;
                    System.out.println("-");
                }
                rownum++;
                System.out.println();
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return data;
    }
}
