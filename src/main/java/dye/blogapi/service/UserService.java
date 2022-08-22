package dye.blogapi.service;

import dye.blogapi.common.CommonResponse;
import dye.blogapi.domain.User;

public interface UserService {

    CommonResponse<User> login(String username, String password);

    CommonResponse<Object> checkField(String fieldName, String fieldValue);

    CommonResponse<Object> register(User user);

    CommonResponse<User> getUserDetail(Integer userId);



}
