package com.digs.dig0.controller;


import com.digs.dig0.dto.ResponseFile;
import com.digs.dig0.model.User;
import com.digs.dig0.service.ImageService;
import com.digs.dig0.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Slf4j
@Controller
@RequestMapping("/flyer")
@RequiredArgsConstructor
public class flyerController {
    private final ImageService imageService;

    @GetMapping("/flyer")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = imageService.getAllFiles().map(imageData -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/flyer")
                    .path(String.valueOf(imageData.getId()))
                    .toUriString();

            try {
                return new ResponseFile(imageData.getName(), fileDownloadUri, imageData.getType(), imageData.getBytes().length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        // model.addAttribute("imageData",  imageService.getAllFiles());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
}