package com.wechat.template.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 天行api配置
 */
@Component
@Data
public class TianApiConfig {

    @Value("${tianapi.appkey}")
    private String appKey;

    @Value("${tianapi.area}")
    private String area;
}
