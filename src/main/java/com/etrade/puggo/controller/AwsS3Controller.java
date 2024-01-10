package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.exception.AwsS3Exception;
import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.third.aws.AswS3Utils;
import com.etrade.puggo.third.aws.S3PutObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author niuzhenyu
 * @description : S3文件上传控制器
 * @date 2023/5/25 18:17
 **/
@RestController
@Slf4j
@Api(tags = "S3文件上传控制器")
@RequestMapping("/base.S3")
public class AwsS3Controller {

    @Resource
    private AswS3Utils aswS3Utils;

    @WebLog
    @ApiOperation(value = "上传图片请求 只上传原图", notes = "前端上传的提交格式为" +
        "enctype=\"multipart/form-data\"", response = S3PutObjectResult.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "file", value = "选择上传的文件", dataType = "file"),
        @ApiImplicitParam(name = "path", value = "选择上传目录,与具体业务有关,以斜线结尾. \n\r"
            + "例如: 商品图片相关的path=\"goods/\",团购图片相关的path=\"groupon/\",用户相关的path=\"user/\"", dataType = "string",
            defaultValue = "default/")
    })
    @PostMapping("/upload")
    public Result<S3PutObjectResult> upload(@RequestParam(value = "file") MultipartFile file,
        @RequestParam(value = "path", defaultValue = "default/") String path)
        throws IOException {

        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new AwsS3Exception(CommonError.BAD_REQUEST.getCode(), "上传失败, 文件不存在");
        }

        File f = new File(Objects.requireNonNull(filename));
        FileUtils.copyInputStreamToFile(file.getInputStream(), f);
        String key = path.endsWith("/") ? path + filename : path + "/" + filename;
        S3PutObjectResult putObjectResult = aswS3Utils.upload(f, key);
        if (f.exists()) {
            boolean b = f.delete();
            log.info("上传到本地的临时文件删除情况 {}", b);
        }

        return Result.ok(putObjectResult);
    }

}
