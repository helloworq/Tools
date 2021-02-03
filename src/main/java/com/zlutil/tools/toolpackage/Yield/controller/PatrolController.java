package com.zlutil.tools.toolpackage.Yield.controller;

import com.zlutil.tools.toolpackage.ResponseUtil.ResponseData;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseUtil;
import com.zlutil.tools.toolpackage.Yield.dto.YieldBlockBasicInfo;
import com.zlutil.tools.toolpackage.Yield.service.YieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("ommsYieldPatrolController")
@RequestMapping(value = "rest/omms")
@Api(tags = {"OMMS-YieldPatrolController"}, description = "田长巡查任务模块")
public class PatrolController {

    @Autowired
    YieldService yieldService;

    @GetMapping("v1/findSubordinateYieldBlockInfo")
    @ApiOperation(value = "获取用户所管理的下级的全部地块信息")
    public ResponseData findSubordinateYieldBlockInfo(@RequestParam @ApiParam(value = "用户id") String userId) {
        return ResponseUtil.success(yieldService.findSubordinateYieldBlockInfo(userId));
    }

    @GetMapping("v1/findYieldBlockBasicInfo")
    @ApiOperation(value = "村级副田长拉取基本巡查日志信息")
    public ResponseData findYieldBlockBasicInfo(@RequestParam @ApiParam(value = "图块ID") String yieldId) {
        return ResponseUtil.success(yieldService.findYieldBlockBasicInfo(yieldId));
    }

    @PostMapping("v1/uploadYieldBlockBasicInfo")
    @ApiOperation(value = "村级副田长上传巡查日志信息")
    public ResponseData findYieldBlockBasicInfo(@RequestBody @ApiParam(value = "图块信息") YieldBlockBasicInfo yieldBlockBasicInfo) {
        return ResponseUtil.success(yieldService.uploadYieldBlockBasicInfo(yieldBlockBasicInfo));
    }

    @PostMapping("v1/submitUnableHandleTask")
    @ApiOperation(value = "提交无法处理的任务")
    public ResponseData submitUnableHandleTask(@RequestParam @ApiParam(value = "任务Id") String taskId) {
        return ResponseUtil.success(yieldService.submitUnableHandleTask(null, taskId, false));
    }

    @GetMapping("v1/getUnhandleSubordinateUploadTask")
    @ApiOperation(value = "上级拉取未处理的所管下级提交上来的任务")
    public ResponseData getUnhandleSubordinateUploadTask(@RequestParam @ApiParam(value = "用户ID") String userId) {
        return ResponseUtil.success(yieldService.getUnhandleSubordinateUploadTask(userId));
    }

    @GetMapping("v1/getUnhandleSubordinateUploadTask")
    @ApiOperation(value = "办结指定任务")
    public ResponseData concludeTask(@RequestParam @ApiParam(value = "任务ID") String taskId) {
        return ResponseUtil.success(yieldService.concludeTask(taskId));
    }

    @GetMapping("v1/getPendingTask")
    @ApiOperation(value = "获取用户的待处理任务")
    public ResponseData getPendingTask(@RequestParam @ApiParam(value = "用户ID") String userId) {
        return ResponseUtil.success(yieldService.getPendingTask(userId));
    }
}
