package com.springtest.toutiao.service;

import com.springtest.toutiao.dao.NewsDao;
import com.springtest.toutiao.model.News;
import com.springtest.toutiao.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDao.selectedByUserIdAndOffset(userId, offset, limit);
    }

    public int addNews(News news) {
        newsDao.addNews(news);
        return news.getId();
    }

    public News getById(int newsId){
        return newsDao.getById(newsId);
    }

    public String saveImage(MultipartFile file) throws IOException {
        //判断文件后缀名，首先判断是否有点
        int doPos = file.getOriginalFilename().lastIndexOf(".");
        if (doPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(doPos + 1).toLowerCase();
        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

    public int updateCommentCount(int id, int count) {
        return newsDao.updateCommentCount(id, count);
    }

    public int updatelikeCount(int id, int count) {
        return newsDao.updatelikeCount(id, count);
    }
}
