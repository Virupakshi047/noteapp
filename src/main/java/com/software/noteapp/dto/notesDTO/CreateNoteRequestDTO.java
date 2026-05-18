package com.software.noteapp.dto.notesDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteRequestDTO {
    @NotBlank(message = "content must not be empty")
    private String content;
    private String category;
}
