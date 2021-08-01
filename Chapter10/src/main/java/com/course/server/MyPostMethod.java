package com.course.server;

import com.course.beam.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/",description = "这是我全部的post方法")
@RequestMapping("/v1")
public class MyPostMethod {
    //这个变量用来装cookies信息
    private static Cookie cookie;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口,成功后获取cookies信息",httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName",required = true) String userName,
                        @RequestParam(value = "passWord",required = true) String passWord){
        if(userName.equals("wanghaojun") && passWord.equals("123456")){
            cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜登录成功";
        }
        return "用户名或密码错误";
    }

    @RequestMapping(value = "getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                              @RequestBody User u){
        User user;
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookies信息是否合法
        for(Cookie c:cookies){
            if(c.getName().equals("login")
            && c.getValue().equals("true")
            && u.getUserName().equals("wanghaojun")
            && u.getPassWord().equals("123456")){
                user = new User();
                user.setName("wanghaojun");
                user.setAge("18");
                user.setSex("man");
                return user.toString();
            }
        }
        return "参数非法";
    }

}
