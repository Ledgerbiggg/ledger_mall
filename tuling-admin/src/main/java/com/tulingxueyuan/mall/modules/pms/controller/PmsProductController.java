package com.tulingxueyuan.mall.modules.pms.controller;


import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/pms/pmsProduct")
public class PmsProductController {

    @Autowired
    private PmsProductService pmsProductService;

    @ApiOperation("商品列表")
    @GetMapping("/list")
    public CommonResult<List<PmsProduct>> list(){
        List<PmsProduct> list = pmsProductService.list();
        return CommonResult.success(list);
    }
}

