//package com.epam.brest.courses.dao;
//
//import com.epam.brest.courses.dao.config.TestConfig;
//import com.epam.brest.courses.model.dto.CarDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@TestPropertySource("classpath:dao.properties")
//@ContextConfiguration(classes = {TestConfig.class})
//@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
//class CarDtoDaoDaoJdbcIT {
//
//    private final CarDtoDao carDtoDao;
//
//    @Autowired
//    CarDtoDaoDaoJdbcIT(CarDtoDao carDtoDao) {
//        this.carDtoDao = carDtoDao;
//    }
//
//    @Test
//    void findAllWithNumberOfOrders() {
//        //given
//        LocalDate dateFrom = LocalDate.of(2020,1,1);
//        LocalDate dateTo = LocalDate.of(2020,1,15);
//
//        //when
//        List<CarDto> carDtoList = carDtoDao.findAllWithNumberOfOrders(dateFrom, dateTo);
//
//        //then
//        assertNotNull(carDtoList);
//    }
//}
