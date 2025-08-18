package com.edelala.mur.repo;

import com.edelala.mur.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository  extends JpaRepository<Image, Long> {
}
