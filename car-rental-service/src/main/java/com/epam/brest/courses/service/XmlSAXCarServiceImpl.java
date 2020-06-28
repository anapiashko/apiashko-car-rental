package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.ArchiverService;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.XmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class XmlSAXCarServiceImpl implements XmlService<Car> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSAXCarServiceImpl.class);

    private final CarService carService;

    private final ArchiverService archiverService;

    @Autowired
    public XmlSAXCarServiceImpl(CarService carService, ArchiverService archiverService) {
        this.carService = carService;
        this.archiverService = archiverService;
    }

    @Override
    public ByteArrayInputStream entitiesToXml(List<Car> objects) throws IOException {
        LOGGER.debug("write entities from db in xml ()");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(byteArrayOutputStream);

            // Открываем XML-документ и Пишем корневой элемент BookCatalogue
            writer.writeStartDocument("1.0");
            writer.writeStartElement("cars");

            List<Car> cars = carService.findAll();

            // Делаем цикл для книг
            for (int i = 0; i < cars.size(); i++) {

                Car car = cars.get(i);

                // Записываем car
                writer.writeStartElement("car");

                // Заполняем все аттрибуты для car
                // Id
                writer.writeAttribute("id", car.getId().toString());
                // Brand
                writer.writeAttribute("brand",car.getBrand());
                // Registration number
                writer.writeAttribute("register_number",car.getRegisterNumber());
                // Price
                writer.writeAttribute("price",car.getPrice().toString());

                // Закрываем тэг car
                writer.writeEndElement();
            }
            // Закрываем корневой элемент
            writer.writeEndElement();
            // Закрываем XML-документ
            writer.writeEndDocument();
            writer.flush();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return new ByteArrayInputStream(archiverService.archiveFile("cars.xml", byteArrayInputStream));
    }

    @Override
    public void xmlToEntities(MultipartFile file) {
        LOGGER.debug("save entities in db from xml ()");

        carService.deleteAll();
        try {
            byte[] bytes = archiverService.unarchiveFile(file);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Здесь мы определили анонимный класс, расширяющий класс DefaultHandler
            DefaultHandler handler = new DefaultHandler() {
                // Поле для указания, что тэг NAME начался
                boolean tagCar = false;

                // Метод вызывается когда SAXParser "натыкается" на начало тэга
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    // Если тэг имеет имя car, то мы этот момент отмечаем - начался тэг car
                    if (qName.equalsIgnoreCase("car")) {
                        Car car = new Car();

                        // Id attribute
                        String id = attributes.getValue("id");
                        car.setId(Integer.parseInt(id));

                        // Brand attribute
                        car.setBrand(attributes.getValue("brand"));

                        // Registration number attribute
                        car.setRegisterNumber(attributes.getValue("register_number"));

                        // Price attribute
                        car.setPrice(new BigDecimal(attributes.getValue("price")));

                        tagCar = true;

                        carService.create(car);
                        System.out.println(car);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (qName.equalsIgnoreCase("car")) {
                        System.out.println("End Element :" + qName);
                    }
                }
            };

            // Стартуем разбор методом parse, которому передаем наследника от DefaultHandler, который будет вызываться в нужные моменты
            saxParser.parse(new ByteArrayInputStream(bytes), handler);

        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
