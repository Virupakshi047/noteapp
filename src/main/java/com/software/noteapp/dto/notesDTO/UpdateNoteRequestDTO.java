package com.software.noteapp.dto.notesDTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNoteRequestDTO {
    private String content;
    @Nullable
    private String category;
}
