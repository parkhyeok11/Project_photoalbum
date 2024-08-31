package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.mapper.PhotoMapper;
import com.squarecross.photoalbum.repository.PhotoRepository;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.List;

import com.squarecross.photoalbum.Constants;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
public class PhotoService {
    @Autowired
    private  PhotoRepository photoRepository;

    public PhotoDto getPhoto(Long photoId){
        Optional<Photo> res =photoRepository.findById(photoId);
        if(res.isPresent()){
            PhotoDto photoDto = PhotoMapper.convertToDto(res.get());
            return photoDto;
        } else {
            throw new EntityNotFoundException(String.format("포토 아이디 %d로 조회되지 않았습니다", photoId)); //없을시 에러 throw
        }
    }
}
