package com.software.noteapp.dto.notesDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNoteRequestDTO {
    @NotBlank(message = "content cannot be empty")
    private String content;
    @Nullable
    private String category;
}
