package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Photo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PhotoDao extends BaseMapper<Photo> {
    public List<String > getPhotoByMomentsId(String momentsId);
    public List<String > getPhotoByCommentId(String coment);
    Boolean deleteByMomentsId(String momentsId);
}
