package com.wechat.template.domain.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 发送消息实体
 */
@Data
public class WechatSendMsgVo implements Serializable {


    /**
     * 接收人id
     */
    private String touser;

    /**
     * 模板id
     */
    private String template_id;

    /**
     * 模板数据
     */
    private Map<String, WechatTemplateVo> data;

    /**
     * 语言类型 默认中文
     */
    private String lang = "zh_CN";
}
