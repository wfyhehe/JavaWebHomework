package com.xcw.web.dao;

import com.xcw.web.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xcw on 18-3-24, good luck.
 */
@Repository
@Mapper
public interface UserDao {

    @Select("SELECT * FROM t_user")
    List<User> list();

    @Select("SELECT t_user.* FROM " +
            "t_user INNER JOIN t_document_user " +
            "ON t_user.id = t_document_user.user_id " +
            "where t_document_user.document_id = #{documentId}")
    List<User> listByDocument(@Param("documentId") Long documentId);

    @Select("SELECT * FROM t_user WHERE id = #{id}")
    User retrieve(Long id);

    @Select("SELECT * FROM t_user WHERE username = #{username}")
    User retrieveByUsername(String username);

    @Insert("INSERT INTO t_user(" +
            "username,password,create_time,last_login_time,authority" +
            ") VALUES (" +
            "#{username}, " +
            "#{password}, " +
            "#{createTime}, " +
            "#{lastLoginTime}, " +
            "#{authority}" +
            ")"
    )
    void create(User user);

    @Update("UPDATE t_user SET " +
            "username=#{username}," +
            "password=#{password}, " +
            "create_time=#{createTime}, " +
            "last_login_time=#{lastLoginTime}, " +
            "authority=#{authority}, " +
            "WHERE id =#{id}"
    )
    void update(User user);

    @Delete("DELETE FROM t_user WHERE id =#{id}")
    void delete(Long id);

}