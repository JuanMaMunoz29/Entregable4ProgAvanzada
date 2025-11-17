package com.playlist.mi_playlist.Repositories;

import com.playlist.mi_playlist.Clases.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
