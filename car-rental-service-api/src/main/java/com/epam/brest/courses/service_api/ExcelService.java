package com.epam.brest.courses.service_api;

import java.io.IOException;

public interface ExcelService {

    void exportFromDB() throws IOException;

    void importInDB(String fileName);
}
