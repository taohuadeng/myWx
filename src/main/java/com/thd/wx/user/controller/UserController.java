package com.thd.wx.user.controller;

import com.thd.wx.user.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/*")
public class UserController {

    @ResponseBody
    @RequestMapping("/saveUser")
    public ModelMap saveUser(User user) {
        String username = user.getUsername();
        Integer age = user.getAge();
        System.out.println("用户姓名 : " + username);
        System.out.println("用户年龄 : " + age);
        ModelMap map = new ModelMap();
        map.put("name", username);
        return map;
    }
}