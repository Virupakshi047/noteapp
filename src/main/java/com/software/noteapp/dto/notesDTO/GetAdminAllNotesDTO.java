package com.software.noteapp.dto.notesDTO;

import com.software.noteapp.entity.Note;
import com.software.noteapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdminAllNotesDTO {
    private Long userId;
    private  String userName;
    private List<Note> notes;
}
