package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.goods.clazz.GoodsClassService;
import com.etrade.puggo.service.goods.clazz.SelectClassVO2;
import com.etrade.puggo.service.goods.publish.GoodsClassVO;
import com.etrade.puggo.service.groupon.clazz.AddClassParam;
import com.etrade.puggo.service.groupon.clazz.EditClassParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author niuzhenyu
 * @description : 商品分类接口
 * @date 2023/6/15 18:37
 **/
@Api(value = "商品分类接口", tags = "商品分类接口")
@RequestMapping("/goods/goods.class/")
@RestController
public class GoodsClassController {

    @Resource
    private GoodsClassService goodsClassService;


    @WebLog
    @ApiOperation("NoToken-分类级别列表")
    @GetMapping("/list")
    public Result<List<GoodsClassVO>> getClassList() {
        return Result.ok(goodsClassService.getClassList());
    }


    @ApiIgnore
    @WebLog
    @PostMapping("/add")
    @ApiOperation(value = "添加分类")
    public Result<?> addClass(@RequestBody @Validated AddClassParam param) {

        goodsClassService.addClass(param);
        return Result.ok();
    }


    @ApiIgnore
    @WebLog
    @PostMapping("/edit")
    @ApiOperation(value = "修改分类")
    public Result<Integer> editClass(@RequestBody @Validated EditClassParam param) {

        return Result.ok(goodsClassService.editClass(param));
    }


    @ApiIgnore
    @WebLog
    @DeleteMapping("/delete/{classId}")
    @ApiOperation(value = "删除分类")
    public Result<Integer> deleteClass(@PathVariable Integer classId) {

        return Result.ok(goodsClassService.deleteClass(classId));
    }


    @ApiIgnore
    @WebLog
    @GetMapping("/select")
    @ApiOperation(value = "分类筛选器")
    public Result<List<SelectClassVO2>> selectClass() {
        return Result.ok(goodsClassService.selectClass());
    }

}
