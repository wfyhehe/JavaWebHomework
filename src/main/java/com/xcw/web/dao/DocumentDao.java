package com.xcw.web.dao;

import com.xcw.web.model.Document;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xcw on 18-3-25, good luck.
 */
@Repository
@Mapper
public interface DocumentDao {

    @Select("SELECT * FROM t_document INNER JOIN t_document_user INNER JOIN t_user " +
            "ON t_document.id = t_document_user.document_id AND " +
            "t_user.id = t_document_user.user_id " +
            "WHERE t_document.deleted = 0"
    )
    @Results({
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "contributors", column = "document_id",
                    many = @Many(select = "com.xcw.web.dao.UserDao.listByDocument")
            )
    })
    List<Document> list();

    @Select("SELECT * FROM t_document INNER JOIN t_document_user INNER JOIN t_user " +
            "ON t_document.id = t_document_user.document_id AND " +
            "t_user.id = t_document_user.user_id " +
            "WHERE t_document.deleted = 0 " +
            "AND t_user.id = #{id}"
    )
    @Results({
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "contributors", column = "document_id",
                    many = @Many(select = "com.xcw.web.dao.UserDao.listByDocument")
            )
    })
    List<Document> listByUser(Long id);

    @Select("SELECT * FROM t_document INNER JOIN t_document_user INNER JOIN t_user " +
            "ON t_document.id = t_document_user.document_id AND " +
            "t_user.id = t_document_user.user_id " +
            "WHERE t_document.deleted = 0 " +
            "AND t_document.id = #{id}"
    )
    @Results({
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "contributors", column = "document_id",
                    many = @Many(select = "com.xcw.web.dao.UserDao.listByDocument")
            )
    })
    Document retrieve(Long id);

    @Insert("INSERT INTO t_document(" +
            "title,content,create_time,status" +
            ") VALUES (" +
            "#{title}, " +
            "#{content}, " +
            "#{createTime}, " +
            "#{status} " +
            ")"
    )
    @Options(useGeneratedKeys = true)
    void create(Document document);

    @Insert("INSERT INTO t_document_user(" +
            "document_id,user_id" +
            ") VALUES (" +
            "#{documentId}, " +
            "#{userId} " +
            ")"
    )
    @Options(useGeneratedKeys = true)
    void createLink(@Param("documentId") Long documentId, @Param("userId") Long userId);

    @Update("UPDATE t_document SET " +
            "title=#{title}," +
            "content=#{content} " +
            "WHERE id =#{id}"
    )
    void update(Document document);

    @Update("UPDATE t_document SET " +
            "status=#{status} " +
            "WHERE id =#{id}"
    )
    void updateStatus(Document document);

    @Update("UPDATE t_document SET " +
            "deleted=1 " +
            "WHERE id =#{id}"
    )
    void delete(Long id);

    @Update("UPDATE t_document SET " +
            "deleted=0 " +
            "WHERE id =#{id}"
    )
    void restore(Long id);

}