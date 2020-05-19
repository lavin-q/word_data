package com.elite.webdata.jwt;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * 用户登录测试
 */
@Slf4j
@RestController
public class AdminUserController {

    @Autowired
    private Audience audience;

    @PostMapping("/jwt/login")
    @JwtIgnore
    public Result adminLogin(HttpServletResponse response, @RequestBody Map<String,String> paramsMap){
        // 这里模拟测试, 默认登录成功，返回用户ID和角色信息
        String userId = UUID.randomUUID().toString();
        String role = "admin";

        String username = paramsMap.get("username");
        String password = paramsMap.get("password");
        // 创建token
        String token = JwtTokenUtil.createJWT(userId, username+password, role, audience);
        log.info("### 登录成功, token={} ###", token);
        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        // 将token响应给客户端
        JSONObject result = new JSONObject();
        result.put("token", token);
        return Result.SUCCESS(result);
    }

    @GetMapping("/jwt/users")
    public Object userList() {
        log.info("### 查询所有用户列表 ###");
        return Result.SUCCESS();
    }


}
