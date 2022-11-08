package com.example.demo.service.impl;

import com.example.demo.service.UpdateAndDownService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
@Service
public class UpdateAndDownServiceImpl implements UpdateAndDownService {
    @Value("${photo.path}")
    private String basePath;

    /**
     * 删除图片
     * @param name
     */
    @Override
    public void deletePhoto(String name) {
        File file = new File(basePath + name);
        if (file.exists()){
            file.delete();
        }
    }
}
