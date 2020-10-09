package com.atguigu.security.component;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        System.out.println(rawPassword);
        System.out.println(rawPassword.toString());
        return MD5Util.encode((String)rawPassword);

    }

    @Override
                                       //原密码         加密密码
    public boolean matches(CharSequence rawPassword, String encodedPassword) {


        return encodedPassword.equals(MD5Util.encode((String)rawPassword));
    }
}
