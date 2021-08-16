package com.project.ImageUploader;

import com.project.ImageUploader.controller.ImageController;
import com.project.ImageUploader.service.ImageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan({"com.project.ImageUploader", "service"})
public class ImageUploaderApplication {

	public static void main(String[] args) {
		new File(ImageService.uploadDir).mkdir();
		SpringApplication.run(ImageUploaderApplication.class, args);
	}

}
