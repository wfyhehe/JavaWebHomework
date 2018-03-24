package com.wfy.web.mapper;

import com.wfy.web.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@Repository
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM t_user")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "last_login_time", property = "lastLoginTime"),
            @Result(column = "authority", property = "authority")
    })
    List<User> list();

    @Select("SELECT * FROM t_user WHERE id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "last_login_time", property = "lastLoginTime"),
            @Result(column = "authority", property = "authority")
    })
    User retrieve(int id);

    @Insert("INSERT INTO t_user(" +
            "username,password,create_time,last_login_time,authority" +
            ") VALUES (" +
            "#{username}, " +
            "#{password}, " +
            "#{createTime}" +
            "#{lastLoginTime}" +
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
    void delete(int id);

}