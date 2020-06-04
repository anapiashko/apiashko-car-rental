package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarRepository;
import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
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
    public ByteArrayInputStream carsToExcel(List<Car> cars) throws IOException {
        String[] COLUMNs = {"Id", "Brand", "RegisterNumber", "Price"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("cars");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Price
            CellStyle ageCellStyle = workbook.createCellStyle();
            ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

            int rowIdx = 1;
            for (Car customer : cars) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(customer.getId());
                row.createCell(1).setCellValue(customer.getBrand());
                row.createCell(2).setCellValue(customer.getRegisterNumber());

                Cell ageCell = row.createCell(3);
                ageCell.setCellValue(customer.getPrice().toString());
                ageCell.setCellStyle(ageCellStyle);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }

    }

    @Override
    public List<Car> excelToCars(String fileName) {

        List<Car> cars = new LinkedList<>();
        final DataFormatter df = new DataFormatter();

        try {

          //  FileInputStream file = new FileInputStream("/"+"home/anastasiya/LABA/apiashko-car-rental/car-rental-web-app/src/main/resources/cars.xlsx");

            FileInputStream file = new FileInputStream(fileName);
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            int rownum = 1;
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
                    if(cell.getCellType() == CellType.BLANK){
                        break;
                    }

                    //Check the cell type and format accordingly
                    data[rownum][colnum] = df.formatCellValue(cell);
                    System.out.print(df.formatCellValue(cell));
                    colnum++;
                    System.out.println("-");
                }
                if(colnum < 4){
                    break;
                }
                Car car = new Car();
                car.setBrand((String)data[rownum][1]);
                car.setRegisterNumber((String)data[rownum][2]);
                car.setPrice(new BigDecimal((String)data[rownum][3]));
                cars.add(car);
                rownum++;
                System.out.println();
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cars;
    }
}
