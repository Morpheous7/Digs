package com.digs.dig0.repository;

import com.digs.dig0.model.Event;
import com.digs.dig0.model.ImageData;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Repository
public interface ImageDataDao extends JpaRepository<ImageData, Long> {

    Optional<ImageData> save(Optional<ImageData> imageData);

    <S extends ImageData> @NotNull S save(@org.jetbrains.annotations.NotNull S entity);
}
