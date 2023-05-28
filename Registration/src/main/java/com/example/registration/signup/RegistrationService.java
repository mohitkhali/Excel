package com.example.registration.signup;

import com.example.registration.appuser.AppUser;
import com.example.registration.appuser.AppUserRole;
import com.example.registration.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.TreeSet;

@Service
@AllArgsConstructor
public class RegistrationService {


    public boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.startsWith("application/vnd.ms-excel")
                || contentType.startsWith("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

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