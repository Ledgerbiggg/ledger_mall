package com.tulingxueyuan.mall.modules.pms.controller;


import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryWithAttrDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductWithAttrIdsDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/productCategory")
@Slf4j
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    /*
     *  url:'/productCategory/list/'+parentId,
     *  method:'get',
     *  params:params
     */
    @GetMapping("/list/{parentId}")
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return pmsProductCategoryService.list(parentId, pageNum, pageSize);
    }

    /*
     *  url:'/productCategory/update/showStatus',
     *  method:'post',
     *  data:data
     */
    @PostMapping("/update/navStatus")
    public CommonResult<String> navStatus(@RequestParam(value = "ids") Long[] ids, @RequestParam(value = "navStatus") Long navStatus) {
        return pmsProductCategoryService.navStatus(ids, navStatus);
    }
    /*
     *   url:'/productCategory/update/showStatus',
     *   method:'post',
     *   data:data
     *   // data.append('ids',ids);
     *   // data.append('showStatus',row.showStatus);
     * */

    @PostMapping("/update/showStatus")
    public CommonResult<String> showStatus(@RequestParam(value = "ids") Long[] ids, @RequestParam(value = "showStatus") Long showStatus) {
        return pmsProductCategoryService.showStatus(ids, showStatus);
    }

    /*
     *   url: '/productCategory/delete/' + id,
     *   method: 'post'
     * */
    @PostMapping("/delete/{id}")
    public CommonResult<String> delete(@PathVariable Long id) {
        return pmsProductCategoryService.delete(id);
    }
    /*
     *   url: '/productCategory/create',
     *   method: 'post',
     *   data: data
     * */

    @PostMapping("/create")
    public CommonResult<String> create(@RequestBody PmsProductWithAttrIdsDTO pmsProductCategoryWithAttrDTO) {
        return pmsProductCategoryService.create(pmsProductCategoryWithAttrDTO);
    }

    /*
     *   url: '/productCategory/' + id,
     *   method: 'get',
     * */
    @GetMapping("/{id}")
    public CommonResult<PmsProductCategory> getById(@PathVariable Long id) {
        return CommonResult.success(pmsProductCategoryService.getById(id));
    }

    /*
     *   url: '/productCategory/update/' + id,
     *   method: 'post',
     *   data: data
     * */

    @PostMapping("/update/{id}")
    public CommonResult<String> update(@RequestBody PmsProductWithAttrIdsDTO pmsProductWithAttrIdsDTO) {
        return pmsProductCategoryService.edit(pmsProductWithAttrIdsDTO);
    }



}
