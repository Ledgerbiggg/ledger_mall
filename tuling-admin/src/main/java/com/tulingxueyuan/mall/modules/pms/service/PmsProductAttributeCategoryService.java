package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryWithAttrDTO;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
public interface PmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {

    CommonResult<CommonPage<PmsProductAttributeCategory>> getList(Long pageNum, Long pageSize);

    CommonResult<String> delete(Long id);

    CommonResult<String> create(PmsProductAttributeCategory pmsProductAttributeCategory);

    CommonResult<String> edit(PmsProductAttributeCategory pmsProductAttributeCategory);

    CommonResult<List<PmsProductCategoryWithAttrDTO>> getListWithAttr();

}
