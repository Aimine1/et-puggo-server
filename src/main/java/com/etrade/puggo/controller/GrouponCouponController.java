package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.groupon.GrouponClassDTO;
import com.etrade.puggo.service.groupon.GrouponCouponDTO;
import com.etrade.puggo.service.groupon.GrouponCouponDetailDTO;
import com.etrade.puggo.service.groupon.GrouponCouponService;
import com.etrade.puggo.service.groupon.ListGrouponCouponParam;
import com.etrade.puggo.service.groupon.admin.GenGrouponCouponDTO;
import com.etrade.puggo.service.groupon.admin.GrouponListParam;
import com.etrade.puggo.service.groupon.admin.GrouponListVO;
import com.etrade.puggo.service.groupon.clazz.GrouponClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author niuzhenyu
 * @description : 团购券类接口
 * @date 2023/6/6 15:34
 **/
@Api(value = "团购券接口", tags = "团购券接口")
@RequestMapping("/goods/groupon.coupon/")
@RestController
public class GrouponCouponController {

    @Resource
    private GrouponCouponService grouponCouponService;
    @Resource
    private GrouponClassService grouponClassService;


    @WebLog
    @PostMapping("/list")
    @ApiOperation(value = "首页-团购券列表接口", response = GrouponCouponDTO.class)
    public Result<PageContentContainer<GrouponCouponDTO>> listGrouponCoupon(
        @RequestBody @Validated ListGrouponCouponParam param) {

        return Result.ok(grouponCouponService.listGrouponCoupon(param));
    }


    @WebLog
    @GetMapping("/detail")
    @ApiOperation(value = "首页-团购券详情接口", response = GrouponCouponDetailDTO.class)
    public Result<GrouponCouponDetailDTO> getGrouponCouponDetail(
        @ApiParam(value = "grouponId", required = true) @RequestParam("grouponId") Long grouponId) {

        return Result.ok(grouponCouponService.getGrouponCouponDetail(grouponId));
    }


    @WebLog
    @GetMapping("/class/list")
    @ApiOperation(value = "团购券分类列表接口", response = GrouponClassDTO.class)
    public Result<List<GrouponClassDTO>> listGrouponClass() {

        return Result.ok(grouponClassService.listGrouponClass());
    }


    @WebLog
    @PutMapping("/user/like")
    @ApiOperation(value = "用户收藏团购券")
    public Result<?> likeGrouponCoupon(
        @ApiParam(value = "grouponId", required = true) @RequestParam("grouponId") Long grouponId) {

        grouponCouponService.likeGrouponCoupon(grouponId);
        return Result.ok();
    }


    @WebLog
    @PutMapping("/user/unlike")
    @ApiOperation(value = "用户取消收藏团购券")
    public Result<?> unlikeGrouponCoupon(
        @ApiParam(value = "grouponId", required = true) @RequestParam("grouponId") Long grouponId) {

        grouponCouponService.unlikeGrouponCoupon(grouponId);
        return Result.ok();
    }

    @WebLog
    @PostMapping("/user/like/list")
    @ApiOperation(value = "我的-收藏团购券列表", response = GrouponCouponDTO.class)
    public Result<PageContentContainer<GrouponCouponDTO>> getUserLikeList(@RequestBody @Validated PageParam pageParam) {

        return Result.ok(grouponCouponService.getUserLikeGrouponList(pageParam));
    }

    //+++++++++++++++++++++++++++++++++++++++ Web 接口 ++++++++++++++++++++++++++++++++++++++++++++++++//

    @ApiIgnore
    @WebLog
    @PostMapping("/web/create")
    @ApiOperation(value = "创建团购券for web")
    public Result<?> createGrouponCouponWeb(@Validated @RequestBody GenGrouponCouponDTO param) {

        grouponCouponService.createGrouponCoupon(param);
        return Result.ok();
    }


    @ApiIgnore
    @WebLog
    @PostMapping("/web/list")
    @ApiOperation(value = "团购券列表接口for web", response = GrouponListVO.class)
    public Result<PageContentContainer<GrouponListVO>> listGrouponCouponWeb(
        @RequestBody @Validated GrouponListParam param) {

        return Result.ok(grouponCouponService.listGrouponCouponWeb(param));
    }


    @ApiIgnore
    @WebLog
    @PutMapping("/web/state")
    @ApiOperation(value = "修改团购券状态for web")
    public Result<?> updateStateWeb(@RequestParam Long grouponId, @RequestParam String state) {

        grouponCouponService.updateState(grouponId, state);
        return Result.ok();
    }


    @ApiIgnore
    @WebLog
    @GetMapping("/web/detail")
    @ApiOperation(value = "团购券详情接口for web", response = GenGrouponCouponDTO.class)
    public Result<GenGrouponCouponDTO> getGrouponCouponDetailWeb(
        @ApiParam(value = "grouponId", required = true) @RequestParam("grouponId") Long grouponId) {

        return Result.ok(grouponCouponService.getGrouponCouponDetailWeb(grouponId));
    }


    @ApiIgnore
    @WebLog
    @PostMapping("/web/edit")
    @ApiOperation(value = "编辑团购券for web")
    public Result<?> editGrouponCouponWeb(@Validated @RequestBody GenGrouponCouponDTO param) {

        grouponCouponService.editGrouponCoupon(param);
        return Result.ok();
    }
}
