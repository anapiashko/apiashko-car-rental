package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarRepository;
import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.ExcelService;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    private final CarRepository carRepository;
    private final CarService carService;
    private Object[][] data = null;

    @Autowired
    public ExcelServiceImpl(CarRepository carRepository, CarService carService) {
        this.carRepository = carRepository;
        this.carService = carService;
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
//
//    @Override
//    public List<Car> excelToCars(MultipartFile file) throws IOException {
//
//        List<Car> cars = new LinkedList<>();
//
//        Integer carId = null;
//        Integer carBrand = null;
//        Integer carRegisterNum = null;
//        Integer carPrice = null;
//
//        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//        XSSFSheet worksheet = workbook.getSheetAt(0);
//        XSSFRow header = worksheet.getRow(0);
//        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
//
//            if ("Id".equals(header.getCell(i).getStringCellValue())) {
//                carId = i;
//            } else if ("Brand".equals(header.getCell(i).getStringCellValue())) {
//                carBrand = i;
//            } else if ("RegisterNumber".equals(header.getCell(i).getStringCellValue())) {
//                carRegisterNum = i;
//            } else if ("Price".equals(header.getCell(i).getStringCellValue())) {
//                carPrice = i;
//            }
//
//        }
//
//        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
//            Car car = new Car();
//            XSSFRow row = worksheet.getRow(i);
//            XSSFCell idCell = row.getCell(carId);
//            XSSFCell brandCell = row.getCell(carBrand);
//            XSSFCell registerNumCell = row.getCell(carRegisterNum);
//            XSSFCell priceCell = row.getCell(carPrice);
//
//            Object idCellValue = getCellValue(idCell);
//            Object brandCellValue = getCellValue(brandCell);
//            Object registerNumCellValue = getCellValue(registerNumCell);
//            Object priceCellValue = getCellValue(priceCell);
//
//
//            if (idCellValue != null) {
//
//                car.setId((int) row.getCell(carId).getNumericCellValue());
//
//                if (brandCellValue != null) {
//                    car.setBrand(new Double(row.getCell(empAge).getNumericCellValue()).intValue());
//                } else {
//                    if (row.getCell(empAge).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
//                        throw new IllegalArgumentException(
//                                "EmployeeAge only allowed Number and not more than Three digit but you are providing : " + new Double(row.getCell(empAge).getNumericCellValue()).longValue());
//                    } else {
//                        throw new IllegalArgumentException(
//                                "EmployeeAge only allowed Number and not more than Three digit but you are providing : " + row.getCell(empAge).getStringCellValue());
//                    }
//                }
//
//                objectContruct(row, car, carBrand, empDept, empAdd);
//
//                carRepository.save(car);
//                cars.add(car);
//            } else {
//                if (row.getCell(carId).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
//                    throw new IllegalArgumentException(
//                            "EmployeeId only allowed Number and not more than Five digit but you are providing : " + new Double(row.getCell(empId).getNumericCellValue()).longValue());
//                } else {
//                    throw new IllegalArgumentException(
//                            "EmployeeId only allowed Number and not more than Five digit but you are providing : " + row.getCell(empId).getStringCellValue());
//                }
//            }
//
//        }
//        return cars;
//    }
//
//    private void objectContruct(XSSFRow row, Car car, Integer carBrand, Integer empDept, Integer empAdd) {
//
//        if (row.getCell(carBrand).getStringCellValue().matches(NAME_REGEX)) {
//            car.setEmployeeName(row.getCell(carBrand).getStringCellValue());
//        } else {
//            throw new IllegalArgumentException(
//                    "EmployeeName only allowed character and not more than Ten character  : ");
//
//        }
//
//
//        if (row.getCell(empDept).getStringCellValue().matches(NAME_REGEX)) {
//
//            car.setEmployeeDepartment(row.getCell(empDept).getStringCellValue());
//
//        } else {
//            throw new EmployeeIdNotFoundException(
//                    "Department only allowed character and not more than Ten character : ");
//
//        }
//        if (row.getCell(empAdd).getStringCellValue().matches(ADDRESS_REGEX)) {
//
//            car.setEmployeeAddress(row.getCell(empAdd).getStringCellValue());
//
//        } else {
//            throw new EmployeeIdNotFoundException(
//                    "Employee Address  allowed only  not more than Ten character : ");
//
//        }
//    }
//
//    private Object getCellValue(XSSFCell cell) {
//        if (cell != null) {
//            switch (cell.getCellType()) {
//                case BLANK:
//                    return null;
//                case BOOLEAN:
//                    return cell.getBooleanCellValue();
//                case NUMERIC:
//                    return cell.getNumericCellValue();
//                case STRING:
//                    return cell.getRichStringCellValue().toString();
//            }
//        }
//        return null;
//    }

    @Override
//    @Transactional(noRollbackFor = Exception.class)
    public ByteArrayInputStream excelToCars(MultipartFile file) throws IOException {

        List<Car> cars = new LinkedList<>();
        final DataFormatter df = new DataFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XSSFWorkbook workbook = null;
        try {

            // FileInputStream file = new FileInputStream(file);
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
//                    XSSFCellStyle style = workbook.createCellStyle();
//                    style.setFillForegroundColor(IndexedColors.CORAL.index);
//                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                    row.setRowStyle(style);
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


//    @Override
//    public List<Car> excelToCars(String fileName) {
//
//        List<Car> cars = new LinkedList<>();
//        final DataFormatter df = new DataFormatter();
//
//        try {
//
//            FileInputStream file = new FileInputStream(fileName);
//            //Create Workbook instance holding reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//            //Get first/desired sheet from the workbook
//            XSSFSheet sheet = workbook.getSheetAt(0);
//
//            //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
//
//            int rownum = 0;
//            int colnum = 0;
//            Row r = rowIterator.next();
//
//            int rowcount = sheet.getLastRowNum();
//            int colcount = r.getPhysicalNumberOfCells();
//            data = new Object[rowcount][colcount];
//
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//
//                //For each row, iterate through all the columns
//                Iterator<Cell> cellIterator = row.cellIterator();
//                colnum = 0;
//                while (cellIterator.hasNext()) {
//
//                    Cell cell = cellIterator.next();
//                    if(cell.getCellType() == CellType.BLANK){
//                        break;
//                    }
//
//                    //Check the cell type and format accordingly
//                    data[rownum][colnum] = df.formatCellValue(cell);
//                    System.out.print(df.formatCellValue(cell));
//                    colnum++;
//                    System.out.println("-");
//                }
//                if(colnum < 4){
//                    break;
//                }
//                Car car = new Car();
//                car.setBrand((String)data[rownum][1]);
//                car.setRegisterNumber((String)data[rownum][2]);
//                car.setPrice(new BigDecimal((String)data[rownum][3]));
//                cars.add(car);
//                rownum++;
//                System.out.println();
//            }
//            file.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return cars;
// }
}
