package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryWithAttrDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@Service
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory> implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;



    @Override
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(Long pageNum, Long pageSize) {
        //查询属性分类的页面
        Page<PmsProductAttributeCategory> pmsProductAttributeCategoryPage = new Page<>(pageNum,pageSize);
        page(pmsProductAttributeCategoryPage);
        return CommonResult.success(CommonPage.restPage(pmsProductAttributeCategoryPage));
    }

    @Override
    public CommonResult<String> delete(Long id) {
        //删除属性分类
        boolean remove = removeById(id);
        //删除属性分类中存在的属性
        LambdaQueryWrapper<PmsProductAttribute> pmsProductAttributeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductAttributeLambdaQueryWrapper.eq(PmsProductAttribute::getProductAttributeCategoryId,id);
        pmsProductAttributeService.remove(pmsProductAttributeLambdaQueryWrapper);
        return remove?CommonResult.success("删除成功"):CommonResult.failed("删除失败");
    }

    @Override
    public CommonResult<String> create(PmsProductAttributeCategory pmsProductAttributeCategory) {
        //创建新的属性类型
        boolean save = saveOrUpdate(pmsProductAttributeCategory);
        return save?CommonResult.success("保存成功"):CommonResult.failed("保存失败");
    }

    @Override
    public CommonResult<String> edit(PmsProductAttributeCategory pmsProductAttributeCategory) {
        //保存新属性类型
        return create(pmsProductAttributeCategory);
    }

    @Override
    public CommonResult<List<PmsProductCategoryWithAttrDTO>> getListWithAttr() {
        //获取属性列分类和属性值的集合
        List<PmsProductAttributeCategory> list = list();
        ArrayList<PmsProductCategoryWithAttrDTO> pmsProductCategoryWithAttrDTOS = new ArrayList<>();
        for (PmsProductAttributeCategory pmsProductAttributeCategory : list) {
            PmsProductCategoryWithAttrDTO pmsProductCategoryWithAttrDTO = new PmsProductCategoryWithAttrDTO();
            BeanUtil.copyProperties(pmsProductAttributeCategory, pmsProductCategoryWithAttrDTO);
            Long id = pmsProductAttributeCategory.getId();
            LambdaQueryWrapper<PmsProductAttribute> pmsProductAttributeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            pmsProductAttributeLambdaQueryWrapper.eq(PmsProductAttribute::getProductAttributeCategoryId,id);
            List<PmsProductAttribute> pmsProductAttributes = pmsProductAttributeService.list(pmsProductAttributeLambdaQueryWrapper);
            pmsProductCategoryWithAttrDTO.setProductAttributeList(pmsProductAttributes);
            pmsProductCategoryWithAttrDTOS.add(pmsProductCategoryWithAttrDTO);
        }
        return CommonResult.success(pmsProductCategoryWithAttrDTOS);
    }


}
