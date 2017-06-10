package com.weinxin.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.weinxin.model02.AccessToken;
import com.weinxin.model02.TextMessage;
import com.weinxin.util.WeixinUtil;

/**
 * ��ʱ��ȡ΢��access_token���߳�
 *��Application��ע��@EnableScheduling���ڳ�������ʱ�Ϳ�����ʱ����
 * ÿ7200��ִ��һ��
 */
@Component
public class AccessTokenThread {
    private static Logger log = LoggerFactory.getLogger(AccessTokenThread.class);

    // �������û�Ψһƾ֤
    public static String appid = "wx316556c2a87f9880";

    // �������û�Ψһƾ֤��Կ
    public static String appsecret = "93ee0273c3a122a89fb57f91dcb5dffe";
    // �������û�Ψһƾ֤
    public static AccessToken accessToken = null;

    @Scheduled(fixedDelay = 2*3600*1000)
    //7200��ִ��һ��
    public void gettoken(){
        accessToken= WeixinUtil.getAccessToken(appid,appsecret);
        if(null!=accessToken){
            log.info("��ȡ�ɹ���accessToken:"+accessToken.getToken());
        }else {
            log.error("��ȡtokenʧ��");
        }
    }
}
