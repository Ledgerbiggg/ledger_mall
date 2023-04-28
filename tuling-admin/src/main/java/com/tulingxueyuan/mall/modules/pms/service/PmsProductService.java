package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductInfoWhitListAndCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductInfoWithListDTO;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
public interface PmsProductService extends IService<PmsProduct> {

    CommonResult<CommonPage<PmsProduct>> getProductList(String keyword, Long pageNum, Long pageSize, Integer publishStatus, Integer verifyStatus, String productSn, Integer productCategoryId, Integer brandId);

    CommonResult<String> publishStatus(List<Long> ids, Integer publishStatus);

    CommonResult<String> newStatus(List<Long> ids, Integer newStatus);

    CommonResult<String> recommendStatus(List<Long> ids, Integer recommendStatus);

    CommonResult<String> create(PmsProductInfoWithListDTO pmsProductInfoWithListDTO);

    CommonResult<PmsProductInfoWhitListAndCategoryDTO> getUpdateInfo(Long id);

    CommonResult<String> delete(List<Long> ids, Long deleteStatus);

    CommonResult<String> edit(PmsProductInfoWithListDTO pmsProductInfoWithListDTO);

}
