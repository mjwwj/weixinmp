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
	     * ����΢�ŷ��������󣨰����¼������ͣ�
	     *
	     * @param request
	     * @return
	     */
		//��ʱ����Ϣ��������
		return "";
	}

	@Override
	public String processRequest(HttpServletRequest request) {
		String message = null;
        // xml�������
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			// ���ͷ��ʺţ�open_id��
			String fromUserName =map.get("FromUserName");
			// �����ʺ�
			String toUserName = map.get("ToUserName");
			// ��Ϣ����
			String msgType = map.get("MsgType");
			
			//����˵�id
            String EventKey =map.get("EventKey");
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				String content = map.get("Content");
				if(MessageUtil.isQqFace(content)){
					message = MessageUtil.initQqFace(toUserName, fromUserName, content);
				}else{
					//�ظ��̶���Ϣ
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
						message = MessageUtil.initText(toUserName, fromUserName, "��������ʾ�ظ���Ӧ�����֣�");
					}
					}
				}
		 // �¼�����
		 }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
			    // �¼�����
				String eventype = map.get("Event");
				// ����
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventype)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				// �Զ���˵�����¼�
				else if(MessageUtil.MESSAGE_CLICK.equals(eventype)){
					switch (EventKey){
                    case "11":{
                        message = MessageUtil.initText(toUserName, fromUserName, "���ǵ�һ����һ��");
                        break;
                    }
                    case "12":{
                    	message = MessageUtil.initText(toUserName, fromUserName, "���ǵ�һ����һ��");
                        break;
                    }
                    case "21":{
                    	message = MessageUtil.initText(toUserName, fromUserName, "���ǵڶ�����һ��");
                        break;
                    }

                    default:{
                        log.error("�����߷�����EventKeyֵû�ҵ�������:"+EventKey);
                        message = MessageUtil.initText(toUserName, fromUserName, "�ܱ�Ǹ���˰����������������޷�ʹ��");
                    }
					}
                }
				//����˵�ת��ҳ
				else if(MessageUtil.MESSAGE_VIEW.equals(eventype)){
                    message = MessageUtil.initText(toUserName, fromUserName, "<a href=\"http://www.baidu.com\">�ٶ���ҳ</a>");
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
