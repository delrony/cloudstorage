package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int saveNote(NoteForm noteForm) {
        if (noteForm.getId() != null) {
            // Update Note
            Note note = this.noteMapper.getNote(noteForm.getId());
            note.setNotetitle(noteForm.getTitle());
            note.setNotedescription(noteForm.getDescription());
            if (note != null && (note.getUserid() == noteForm.getUserid())) {
                this.noteMapper.update(note);
                return 1;
            } else {
                return -1;
            }
        } else {
            // Insert Note
            Note note = new Note(null, noteForm.getTitle(), noteForm.getDescription(), noteForm.getUserid());
            return this.noteMapper.insert(note);
        }
    }

    public Note getNote(Integer noteid) {
        return this.noteMapper.getNote(noteid);
    }

    public Note[] getAllNotes() {
        return this.noteMapper.getAllNotes();
    }

    public void deleteNote(Integer noteId) {
        this.noteMapper.delete(noteId);
    }
}
