package cloud.heguangwu.system.controller;

import cloud.heguangwu.system.entity.Login;
import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.mapper.UserMapper;
import cloud.heguangwu.system.service.UserService;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/islogin")
    public String isLogin(){
        log.info("查询登陆状态，登陆ID:{}", StpUtil.getLoginId());
        if(StpUtil.isLogin()){
            return "已经登陆";
        }
        return "用户未登陆";
    }

    @PostMapping("/login")
    public SaResult login(@RequestBody Login login){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, login.username());
        User user = userService.getOne(queryWrapper);
        if(user == null){
            log.info("查询{}用户不存在", login.username());
            return SaResult.error("用户或密码错误");
        }
        if(!user.getPassword().equals(login.password())) {
            log.info("用户{}输入的密码错误", login.username());
            return SaResult.error("用户或密码错误") ;
        }
        log.info("用户{}登陆成功，用户ID：{}", login.username(), user.getId());
        StpUtil.login(user.getId());
        return SaResult.ok("登陆成功");
    }

}
