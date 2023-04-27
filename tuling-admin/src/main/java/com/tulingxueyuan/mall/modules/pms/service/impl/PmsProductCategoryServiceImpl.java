package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategoryAttributeRelation;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryWithAttrDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductWithAttrIdsDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryAttributeRelationService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {

    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;

    @Autowired
    private PmsProductCategoryAttributeRelationService pmsProductCategoryAttributeRelationService;


    @Override
    public CommonResult<CommonPage<PmsProductCategory>> list(Long parentId, Integer pageNum, Integer pageSize) {
        Page<PmsProductCategory> pmsProductCategoryPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<PmsProductCategory> pmsProductCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据父id查询数据
        pmsProductCategoryLambdaQueryWrapper.eq(parentId!=null,PmsProductCategory::getParentId,parentId);
        pmsProductCategoryLambdaQueryWrapper.orderByDesc(PmsProductCategory::getSort);
        page(pmsProductCategoryPage,pmsProductCategoryLambdaQueryWrapper);
        //返回标准页码
        return CommonResult.success(CommonPage.restPage(pmsProductCategoryPage));
    }

    @Override
    public CommonResult<String> navStatus(Long[] ids, Long navStatus) {
        LambdaUpdateWrapper<PmsProductCategory> pmsProductCategoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        //根据ids批量设置设置状态
        pmsProductCategoryLambdaUpdateWrapper.set(ids!=null&&navStatus!=null,PmsProductCategory::getShowStatus,navStatus);
        pmsProductCategoryLambdaUpdateWrapper.in(ids!=null&&navStatus!=null,PmsProductCategory::getId,ids);
        update(pmsProductCategoryLambdaUpdateWrapper);
        return CommonResult.success("编辑成功");
    }

    @Override
    public CommonResult<String> showStatus(Long[] ids, Long showStatus) {
        LambdaUpdateWrapper<PmsProductCategory> pmsProductCategoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        //根据ids批量设置设置可见
        pmsProductCategoryLambdaUpdateWrapper.set(ids!=null&&showStatus!=null,PmsProductCategory::getShowStatus,showStatus);
        pmsProductCategoryLambdaUpdateWrapper.in(ids!=null&&showStatus!=null,PmsProductCategory::getId,ids);
        update(pmsProductCategoryLambdaUpdateWrapper);
        return CommonResult.success("编辑成功");
    }

    @Override
    @Transactional
    public CommonResult<String> delete(Long id) {
        //删除品类和品类下面的所有实物
        boolean check = removeById(id);
        LambdaQueryWrapper<PmsProductCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id!=null,PmsProductCategory::getParentId,id);
        remove(lambdaQueryWrapper);
        //将商品类型和关系表清除干净
        LambdaQueryWrapper<PmsProductCategoryAttributeRelation> pmsProductCategoryAttributeRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductCategoryAttributeRelationLambdaQueryWrapper.in(PmsProductCategoryAttributeRelation::getProductCategoryId,id);
        pmsProductCategoryAttributeRelationService.remove(pmsProductCategoryAttributeRelationLambdaQueryWrapper);
        return check?CommonResult.success("删除成功"):CommonResult.failed("删除失败");
    }

    @Override
    @Transactional
    public CommonResult<String> create(PmsProductWithAttrIdsDTO pmsProductCategoryWithAttrDTO) {
        //保存商品分类数据
        PmsProductCategory pmsProductCategory=new PmsProductCategory();
        BeanUtil.copyProperties(pmsProductCategoryWithAttrDTO,pmsProductCategory);
        pmsProductCategory.setProductCount(0);
        pmsProductCategory.setLevel(pmsProductCategory.getParentId().intValue());
        boolean save = saveOrUpdate(pmsProductCategory);
        //获取属性的集合
        List<Long> productAttributeList =
                pmsProductCategoryWithAttrDTO.getProductAttributeIdList();
        ArrayList<PmsProductCategoryAttributeRelation> pmsProductCategoryAttributeRelations = new ArrayList<>();
        Long pmsProductCategoryId = pmsProductCategory.getId();
        //保存商品种类和关系的集合
        for (Long pmsProductAttribute : productAttributeList) {
            PmsProductCategoryAttributeRelation pmsProductCategoryAttributeRelation = new PmsProductCategoryAttributeRelation();
            pmsProductCategoryAttributeRelation.setProductCategoryId(pmsProductCategoryId);
            pmsProductCategoryAttributeRelation.setProductAttributeId(pmsProductAttribute);
            pmsProductCategoryAttributeRelations.add(pmsProductCategoryAttributeRelation);
        }
        pmsProductCategoryAttributeRelationService.saveBatch(pmsProductCategoryAttributeRelations);
        return save?CommonResult.success("保存成功"):CommonResult.failed("保存失败");
    }

    @Override
    @Transactional
    public CommonResult<String> edit(PmsProductWithAttrIdsDTO pmsProductWithAttrIdsDTO) {
        //先将商品分类属性移出
        Long id = pmsProductWithAttrIdsDTO.getId();
        //将商品和属性的关系移出
        LambdaQueryWrapper<PmsProductCategoryAttributeRelation> pmsProductCategoryAttributeRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductCategoryAttributeRelationLambdaQueryWrapper.in(PmsProductCategoryAttributeRelation::getProductCategoryId,id);
        pmsProductCategoryAttributeRelationService.remove(pmsProductCategoryAttributeRelationLambdaQueryWrapper);
        return create(pmsProductWithAttrIdsDTO);
    }

    /**
     * 在编辑界面需要调用的方法，用来获取该条商品类型的属性级联下拉列表
     * @param id 这个是商品分类的id
     * @return
     */
    @Override
    public CommonResult<List<PmsProductAttrInfoDTO>> getAttrInfo(Long id) {
        //用于获取编辑状态的级联属性
        LambdaQueryWrapper<PmsProductCategoryAttributeRelation> pmsProductCategoryAttributeRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductCategoryAttributeRelationLambdaQueryWrapper.eq(PmsProductCategoryAttributeRelation::getProductCategoryId,id);
        List<PmsProductCategoryAttributeRelation> list = pmsProductCategoryAttributeRelationService.list(pmsProductCategoryAttributeRelationLambdaQueryWrapper);
        //批量获取用于修改式，所需要的商品的属性id和属性分类id
        List<PmsProductAttrInfoDTO> collect = list.stream().map(pmsProductCategoryAttributeRelation -> {
            Long productAttributeId = pmsProductCategoryAttributeRelation.getProductAttributeId();
            PmsProductAttribute pmsProductAttributeServiceById = pmsProductAttributeService.getById(productAttributeId);
            Long productAttributeCategoryId = pmsProductAttributeServiceById.getProductAttributeCategoryId();
            PmsProductAttrInfoDTO pmsProductAttrInfoDTO = new PmsProductAttrInfoDTO();
            pmsProductAttrInfoDTO.setAttributeId(productAttributeId);
            pmsProductAttrInfoDTO.setAttributeCategoryId(productAttributeCategoryId);
            return pmsProductAttrInfoDTO;
        }).collect(Collectors.toList());
        return CommonResult.success(collect);
    }
}
