package com.huihui.memo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huihui.memo.pojo.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>{

	User findbyUsername(@Param("username") String username);

	User findbyEmail(@Param("email") String email);

}
