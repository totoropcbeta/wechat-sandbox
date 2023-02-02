package com.wechat.template.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 模板消息内容值
 */
@Getter
@Setter
public class WechatTemplateVo implements Serializable {

    /**
     * 值
     */
    private String value;

    /**
     * 颜色
     */
    private String color;

    public WechatTemplateVo(String value, String color) {
        this.value = value;
        this.color = color;
    }

}
