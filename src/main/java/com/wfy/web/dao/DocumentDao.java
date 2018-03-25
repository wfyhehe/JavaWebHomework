package com.wfy.web.dao;

import com.wfy.web.model.Document;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wfy on 18-3-25, good luck.
 */
@Repository
@Mapper
public interface DocumentDao {

    @Select("SELECT * FROM t_document INNER JOIN t_document_user INNER JOIN t_user " +
            "ON t_document.id = t_document_user.document_id AND " +
            "t_user.id = t_document_user.user_id " +
            "WHERE t_document.deleted = false"
    )
    List<Document> list();

    @Select("SELECT * FROM t_document INNER JOIN t_document_user INNER JOIN t_user " +
            "ON t_document.id = t_document_user.document_id AND " +
            "t_user.id = t_document_user.user_id " +
            "WHERE t_document.deleted = false" +
            "AND t_user.id = #{id}"
    )
    List<Document> listByUser(Long id);

    @Select("SELECT * FROM t_document INNER JOIN t_document_user INNER JOIN t_user " +
            "ON t_document.id = t_document_user.document_id AND " +
            "t_user.id = t_document_user.user_id " +
            "WHERE t_document.deleted = false" +
            "AND t_document.id = #{id}"
    )
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
    Long create(Document document);

    @Insert("INSERT INTO t_document_user(" +
            "document_id,user_id" +
            ") VALUES (" +
            "#{documentId}, " +
            "#{userId} " +
            ")"
    )
    Long createLink(Long documentId, Long userId);

    @Update("UPDATE t_document SET " +
            "title=#{title}," +
            "content=#{content}, " +
            "status=#{status} " +
            "WHERE id =#{id}"
    )
    void update(Document document);

    @Update("UPDATE t_document SET " +
            "deleted=true " +
            "WHERE id =#{id}"
    )
    void delete(Long id);

}