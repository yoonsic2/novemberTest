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

    public String serviceLogin(AccountDto aDto) {
        //aDto 에서 받아온 ID를, aDao 에 있는 ID와 조회?체크? 한걸 tmp 에 담아
        AccountDto tmp = aDao.getId(aDto.getA_id());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //null 이 아니면 맞는 아이디가 있어
        if(tmp != null){
            //입력한 aDto 비번과 , DB에 있는 tmp 비번과 매치해봐
            if(encoder.matches(aDto.getA_pw(), tmp.getA_pw())){
                //맞으면 입력한 아이디 반환 , 아이디 계속 담고 갈거거든
                return aDto.getA_id();
            }else{
                return null;
            }
        }else{
            //아이디 같은거 없음, 계정 없음
            return null;
        }
    }
}
