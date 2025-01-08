package com.namesic.novembertest.controller;


import com.namesic.novembertest.dto.AccountDto;
import com.namesic.novembertest.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/account")
@RequiredArgsConstructor

public class AccountController {
    private final AccountService aSer;

//-------------------------------------------------------------------
    @GetMapping("/join")
    public String join() {
        return "account/join";
    }
    @PostMapping("/join")
    public String join(AccountDto aDto){
        if(aSer.serviceJoin(aDto)){
            return "redirect:/";
        }
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
