package com.project.ImageUploader.controller;

import com.project.ImageUploader.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<List<String>> getImages() throws IOException {
        return ResponseEntity.ok(imageService.retrieveAllImage());
    }

    @PostMapping("/upload")
    @CrossOrigin
    public String uploadImages(@RequestParam("images") MultipartFile[] images) throws IOException {
        if(imageService.uploadImages(images)){
            return "File Uploaded Successfully";
        } else{
            return "File Upload Failed!";
        }
    }

}
