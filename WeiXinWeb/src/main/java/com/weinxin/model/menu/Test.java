package com.weinxin.model.menu;

import org.springframework.beans.factory.annotation.Autowired;

import com.weinxin.controller.MenuController;
import com.weinxin.service.menu.MenuService;
import com.weinxin.service.menu.MenuSeviceImpl;
import com.weinxin.thread.AccessTokenThread;

public class Test {

	public static void main(String[] args) {
		 MenuService menuService = new MenuSeviceImpl();
		AccessTokenThread a = new AccessTokenThread();
		a.gettoken();
		System.out.println("666"+AccessTokenThread.accessToken.getToken());
		System.out.println(AccessTokenThread.accessToken.getExpiresIn());
		String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {

            // 调用接口创建菜单
            result = menuService.createMenu(MenuController.getFirstMenu(),at);
            // 判断菜单创建结果
            if (0 == result) {
                System.out.println("菜单创建成功！");
            } else {
            	System.out.println("菜单创建失败，错误码：" + result);
            }
        }
	}

}
