package com.namesic.novembertest.controller;

import com.namesic.novembertest.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor

public class PostController {
    //private final PostService pSer;

    @GetMapping("/list")
    public String list() {
        return "post/list";
    }
    @GetMapping("/write")
    public String write() {
        return "post/write";
    }
}
