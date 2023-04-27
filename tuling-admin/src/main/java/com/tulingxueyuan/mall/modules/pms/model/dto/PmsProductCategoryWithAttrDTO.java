package com.tulingxueyuan.mall.modules.pms.model.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
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
@ApiModel(value = "属性类别属性传输对象",description = "用来存储属性的分类和属性集合")
public class PmsProductCategoryWithAttrDTO extends PmsProductAttributeCategory {
    private List<PmsProductAttribute>  productAttributeList;
}
