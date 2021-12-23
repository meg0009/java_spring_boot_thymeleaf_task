package com.chivapchichi.controler;

import com.chivapchichi.filerw.PersonReaderWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
    @GetMapping("/upload-from-file")
    public String uploadFromFile() {
        return "upload-from-file";
    }

    @PostMapping("/upload-from-file")
    public String uploadSubmit(@RequestParam("file")MultipartFile inFile) {
        if (!PersonReaderWriter.writeFromFile(inFile, "tmp.csv")) {
            return "not-found";
        }

        return "ok";
    }
}
