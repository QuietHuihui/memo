package com.huihui.memo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huihui.memo.pojo.Note;


@Repository
public interface NoteDao extends JpaRepository<Note, Integer>{

	List<Note> findByUserId(@Param("uid") Integer uid);

	//根据标题和内容查找备忘
	@Query(value="select * from Note where title like '%'||?1||'%' or content like '%'||?1||'%' and user_fk = ?2 ",nativeQuery = true)
	List<Note> findBySearch(String search, Integer uid);

	//根据标题和内容查找备忘，并替换其中的内容
	@Transactional
	@Modifying
	@Query(value = "UPDATE Note n SET n.content = replace(n.content, :search , :replaceString), n.title = replace(n.title, :search , :replaceString) WHERE n.content LIKE CONCAT('%',:search,'%') or n.title LIKE CONCAT('%',:search,'%') and n.user.id=:uid")
	void replace(@Param("search") String search,@Param("replaceString") String replaceString,@Param("uid") Integer uid);

}
