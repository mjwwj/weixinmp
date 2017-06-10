package com.weinxin.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weinxin.model.menu.Menu;
import com.weinxin.service.menu.MenuService;
import com.weinxin.thread.AccessTokenThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * �Զ��ĺŵĲ˵��Ĳ���
 *
 */
@RestController
@RequestMapping("/menu")

public class MenuController {
    @Autowired
    private MenuService menuService;


    private static Logger log = LoggerFactory.getLogger(MenuController.class);

    //��ѯȫ���˵�
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String getMenu() {
        // ���ýӿڻ�ȡaccess_token
        String at = AccessTokenThread.accessToken.getToken();
        JSONObject jsonObject =null;
        if (at != null) {
            // ���ýӿڲ�ѯ�˵�
            jsonObject = menuService.getMenu(at);
            // �жϲ˵��������
            return String.valueOf(jsonObject);
        }
        log.info("tokenΪ"+at);
        return "������";
    }

    //�����˵�
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public int createMenu() {
        // ���ýӿڻ�ȡaccess_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {

            // ���ýӿڴ����˵�
            result = menuService.createMenu(getFirstMenu(),at);
            // �жϲ˵��������
            if (0 == result) {
                log.info("�˵������ɹ���");
            } else {
                log.info("�˵�����ʧ�ܣ������룺" + result);
            }
        }
        return result ;
    }

    //ɾ���˵�
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public int deleteMenu() {
        // ���ýӿڻ�ȡaccess_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {
            // ɾ���˵�
            result = menuService.deleteMenu(at);
            // �жϲ˵�ɾ�����
            if (0 == result) {
                log.info("�˵�ɾ���ɹ���");
            } else {
                log.info("�˵�ɾ��ʧ�ܣ������룺" + result);
            }
        }
        return  result;
    }




        /**
         * ��װ�˵�����
         */
    public static Map<String, Object> getFirstMenu(){
        //��һ���˵�
        Menu menu1=new Menu();
        menu1.setId("1");
        menu1.setName("��һ��");
        menu1.setType("click");
        menu1.setKey("1");

        Menu menu11=new Menu();
        menu11.setId("11");
        menu11.setName("��һ���ĵ�һ��v3");
        menu11.setType("click");
        menu11.setKey("11");

        Menu menu12=new Menu();
        menu12.setId("12");
        menu12.setName("��һ���ĵڶ���");
        menu12.setType("click");
        menu12.setKey("12");

        //�ڶ���
        Menu menu2=new Menu();
        menu2.setId("2");
        menu2.setName("�ڶ���");
        menu2.setType("click");
        menu2.setKey("2");

        Menu menu21=new Menu();
        menu21.setId("21");
        menu21.setName("�ڶ����ĵ�һ��");
        menu21.setType("click");
        menu21.setKey("21");



        Menu menu3=new Menu();
        menu3.setId("3");
        menu3.setName("������");
        menu3.setType("view");
        menu3.setUrl("http://www.baidu.com");

        //����һ�������
        Map<String, Object> wechatMenuMap = new HashMap<String, Object>();

        //��װbutton��List
        List<Map<String, Object>> wechatMenuMapList = new ArrayList<Map<String, Object>>();

        //��װ��һ��
        Map<String, Object> menuMap1 = new HashMap<String, Object>();
        Map<String, Object> menuMap11 = new HashMap<String, Object>();
        Map<String, Object> menuMap12 = new HashMap<String, Object>();
        List<Map<String, Object>> subMenuMapList1 = new ArrayList<Map<String, Object>>();


        //��һ����һ��
        menuMap11.put("name",menu11.getName());
        menuMap11.put("type",menu11.getType());
        menuMap11.put("key",menu11.getKey());
        subMenuMapList1.add(menuMap11);

        //�ڶ����ڶ���
        menuMap12.put("name",menu12.getName());
        menuMap12.put("type",menu12.getType());
        menuMap12.put("key",menu12.getKey());
        subMenuMapList1.add(menuMap12);

        menuMap1.put("name",menu1.getName());
        menuMap1.put("sub_button",subMenuMapList1);

        //��װ�ڶ���
        Map<String, Object> menuMap2 = new HashMap<String, Object>();
        Map<String, Object> menuMap21 = new HashMap<String, Object>();
        List<Map<String, Object>> subMenuMapList2 = new ArrayList<Map<String, Object>>();

        //�ڶ�����һ��
        menuMap21.put("name",menu21.getName());
        menuMap21.put("type",menu21.getType());
        menuMap21.put("key",menu21.getKey());
        subMenuMapList2.add(menuMap21);

        menuMap2.put("name",menu2.getName());
        menuMap2.put("sub_button",subMenuMapList2);

        //��װ������
        Map<String, Object> menuMap3 = new HashMap<String, Object>();
        List<Map<String, Object>> subMenuMapList3 = new ArrayList<Map<String, Object>>();

        menuMap3.put("name",menu3.getName());
        menuMap3.put("type",menu3.getType());
        menuMap3.put("url",menu3.getUrl());
        menuMap3.put("sub_button",subMenuMapList3);


        wechatMenuMapList.add(menuMap1);
        wechatMenuMapList.add(menuMap2);
        wechatMenuMapList.add(menuMap3);
        wechatMenuMap.put("button",wechatMenuMapList);
        return  wechatMenuMap;
    }
}
