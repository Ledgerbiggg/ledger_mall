package com.tulingxueyuan.mall.modules.pms.controller;


import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductInfoWhitListAndCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductInfoWithListDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    private PmsProductService pmsProductService;


    /*
    * product/list?keyword=11&pageNum=1&pageSize=5&publishStatus=1&verifyStatus=1&productSn=11&productCategoryId=43&brandId=1
    * */
    @ApiOperation("商品列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProduct>> list(String keyword,
                                                     Long pageNum,
                                                     Long pageSize,
                                                     Integer publishStatus,
                                                     Integer verifyStatus,
                                                     String productSn,
                                                     Integer productCategoryId,
                                                     Integer brandId){
        return pmsProductService.getProductList(keyword,pageNum,pageSize,publishStatus,verifyStatus,productSn,productCategoryId,brandId);
    }

    /*
    * product/update/publishStatus?ids=26&publishStatus=0
    * post
    * */
    @PostMapping("/update/publishStatus")
    public CommonResult<String> publishStatus(@RequestParam List<Long> ids,Integer publishStatus){
        return pmsProductService.publishStatus(ids,publishStatus);
    }

    /*
    * product/update/newStatus?ids=26&newStatus=0
    * post
    * */
    @PostMapping("/update/newStatus")
    public CommonResult<String> newStatus(@RequestParam List<Long> ids,Integer newStatus){
        return pmsProductService.newStatus(ids,newStatus);
    }

    /*
    * product/update/recommendStatus?ids=26&recommendStatus=0
    * post
    * */
    @PostMapping("/update/recommendStatus")
    public CommonResult<String> recommendStatus(@RequestParam List<Long> ids,Integer recommendStatus){
        return pmsProductService.recommendStatus(ids,recommendStatus);
    }
    /*
    * product/create
    * post
    * */

    @PostMapping("/create")
    public CommonResult<String> create(@RequestBody PmsProductInfoWithListDTO pmsProductInfoWithListDTO){
        return pmsProductService.create(pmsProductInfoWithListDTO);
    }

    /*
    * product/updateInfo/52
    * get
    * */
    @GetMapping("/updateInfo/{id}")
    public CommonResult<PmsProductInfoWhitListAndCategoryDTO> getUpdateInfo(@PathVariable Long id){
        return pmsProductService.getUpdateInfo(id);
    }
    /*
    * product/update/deleteStatus?ids=59&deleteStatus=1
    * post
    * */
    @PostMapping("/update/deleteStatus")
    public CommonResult<String> delete(@RequestParam List<Long> ids,Long deleteStatus){
        return pmsProductService.delete(ids,deleteStatus);
    }
    /*
    * /product/update/56
    * POST
    * */
    @PostMapping("/update/{id}")
    public CommonResult<String> update(@RequestBody PmsProductInfoWithListDTO pmsProductInfoWithListDTO){
        return pmsProductService.edit(pmsProductInfoWithListDTO);
    }





}

