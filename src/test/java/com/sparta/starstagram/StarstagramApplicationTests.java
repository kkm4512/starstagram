package com.sparta.starstagram;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class StarstagramApplicationTests {

    @Test
    void contextLoads() {
    }


    //비밀번호 encoding
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void pwdEnc() {
        String pwd = "qwe123";
        String encodedPwd = passwordEncoder.encode(pwd); //암호화
        System.out.println(encodedPwd);
    }

    //비밀번호 확인
    @Test
    void pwdMatch() {
        //저장해두었던 비밀번호
        String encodedPwd = "{bcrypt}$2a$10$eVOEVuyjWoDUtnJBO7VhQukq0.sHP0Cw/T.l6rC9wSoEC1dzfC25S";

        //확인 할 비밀번호
        String newPwd = "qweret2323";

        if(passwordEncoder.matches(newPwd, encodedPwd)) {
            //비밀번호가 동일할 때
            System.out.println("ㅇㅇ");
        } else {
            //비밀번호가 안맞을 때
            System.out.println("ㄴㄴ");
        }
    }

}
