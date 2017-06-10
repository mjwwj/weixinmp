package com.weinxin.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.weinxin.util.MessageUtil;

@Component
public class CoreServiceImpl implements CoreService {
	
	private static Logger log = LoggerFactory.getLogger(CoreServiceImpl.class);
	
	@Override
	public String toString() {
		/**
	     * 处理微信发来的请求（包括事件的推送）
	     *
	     * @param request
	     * @return
	     */
		//暂时对消息不作处理
		return "";
	}

	@Override
	public String processRequest(HttpServletRequest request) {
		String message = null;
        // xml请求解析
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			// 发送方帐号（open_id）
			String fromUserName =map.get("FromUserName");
			// 公众帐号
			String toUserName = map.get("ToUserName");
			// 消息类型
			String msgType = map.get("MsgType");
			
			//点击菜单id
            String EventKey =map.get("EventKey");
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				String content = map.get("Content");
				if(MessageUtil.isQqFace(content)){
					message = MessageUtil.initQqFace(toUserName, fromUserName, content);
				}else{
					//回复固定消息
					switch(content){
					case "1":{
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
						break;
					}
					case "2":{
						message = MessageUtil.initNewsMessage(toUserName, fromUserName);
						break;
					}
					default:{
						message = MessageUtil.initText(toUserName, fromUserName, "请您按提示回复相应的数字！");
					}
					}
				}
		 // 事件推送
		 }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
			    // 事件类型
				String eventype = map.get("Event");
				// 订阅
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventype)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				// 自定义菜单点击事件
				else if(MessageUtil.MESSAGE_CLICK.equals(eventype)){
					switch (EventKey){
                    case "11":{
                        message = MessageUtil.initText(toUserName, fromUserName, "这是第一栏第一个");
                        break;
                    }
                    case "12":{
                    	message = MessageUtil.initText(toUserName, fromUserName, "这是第一栏第一个");
                        break;
                    }
                    case "21":{
                    	message = MessageUtil.initText(toUserName, fromUserName, "这是第二栏第一个");
                        break;
                    }

                    default:{
                        log.error("开发者反馈：EventKey值没找到，它是:"+EventKey);
                        message = MessageUtil.initText(toUserName, fromUserName, "很抱歉，此按键功能正在升级无法使用");
                    }
					}
                }
				//点击菜单转网页
				else if(MessageUtil.MESSAGE_VIEW.equals(eventype)){
                    message = MessageUtil.initText(toUserName, fromUserName, "<a href=\"http://www.baidu.com\">百度主页</a>");
				}
			}
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return message;
	}



}
