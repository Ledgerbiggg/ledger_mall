package com.tulingxueyuan.mall.modules.pms.controller;


import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;
import com.tulingxueyuan.mall.modules.pms.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService pmsBrandService;

    /*
    * brand/list?pageNum=1&pageSize=10
    * get
    * */

    @GetMapping("/list")
    public CommonResult<CommonPage<PmsBrand>> getList(String keyword,Long pageNum, Long pageSize){
       return pmsBrandService.getPage(keyword,pageNum,pageSize);
    }
    /*
    * update/factoryStatus
    * post
    * */

    @PostMapping("/update/factoryStatus")
    public CommonResult<String> updateFactoryStatus(PmsBrand pmsBrand){
        return pmsBrandService.updateFactoryStatus(pmsBrand);
    }

    /*vv
    * update/showStatus
    * post
    * */
    @PostMapping("/update/showStatus")
    public CommonResult<String> showStatus(PmsBrand pmsBrand){
        return pmsBrandService.showStatus(pmsBrand);
    }
    /*
    * brand/delete/59
    * */
    @GetMapping("/delete/{id}")
    public CommonResult<String> delete(@PathVariable Long id){
        return pmsBrandService.removeById(id)?CommonResult.success("删除成功"):CommonResult.failed("删除失败");
    }
    /*
    * brand/create
    * */
    @PostMapping("/create")
    public CommonResult<String> create(@RequestBody PmsBrand pmsBrand){
        return pmsBrandService.create(pmsBrand);
    }
    /*
    * brand/1
    * */
    @GetMapping("/{id}")
    public CommonResult<PmsBrand> getById(@PathVariable Long id){
        return CommonResult.success(pmsBrandService.getById(id));
    }











}

