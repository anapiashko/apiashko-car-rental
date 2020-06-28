package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.XmlService;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class XmlDOMCarServiceImpl implements XmlService<Car> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlDOMCarServiceImpl.class);

    private final CarService carService;

    public XmlDOMCarServiceImpl(CarService carService) {
        this.carService = carService;
    }

    @Override
    public ByteArrayInputStream entitiesToXml(List<Car> cars) throws IOException {
        LOGGER.debug("write entities from db in xml ()");

        /** Build car XML DOM **/
        Document xmlDoc = buildEmployeeXML(cars);

        ByteArrayInputStream xmlInBytes = new ByteArrayInputStream(Objects.requireNonNull(doc2bytes(xmlDoc)));

        return new ByteArrayInputStream(archiveFile(xmlInBytes));
    }

    @Override
    public void xmlToEntities(MultipartFile file) {
        LOGGER.debug("save entities in db from xml ()");

        carService.deleteAll();
        try {

            byte[] bytes = unarchiveFile(file);

            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(new ByteArrayInputStream(bytes));

            // Получаем корневой элемент
            Node root = document.getDocumentElement();

            System.out.println("List of cars:");
            System.out.println();
            // Просматриваем все подэлементы корневого - т.е. cars

            NodeList cars = root.getChildNodes();
            for (int i = 0; i < cars.getLength(); i++) {
                Node car = cars.item(i);
                // Если нода не текст, то это car - заходим внутрь
                if (car.getNodeType() != Node.TEXT_NODE) {

                    Car newCar = new Car();
                    NamedNodeMap attributes = car.getAttributes();

                    newCar.setBrand(attributes.item(0).getTextContent());
                    newCar.setPrice(new BigDecimal(attributes.item(2).getTextContent()));
                    newCar.setRegisterNumber(attributes.item(3).getTextContent());

                    carService.create(newCar);
                    System.out.println(newCar);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private byte[] unarchiveFile(MultipartFile file) {
        LOGGER.debug("unarchive xml file ()");

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
        LOGGER.debug("archive xml file ()");

        //creating byteArray stream, make it bufferable and passing this buffer to ZipOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        //packing files
        zipOutputStream.putNextEntry(new ZipEntry("cars.xml"));

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

    // Функция для сохранения DOM в файл
    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream("other.xml");
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }


    private Document buildEmployeeXML(List<Car> cars) {
        Document xmlDoc = new DocumentImpl();

        /* Creating the root element */
        Element rootElement = xmlDoc.createElement("cars");
        xmlDoc.appendChild(rootElement);

        for (Car car : cars) {

            Element carElement = xmlDoc.createElement("car");

            /* Build the CarId as a Attribute*/
            carElement.setAttribute("id", car.getId().toString());

            /* Build the CarBrand as a Attribute*/
            carElement.setAttribute("brand", car.getBrand());

            /* Build the CarRegisterNumber as a Attribute*/
            carElement.setAttribute("register_number", car.getRegisterNumber());

            /* Build the CarPrice as a Attribute*/
            carElement.setAttribute("price", car.getPrice().toString());


            /* Appending emp to the Root Class*/
            rootElement.appendChild(carElement);
        }
        //writeDocument(xmlDoc);
        return xmlDoc;
    }

}
