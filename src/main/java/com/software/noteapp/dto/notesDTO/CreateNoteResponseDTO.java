package com.software.noteapp.dto.notesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteResponseDTO {
    private Long id;
    private String content;
    private String category;
    private String message;
}
