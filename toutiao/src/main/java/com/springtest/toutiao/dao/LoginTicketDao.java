package com.springtest.toutiao.dao;

import com.springtest.toutiao.model.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = "user_id,expired,ticket,status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    //插入的是一个字符串数组
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values(#{userId},#{expired},#{ticket},#{status})"})
    int addTicket(LoginTicket loginTicket);

    @Select({"select ", SELECT_FIELDS, "from", TABLE_NAME, "where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update", TABLE_NAME, "set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
