package com.mutest.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2018/11/26 9:28
 * @description 登录页配置
 * @modify
 */
@Controller
public class LoginPageConfig {

	@RequestMapping("/")
	public RedirectView loginPage() {
		return new RedirectView("/login.html");
	}
}
