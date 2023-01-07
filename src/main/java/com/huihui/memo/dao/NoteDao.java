package com.huihui.memo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huihui.memo.pojo.Note;

@Repository
public interface NoteDao extends JpaRepository<Note, Integer>{

}
