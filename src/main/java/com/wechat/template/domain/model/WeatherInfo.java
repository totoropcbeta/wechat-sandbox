package com.wechat.template.domain.model;

import lombok.Data;

/**
 * 天气api返回信息
 */
@Data
public class WeatherInfo {

    /**
     * 地区
     */
    private String area;

    /**
     * 日期
     */
    private String date;

    /**
     * 星期
     */
    private String week;

    /**
     * 早晚天气变化
     */
    private String weather;

    /**
     * 	天气图标
     */
    private String weatherimg;

    /**
     * 	实时天气
     */
    private String real;

    /**
     * 最低温
     */
    private String lowest;

    /**
     * 	最高温
     */
    private String highest;

    /**
     * 	风向
     */
    private String wind;

    /**
     * 风向360°角度
     */
    private String winddeg;

    /**
     * 	风速，km/h
     */
    private String windspeed;

    /**
     * 风力
     */
    private String windsc;

    /**
     * 日出时间
     */
    private String sunrise;

    /**
     * 日落时间
     */
    private String sunset;

    /**
     * 月升时间
     */
    private String moonrise;

    /**
     * 月落时间
     */
    private String moondown;

    /**
     * 降雨量
     */
    private String pcpn;

    /**
     * 	降雨概率
     */
    private String pop;

    /**
     * 紫外线强度指数
     */
    private String uv_index;

    /**
     * 	能见度，单位：公里
     */
    private String vis;

    /**
     * 	相对湿度
     */
    private String humidity;

    /**
     * 	生活指数提示
     */
    private String tips;
}
