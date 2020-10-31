package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.geom.AffineTransform;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note-save")
    public String saveNote(@ModelAttribute("newNote")NoteForm noteForm, Authentication auth, RedirectAttributes attributes) {
        User user = this.userService.getUser(auth.getName());
        Integer userid = user.getUserid();
        noteForm.setUserid(userid);

        this.noteService.saveNote(noteForm);

        attributes.addFlashAttribute("fromNote", "OK");

        return "redirect:/home";
    }

    @GetMapping("/note-delete")
    public String deleteNote(@RequestParam(name="note_id") Integer noteid, Authentication auth, RedirectAttributes attributes) {
        User user = userService.getUser(auth.getName());
        Note note = noteService.getNote(noteid);

        if ((note != null) && (user != null)
                && user.getUserid().intValue() == note.getUserid().intValue()) {
            this.noteService.deleteNote(noteid);
        }

        attributes.addFlashAttribute("fromNote", "OK");

        return "redirect:/home";
    }
}
