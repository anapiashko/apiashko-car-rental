package com.epam.brest.courses.service_api;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ArchiverService {
    byte[] archiveFile(String filename, ByteArrayInputStream in) throws IOException;
    byte[] unarchiveFile(MultipartFile file);
}
