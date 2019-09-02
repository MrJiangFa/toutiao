package com.springtest.toutiao.service;

import com.springtest.toutiao.dao.CommentDao;
import com.springtest.toutiao.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("")
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public List<Comment> getCommentsByEntity(int entityId,int entityType){
        return commentDao.selectByEntity(entityId,entityType);
    }

    public int addComment(Comment comment){
        return commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId,int entityType){
        return commentDao.getCommentCount(entityId,entityType);
    }

    //良好的封装性
    public void deleteComment(int entityId,int entityType,int status){
        commentDao.updateStatus(entityId,entityType,1);
    }

}
