package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.dao.ai.AiBrandListDao;
import com.etrade.puggo.dao.ai.AiKindListDao;
import com.etrade.puggo.dao.ai.AiPointListDao;
import com.etrade.puggo.dao.ai.AiSeriesListDao;
import com.etrade.puggo.service.ai.AiIdentityService;
import com.etrade.puggo.service.ai.pojo.AIIdentifyRecord;
import com.etrade.puggo.service.ai.pojo.AiBrandDTO;
import com.etrade.puggo.service.ai.pojo.AiIdentificationRecordParam;
import com.etrade.puggo.service.ai.pojo.AiKindDTO;
import com.etrade.puggo.service.ai.pojo.AiPointDTO;
import com.etrade.puggo.service.ai.pojo.AiSeriesDTO;
import com.etrade.puggo.service.ai.pojo.IdentifyOverallAppraisal;
import com.etrade.puggo.service.ai.pojo.IdentifyOverallParam;
import com.etrade.puggo.service.ai.pojo.IdentifyReportVO;
import com.etrade.puggo.service.ai.pojo.IdentifySingleAppraisal;
import com.etrade.puggo.service.ai.pojo.IdentifySingleParam;
import com.etrade.puggo.service.ai.pojo.UpdateAvailableParam;
import com.etrade.puggo.third.ai.UpdateDataSchedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuzhenyu
 * @description : AI鉴定
 * @date 2023/9/11 23:25
 **/
@Api(value = "AI鉴定接口", tags = "AI鉴定接口")
@RestController
@RequestMapping("/goods/goods.ai")
public class AiIdentifyController {

    @Resource
    private AiKindListDao aiKindListDao;
    @Resource
    private AiBrandListDao aiBrandListDao;
    @Resource
    private AiSeriesListDao aiSeriesListDao;
    @Resource
    private AiPointListDao aiPointListDao;
    @Resource
    private AiIdentityService aiIdentityService;
    @Resource
    private UpdateDataSchedule updateDataSchedule;

    @WebLog
    @GetMapping("/kindList/query")
    @ApiOperation(value = "品类列表查询", response = AiKindDTO.class)
    public Result<List<AiKindDTO>> queryKindList() {

        return Result.ok(aiKindListDao.getList());
    }

    @WebLog
    @GetMapping("/brandList/query")
    @ApiOperation(value = "品牌列表查询", response = AiBrandDTO.class)
    public Result<List<AiBrandDTO>> queryBrandList(@RequestParam Integer kindId,
        @RequestParam(name = "brandName", required = false) String brandName) {

        return Result.ok(aiBrandListDao.getList(kindId, brandName));
    }

    @WebLog
    @GetMapping("/seriesList/query")
    @ApiOperation(value = "系列列表查询", response = AiSeriesDTO.class)
    public Result<List<AiSeriesDTO>> querySeriesList(@RequestParam Integer brandId) {

        return Result.ok(aiSeriesListDao.getList(brandId));
    }

    @WebLog
    @GetMapping("/pointList/query")
    @ApiOperation(value = "鉴定点列表查询", response = AiPointDTO.class)
    public Result<List<AiPointDTO>> queryPointList(
        @RequestParam @ApiParam(value = "品类id") Integer kindId,
        @RequestParam @ApiParam(value = "品牌id") Integer brandId,
        @RequestParam @ApiParam(value = "系列id，如果是全系列传0") Integer seriesId) {

        return Result.ok(aiPointListDao.getList(kindId, brandId, seriesId));
    }

    @WebLog
    @PostMapping("/singlePoint/identity")
    @ApiOperation(value = "单个鉴定点鉴定", response = IdentifySingleAppraisal.class)
    public Result<IdentifySingleAppraisal> identifySinglePoint(@RequestBody @Validated IdentifySingleParam param) {

        return Result.ok(aiIdentityService.identifySinglePoint(param));
    }

    @WebLog
    @PostMapping("/overallPoint/identity")
    @ApiOperation(value = "整包鉴定点鉴定，需要所有单鉴返回成功才可以调用整包鉴定接口", response = IdentifyOverallAppraisal.class)
    public Result<IdentifyOverallAppraisal> identifyOverallPoints(@RequestBody @Validated IdentifyOverallParam param) {

        return Result.ok(aiIdentityService.identifyOverallPoints(param));
    }

    @WebLog
    @PostMapping("/available/balance/update")
    @ApiOperation(value = "更新品类Ai鉴定可用次数")
    public Result<?> updateAvailableBalance(@RequestBody UpdateAvailableParam param) {

        aiIdentityService.updateAvailableBalance(param);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/identification/record/list")
    @ApiOperation(value = "AI鉴定记录", response = AIIdentifyRecord.class)
    public Result<PageContentContainer<AIIdentifyRecord>> listAiIdentifyRecord(
        @RequestBody @Validated AiIdentificationRecordParam param) {

        return Result.ok(aiIdentityService.listAiIdentifyRecord(param));
    }

    @WebLog
    @GetMapping("/baseData/update")
    @ApiOperation("基础数据更新接口")
    public Result<?> updateBaseData() {

        updateDataSchedule.update();
        return Result.ok();
    }


    @WebLog
    @GetMapping("/identification/report/get")
    @ApiOperation("获取鉴定报告")
    public Result<IdentifyReportVO> getReport(@RequestParam @ApiParam("AI鉴定编号") String aiIdentifyNo) {

        return Result.ok(aiIdentityService.getReport(aiIdentifyNo));
    }


    @WebLog
    @GetMapping("/aiIdentifyNo/check")
    @ApiOperation("检查AI鉴定编号，支持模糊搜索")
    public Result<Boolean> checkAiIdentifyNo(@RequestParam @ApiParam("AI鉴定编号") String aiIdentifyNo) {

        return Result.ok(aiIdentityService.checkAiIdentifyNo(aiIdentifyNo));
    }

}
