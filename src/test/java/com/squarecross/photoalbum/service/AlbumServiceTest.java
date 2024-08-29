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

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.List;

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
    @Test
    void testAlbumRepository() throws InterruptedException{
        Album album1 = new Album();
        Album album2 = new Album();
        album1.setAlbumName("aaaa");
        album2.setAlbumName("aaab");

        albumRepository.save(album1);
        TimeUnit.SECONDS.sleep(1);
        albumRepository.save(album2);

        List<Album> resDate=albumRepository.findByAlbumNameContainingOrderByCreatedAtDesc("aaa");
        assertEquals("aaab",resDate.get(0).getAlbumName());
        assertEquals("aaaa",resDate.get(0).getAlbumName());
        assertEquals(2,resDate.size());

        List<Album> resName = albumRepository.findByAlbumNameContainingOrderByAlbumNameAsc("aaa");
        assertEquals("aaaa",resName.get(0).getAlbumName());
        assertEquals("aaab",resName.get(0).getAlbumName());
        assertEquals(2,resName.size());
    }

    @Test
    void testChangeName() throws IOException {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("변경전");
        AlbumDto res = albumService.createAlbum(albumDto);

        Long albumId = res.getAlbumId();
        AlbumDto updateDto = new AlbumDto();
        updateDto.setAlbumName("변경후");
        albumService.changeName(albumId,updateDto);

        AlbumDto updatedDto = albumService.getAlbum(albumId);

        assertEquals("변경후",updatedDto.getAlbumName());
    }
}