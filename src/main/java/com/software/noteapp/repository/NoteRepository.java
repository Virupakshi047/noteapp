package com.software.noteapp.repository;

import com.software.noteapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    public List<Note> findByUsersId(Long userId);
}
