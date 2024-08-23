package com.squarecross.photoalbum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping; //url경로의 앞부분을 나타냄 https://<url>albums
import org.springframework.web.bind.annotation.RestController; //해당 클래스가 controller라는것을 나타내고 restapi목적으로 사용할것임을 나타냄

import org.springframework.web.bind.annotation.RequestMethod; //url경로와 http메서드 정의용

import com.squarecross.photoalbum.dto.AlbumDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.squarecross.photoalbum.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @RequestMapping(value = "/{albumId}",method = RequestMethod.GET)
    public ResponseEntity<AlbumDto> getAlbum(@PathVariable("albumId")final long albumId){ //메서드 입출력 정의
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }
}
