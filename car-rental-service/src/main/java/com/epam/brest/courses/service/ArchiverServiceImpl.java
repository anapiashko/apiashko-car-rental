package com.epam.brest.courses.service;

import com.epam.brest.courses.service_api.ArchiverService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class ArchiverServiceImpl implements ArchiverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiverServiceImpl.class);

    @Override
    public byte[] archiveFile(String filename, ByteArrayInputStream in) throws IOException {
        LOGGER.debug("archive xml file ()");

        //creating byteArray stream, make it bufferable and passing this buffer to ZipOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        //packing files
        zipOutputStream.putNextEntry(new ZipEntry(filename));

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

    @Override
    public byte[] unarchiveFile(MultipartFile file) {
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
}
