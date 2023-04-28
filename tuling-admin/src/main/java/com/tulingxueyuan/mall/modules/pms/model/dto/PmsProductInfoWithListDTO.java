package com.tulingxueyuan.mall.modules.pms.model.dto;

import com.tulingxueyuan.mall.modules.pms.model.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品信息带优惠列表数据传输对象",description = "用来增加商品的信息和优惠列表的")
public class PmsProductInfoWithListDTO extends PmsProduct {
    List<PmsProductAttributeValue> productAttributeValueList;
    List<PmsSkuStock> skuStockList;
    List<PmsMemberPrice> memberPriceList;
    List<PmsProductFullReduction> productFullReductionList;
    List<PmsProductLadder> productLadderList;
}
