package dye.blogapi.controller;

import dye.blogapi.common.CONSTANT;
import dye.blogapi.common.CommonResponse;
import dye.blogapi.domain.User;
import dye.blogapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController//使用Rest前缀就不用在方法上加ResponseBody
@RequestMapping("/user/")
@Validated//验证组件
@Api(tags = "用户相关接口")
public class UserController {

    private final HttpSession session;

    @Autowired
    private UserService userService;

    public UserController(HttpSession session){
        this.session = session;
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名" ,required = true, dataType = "String"),
            @ApiImplicitParam(name = "password",value = "用户密码",required = true, dataType = "String")
    })
    public CommonResponse<User> login(
            @RequestParam @NotBlank(message = "用户名不能为空") String username,
            @RequestParam @NotBlank(message = "密码不能为空") String password
            ){
        CommonResponse<User> result = userService.login(username, password);
        if(result.isSuccess()){
            session.setAttribute(CONSTANT.LOGIN_USER, result.getData());
        }
        return result;
    }

    @PostMapping("check_field")
    @ApiOperation(value = "检查字段是否可用",notes = "根据字段名和字段值判断数据库中有无重复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fieldName",value = "字段名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "fieldValue",value = "字段值",required = true, dataType = "String")
    })
    public CommonResponse<Object> checkField(
            @RequestParam @NotBlank(message = "字段名不能为空") String fieldName,
            @RequestParam @NotBlank(message = "字段值不能为空") String fieldValue){
        return userService.checkField(fieldName,fieldValue);
    }

    @PostMapping("register")
    @ApiOperation(value = "用户注册",notes = "")
    public CommonResponse<Object> register(@RequestBody @Valid User user){
        System.out.println("user is"+user);
        return userService.register(user);
    }

    @PostMapping("get_user_detail")
    @ApiOperation(value = "获取登录用户数据",notes = "获取当前登录用户的全部信息")
    public CommonResponse<User> getUserDetail(){
        User loginUser = (User) session.getAttribute(CONSTANT.LOGIN_USER);
        if(loginUser == null){
            return CommonResponse.createForError("用户未登录");
        }
        return userService.getUserDetail(loginUser.getId());
    }

}
