package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public Album getAlbum(Long albumId){
        Optional<Album> res= albumRepository.findById(albumId); //Optional값을 반환
        if(res.isPresent()){ //값이 있는지 확인
            return res.get(); //있으면 Album 엔티티 반환
        }
        else {
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다",albumId)); //없을시 에러 throw
        }
    }
}
