package com.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportTest {

    private final Report report = new Report();

    @Test
    public void testCreateReport() throws IOException, InvalidFormatException {
        // Given
        File output = TestHelper.genOutputFile();
        List<Profile> profiles = TestHelper.loadProfiles();

        report.create(profiles, output);

        assertTrue(output.exists());
        assertTrue(output.isFile());
    }

    @Test
    public void testReadTemplate() {
        assertNotNull(report.readTemplate());
    }

}