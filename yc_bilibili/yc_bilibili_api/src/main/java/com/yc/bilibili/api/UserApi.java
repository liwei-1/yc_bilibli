package com.yc.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.yc.bilibili.api.support.UserSupport;
import com.yc.bilibili.daomin.JsonResponse;
import com.yc.bilibili.daomin.PageResult;
import com.yc.bilibili.daomin.User;
import com.yc.bilibili.daomin.UserInfo;
import com.yc.bilibili.service.UserFollowingService;
import com.yc.bilibili.service.UserService;
import com.yc.bilibili.service.util.RSAUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserFollowingService userFollowingService;
    /**
     * 根据token获取用户信息
     * @return user
     */
    @GetMapping("/users")
    public JsonResponse<User> getUserInfo(){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        return new JsonResponse<>(user);
    }

    /**
     * 获取rsa公钥
     * @return
     */
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPulicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonResponse.success("注册成功！");
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception{
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }

    /**
     * 修改用户详情信息
     * @param userInfo
     * @return
     */
    @PutMapping("/user-infos")
    public JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo){
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfos(userInfo);
        return JsonResponse.success();
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping("/users")
    public JsonResponse<String> updateUsers(@RequestBody User user){
        userService.updateUsers(user);
        return JsonResponse.success();
    }

    /**
     * 用户分页查询
     * @param no 页码
     * @param size 页码个数
     * @param nick 名称
     * @return PageResult<UserInfo>
     *    用@RequestParam修饰 必须传参
     */
    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam  Integer no,@RequestParam Integer size,String nick){
        Long userId = userSupport.getCurrentUserId();
        //
        JSONObject params = new JSONObject();
        params.put("no",no);
        params.put("size",size);
        params.put("nick",nick);
        params.put("userId",userId);
        // 分页查询用户信息
        PageResult<UserInfo> result = userService.pageListUserInfos(params);
        // 判断用户有没有被当前用户关注 ====
        if(result.getTotal() > 0 ){
            List<UserInfo> userInfoList = userFollowingService.checkFollowingStatus(result.getList(),userId);
            result.setList(userInfoList);
        }
        return new JsonResponse<>(result);
    }

    /**
     * 登录双token验证 token和refreshToken
     * @param user
     * @return
     * @throws Exception
     */

    @PostMapping("/user-dts")
    public JsonResponse<Map<String, Object>> loginForDts(@RequestBody User user) throws Exception {
        Map<String,Object> map = userService.loginForDts(user);
        return new JsonResponse<>(map);
    }

    /**
     * 用户退出登录
     */
    @DeleteMapping("/refresh-tokens")
    public JsonResponse<String> logout(HttpServletRequest request){
        String refreshToken = request.getHeader("refreshToken");
        Long userId = userSupport.getCurrentUserId();
        userService.logout(refreshToken,userId);
        return JsonResponse.success();
    }

    /**
     * 刷新token令牌
     */
    @PostMapping("/access-tokens")
    public JsonResponse<String> refreshAccessToken(HttpServletRequest request) throws Exception {
        String  refreshToken = request.getHeader("refreshToken");
        String accessToken = userService.refreshAccessToken(refreshToken);
        return  new JsonResponse<>(accessToken);
    }

}
