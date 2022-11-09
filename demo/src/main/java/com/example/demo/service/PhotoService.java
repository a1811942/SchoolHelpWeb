package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Photo;

import java.util.List;

public interface PhotoService extends IService<Photo> {
    //保存照片
    Boolean savePhoto(Photo photo);
    //根据动态id查询所有照片名称
    List<String > getPhotoByMomentsId(String momentsId);
    //根据动态id删除所有照片信息
    Boolean deleteByMomentsId(String momentsId);
}
