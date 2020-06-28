package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.ArchiverService;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.service_api.XmlService;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class XmlDOMOrderServiceImpl implements XmlService<Order> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlDOMOrderServiceImpl.class);

    private final OrderService orderService;

    private final ArchiverService archiverService;

    @Autowired
    public XmlDOMOrderServiceImpl(OrderService orderService, ArchiverService archiverService) {
        this.orderService = orderService;
        this.archiverService = archiverService;
    }

    @Override
    public ByteArrayInputStream entitiesToXml(List<Order> orders) throws IOException {
        LOGGER.debug("write entities from db in xml ()");

        /** Build order XML DOM **/
        Document xmlDoc = buildOrderXML(orders);

        ByteArrayInputStream xmlInBytes = new ByteArrayInputStream(Objects.requireNonNull(doc2bytes(xmlDoc)));

        return new ByteArrayInputStream(archiverService.archiveFile("order.xml", xmlInBytes));
    }

    @Override
    public void xmlToEntities(MultipartFile file) {
        LOGGER.debug("save entities in db from xml ()");

        orderService.deleteAll();
        try {

            byte[] bytes = archiverService.unarchiveFile(file);

            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(new ByteArrayInputStream(bytes));

            // Получаем корневой элемент
            Node root = document.getDocumentElement();

            System.out.println("List of orders:");
            System.out.println();
            // Просматриваем все подэлементы корневого - т.е. orders

            NodeList orders = root.getChildNodes();
            for (int i = 0; i < orders.getLength(); i++) {
                Node order = orders.item(i);
                // Если нода не текст, то это order - заходим внутрь
                if (order.getNodeType() != Node.TEXT_NODE) {

                    Order newOrder = new Order();
                    NamedNodeMap attributes = order.getAttributes();

                    newOrder.setCarId(new Integer(attributes.item(0).getTextContent()));
                    newOrder.setDate(LocalDate.parse(attributes.item(1).getTextContent()));

                    orderService.create(newOrder);
                    System.out.println(newOrder);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private static byte[] doc2bytes(Document node) {
        try {
            Source source = new DOMSource(node);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Result result = new StreamResult(out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return out.toByteArray();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Document buildOrderXML(List<Order> orders) {
        Document xmlDoc = new DocumentImpl();

        /* Creating the root element */
        Element rootElement = xmlDoc.createElement("orders");
        xmlDoc.appendChild(rootElement);

        for (Order order : orders) {

            Element orderElement = xmlDoc.createElement("order");

            /* Build the CarId as a Attribute*/
            orderElement.setAttribute("id", order.getId().toString());

            /* Build the CarBrand as a Attribute*/
            orderElement.setAttribute("date", order.getDate().toString());

            /* Build the CarRegisterNumber as a Attribute*/
            orderElement.setAttribute("car_id", order.getCarId().toString());

            /* Appending emp to the Root Class*/
            rootElement.appendChild(orderElement);
        }
        //writeDocument(xmlDoc);
        return xmlDoc;
    }
}
