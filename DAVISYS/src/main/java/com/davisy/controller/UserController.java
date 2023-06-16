package com.davisy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.davisy.dao.UserDao;
import com.davisy.entity.User;
import com.davisy.service.SessionService;


 
@Controller
public class UserController {
	@Autowired
	UserDao dao;
	@Autowired
	HttpServletRequest request;
	@Autowired
	SessionService sessionService;
	
@RequestMapping("/edit/{username}")
public String userManager(@PathVariable("username") String username, Model m) {
	User userSession = sessionService.get("user");
	if(userSession == null) {
		return "error";
	}
	User userss = dao.findByUsername(username);
	request.setAttribute("Users",userss);
	
	
	return "admin/home/formuser";
}

@RequestMapping("/ban")
public String banUser(Model m) {
	User userSession = sessionService.get("user");
	if(userSession == null) {
		return "error";
	}
	String username = request.getParameter("username");
	User user = dao.findByUsername(username);
	if(user.isUser_Role() != true) {
		user.setBan(true);
	dao.saveAndFlush(user);
	}else {
		m.addAttribute("messageban","Không được vô hiệu hóa admin!");
	}
	
	return "redirect:/admin/tableuser";
}

@RequestMapping("/onban")
public String onbanUser(Model m) {
	User userSession = sessionService.get("user");
	if(userSession == null) {
		return "error";
	}
	String username = request.getParameter("username");
	User user = dao.findByUsername(username);
	if(user.isBan() != false) {
		user.setBan(false);
	dao.saveAndFlush(user);
	}else {
		m.addAttribute("messageban","Tài khoản đang hoạt động!");
	}
	
	return "redirect:/admin/tableuser";
}
}
