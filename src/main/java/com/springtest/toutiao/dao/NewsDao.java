package com.springtest.toutiao.dao;

import com.springtest.toutiao.model.News;
import com.springtest.toutiao.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsDao {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = "title,link,image,like_count,comment_count,created_date,user_id";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;//出错地方——id后没有带逗号

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values(#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    List<News> selectedByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);


    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " where id=#{id}"})
    News getById(int id);

    @Update({"update", TABLE_NAME, "set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Update({"update", TABLE_NAME, "set head_url=#{headUrl} where id=#{id}"})
    void updateHead_url(User user);

    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    @Update({"update ", TABLE_NAME, " set like_count = #{likeCount} where id=#{id}"})
    int updatelikeCount(@Param("id") int id, @Param("likeCount") int likeCount);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void deleteById(User user);
}
