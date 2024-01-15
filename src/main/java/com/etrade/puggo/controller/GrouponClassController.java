package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.groupon.clazz.AddClassParam;
import com.etrade.puggo.service.groupon.clazz.EditClassParam;
import com.etrade.puggo.service.groupon.clazz.GrouponClassService;
import com.etrade.puggo.service.groupon.clazz.SelectClassVO;
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
 * @description : 团购券分类接口
 * @date 2023/6/9 13:20
 **/
@Api(value = "团购券分类接口", tags = "团购券分类接口")
@RequestMapping("/goods/groupon.class/")
@RestController
public class GrouponClassController {

    @Resource
    private GrouponClassService grouponClassService;

    @ApiIgnore
    @WebLog
    @PostMapping("/class/add")
    @ApiOperation(value = "添加分类")
    public Result<?> addClass(@RequestBody @Validated AddClassParam param) {
        grouponClassService.addClass(param);
        return Result.ok();
    }

    @ApiIgnore
    @WebLog
    @PostMapping("/class/edit")
    @ApiOperation(value = "修改分类")
    public Result<Integer> editClass(@RequestBody @Validated EditClassParam param) {
        return Result.ok(grouponClassService.editClass(param));
    }

    @ApiIgnore
    @WebLog
    @DeleteMapping("/class/delete/{classId}")
    @ApiOperation(value = "删除分类")
    public Result<Integer> deleteClass(@PathVariable Integer classId) {
        return Result.ok(grouponClassService.deleteClass(classId));
    }

    @ApiIgnore
    @WebLog
    @GetMapping("/class/select")
    @ApiOperation(value = "分类筛选器")
    public Result<List<SelectClassVO>> selectClass() {
        return Result.ok(grouponClassService.selectClass());
    }
}
