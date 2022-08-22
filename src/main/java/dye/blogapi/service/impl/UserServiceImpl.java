package dye.blogapi.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import dye.blogapi.common.CONSTANT;
import dye.blogapi.common.CommonResponse;
import dye.blogapi.domain.User;
import dye.blogapi.persistence.UserMapper;
import dye.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

//      ("userService")可以不加,加了可以提高效率
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public CommonResponse<User> login(String username, String password) {

        User loginUser = userMapper.selectOne(Wrappers.<User>query().eq("username",username));
        if(loginUser == null){
            return CommonResponse.createForError("用户名或密码错误");
        }
        //使用注入的方式避免了手动new
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean checkPassword = bCryptPasswordEncoder.matches(password, loginUser.getPassword());
        loginUser.setPassword(StringUtils.EMPTY);
        return checkPassword? CommonResponse.createForSuccess(loginUser):CommonResponse.createForError("用户名或密码错误");
    }

    @Override
    public CommonResponse<Object> checkField(String fieldName, String fieldValue){
        if(StringUtils.equals(fieldName,"username")){
            long rows = userMapper.selectCount(Wrappers.<User>query().eq("username",fieldValue));
            if(rows > 0){
                return CommonResponse.createForError("用户名已存在");
            }
        }
//        else if(StringUtils.equals(fieldName,"phone")){
//            long rows = userMapper.selectCount(Wrappers.<User>query().eq("phone",fieldValue));
//            if(rows > 0){
//                return CommonResponse.createForError("电话号码已存在");
//            }
//        }
//        else if(StringUtils.equals(fieldName,"email")){
//            long rows = userMapper.selectCount(Wrappers.<User>query().eq("email",fieldValue));
//            if(rows > 0){
//                return CommonResponse.createForError("邮箱已存在");
//            }
//        }
        else{
            return CommonResponse.createForError("参数错误");
        }
        return CommonResponse.createForSuccess();
    }

    @Override
    public CommonResponse<Object> register(User user){
        CommonResponse<Object> checkResult = checkField("username", user.getUsername());
        if(!checkResult.isSuccess()){
            return checkResult;
        }
//        checkResult = checkField("email", user.getEmail());
//        if(!checkResult.isSuccess()){
//            return checkResult;
//        }
//        checkResult = checkField("phone", user.getPhone());
//        if(!checkResult.isSuccess()){
//            return checkResult;
//        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

//        user.setRole(CONSTANT.ROLE.CUSTOMER);
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(LocalDateTime.now());

        int rows = userMapper.insert(user);

        if(rows == 0){
            return CommonResponse.createForError("注册用户失败");
        }
        return CommonResponse.createForSuccess();
    }

    @Override
    public CommonResponse<User> getUserDetail(Integer userId){
        User user = userMapper.selectById(userId);
        if(user == null){
            return CommonResponse.createForError("找不到当前用户信息");
        }
        user.setPassword(StringUtils.EMPTY);
        return CommonResponse.createForSuccess(user);
    }





}
