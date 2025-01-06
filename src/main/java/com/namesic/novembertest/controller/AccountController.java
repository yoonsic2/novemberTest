package com.namesic.novembertest.controller;


import com.namesic.novembertest.dto.AccountDto;
import com.namesic.novembertest.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/account")
@RequiredArgsConstructor

public class AccountController {
    private final AccountService aSer;

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

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

}
