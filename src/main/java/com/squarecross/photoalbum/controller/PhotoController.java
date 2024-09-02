package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.service.PhotoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
@RestController
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;
    @RequestMapping(value = "/{photoId}",method = RequestMethod.GET)
    public ResponseEntity<PhotoDto> getPhotoInfo(@PathVariable("photoId") final long photoId){
        PhotoDto photoDto = photoService.getPhoto(photoId);
        return new ResponseEntity<>(photoDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<PhotoDto>> getPhotoList(@PathVariable("albumId") final long albumId) {
        List<PhotoDto> photos = photoService.getPhotoList(albumId);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseEntity<List<PhotoDto>> uploadPhotos(@PathVariable("albumId")final long albumId,
                                                       @RequestParam("photos")MultipartFile[] files){
        List<PhotoDto> photos = new ArrayList<>();
        for (MultipartFile file : files) {
            PhotoDto photoDto = photoService.savePhoto(file, albumId);
            photos.add(photoDto);
        }
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @RequestMapping(value="/download", method = RequestMethod.GET)
    public void downloadPhotos(@RequestParam("photoIds") Long[] photoIds, HttpServletResponse response){
        try {
            if (photoIds.length == 1){
                File file = photoService.getImageFile(photoIds[0]);
                OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(new FileInputStream(file), outputStream);
                outputStream.close();
            }
            else {
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", "attachment; filename=\"photos.zip\"");

                ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

                for (Long photoId : photoIds) {
                    File file = photoService.getImageFile(photoId);
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);

                    FileInputStream fis = new FileInputStream(file);
                    IOUtils.copy(fis, zipOut);
                    fis.close();

                    zipOut.closeEntry();
                }

                zipOut.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}