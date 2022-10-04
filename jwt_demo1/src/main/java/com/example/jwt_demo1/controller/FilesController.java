package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.File.FileInfo;
import com.example.jwt_demo1.File.FilesStorageService;
import com.example.jwt_demo1.payload.ResponseMessageFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.stream.Collectors;

@Controller
//@CrossOrigin is for configuring allowed origins.
@CrossOrigin("http://localhost:8080")
public class FilesController {

    private final FilesStorageService storageService;
    private static final Logger logger = LoggerFactory.getLogger(FilesController.class);

    @Autowired
    public FilesController(FilesStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessageFile> uploadFile(@RequestParam("file") MultipartFile[] file) {

        try {
            int length = file.length;
            ExecutorService executor = Executors.newFixedThreadPool(length);
            for (MultipartFile multipartFile : file) {
                Runnable runnable = () -> storageService.save(multipartFile);
                executor.execute(runnable);
                logger.info("luồng đang được thực thi "+ executor);
                logger.info("tải file thành công: tên file"+ multipartFile.getOriginalFilename());
            }
            executor.shutdown();
            return new ResponseEntity<ResponseMessageFile>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ResponseMessageFile>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
