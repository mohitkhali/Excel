package com.example.registration.signup;

import com.example.registration.appuser.AppUserRepository;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    AppUserRepository userRepository;

    @PostMapping("/import-file")
    public ResponseEntity<String> importExcelFile( @RequestParam(value = "file",required = true) MultipartFile file) throws IOException {

        if (registrationService.checkExcelFormat(file)) {
            Workbook workbook;
            String fileName = file.getOriginalFilename();

            if (fileName!=null && fileName.endsWith(".xls"))
                workbook = new HSSFWorkbook(file.getInputStream());
            else
                workbook = new XSSFWorkbook(file.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);

            if (headerMatches(row))
               return ResponseEntity.status(HttpStatus.OK).body("HEADER MATCHES");
            else
                return ResponseEntity.status(HttpStatus.OK).body("HEADER NOT");
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD FILE");
    }





    public boolean headerMatches(Row row) {
        if (row.getLastCellNum() != 3) return false;
        var set1 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        var set2 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        set1.addAll(Set.of("NAME", "DOB", "DESIGNATION"));
        DataFormatter dataFormatter = new DataFormatter();
        for (Cell cell : row) {
            String cellValue = dataFormatter.formatCellValue(cell);
            set2.add(cellValue);
        }
        return set1.equals(set2);
    }

}