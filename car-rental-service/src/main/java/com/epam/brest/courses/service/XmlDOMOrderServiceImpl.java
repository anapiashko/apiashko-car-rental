package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.service_api.XmlService;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class XmlDOMOrderServiceImpl implements XmlService<Order> {

    private final OrderService orderService;

    @Autowired
    public XmlDOMOrderServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ByteArrayInputStream entitiesToXml(List<Order> orders) throws IOException {

        /** Build order XML DOM **/
        Document xmlDoc = buildEmployeeXML(orders);

        ByteArrayInputStream xmlInBytes = new ByteArrayInputStream(Objects.requireNonNull(doc2bytes(xmlDoc)));

        return new ByteArrayInputStream(archiveFile(xmlInBytes));
    }

    @Override
    public void xmlToEntities(MultipartFile file) {
        orderService.deleteAll();
        try {

            byte[] bytes = unarchiveFile(file);

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

    private byte[] unarchiveFile(MultipartFile file) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ZipInputStream zin = new ZipInputStream(file.getInputStream());
            ZipEntry entry;

            String name;
            long size;
            while ((entry = zin.getNextEntry()) != null) {

                name = entry.getName(); // получим название файла
                size = entry.getSize();  // получим его размер в байтах
                System.out.printf("File name: %s \t File size: %d \n", name, size);

                // распаковка
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    byteArrayOutputStream.write(c);
                }

                IOUtils.closeQuietly(byteArrayOutputStream);
                zin.closeEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] archiveFile(ByteArrayInputStream in) throws IOException {

        //creating byteArray stream, make it bufferable and passing this buffer to ZipOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        //packing files
        zipOutputStream.putNextEntry(new ZipEntry("order.xml"));

        IOUtils.copy(in, zipOutputStream);

        in.close();
        zipOutputStream.closeEntry();

        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
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

    private Document buildEmployeeXML(List<Order> orders) {
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
