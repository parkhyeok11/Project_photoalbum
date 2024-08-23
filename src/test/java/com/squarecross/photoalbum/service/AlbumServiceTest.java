package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.squarecross.photoalbum.dto.AlbumDto;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest //스프링 컨테이너 내에 있는 모든 빈을 DI로 가져와서 사용할 수 있도록 만듬 Autowired로 loC내에 있는 빈 모두 사용
@Transactional //데이터베이스에 입/출력시 쿼리를 실행한후 commit을 해야지만 DB에 실제로 적용
class AlbumServiceTest {
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    AlbumService albumService;
    @Autowired
    private PhotoRepository photoRepository;
    @Test
    void getAlbum() {
        Album album = new Album();
        album.setAlbumName("테스트");
        Album savedAlbum = albumRepository.save(album);

        AlbumDto resAlbum = albumService.getAlbum(savedAlbum.getAlbumId());
        assertEquals("테스트",resAlbum.getAlbumName());
    }
    @Test
    void testPhotoCount(){
        Album album=new Album();
        album.setAlbumName("테스트");
        Album saveAlbum = albumRepository.save(album);

        Photo photo1=new Photo();
        photo1.setFileName("사진1");
        photo1.setAlbum(saveAlbum);
        photoRepository.save(photo1);
    }
}