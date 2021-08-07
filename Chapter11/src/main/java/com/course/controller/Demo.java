package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Log4j
@RestController
@Api(value ="v1",description = "这是我的第一个版本的demo")
@RequestMapping("v1")
public class Demo {

    //获取一个执行sql语句的对象，里面会有增删改查的4个模版

    @Autowired //下面的对象会被加载
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ApiOperation(value = "可以获取到用户数")
    public int getUserCount(){
        return template.selectOne("getUserCount");
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "插入用户")
    public int addUser(@RequestBody User user){
        int result= template.insert("addUser",user);
        return result;
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ApiOperation(value = "更新用户")
    public int updateUser(@RequestBody User user){
        return template.update("updateUser",user);
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    @ApiOperation(value = "删除用户")
    public int deleteUser(@RequestParam int id){
        return template.delete("deleteUser",id);
    }
}
