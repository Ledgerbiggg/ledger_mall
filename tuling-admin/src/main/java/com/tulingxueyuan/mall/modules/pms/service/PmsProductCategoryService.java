package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryWithAttrDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductWithAttrIdsDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
public interface PmsProductCategoryService extends IService<PmsProductCategory> {

    CommonResult<CommonPage<PmsProductCategory>> list(Long parentId, Integer pageNum, Integer pageSize);

    CommonResult<String> navStatus(Long[] ids, Long navStatus);

    CommonResult<String> showStatus(Long[] ids, Long showStatus);

    CommonResult<String> delete(Long id);

    CommonResult<String> create(PmsProductWithAttrIdsDTO pmsProductCategoryWithAttrDTO);

    CommonResult<String> edit(PmsProductWithAttrIdsDTO pmsProductWithAttrIdsDTO);

    CommonResult<List<PmsProductAttrInfoDTO>> getAttrInfo(Long id);
}
