package com.namesic.novembertest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

//우리가 주소창에 무언가 입력을 한다? -> 무조건 어떠한 controller 로 간다!
// ("/") 아무것도 입력하지 않았어? -> index.html 로 가라!
// 이것이 model2 방식. -> 현업의 사이즈(규모) 가 커져가면서 분업의 필요성이 커짐
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
