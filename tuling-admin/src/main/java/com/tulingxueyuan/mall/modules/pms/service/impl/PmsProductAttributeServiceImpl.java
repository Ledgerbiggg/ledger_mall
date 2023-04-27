package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {


    @Override
    public CommonResult<CommonPage<PmsProductAttribute>> getListById(Long pageNum, Long pageSize, Integer id, Long type) {
        //查询属性的页面
        Page<PmsProductAttribute> pmsProductAttributePage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<PmsProductAttribute> pmsProductAttributeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductAttributeLambdaQueryWrapper.eq(PmsProductAttribute::getProductAttributeCategoryId,id);
        pmsProductAttributeLambdaQueryWrapper.eq(PmsProductAttribute::getType, type);
        pmsProductAttributeLambdaQueryWrapper.orderByDesc(PmsProductAttribute::getSort);
        page(pmsProductAttributePage,pmsProductAttributeLambdaQueryWrapper);
        return CommonResult.success(CommonPage.restPage(pmsProductAttributePage));
    }

    @Override
    public CommonResult<String> create(PmsProductAttribute pmsProductAttribute) {
        //添加属性
        boolean saveOrUpdate = saveOrUpdate(pmsProductAttribute);
        return saveOrUpdate?CommonResult.success("添加成功"):CommonResult.failed("添加失败");
    }

    @Override
    public CommonResult<PmsProductAttribute> getAttrDetail(Long id) {
        //根据id查询属性详细数据
        PmsProductAttribute byId = getById(id);
        return CommonResult.success(byId);
    }

    @Override
    public CommonResult<String> updateInfo(PmsProductAttribute pmsProductAttribute) {
        //创建属性
        return create(pmsProductAttribute);
    }

    @Override
    public CommonResult<String> delete(List<Long> ids) {
        //删除属性集合
        boolean removeByIds = removeByIds(ids);
        return removeByIds?CommonResult.success("删除成功"):CommonResult.failed("删除失败");
    }
}
