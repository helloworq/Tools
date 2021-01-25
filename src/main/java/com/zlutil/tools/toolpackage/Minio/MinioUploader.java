package com.zlutil.tools.toolpackage.Minio;

import com.zlutil.tools.toolpackage.ResponseUtil.ResponseData;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

@RestController
public class MinioUploader {

    @Autowired
    MinioRunnerVersion8 minioClient;

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/fileUpload")
    public ResponseData fileUpload(@ApiParam("多选") @RequestParam(value = "file") MultipartFile[] fileList) {
        Arrays.stream(fileList).forEach(file -> {
            try {
                InputStream inputStream = file.getInputStream();
                minioClient.uploadFileStream(UUID.randomUUID().toString(), inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return ResponseUtil.success();
    }

    @ApiOperation(value = "获取文件信息")
    @PostMapping(value = "/getFileInfo")
    public ResponseData getFileInfo(@ApiParam("多选") @RequestParam(value = "objectId") String objectId) {
        return ResponseUtil.success(minioClient.getFileInfo(objectId));
    }
}
