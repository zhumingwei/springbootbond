package com.zhumingwei.bond.controller;


import com.zhumingwei.bond.entity.User;
import com.zhumingwei.bond.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserService userService;
    @RequestMapping(path ="info",method = {RequestMethod.GET})
    public String login(HttpServletRequest request, HttpServletResponse response, @Param("id") long id){
        User user = userService.queryById(id);
        if (user!=null){
            return  user.toString() ;
        }else{
            return "获取信息失败";
        }
    }
}
