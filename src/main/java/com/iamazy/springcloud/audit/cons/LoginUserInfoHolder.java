package com.iamazy.springcloud.audit.cons;

import com.iamazy.springcloud.audit.security.model.AnonymousUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Objects;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition 获取当前登录的用户信息
 **/
public class LoginUserInfoHolder {

    public static UserDetails getLoginUserInfo(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication)){
            return new AnonymousUser();
        }
        return (UserDetails)authentication.getPrincipal();
    }
}
