package com.software.noteapp.controller;

import com.software.noteapp.dto.notesDTO.*;
import com.software.noteapp.service.NoteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    //create note

    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    @GetMapping
    public List<GetAllNotesResponseDTO> getallNotes(@AuthenticationPrincipal String emailId){
        return noteService.getAllnotes(emailId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<GetAdminAllNotesDTO> getUsersAndNotes(@AuthenticationPrincipal String emailId){
        return noteService.getUsersNotes(emailId);
    }

    @PostMapping
    public CreateNoteResponseDTO createNote(@RequestBody CreateNoteRequestDTO createNoteRequestDTO, @AuthenticationPrincipal String email){
        return noteService.addNote(createNoteRequestDTO,email);
    }
    @PutMapping("/{noteId}")
    public UpdateNoteResponseDTO updateNote(@AuthenticationPrincipal String email,@PathVariable Long noteId, @RequestBody UpdateNoteRequestDTO updateNoteRequestDTO){
        return noteService.updateNote(email, noteId, updateNoteRequestDTO);
    }

    @DeleteMapping("/{noteId}")
    public String deleteNote(@PathVariable Long noteId,@AuthenticationPrincipal String email){
        return noteService.deleteNoteById(noteId,email);
    }
}
