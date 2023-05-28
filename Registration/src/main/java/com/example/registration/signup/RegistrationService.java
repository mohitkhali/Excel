package com.example.registration.signup;

import com.example.registration.appuser.AppUser;
import com.example.registration.appuser.AppUserRole;
import com.example.registration.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class RegistrationService {


    public boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.startsWith("application/vnd.ms-excel")
                || contentType.startsWith("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    }


}