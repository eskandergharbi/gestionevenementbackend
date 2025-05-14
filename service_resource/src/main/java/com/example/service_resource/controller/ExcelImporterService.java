package com.example.service_resource.controller;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_resource.model.Salle;
import com.example.service_resource.model.SalleStatus;
import com.example.service_resource.repository.SalleRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/import") // Base path for this controller
@RequiredArgsConstructor
public class ExcelImporterService {

    private final SalleRepository salleRepository;

    @PostMapping("/excel") // Endpoint to trigger the import
    public ResponseEntity<String> importExcelData() {
        try {
            InputStream input = new ClassPathResource("data/salle_data.xlsx").getInputStream();
            Workbook workbook = new XSSFWorkbook(input);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header

                String nom = row.getCell(0).getStringCellValue();
                int capacite = (int) row.getCell(1).getNumericCellValue();

                Salle salle = new Salle();
                salle.setNom(nom);
                salle.setCapacite(capacite);
                salle.setSalleStatus(SalleStatus.NorReserved);

                salleRepository.save(salle);
            }

            workbook.close();
            input.close();
            return ResponseEntity.ok("Excel data imported successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing Excel data: " + e.getMessage());
        }
    }
}