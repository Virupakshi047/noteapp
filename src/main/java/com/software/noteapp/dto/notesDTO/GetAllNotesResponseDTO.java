package com.software.noteapp.dto.notesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllNotesResponseDTO {
    private Long noteId;
    private String content;
    private String category;
}
