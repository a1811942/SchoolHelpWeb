package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.dto.PhotoDto;
import com.example.demo.entity.Photo;
import com.example.demo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/demo/photo")
public class photoController {

    @Autowired
    private PhotoService photoService;

    /**
     * 根据动态id查询图片
     * @param momentsId
     * @return
     */
    @RequestMapping("/getPhotoByMomentsById")
    public Result<List<String >> getPhotoByMomentsId(@RequestBody String momentsId){
        List<String> photo = photoService.getPhotoByMomentsId(momentsId);
        return Result.getSuccessResult(photo);
    }

    /**
     * 保存发布动态的图片
     * @param photo
     * @return
     */
    @PutMapping("/savePhoto")
    public Result<Boolean> savePhoto(@RequestBody PhotoDto photo){
        String[] photoName = photo.getPhotoName();
        String momentsId = photo.getMomentsId();
        Photo photo1 = new Photo();
        Boolean res=false;
        for (String s : photoName) {
            photo1.setPhotoName(s);
            photo1.setMomentsId(momentsId);
            photoService.savePhoto(photo1);

        }


        if (res){
            return Result.getMessageResult(res,"A001");
        }
        return Result.getMessageResult(res,"A002");
    }
}
