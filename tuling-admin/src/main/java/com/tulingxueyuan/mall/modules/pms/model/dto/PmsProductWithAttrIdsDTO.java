package com.tulingxueyuan.mall.modules.pms.model.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
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
@ApiModel(value = "属性类别属性id传输对象",description = "用来存储属性的分类和属性id集合")
public class PmsProductWithAttrIdsDTO extends PmsProductCategory {
    private List<Long> productAttributeIdList;
}
