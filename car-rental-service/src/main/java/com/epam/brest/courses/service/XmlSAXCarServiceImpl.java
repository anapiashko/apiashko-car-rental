package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.XmlService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


@Service
public class XmlSAXCarServiceImpl implements XmlService<Car> {

    private final CarService carService;

    @Autowired
    public XmlSAXCarServiceImpl(CarService carService) {
        this.carService = carService;
    }

    @Override
    public ByteArrayInputStream entitiesToXml(List<Car> objects) throws IOException {

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
        return new ByteArrayInputStream(archiveFile(byteArrayInputStream));
    }

    @Override
    public void xmlToEntities(MultipartFile file) {

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
}
