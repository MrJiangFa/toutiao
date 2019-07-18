package com.springtest.toutiao.dao;

import com.springtest.toutiao.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper与mybatis连接
@Mapper
public interface CommentDao {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "user_id,content,entity_id,entity_type,created_date,status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{userId},#{content}," +
            "#{entityId},#{entityType},#{createdDate},#{status}) "})
    int addComment(Comment comment);//返回int表示是否添加成功

    //"order by id desc"将最新的评论放置在前面
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    //获得某个评论实体的总数
    @Select({"select count(id) from", TABLE_NAME, "where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    //通常没有删除评论的习惯，基本上都是更新评论的状态
    @Update({"update", TABLE_NAME, "set status = #{status} where entity_id=#{entityId} and entity_type=#{entityType}"})
    void updateStatus(@Param("entityId") int entityId, @Param("entityType") int entityType, @Param("status") int status);
}
