package dye.blogapi.controller;

import dye.blogapi.common.CONSTANT;
import dye.blogapi.common.CommonResponse;
import dye.blogapi.domain.User;
import dye.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user/")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public CommonResponse<User> login(
            @RequestParam @NotBlank(message = "用户名不能为空") String username,
            @RequestParam @NotBlank(message = "密码不能为空") String password,
            HttpSession session){
        CommonResponse<User> result = userService.login(username, password);
        if(result.isSuccess()){
            session.setAttribute(CONSTANT.LOGIN_USER, result.getData());
        }
        return result;
    }

    @PostMapping("check_field")
    public CommonResponse<Object> checkField(
            @RequestParam @NotBlank(message = "字段名不能为空") String fieldName,
            @RequestParam @NotBlank(message = "字段值不能为空") String fieldValue){
        return userService.checkField(fieldName,fieldValue);
    }

    @PostMapping("register")
    public CommonResponse<Object> register(@RequestBody @Valid User user){
        return userService.register(user);
    }

    @PostMapping("get_user_detail")
    public CommonResponse<User> getUserDetail(HttpSession session){
        User loginUser = (User) session.getAttribute(CONSTANT.LOGIN_USER);
        if(loginUser == null){
            return CommonResponse.createForError("用户未登录");
        }
        return userService.getUserDetail(loginUser.getId());
    }

}
