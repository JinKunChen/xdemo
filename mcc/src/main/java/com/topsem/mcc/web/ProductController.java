package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.common.web.Response;
import com.topsem.mcc.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 产品控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/products")
@Slf4j
public class ProductController extends CrudController<Product, Long> {

    /**
     * 上传商品图片
     */
    @RequestMapping(value = "/fileUpload", method = {RequestMethod.POST})
    @ResponseBody
    public void processUpload(@RequestParam MultipartFile file, ServletRequest request) throws IOException {
        log.info("File '" + file.getOriginalFilename() + "' uploaded successfully");
        String realPath = request.getServletContext().getRealPath("/");
        String uploadDir = realPath + "/images/";
        Files.copy(file.getInputStream(), Paths.get(uploadDir + System.currentTimeMillis() + "-" + file.getOriginalFilename()));
        Response.success("上传文件成功:" + file.getOriginalFilename());
    }
}
