package com.tulingxueyuan.mall.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.domain.OssPolicyResult;
import com.tulingxueyuan.mall.service.OssService;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
@RestController
@RequestMapping("/aliyun")
public class OssController {
    /*
     *   url:'/aliyun/oss/policy',
     *   method:'get',
     * */
    @Autowired
    private OssService ossService;

    @GetMapping("/oss/policy")
    public CommonResult<OssPolicyResult> policy() {
        return CommonResult.success(ossService.policy());
    }
}

