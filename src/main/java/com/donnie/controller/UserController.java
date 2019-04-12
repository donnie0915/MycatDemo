package com.donnie.controller;

import com.donnie.dao.UserMapper;
import com.donnie.entity.User;
import com.donnie.interceptor.MultiTenantHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController  {


    @Autowired
    UserMapper userMapper;

    @GetMapping("/lists")
    @ResponseBody
    public Object list(String tenantId){

        MultiTenantHolder.setCurrentNode("dn"+tenantId);

        List<User> all = userMapper.getAll();

        return all;

    }


}
