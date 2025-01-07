package com.namesic.novembertest.service;

import com.namesic.novembertest.dao.AccountDao;
import com.namesic.novembertest.dto.AccountDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AccountService {
    private final AccountDao aDao;

    //회원가입 성공시 index 로 = true  /  실패시 다시 회원가입 = false
    public boolean serviceJoin(AccountDto aDto) {
        //암호화 기능을 가진 클래스 (BCryptPasswordEncoder)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(aDto.getA_pw());
        aDto.setA_pw(encodedPw);
        return aDao.join(aDto);
    }

    public boolean idCheck(String aId) {
        if(aDao.getId(aId) != null){
            return true;
        }
        return false;
    }
}
