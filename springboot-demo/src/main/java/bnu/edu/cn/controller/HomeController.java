package bnu.edu.cn.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bnu.edu.cn.entity.Msg;
import bnu.edu.cn.entity.User;
import bnu.edu.cn.exception.UserNotExistException;
import bnu.edu.cn.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String index(Model model) {
		Msg msg = new Msg("测试标题", "测试内容", "额外信息，只对管理员显示");
		model.addAttribute("msg", msg);
		return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/findUserRole/{id}")
	@ResponseBody
	public String findUserRole(@PathVariable("id") Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			throw new UserNotExistException(id);
		}
		return user.getAuthorities().toString();
	}
}
