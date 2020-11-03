package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @ModelAttribute("newNote")
    public NoteForm newNote() {
        return new NoteForm();
    }

    @ModelAttribute("newCredential")
    public CredentialForm newCredential()  {
        return new CredentialForm();
    }

    @GetMapping("/home")
    public String homeView(Model model) {
        File[] files = fileService.getAllFiles();
        model.addAttribute("files", files);

        Note[] notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);

        Credential[] credentials = credentialService.getAllCredentials();
        model.addAttribute("credentials", credentials);

        return "home";
    }

    @GetMapping("/error")
    public String errorView(Model model) {

        return "error";
    }
}
