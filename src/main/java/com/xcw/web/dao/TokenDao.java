package com.xcw.web.dao;

import com.xcw.web.model.Token;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


/**
 * Created by xcw on 18-3-25, good luck.
 */
@Repository
@Mapper
public interface TokenDao {
    @Select("SELECT * FROM t_token WHERE id = #{id}")
    Token retrieve(String id);

    @Insert("INSERT INTO t_token(" +
            "id, credentials" +
            ") VALUES (" +
            "#{id}, " +
            "#{credentials} " +
            ")"
    )
    void create(Token token);

    @Update("UPDATE t_token SET " +
            "credentials=#{credentials}" +
            "WHERE id =#{id}"
    )
    void update(Token token);

    @Delete("DELETE FROM t_token WHERE id =#{id}")
    void delete(String id);

}