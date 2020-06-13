package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.XmlService;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class XmlServiceImpl implements XmlService {

    @Override
    public ByteArrayInputStream carsToXml(List<Car> cars) throws IOException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            /** Build customer XML DOM **/
            Document xmlDoc = buildEmployeeXML(cars);

            ByteArrayInputStream xmlInBytes = new ByteArrayInputStream(Objects.requireNonNull(doc2bytes(xmlDoc)));

            return new ByteArrayInputStream(archiveFile(xmlInBytes));
        }
    }

    private byte[] archiveFile(ByteArrayInputStream in) throws IOException {

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
