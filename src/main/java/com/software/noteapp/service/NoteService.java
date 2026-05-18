package com.software.noteapp.service;

import com.software.noteapp.dto.notesDTO.*;
import com.software.noteapp.entity.Note;
import com.software.noteapp.entity.User;
import com.software.noteapp.repository.NoteRepository;
import com.software.noteapp.repository.UserRepository;
import com.software.noteapp.util.JwtUtil;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository,UserRepository userRepository){
        this.noteRepository=noteRepository;
        this.userRepository=userRepository;
    }

    public CreateNoteResponseDTO addNote(CreateNoteRequestDTO createNoteRequestDTO , String emailId){
        Note note = new Note();
        note.setCategory(createNoteRequestDTO.getCategory());
        note.setContent(createNoteRequestDTO.getContent());
        Optional<User> user = userRepository.findByEmailId(emailId);
        if (user.isEmpty()) return new CreateNoteResponseDTO(null,"","","User Not found");
        note.setUsers(user.get());
        noteRepository.save(note);
        return new CreateNoteResponseDTO(note.getId(), note.getContent(),note.getCategory(),"Created successfully");
    }

    public GetAllNotesResponseDTO convert(Note note){
        return new GetAllNotesResponseDTO(note.getId(),note.getContent(),note.getCategory());
    }
    public List<GetAllNotesResponseDTO> getAllnotes(String email){
        User user = userRepository.findByEmailId(email).orElseThrow();
        List<Note> allNotes = noteRepository.findByUsersId(user.getId());
        return  allNotes.stream().map(this::convert).toList();
    }

    public UpdateNoteResponseDTO updateNote(String emailId,Long noteId, UpdateNoteRequestDTO updateNoteRequestDTO){
        Long userId = userRepository.findByEmailId(emailId).orElseThrow().getId();
        Note note = noteRepository.findById(noteId).get();
        Long getUserIgfromNote = note.getUsers().getId();
        if(!Objects.equals(userId, getUserIgfromNote)) throw new RuntimeException("Note not found");
        note.setContent(updateNoteRequestDTO.getContent());
        note.setCategory(updateNoteRequestDTO.getCategory());
        noteRepository.save(note);
        return new UpdateNoteResponseDTO("updated scuccessfully");
    }

    public String deleteNoteById(Long noteId , String emailId){
        Long userId = userRepository.findByEmailId(emailId).orElseThrow().getId();
        Optional<Note> note = noteRepository.findById(noteId);
        if(note.isEmpty()) return "Note not found";
        Long getUserIgfromNote = note.get().getUsers().getId();
        if(!Objects.equals(userId, getUserIgfromNote)) return "Note Not found";
        noteRepository.deleteById(noteId);
        return "deleted successfully";
    }

    private GetAdminAllNotesDTO getAndCovert(User user){
        return new GetAdminAllNotesDTO(user.getId(),user.getName(),user.getNotes());
    }

    public List<GetAdminAllNotesDTO> getUsersNotes(String emilId){
        List<User> users = userRepository.findAll();
        return users.stream().filter(u-> !Objects.equals(u.getEmailId(), emilId)).map(this::getAndCovert).toList();
    }

}
