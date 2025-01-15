package com.namesic.novembertest.controller;

import com.namesic.novembertest.dto.PageDto;
import com.namesic.novembertest.dto.PostDto;
import com.namesic.novembertest.service.AccountService;
import com.namesic.novembertest.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor

public class PostController {
    private final PostService pSer;

    @GetMapping("/list")
    public String list(PageDto pageDto ,Model model) {
        model.addAttribute("postList",pSer.getPostList(pageDto));
        return "post/list";
    }

    @GetMapping("/write")
    public String write() {
        return "post/write";
    }
    @PostMapping("/write")
    //RedirectAttributes = 한번 번쩍 하고 알아서 삭제돼 , ex) 메세지창
    public String write(PostDto pDto, HttpSession session, RedirectAttributes redirectAttributes) {
        String postWriter = session.getAttribute("a_id").toString();
        String realPath = session.getServletContext().getRealPath("/");
        pDto.setA_id(postWriter);
        boolean result = pSer.postUpload(pDto, realPath+"uploads/");
        if (result) {
            redirectAttributes.addFlashAttribute("msg", "업로드 성공");
            return "redirect:/post/list";
        }else{
            redirectAttributes.addFlashAttribute("msg", "업로드 실패");
            return "redirect:/post/write";
        }
    }

}
