package com.digs.dig0.controller;


import com.digs.dig0.dto.EventDto;
import com.digs.dig0.dto.ResponseFile;
import com.digs.dig0.dto.ResponseMessage;
import com.digs.dig0.model.*;
import com.digs.dig0.service.EventService;
import com.digs.dig0.service.FilesStorageService;
import com.digs.dig0.service.ImageService;
import com.digs.dig0.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.annotation.MultipartConfig;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Slf4j
@Controller
@MultipartConfig(fileSizeThreshold = 20971520) // 20MB
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final FilesStorageService storageService;
    private final ImageService imageService;
    private final UserService userService;
    private User user;

    @GetMapping
    public String event(Model model, HttpServletRequest request) throws IOException {
        EventDto event = new EventDto();
        if ( this.user == null ) {
            String user0 = request.getUserPrincipal().getName();
            Optional<User> user2  = userService.findByUsername(user0);
            user2.ifPresent(event::setEvent_Organizer);
            this.user = user2.get();
        } else {
            event.setEvent_Organizer(this.user);
        }
        ImageData file = new ImageData();
        event.setInputFile(file);
        model.addAttribute("event", event);
        model.addAttribute("user", user);
        model.addAttribute("inputFile", event.getInputFile());
        event.setEvent_Organizer(user);
         return "event";
    }

    @PostMapping(path = "/create", consumes ={ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE,
   MediaType.MULTIPART_MIXED_VALUE}, produces ={ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.MULTIPART_MIXED_VALUE})
    public String createEvent (@ModelAttribute("event")EventDto eventDto) throws IOException {
        log.info("Inside createEvent: " );
        eventDto.setEvent_Organizer(user);
        //storageService.save(inputFile);
        ResponseEntity.BodyBuilder builder = this.eventService.createEvent(eventDto);
        if (builder.build().getStatusCode().equals(HttpStatus.CREATED)) {
            return "redirect:/home";
        } else {
            return "redirect:/event?error=createEvent";
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            //storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

 /*   @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(EventController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
*/
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
