package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Photo;

import java.util.List;

public interface PhotoService extends IService<Photo> {
    Boolean savePhoto(Photo photo);
    List<String > getPhotoByMomentsId(String momentsId);
}
