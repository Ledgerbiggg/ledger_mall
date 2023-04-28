package com.tulingxueyuan.mall.modules.pms.model.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
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
@ApiModel(value = "商品分类带孩子", description = "用来存储查询商品时的商品筛选条件分类")
public class PmsProductCategoryWithChildrenDTO extends PmsProductCategory {
    List<PmsProductCategory> children;
}
