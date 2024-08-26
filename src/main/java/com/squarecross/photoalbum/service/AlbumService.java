package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.PhotoRepository;

import java.io.IOException;
import java.util.Optional;

import com.squarecross.photoalbum.Constants;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private PhotoRepository photoRepository;

    public AlbumDto getAlbum(Long albumId){
        Optional<Album> res= albumRepository.findById(albumId); //Optional값을 반환
        if(res.isPresent()){ //값이 있는지 확인
            AlbumDto albumDto = AlbumMapper.convertToDto(res.get());
            albumDto.setCount(photoRepository.countByAlbum_AlbumId(albumId));
            return albumDto;
        }
        else {
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다",albumId)); //없을시 에러 throw
        }
    }
    public AlbumDto createAlbum(AlbumDto albumDto) throws IOException {
        Album album=AlbumMapper.convertToModel(albumDto);
        this.albumRepository.save(album);
        this.createAlbumDirectories(album);
        return AlbumMapper.convertToDto(album);
    }
    private void createAlbumDirectories(Album album) throws IOException{
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/original/" + album.getAlbumId()));
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + album.getAlbumId()));
    }
}
