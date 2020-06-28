package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.ArchiverService;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.service_api.XmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Profile("sax")
@Service
@Transactional
public class XmlSAXOrderServiceImpl implements XmlService<Order> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSAXOrderServiceImpl.class);

    private final OrderService orderService;

    private final ArchiverService archiverService;

    public XmlSAXOrderServiceImpl(OrderService orderService, ArchiverService archiverService) {
        this.orderService = orderService;
        this.archiverService = archiverService;
    }

    @Override
    public ByteArrayInputStream entitiesToXml(List<Order> objects) throws IOException {
        LOGGER.debug("write entities from db in xml ()");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(byteArrayOutputStream);

            // Открываем XML-документ и Пишем корневой элемент BookCatalogue
            writer.writeStartDocument("1.0");
            writer.writeStartElement("orders");

            List<Order> orders = orderService.findAll();

            // Делаем цикл для книг
            for (int i = 0; i < orders.size(); i++) {

                Order order = orders.get(i);

                // Записываем order
                writer.writeStartElement("order");

                // Заполняем все аттрибуты для order
                // Id
                writer.writeAttribute("id", order.getId().toString());
                // Date
                writer.writeAttribute("date",order.getDate().toString());
                // Id of car
                writer.writeAttribute("car_id",order.getCarId().toString());

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
        return new ByteArrayInputStream(archiverService.archiveFile("order.xml", byteArrayInputStream));
    }

    @Override
    public void xmlToEntities(MultipartFile file) {
        LOGGER.debug("save entities in db from xml ()");

        orderService.deleteAll();
        try {
            byte[] bytes = archiverService.unarchiveFile(file);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Здесь мы определили анонимный класс, расширяющий класс DefaultHandler
            DefaultHandler handler = new DefaultHandler() {
                // Поле для указания, что тэг order начался
                boolean tagOrder = false;

                // Метод вызывается когда SAXParser "натыкается" на начало тэга
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    // Если тэг имеет имя order, то мы этот момент отмечаем - начался тэг order
                    if (qName.equalsIgnoreCase("order")) {
                       Order order = new Order();

                        // Id attribute
                        String id = attributes.getValue("id");
                        order.setId(Integer.parseInt(id));

                        // Date attribute
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        order.setDate(LocalDate.parse(attributes.getValue("date"), formatter));

                        // CarId attribute
                        String carId = attributes.getValue("car_id");
                        order.setCarId(Integer.parseInt(carId));

                        tagOrder = true;

                        orderService.create(order);
                        System.out.println(order);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (qName.equalsIgnoreCase("order")) {
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
