package com.wechat.template.service;

import cn.hutool.json.JSONObject;
import com.wechat.template.domain.model.WeatherInfo;
import com.wechat.template.domain.vo.WechatSendMsgVo;

import java.util.List;

public interface WeiXinService {

    //获取accessToken
    String getAccessToken(String appId, String appSecret);

    //获取用户信息
    List<String> getUserList(String accessToken);

    //发送模板消息
    JSONObject sendMsg(WechatSendMsgVo sendMsgVo,String token, String openId);

    //彩虹屁信息
    String getCaiHongPiInfo(String appKey);

    //早安
    String getZaoAnInfo(String appKey);

    //天气信息
    WeatherInfo getWeatherInfo(String appKey, String city);
}
