package com.tulingxueyuan.mall.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
public class OssPolicyResult {
    @ApiModelProperty("访问身份验证中用到的用户标示")
    private String accessid;
    @ApiModelProperty("用户表单上传的策略")
    private String policy;
    @ApiModelProperty("对policy签名后的字符串")
    private String signature;
    @ApiModelProperty("上传文件夹的路径前缀")
    private String dir;
    @ApiModelProperty("ossd对外服务的访问域名")
    private String host;
    @ApiModelProperty("上传成功后的回调设置")
    private String callback;

}
