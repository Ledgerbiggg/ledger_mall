package com.tulingxueyuan.mall.modules.pms.controller;


import cn.hutool.db.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;
    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    //productAttribute/list/1?pageNum=1&pageSize=5&type=0
    //get
    @GetMapping("/list/{id}")
    public CommonResult<CommonPage<PmsProductAttribute>> getListById(Long pageNum,Long pageSize,@PathVariable Integer id,Long type){
        return pmsProductAttributeService.getListById(pageNum,pageSize,id,type);
    }
    //productAttribute/create
    //post
    @PostMapping("/create")
    public CommonResult<String> create(@RequestBody PmsProductAttribute pmsProductAttribute){
        return pmsProductAttributeService.create(pmsProductAttribute);
    }
    //productAttribute/86
    //get
    @GetMapping("/{id}")
    public CommonResult<PmsProductAttribute> getById(@PathVariable Long id){
        return pmsProductAttributeService.getAttrDetail(id);
    }
    //productAttribute/update/86
    //post
    @PostMapping("/update/{id}")
    public CommonResult<String> update(@RequestBody PmsProductAttribute pmsProductAttribute){
        return pmsProductAttributeService.updateInfo(pmsProductAttribute);
    }
    //productAttribute/delete
    //post
    @PostMapping("/delete")
    public CommonResult<String> delete(@RequestParam List<Long> ids){
        return pmsProductAttributeService.delete(ids);
    }

    /*
     *  productAttribute/attrInfo/19
     *  GET
     * */

    @GetMapping("/attrInfo/{id}")
    public CommonResult<List<PmsProductAttrInfoDTO>> getAttrInfo(@PathVariable Long id){
        return pmsProductCategoryService.getAttrInfo(id);
    }

}

