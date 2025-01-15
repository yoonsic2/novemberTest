package com.namesic.novembertest.controller;


import com.namesic.novembertest.dto.AccountDto;
import com.namesic.novembertest.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//컨트롤러면 해
@Controller

//log.info 스프링부트 디버깅툴 (Console 에 출력! 확인용)
@Slf4j

//Mapping 들 앞에 "/account" 생략가능
@RequestMapping("/account")

//final -> 반드시 생성해줘야 하는 것들
@RequiredArgsConstructor

public class AccountController {
    private final AccountService aSer;

//-------------------------------------------------------------------
    //index 에서 a태그 로 @{/account/join} Get!
    @GetMapping("/join")
    public String join() {
        //join.html 로!
        return "account/join";
    }
    //join.html 에서 joinCheck function 때리면 submit() 때리고, form 태그에 action!
    @PostMapping("/join")
    //aDto 를 가지고 join!
    public String join(AccountDto aDto){
        //aSer에 있는 serviceJoin(aDto를 담고있음) -> boolean
        if(aSer.serviceJoin(aDto)){
            //회원가입이 성공했다면 "redirect:/" -> index.html 로
            return "redirect:/";
        }
        //회원가입 실패했다면 "account/join" -> join.html 로 (다시해)
        return "account/join";
    }
    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(@RequestBody AccountDto aDto){
        if(aSer.idCheck(aDto.getA_id())){
            return false;
        }
        return true;
    }


    //-------------------------------------------------------------------
    @GetMapping("/login")
    public String login() {
        return "account/login";
    }
    @PostMapping("/login")
    public String login(AccountDto aDto, HttpSession session) {
        String a_id = aSer.serviceLogin(aDto);
        if(a_id != null){
            session.setAttribute("a_id", a_id);
            return "redirect:/";
        }else{
            return "account/login";
        }
    }
    //-------------------------------------------------------------------

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //invalidate() 세션에 남아있는 모든걸 지움
        session.invalidate();
        return "redirect:/";
    }
}
