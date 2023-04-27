package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
public interface PmsProductAttributeService extends IService<PmsProductAttribute> {

    CommonResult<CommonPage<PmsProductAttribute>> getListById(Long pageNum, Long pageSize, Integer id, Long type);

    CommonResult<String> create(PmsProductAttribute pmsProductAttribute);

    CommonResult<PmsProductAttribute> getAttrDetail(Long id);

    CommonResult<String> updateInfo(PmsProductAttribute pmsProductAttribute);

    CommonResult<String> delete(List<Long> ids);
}
