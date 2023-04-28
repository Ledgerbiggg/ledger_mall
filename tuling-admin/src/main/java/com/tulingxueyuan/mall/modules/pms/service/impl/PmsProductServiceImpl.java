package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductMapper;
import com.tulingxueyuan.mall.modules.pms.model.*;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductInfoWhitListAndCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductInfoWithListDTO;
import com.tulingxueyuan.mall.modules.pms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2023-04-22
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

    @Autowired
    private PmsProductService pmsProductService;

    @Autowired
    private PmsSkuStockService pmsSkuStockService;

    @Autowired
    private PmsProductAttributeValueService pmsProductAttributeValueService;

    @Autowired
    private PmsMemberPriceService pmsMemberPriceService;

    @Autowired
    private PmsProductFullReductionService pmsProductFullReductionService;

    @Autowired
    private PmsProductLadderService pmsProductLadderService;

    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;
    @Override
    public CommonResult<CommonPage<PmsProduct>> getProductList(String keyword,
                                                               Long pageNum,
                                                               Long pageSize,
                                                               Integer publishStatus,
                                                               Integer verifyStatus,
                                                               String productSn,
                                                               Integer productCategoryId,
                                                               Integer brandId) {
        //查询商品的分页信息
        LambdaQueryWrapper<PmsProduct> pmsProductLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductLambdaQueryWrapper.like(StrUtil.isNotBlank(keyword),PmsProduct::getKeywords,keyword);
        pmsProductLambdaQueryWrapper.eq(publishStatus!=null,PmsProduct::getPublishStatus,publishStatus);
        pmsProductLambdaQueryWrapper.eq(verifyStatus!=null,PmsProduct::getDeleteStatus,0);
        pmsProductLambdaQueryWrapper.eq(productCategoryId!=null,PmsProduct::getProductCategoryId,productCategoryId);
        pmsProductLambdaQueryWrapper.eq(brandId!=null,PmsProduct::getBrandId,brandId);
        pmsProductLambdaQueryWrapper.like(StrUtil.isNotBlank(productSn),PmsProduct::getProductSn,productSn);
        pmsProductLambdaQueryWrapper.eq(PmsProduct::getVerifyStatus,0);
        pmsProductLambdaQueryWrapper.orderByDesc(PmsProduct::getSort);
        Page<PmsProduct> pmsProductPage = new Page<>(pageNum, pageSize);
        page(pmsProductPage, pmsProductLambdaQueryWrapper);
        return CommonResult.success(CommonPage.restPage(pmsProductPage));
    }

    @Override
    public CommonResult<String> publishStatus(List<Long> ids, Integer publishStatus) {
        //更新发布状态
        LambdaUpdateWrapper<PmsProduct> pmsProductLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        pmsProductLambdaUpdateWrapper.in(PmsProduct::getId,ids);
        pmsProductLambdaUpdateWrapper.set(PmsProduct::getPublishStatus,publishStatus);
        boolean update = update(pmsProductLambdaUpdateWrapper);
        return update?CommonResult.success("更新成功"):CommonResult.failed("更新失败");
    }

    @Override
    public CommonResult<String> newStatus(List<Long> ids, Integer newStatus) {
        //跟心新品状态
        LambdaUpdateWrapper<PmsProduct> pmsProductLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        pmsProductLambdaUpdateWrapper.in(PmsProduct::getId,ids);
        pmsProductLambdaUpdateWrapper.set(PmsProduct::getNewStatus,newStatus);
        boolean update = update(pmsProductLambdaUpdateWrapper);
        return update?CommonResult.success("更新成功"):CommonResult.failed("更新失败");
    }

    @Override
    public CommonResult<String> recommendStatus(List<Long> ids, Integer recommendStatus) {
        //跟心推荐状态
        LambdaUpdateWrapper<PmsProduct> pmsProductLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        pmsProductLambdaUpdateWrapper.in(PmsProduct::getId,ids);
        pmsProductLambdaUpdateWrapper.set(PmsProduct::getRecommandStatus,recommendStatus);
        boolean update = update(pmsProductLambdaUpdateWrapper);
        return update?CommonResult.success("更新成功"):CommonResult.failed("更新失败");
    }

    @Override
    @Transactional
    public CommonResult<String> create(PmsProductInfoWithListDTO pmsProductInfoWithListDTO) {
        //添加商品的详细信息
        PmsProduct pmsProduct = new PmsProduct();
        BeanUtil.copyProperties(pmsProductInfoWithListDTO,pmsProduct);
        boolean save = pmsProductService.saveOrUpdate(pmsProduct);
        Long id = pmsProduct.getId();
        if (save) {
            saveManyList(pmsProductInfoWithListDTO.getProductLadderList(),id,pmsProductLadderService);
            saveManyList(pmsProductInfoWithListDTO.getProductAttributeValueList(),id,pmsProductAttributeValueService);
            saveManyList(pmsProductInfoWithListDTO.getSkuStockList(),id,pmsSkuStockService);
            saveManyList(pmsProductInfoWithListDTO.getMemberPriceList(),id,pmsMemberPriceService);
            saveManyList(pmsProductInfoWithListDTO.getProductFullReductionList(),id,pmsProductFullReductionService);
            return CommonResult.success("保存成功");
        }
        return CommonResult.failed("保存失败");
    }

    @Override
    public CommonResult<PmsProductInfoWhitListAndCategoryDTO> getUpdateInfo(Long id) {
        //查询商品的信息
        PmsProduct product = pmsProductService.getById(id);
        PmsProductInfoWithListDTO pmsProductInfoWithListDTO = new PmsProductInfoWithListDTO();
        BeanUtil.copyProperties(product,pmsProductInfoWithListDTO);
        //查询三种优惠，sku，spu
        LambdaQueryWrapper<PmsProductLadder> pmsProductLadderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductLadderLambdaQueryWrapper.eq(PmsProductLadder::getProductId,id);
        pmsProductInfoWithListDTO.setProductLadderList(pmsProductLadderService.list(pmsProductLadderLambdaQueryWrapper));
        LambdaQueryWrapper<PmsProductAttributeValue> pmsProductAttributeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductAttributeValueLambdaQueryWrapper.eq(PmsProductAttributeValue::getProductId,id);
        pmsProductInfoWithListDTO.setProductAttributeValueList(pmsProductAttributeValueService.list(pmsProductAttributeValueLambdaQueryWrapper));
        LambdaQueryWrapper<PmsSkuStock> pmsSkuStockLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsSkuStockLambdaQueryWrapper.eq(PmsSkuStock::getProductId,id);
        pmsProductInfoWithListDTO.setSkuStockList(pmsSkuStockService.list(pmsSkuStockLambdaQueryWrapper));
        LambdaQueryWrapper<PmsMemberPrice> pmsMemberPriceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsMemberPriceLambdaQueryWrapper.eq(PmsMemberPrice::getProductId,id);
        pmsProductInfoWithListDTO.setMemberPriceList(pmsMemberPriceService.list(pmsMemberPriceLambdaQueryWrapper));
        LambdaQueryWrapper<PmsProductFullReduction> pmsProductFullReductionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductFullReductionLambdaQueryWrapper.eq(PmsProductFullReduction::getProductId,id);
        pmsProductInfoWithListDTO.setProductFullReductionList(pmsProductFullReductionService.list(pmsProductFullReductionLambdaQueryWrapper));
        PmsProductInfoWhitListAndCategoryDTO pmsProductInfoWhitListAndCategoryDTO = new PmsProductInfoWhitListAndCategoryDTO();
        Long productCategoryId = pmsProductInfoWithListDTO.getProductCategoryId();
        Long parentId = pmsProductCategoryService.getById(productCategoryId).getParentId();
        BeanUtil.copyProperties(pmsProductInfoWithListDTO,pmsProductInfoWhitListAndCategoryDTO);
        pmsProductInfoWhitListAndCategoryDTO.setCateParentId(parentId);
        return CommonResult.success(pmsProductInfoWhitListAndCategoryDTO);
    }

    @Override
    public CommonResult<String> delete(List<Long> ids, Long deleteStatus) {
        boolean removeByIds = removeByIds(ids);
        return removeByIds?CommonResult.success("删除成功"):CommonResult.failed("删除失败");
    }

    @Override
    public CommonResult<String> edit(PmsProductInfoWithListDTO pmsProductInfoWithListDTO) {
        return create(pmsProductInfoWithListDTO);
    }

    private void saveManyList(List list, Long productId, IService service){
        if (list.isEmpty()) {
            return;
        }
        for (Object obj : list) {
            try {
                Method setProductId = obj.getClass().getMethod("setProductId",Long.class);
                setProductId.invoke(obj,productId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        service.saveOrUpdateBatch(list);
    }
}
