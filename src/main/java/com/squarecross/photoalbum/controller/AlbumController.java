package com.squarecross.photoalbum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.squarecross.photoalbum.dto.AlbumDto;
import org.springframework.http.ResponseEntity;
import com.squarecross.photoalbum.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

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

    // Query String을 사용하는 메서드 http://localhost:8080/albums?albumId=2
    @RequestMapping(value="/query?albumId=<album_id>",method = RequestMethod.GET)  // @GetMapping사용가능
     public ResponseEntity<AlbumDto> getAlbumByQuery(@RequestParam("albumId") final long albumId) {
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    // JSON body를 사용하는 메서드 (albumId만 받음) http://localhost:8080/albums body클릭후 json
    @RequestMapping(value="json_body",method = RequestMethod.POST)
    public ResponseEntity<AlbumDto> getAlbumByJson(@RequestBody AlbumIdRequest request) {
        AlbumDto album = albumService.getAlbum(request.getAlbumId());
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST) //url /httpl 메서드
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody final AlbumDto albumDto) throws IOException {  //입출력
        AlbumDto saveAlbumDto = albumService.createAlbum(albumDto);
        return new ResponseEntity<>(saveAlbumDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<AlbumDto>> getAlbumList(@RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                 @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort) {
        List<AlbumDto> albumDtos = albumService.getAlbumList(keyword, sort);
        return new ResponseEntity<>(albumDtos, HttpStatus.OK);
    }

    @RequestMapping(value="/{albumId}", method = RequestMethod.PUT)
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable("albumId") final long albumId,
                                                @RequestBody final AlbumDto albumDto){
        AlbumDto res = albumService.changeName(albumId,albumDto);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable("albumId") final long albumId) {
        try {
            albumService.deleteAlbum(albumId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

 //JSON 요청을 위한 간단한 클래스
class AlbumIdRequest {
    private long albumId;

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }
}