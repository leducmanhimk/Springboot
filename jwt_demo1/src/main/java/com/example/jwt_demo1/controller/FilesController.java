package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.File.FileInfo;
import com.example.jwt_demo1.File.FilesStorageService;
import com.example.jwt_demo1.payload.ResponseMessageFile;
import java8.util.concurrent.CompletableFuture;
import net.bytebuddy.build.BuildLogger;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
        String message = "";
        try {
            List<String> filename = new ArrayList<>();

            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() ->
                    {
                        storageService.save(file[0]);
                        return file[0].getOriginalFilename();
                    }
            );
            CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() ->
                    {
                        storageService.save(file[1]);
                        return file[1].getOriginalFilename();
                    }
            );
            CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() ->
                    {
                        logger.info("thực hiện luồng 3: ");
                        try {
                            Thread.sleep(30000);
                            storageService.save(file[2]);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return file[2].getOriginalFilename();
                    }
            );
            CompletableFuture.allOf(completableFuture, completableFuture2, completableFuture3).join();
            logger.info("đã hoàn thành cả 3 luồng: ");
            logger.info("--> " + completableFuture3.get());
            logger.info("--> " + completableFuture2.get());
            logger.info("--> " + completableFuture.get());
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
