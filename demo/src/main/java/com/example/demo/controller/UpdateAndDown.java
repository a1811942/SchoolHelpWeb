package com.example.demo.controller;

import com.example.demo.common.R.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
//@CrossOrigin
@RequestMapping("/demo/UpdateAndDown")
public class UpdateAndDown {
    @Value("${photo.path}")
    private String basePath;

    @RequestMapping("/upload")
    public Result<String> upload(MultipartFile file){
        System.out.println("file======="+file);
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename====="+originalFilename);
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;//dfsdfdfd.jpg
        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }  //将临时文件转存到指定位置
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.getSuccessResult(fileName);
    }

    @GetMapping("/down")
    public void download(@RequestParam(value = "name") String  name, HttpServletResponse response){

        try {

            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
                //关闭资源
                outputStream.close();
                fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @RequestMapping("/detelePhoto")
    public void deletePhoto(@RequestBody String name){
        File file = new File(basePath + name);
        if (file.exists()){
            file.delete();
        }

    }
    @Test
    public void heiKe(){
            int[] a=new  int[1];
        System.out.println(a);

    }
}
