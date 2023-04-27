package com.tulingxueyuan.mall.modules.pms.controller;


import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryWithAttrDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;
    //productAttribute/category/list?pageNum=1&pageSize=5
    //get
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProductAttributeCategory>> list(Long pageNum,Long pageSize){
       return pmsProductAttributeCategoryService.getList(pageNum, pageSize);
    }
    //productAttribute/category/delete/17
    //delete
    @DeleteMapping("/delete/{id}")
    public CommonResult<String> delete(@PathVariable Long id){
        return pmsProductAttributeCategoryService.delete(id);
    }
    //productAttribute/category/create
    //post
    @PostMapping("/create")
    public CommonResult<String> create(PmsProductAttributeCategory pmsProductAttributeCategory){
        return pmsProductAttributeCategoryService.create(pmsProductAttributeCategory);
    }
    //productAttribute/category/update/21
    //post
    @PostMapping("/update/{id}")
    public CommonResult<String> update(PmsProductAttributeCategory pmsProductAttributeCategory){
        return pmsProductAttributeCategoryService.edit(pmsProductAttributeCategory);
    }
    //productAttribute/category/list/withAttr
    //get
    @GetMapping("/list/withAttr")
    public CommonResult<List<PmsProductCategoryWithAttrDTO>> getListWithAttr(){
        return pmsProductAttributeCategoryService.getListWithAttr();
    }



}

