package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;

public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;
}
