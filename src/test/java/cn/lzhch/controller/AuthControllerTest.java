package cn.lzhch.controller;

import cn.lzhch.dto.auth.UserLoginRequest;
import cn.lzhch.dto.auth.UserRegisterRequest;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 认证控制器测试
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/12/19
 */

@SpringBootTest
@AutoConfigureWebMvc
class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testRegister() throws Exception {
        setUp();
        
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("TestPassword123")
                .build();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"));
    }

    @Test
    void testLogin() throws Exception {
        setUp();
        
        // 首先注册一个用户
        UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .username("logintest")
                .email("logintest@example.com")
                .password("TestPassword123")
                .build();

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(registerRequest)));

        // 然后登录
        UserLoginRequest loginRequest = UserLoginRequest.builder()
                .username("logintest")
                .password("TestPassword123")
                .build();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.data.userInfo.username").value("logintest"));
    }

    @Test
    void testGetProfileWithoutAuth() throws Exception {
        setUp();
        
        mockMvc.perform(get("/api/auth/profile"))
                .andExpect(status().isUnauthorized());
    }
}
