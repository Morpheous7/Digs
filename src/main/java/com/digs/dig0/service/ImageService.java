package com.digs.dig0.service;

import com.digs.dig0.model.ImageData;
import com.digs.dig0.repository.ImageDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ImageService {


    @Autowired
    private ImageDataDao imageDataDao;

    public void store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        ImageData FileDB = new ImageData(fileName, file.getContentType(), file.getBytes());

        imageDataDao.save(FileDB);
    }

    public ImageData getFile(String id) {
        return imageDataDao.findById(Long.valueOf(id)).get();
    }

    public Stream<ImageData> getAllFiles() {
        return imageDataDao.findAll().stream();
    }
}
