package com.huihui.memo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huihui.memo.pojo.CurrentUser;

public interface CurrentUserDao extends JpaRepository<CurrentUser, Integer>{

}
