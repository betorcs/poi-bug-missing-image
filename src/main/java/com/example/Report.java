package com.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class Report {

    public void create(List<Profile> profiles, File dest) throws IOException, InvalidFormatException {
        try (XWPFDocument doc = new XWPFDocument(readTemplate())) {

            XWPFTable table = doc.createTable(profiles.size(), 2);
            table.setColBandSize(0);

            int row = 0;
            for (Profile profile : profiles) {
                addProfile(table.getRow(row++), profile);
            }

            try (FileOutputStream out = new FileOutputStream(dest)) {
                doc.write(out);
            }
        }
    }

    private void addProfile(XWPFTableRow row, Profile profile) throws IOException, InvalidFormatException {

        row.getCell(0)
                .addParagraph()
                .createRun()
                .setText(profile.getName());

        try (FileInputStream fis = new FileInputStream(profile.getPic())) {
            BufferedImage bi = ImageIO.read(new File(profile.getPic()));
            row.getCell(1)
                    .addParagraph()
                    .createRun()
                    .addPicture(fis,
                    Document.PICTURE_TYPE_JPEG,
                    profile.getPic(),
                    getScaledSize(bi.getWidth()),
                    getScaledSize(bi.getHeight()));
        }
    }

    private int getScaledSize(int size) {
        return Units.pixelToEMU(size / 3);
    }

    InputStream readTemplate() {
        return ClassLoader.getSystemResourceAsStream("template.docx");
    }

}
