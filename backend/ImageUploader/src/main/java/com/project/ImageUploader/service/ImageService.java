package com.project.ImageUploader.service;

import com.project.ImageUploader.dto.WsMessageDTO;
import com.project.ImageUploader.entity.Image;
import com.project.ImageUploader.repository.ImageRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    SimpMessagingTemplate brokerMessagingTemplate;

    public static String uploadDir = System.getProperty("user.dir")+"/uploads";

    public List<String> retrieveAllImage() throws IOException {
        List<String> imageList = new ArrayList<>();
        List<Image> images = imageRepository.findAll();

        if(images.size() > 0){
            for(Image image: images){
                File file = new File(uploadDir+File.separator+image.getThumbnail());
                String extension = FilenameUtils.getExtension(file.getName());
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[(int)file.length()];
                fileInputStream.read(bytes);
                String encodeBase64 = Base64.getEncoder().encodeToString(bytes);
                imageList.add("data:image/"+extension+";base64,"+encodeBase64);
            }
        }
        return imageList;
    }

    public boolean uploadImages(MultipartFile[] images) throws IOException {
        // save images to upload directory
        ArrayList<String> fileNames = new ArrayList<>();
        for(MultipartFile file: images){
            Path filenameAndPath = Paths.get(uploadDir, file.getOriginalFilename());
            fileNames.add(file.getOriginalFilename());

            // TODO:
            // check exist
            // provide unique name if file already exists

            try{
                Files.write(filenameAndPath, file.getBytes());
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }

        // create thumbnail and save to upload directory
        ArrayList<String> thumbnailNames = new ArrayList<>();
        for(String filename: fileNames){
            Path filePath = Paths.get(uploadDir, filename);
            File image = new File(String.valueOf(filePath));

            String thumbnailName = "thumbnail_" + filename;
            String thumbnailFullPath = uploadDir + File.separator + thumbnailName;
            File thumbnailFile = new File(thumbnailFullPath);
            Thumbnails.of(image).size(200, 200).toFile(String.valueOf(thumbnailFile));
            thumbnailNames.add(thumbnailName);
        }
        System.out.println(fileNames);
        System.out.println(thumbnailNames);

        // save data to database
        List<Image> imageList  = new ArrayList<>();
        for(int i=0; i<fileNames.size(); i++){
            Image image = new Image();
            image.setFilename(fileNames.get(i));
            image.setThumbnail(thumbnailNames.get(i));
            imageList.add(image);
        }
        imageRepository.saveAll(imageList);

        // send notification to clients
        WsMessageDTO wsMessageDTO = new WsMessageDTO("foo");
        this.brokerMessagingTemplate.convertAndSend("/topic/message", wsMessageDTO);
        return true;
    }


}
