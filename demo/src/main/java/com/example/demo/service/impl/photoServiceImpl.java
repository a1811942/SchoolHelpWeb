package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Photo;
import com.example.demo.mapper.PhotoDao;
import com.example.demo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class photoServiceImpl extends ServiceImpl<PhotoDao, Photo> implements PhotoService {
    @Autowired
    private PhotoDao photoDao;
    @Override
    public Boolean savePhoto(Photo photo) {
        photo.setId(UUID.randomUUID().toString());
        int insert = photoDao.insert(photo);
        if (insert>0){
            return true;
        }
        return false;
    }

    @Override
    public List<String> getPhotoByMomentsId(String momentsId) {
        List<String> photo = photoDao.getPhotoByMomentsId(momentsId);
        return photo;
    }
}
