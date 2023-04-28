package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsBrandMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;
import com.tulingxueyuan.mall.modules.pms.service.PmsBrandService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {

    @Override
    public CommonResult<CommonPage<PmsBrand>> getPage(String keyword, Long pageNum, Long pageSize) {
        //查询品牌页码
        Page<PmsBrand> pmsBrandPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PmsBrand> pmsBrandLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsBrandLambdaQueryWrapper.like(StrUtil.isNotBlank(keyword), PmsBrand::getName, keyword);
        page(pmsBrandPage, pmsBrandLambdaQueryWrapper);
        return CommonResult.success(CommonPage.restPage(pmsBrandPage));
    }

    @Override
    public CommonResult<String> updateFactoryStatus(PmsBrand pmsBrand) {
        return updateStatus(pmsBrand);
    }

    @Override
    public CommonResult<String> showStatus(PmsBrand pmsBrand) {
        return updateStatus(pmsBrand);
    }

    @Override
    public CommonResult<String> create(PmsBrand pmsBrand) {
        boolean saveOrUpdate = saveOrUpdate(pmsBrand);
        return saveOrUpdate? CommonResult.success("操作成功") : CommonResult.failed("操作失败");
    }

    CommonResult<String> updateStatus(PmsBrand pmsBrand) {
        boolean save = save(pmsBrand);
        return save ? CommonResult.success("操作成功") : CommonResult.failed("操作失败");
    }

    ;

}
