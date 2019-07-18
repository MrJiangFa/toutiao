package com.springtest.toutiao.dao;

import com.springtest.toutiao.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name,password,salt,head_url";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    //插入的是一个字符串数组
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, "from", TABLE_NAME, "where name=#{name}"})
    User selectByName(String name);

    @Update({"update", TABLE_NAME, "set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Update({"update", TABLE_NAME, "set head_url=#{headUrl} where id=#{id}"})
    void updateHead_url(User user);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void deleteById(User user);
}
