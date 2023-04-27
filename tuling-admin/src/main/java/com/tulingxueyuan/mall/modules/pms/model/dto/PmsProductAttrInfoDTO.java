package com.tulingxueyuan.mall.modules.pms.model.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ledger
 * @version 1.0
 **/
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value = "用于接收属性分类id和属性id的数据传输对象",description = "展示商品的归属的属性和属性的分类")
public class PmsProductAttrInfoDTO  {
    private Long attributeCategoryId;
    private Long attributeId;
}
