package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import jakarta.persistence.EntityNotFoundException;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.mapper.PhotoMapper;
import com.squarecross.photoalbum.repository.PhotoRepository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.List;

import com.squarecross.photoalbum.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.*;

@Service
public class PhotoService {
    //생성자로 의존성을 가져오기
    @Autowired
    private PhotoRepository photoRepository;

    private final String original_path = Constants.PATH_PREFIX+"/photos/original";
    private final String thumb_path = Constants.PATH_PREFIX+"/photos/thumb";
    @Autowired
    private AlbumRepository albumRepository;
    public PhotoDto getPhoto(Long photoId){
        Optional<Photo> res =photoRepository.findById(photoId);
        if(res.isPresent()){
            PhotoDto photoDto = PhotoMapper.convertToDto(res.get());
            return photoDto;
        } else {
            throw new EntityNotFoundException(String.format("포토 아이디 %d로 조회되지 않았습니다", photoId)); //없을시 에러 throw
        }
    }
    public List<PhotoDto> getPhotoList(Long albumId) {
        List<Photo> photos;
        if (albumId != null) {
            if (!albumRepository.existsById(albumId)) {
                throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다", albumId));
            }
            photos = photoRepository.findByAlbum_AlbumId(albumId);
        } else {
            photos = photoRepository.findAll();
        }
        return photos.stream()
                .map(PhotoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public PhotoDto savePhoto(MultipartFile file, Long albumId){
        checkFile(file);
        Optional<Album> res = albumRepository.findById(albumId);
        if(res.isEmpty()){
            throw  new EntityNotFoundException("앨범이 존재하지 않습니다.");
        }
        String fileName = file.getOriginalFilename();
        int fileSize = (int)file.getSize();
        fileName = getNextFileName(fileName,albumId);
        saveFile(file,albumId,fileName);
        Photo photo = new Photo();
        photo.setOriginalUrl("/photos/original/" + albumId + "/" + fileName);
        photo.setThumbUrl("/photos/thumb/" + albumId + "/" + fileName);
        photo.setFileName(fileName);
        photo.setFileSize(fileSize);
        photo.setAlbum(res.get());
        Photo createdPhoto = photoRepository.save(photo);
        return PhotoMapper.convertToDto(createdPhoto);
    }

    private String getNextFileName(String fileName, long albumId){
        String fileNameNoExt = stripFilenameExtension(fileName);
        String ext = getFilenameExtension(fileName);

        Optional<Photo> res = photoRepository.findByFileNameAndAlbum_AlbumId(fileName,albumId);
        int count =2;
        while(res.isPresent()){
            fileName= String.format("%s (%d).%s",fileNameNoExt, count, ext);
            res = photoRepository.findByFileNameAndAlbum_AlbumId(fileName,albumId);
            count++;
        }
        return  fileName;
    }

    private void saveFile(MultipartFile file,Long AlbumId, String fileName){
        try {
            String filePath =AlbumId+"/"+fileName;
            Files.copy(file.getInputStream(),Paths.get(original_path+"/"+filePath));

            BufferedImage thumbImg = Scalr.resize(ImageIO.read(file.getInputStream()),Constants.THUMB_SIZE,Constants.THUMB_SIZE);
            File thumbFile = new File(thumb_path+"/"+filePath);
            String ext = getFilenameExtension(fileName);
            if(ext==null){
                throw new IllegalArgumentException("N0 Extention");
            }
            ImageIO.write(thumbImg,ext,thumbFile);
        } catch (Exception e){
            throw new RuntimeException("could not store the file.Error:"+e.getMessage());
        }
    }

    private void checkFile(MultipartFile file) {
        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        // 파일 확장자 확인
        String fileName = file.getOriginalFilename();
        String ext = getFilenameExtension(fileName);
        if (ext == null) {
            throw new IllegalArgumentException("파일 확장자가 없습니다.");
        }

        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");
        if (!allowedExtensions.contains(ext.toLowerCase())) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다. 지원되는 형식: " + String.join(", ", allowedExtensions));
        }

        // 실제 파일 내용 확인
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new IllegalArgumentException("유효한 이미지 파일이 아닙니다.");
            }
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }

    public File getImageFile(Long photoId) {
        Optional<Photo> res = photoRepository.findById(photoId);
        if(res.isEmpty()){
            throw new EntityNotFoundException(String.format("사진을 ID %d를 찾을 수 없습니다", photoId));
        }
        return new File(Constants.PATH_PREFIX + res.get().getOriginalUrl());
    }

}
