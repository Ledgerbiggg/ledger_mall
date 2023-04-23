package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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

    @Override
    public CommonResult<CommonPage<PmsProductCategory>> list(Long parentId, Integer pageNum, Integer pageSize) {
        Page<PmsProductCategory> pmsProductCategoryPage = new Page<>();
        LambdaQueryWrapper<PmsProductCategory> pmsProductCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据父id查询数据
        pmsProductCategoryLambdaQueryWrapper.eq(parentId!=null,PmsProductCategory::getParentId,parentId);
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
        boolean remove = remove(lambdaQueryWrapper);
        return check&&remove?CommonResult.success("删除成功"):CommonResult.failed("删除失败");
    }

    @Override
    public CommonResult<String> create(PmsProductCategory pmsProductCategory) {
        boolean save = save(pmsProductCategory);
        return save?CommonResult.success("保存成功"):CommonResult.failed("保存失败");
    }

    @Override
    public CommonResult<String> edit(PmsProductCategory pmsProductCategory) {
        boolean update = updateById(pmsProductCategory);
        return update?CommonResult.success("修改成功"):CommonResult.failed("修改失败");
    }
}
