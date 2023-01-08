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

	//根据书名、作者或者ISBN或者出版社查询图书
	@Query(value="select * from Note where title like '%'||?1||'%' or content like '%'||?1||'%' and user_fk = ?2",nativeQuery = true)
	List<Note> findBySearch(String search, Integer uid);

}
