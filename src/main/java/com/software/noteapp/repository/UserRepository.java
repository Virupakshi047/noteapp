package com.software.noteapp.repository;

import com.software.noteapp.entity.Note;
import com.software.noteapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   public Optional<User> findByEmailId(String email);
}
