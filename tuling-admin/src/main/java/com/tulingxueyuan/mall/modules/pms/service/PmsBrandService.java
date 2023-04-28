package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
public interface PmsBrandService extends IService<PmsBrand> {

    CommonResult<CommonPage<PmsBrand>> getPage(String keyword, Long pageNum, Long pageSize);

    CommonResult<String> updateFactoryStatus(PmsBrand pmsBrand);

    CommonResult<String> showStatus(PmsBrand pmsBrand);

    CommonResult<String> create(PmsBrand pmsBrand);
}
