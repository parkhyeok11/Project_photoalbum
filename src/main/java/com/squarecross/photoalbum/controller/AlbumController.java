package com.squarecross.photoalbum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.squarecross.photoalbum.dto.AlbumDto;
import org.springframework.http.ResponseEntity;
import com.squarecross.photoalbum.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    // Path Variable을 사용하는 메서드 http://localhost:8080/albums/1
    @RequestMapping(value = "/{albumId}", method = RequestMethod.GET)
    public ResponseEntity<AlbumDto> getAlbumByPath(@PathVariable("albumId") final long albumId) {
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

//    // Query String을 사용하는 메서드 http://localhost:8080/albums?albumId=2
//    @RequestMapping(method = RequestMethod.GET)  // @GetMapping사용가능
//   public ResponseEntity<AlbumDto> getAlbumByQuery(@RequestParam("albumId") final long albumId) {
//        AlbumDto album = albumService.getAlbum(albumId);//       return new ResponseEntity<>(album, HttpStatus.OK);
//    }

    // JSON body를 사용하는 메서드 (albumId만 받음) http://localhost:8080/albums body클릭후 json
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<AlbumDto> getAlbumByJson(@RequestBody AlbumIdRequest request) {
//        AlbumDto album = albumService.getAlbum(request.getAlbumId());
//        return new ResponseEntity<>(album, HttpStatus.OK);//   }
    @RequestMapping(value = "",method = RequestMethod.POST) //url /httpl 메서드
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody final AlbumDto albumDto) throws IOException {  //입출력
        AlbumDto saveAlbumDto = albumService.createAlbum(albumDto);
        return new ResponseEntity<>(saveAlbumDto,HttpStatus.OK);
    }
}

// JSON 요청을 위한 간단한 클래스
//class AlbumIdRequest {
//    private long albumId;

//    public long getAlbumId() {
//        return albumId;
//    }

//    public void setAlbumId(long albumId) {
//        this.albumId = albumId;
//    }
//}