package com.example.FileCoversionProject;

import com.example.FileCoversionProject.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;
    @GetMapping("/getPDFtoDocx")
    public ResponseEntity<ResponseMessage> getPDFConvertIntoDoc(@RequestParam("file") MultipartFile file) {
        String message="";
        if(fileService.checkTypePDF(file))
        {
            try {
                fileService.uploadFileAndConvertPDF(file);
            }
            catch(Exception e)
            {
                message="file upload failed ";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message+e));
            }
            message="file uploaded successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }
        else {
            message="file does not belongs to type pdf";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
    @GetMapping("/getDocxtoPDF")
    public ResponseEntity<ResponseMessage> getWordConvertIntoPDF(@RequestParam("file") MultipartFile file) {
        String message = "";
        //System.out.println(file.getContentType());
        if(fileService.checkTypeDocx(file))
        {
            try {
                fileService.uploadFileAndConvertDocX(file);
            }
            catch(Exception e)
            {
                message="file upload failed ";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message+e));
            }
            message="file uploaded successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }
        else {
            message="file does not belongs to type doc/docx";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
}
