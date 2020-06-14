package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.ExcelService;
import com.epam.brest.courses.service_api.OrderService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    private final CarService carService;
    private final OrderService orderService;

    private Object[][] data = null;

    @Autowired
    public ExcelServiceImpl(CarService carService, OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    @Override
    @Transactional
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
            CellStyle priceCellStyle = workbook.createCellStyle();
            priceCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

            int rowIdx = 1;
            for (Car customer : cars) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(customer.getId());
                row.createCell(1).setCellValue(customer.getBrand());
                row.createCell(2).setCellValue(customer.getRegisterNumber());

                Cell priceCell = row.createCell(3);
                priceCell.setCellValue(customer.getPrice().toString());
                priceCell.setCellStyle(priceCellStyle);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }

    }

    @Override
    public ByteArrayInputStream excelToCars(MultipartFile file) throws IOException {

        List<Car> cars = new LinkedList<>();
        final DataFormatter df = new DataFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XSSFWorkbook workbook = null;
        try {

            //Create Workbook instance holding reference to .xlsx file
            workbook = new XSSFWorkbook(file.getInputStream());

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
                    if (cell.getCellType() == CellType.BLANK) {
                        break;
                    }

                    //Check the cell type and format accordingly
                    data[rownum][colnum] = df.formatCellValue(cell);
                    System.out.print(df.formatCellValue(cell));
                    colnum++;
                    System.out.println("-");
                }
                if (colnum < 4) {
                    break;
                }
                Car car = new Car();
                car.setBrand((String) data[rownum][1]);
                car.setRegisterNumber((String) data[rownum][2]);
                car.setPrice(new BigDecimal((String) data[rownum][3]));
                try {
                    carService.create(car);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();

                        XSSFCellStyle style = workbook.createCellStyle();
                        style.setFillForegroundColor(IndexedColors.CORAL.index);
                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                        cell.setCellStyle(style);
                    }
                }
                cars.add(car);
                rownum++;
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    @Transactional
    public ByteArrayInputStream ordersToExcel(List<Order> orders) throws IOException {
        String[] COLUMNs = {"Id", "Date", "CarId"};
        Workbook workbook;
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {

            //saving car sheet to orders workbook
            List<Car> cars = carService.findAll();
            ByteArrayInputStream byteArrayInputStream = carsToExcel(cars);
            Workbook carWorkBook =  new XSSFWorkbook(byteArrayInputStream);

            workbook = carWorkBook;

            //create orders sheet
            Sheet sheet = workbook.createSheet("orders");
            workbook.setSheetOrder("orders", 0);
            workbook.setActiveSheet(0);

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Headers
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Date
            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

            int rowIdx = 1;
            for (Order customer : orders) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(customer.getId());

                Cell dateCell = row.createCell(1);
                dateCell.setCellValue(customer.getDate().toString());
                dateCell.setCellStyle(dateCellStyle);

                row.createCell(2).setCellValue(customer.getCarId());
            }

            //protect car sheet
            workbook.getSheetAt(1).protectSheet("password");

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }

    }

    @Override
    public ByteArrayInputStream excelToOrders(MultipartFile file) throws IOException {

        final DataFormatter df = new DataFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XSSFWorkbook workbook = null;
        try {

            //Create Workbook instance holding reference to .xlsx file
            workbook = new XSSFWorkbook(file.getInputStream());

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
                    if (cell.getCellType() == CellType.BLANK) {
                        break;
                    }

                    //Check the cell type and format accordingly
                    data[rownum][colnum] = df.formatCellValue(cell);
                    System.out.print(df.formatCellValue(cell));
                    colnum++;
                    System.out.println("-");
                }
                if (colnum < 3) {
                    break;
                }
                Order order = new Order();
                order.setDate(LocalDate.parse(data[rownum][1].toString()));
                order.setCarId(Integer.parseInt(data[rownum][2].toString()));

                try {
                    orderService.create(order);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();

                        XSSFCellStyle style = workbook.createCellStyle();
                        style.setFillForegroundColor(IndexedColors.CORAL.index);
                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                        cell.setCellStyle(style);
                    }
                }
                rownum++;
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
