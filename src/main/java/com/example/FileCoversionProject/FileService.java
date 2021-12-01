package com.example.FileCoversionProject;

import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileService {
    private String UPLOAD_FOLDER = "src/main/resources/tempFiles/";
    private String DOWNLOAD_FOLDER ="C:\\Users\\JD\\converted\\";
    public boolean checkTypePDF(MultipartFile file)
    {

       // System.out.println(file.getContentType());
        if(file.getContentType().equals("application/pdf"))
            return true;
        else
            return false;
    }
    //application/vnd.openxmlformats-officedocument.wordprocessingml.document
   // application/msword
    public boolean checkTypeDocx(MultipartFile file)
    {

        //System.out.println(file.getContentType());
        if(file.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
            return true;
        //else if(file.getContentType().equals("application/msword"))
          //  return true;
        else
            return false;
    }
    public void uploadFileAndConvertPDF(MultipartFile file) throws Exception
    {
        String filename= file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-5);
        String source=UPLOAD_FOLDER+file.getOriginalFilename();
        String target=DOWNLOAD_FOLDER+filename+".docx";
        try{
            byte[] data=file.getBytes();
            Path path= Paths.get(source);
            Files.write(path,data);
            Document doc = new Document(source);

            DocSaveOptions saveOptions = new DocSaveOptions();
            saveOptions.setFormat(DocSaveOptions.DocFormat.DocX);
            //saveOptions.setMode(DocSaveOptions.RecognitionMode.Textbox);
            //saveOptions.setRecognizeBullets(true);

            doc.save(target,saveOptions);
            File f=new File(source);
            f.delete();
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw e;
        }
    }
    public void uploadFileAndConvertDocX(MultipartFile file) throws Exception{
        String filename= file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-6);
        String source=UPLOAD_FOLDER+file.getOriginalFilename();
        String target=DOWNLOAD_FOLDER+filename+".pdf";
        try {
            byte[] data=file.getBytes();
            Path path= Paths.get(source);
            Files.write(path,data);
            InputStream doc = new FileInputStream(new File(source));
            XWPFDocument document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(new File(target));
            PdfConverter.getInstance().convert(document, out, options);
            File f=new File(source);
            f.delete();
        } catch(Exception e)
        {
            System.out.println(e);
            throw e;
        }

    }

}
