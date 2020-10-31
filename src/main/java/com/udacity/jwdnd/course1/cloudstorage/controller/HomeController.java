package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
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

    public HomeController(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @ModelAttribute("newNote")
    public NoteForm newNote() {
        return new NoteForm();
    }

    @GetMapping("/home")
    public String homeView(Model model) {
        File[] files = fileService.getAllFiles();
        model.addAttribute("files", files);

        Note[] notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);

        return "home";
    }
}
