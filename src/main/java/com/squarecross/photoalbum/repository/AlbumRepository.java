package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface AlbumRepository extends JpaRepository<Album,Long>{
    List<Album> findByAlbumNameContainingOrderByCreatedAtDesc(String keyword);

    List<Album> findByAlbumNameContainingOrderByAlbumNameAsc(String keyword);
}
