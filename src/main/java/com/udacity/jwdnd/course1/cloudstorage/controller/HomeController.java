package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
    private FileService fileService;
    private UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String homeView(Model model) {
        File[] files = fileService.getAllFiles();
        model.addAttribute("files", files);

        return "home";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication auth, Model model) {
        User user = userService.getUser(auth.getName());

        int fileId = this.fileService.createFile(file, user.getUserid());

        switch (fileId) {
            case -1:
                model.addAttribute("fileError", "Unable to upload the file");
                break;
            case -2:
                model.addAttribute("fileError", "The filename already exists");
                break;
        }

        File[] files = fileService.getAllFiles();
        model.addAttribute("files", files);

        return "home";
    }

    @GetMapping("/file-delete")
    public String deleteFile(@RequestParam(name = "file_id") Integer fileId, Authentication auth, Model model) {
        User user = userService.getUser(auth.getName());
        File file = fileService.getFile(fileId);

        if ((file != null) && (user != null)
                && user.getUserid().intValue() == file.getUserid().intValue()) {
            fileService.deleteFile(fileId);
        }

        File[] files = fileService.getAllFiles();
        model.addAttribute("files", files);

        return "home";
    }

    @GetMapping("/file-download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(name = "file_id") Integer fileId, Authentication auth, Model model) {
        User user = userService.getUser(auth.getName());
        File file = fileService.getFile(fileId);

        if ((file == null) || (user == null)
                || user.getUserid().intValue() != file.getUserid().intValue()) {
            return null;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, file.getContenttype());
            headers.add(HttpHeaders.CONTENT_LENGTH, file.getFilesize());
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename());

            return new ResponseEntity<>(file.getFiledata(), headers, HttpStatus.OK);
        }
    }
}
