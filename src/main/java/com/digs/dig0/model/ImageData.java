package com.digs.dig0.model;


import jakarta.persistence.*;
import jakarta.servlet.http.Part;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class ImageData extends BaseEntity implements MultipartFile, Serializable {

    private String name;
    private String type;
    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    public ImageData(ImageData imageData) {
        super();
        this.name = imageData.getName();
        this.type = imageData.getType();
        this.imageData = imageData.getImageData();
    }

    public ImageData(MultipartFile imageData) throws IOException {
        super();
        this.name = imageData.getOriginalFilename();
        this.type = imageData.getContentType();
        this.imageData = imageData.getBytes();
    }

    public ImageData(String fileName, String contentType, byte[] bytes) {
        super();
        this.name = fileName;
        this.type = contentType;
        this.imageData = bytes;
    }

    @Override
    public String getOriginalFilename() {
        return this.name;
    }

    @Override
    public String getContentType() {            return this.type;        }

    @Override
    public boolean isEmpty() {
        return this.getSize() == 0L;
    }

    @Override
    public long getSize() {
        return this.imageData.getClass().accessFlags().size();
    }


    @Override
    public byte @NotNull [] getBytes() throws IOException {
        return new byte[]{imageData[0]};
    }

    @Override
    public @NotNull InputStream getInputStream() throws IOException {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        this.write(dest.getPath());
        if (dest.isAbsolute() && !dest.exists()) {
            FileCopyUtils.copy(this.getInputStream(), Files.newOutputStream(dest.toPath()));
        }
    }

    private void write(String path) {
    }

    public void transferTo(Path dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.getInputStream(), Files.newOutputStream(dest));
    }
}