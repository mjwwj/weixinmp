package com.weinxin.controller;



import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weinxin.service.CoreService;
import com.weinxin.util.SignUtil;

@RestController
@RequestMapping(value="weixin")
public class CoreController {
	@Autowired
	private CoreService coreService;
	//������־
	private static Logger log = LoggerFactory.getLogger(CoreController.class);
	//��֤�Ƿ�����΢�ŷ���������Ϣ
	
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String checkSignature(@RequestParam(name = "signature" ,required = false) String signature  ,
	                             @RequestParam(name = "nonce",required = false) String  nonce ,
	                             @RequestParam(name = "timestamp",required = false) String  timestamp ,
	                             @RequestParam(name = "echostr",required = false) String  echostr){
	    // ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			log.info("����ɹ�");
	        return echostr;
	    }
		log.error("����ʧ��");
	    return "";
	    }
	
	// ���ú���ҵ���������Ϣ��������Ϣ��������Ϣ
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/xml; charset=UTF-8")
    public String post(HttpServletRequest req){
        String respMessage = coreService.processRequest(req);
        return respMessage;
    }

}
