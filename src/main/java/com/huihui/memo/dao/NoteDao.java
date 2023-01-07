package com.huihui.memo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huihui.memo.pojo.Note;

@Repository
public interface NoteDao extends JpaRepository<Note, Integer>{

	List<Note> findByUserId(@Param("uid") Integer uid);

}
