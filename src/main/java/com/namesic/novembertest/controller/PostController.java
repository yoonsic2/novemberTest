package com.namesic.novembertest.controller;

import com.namesic.novembertest.dto.PageDto;
import com.namesic.novembertest.dto.PostDto;
import com.namesic.novembertest.dto.ReplyDto;
import com.namesic.novembertest.service.AccountService;
import com.namesic.novembertest.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor

public class PostController {
    private final PostService pSer;

    //index.html 에서 Get 으로 list 받았어요
    @GetMapping("/list")
    //page_number 와 page_keyword 는 pageDto 로! , model 에 담아서 post/ list.html 로 보내자!
    public String list(PageDto pageDto ,Model model) {
        //생각해봐, 게시판 글들이 1개겠어? -> List 로 받자!
        //pageDto 를 가지고 getPostList 라는 기능 해줘라 pSer 야~
        //게시판 에는 PostDto 들을 사용한 List 로 postlist 라 할게
        List<PostDto> postlist = pSer.getPostList(pageDto);
        //postList 라는 이름으로 postlist 를 modle 에 담아서 보낼게
        model.addAttribute("postList", postlist);
        //list.html 로!
        return "post/list";
    }

    //list.html 에서 글작성 눌러서 왔어요
    @GetMapping("/write")
    public String write() {
        //write.html 로 가세요
        return "post/write";
    }

    //write.html 에서 왔어요
    @PostMapping("/write")
    //RedirectAttributes = 한번 번쩍 하고 알아서 삭제돼 , ex) 메세지창
    public String write(PostDto pDto, HttpSession session, RedirectAttributes redirectAttributes) {

        //getServletContext().getRealPath("/") 외우자 = 프로그램의 실제 주소, session 에서 가져와 realPath 담아
        String realPath = session.getServletContext().getRealPath("/");

        //session 에 있는, 누가 글쓰는지 알아야 하기에 a_id 를 가져와서 postWriter 에 담아
        String postWriter = session.getAttribute("a_id").toString();
        pDto.setA_id(postWriter);

        //realPath(=webapp) + uploads(폴더) 거기에 pDto 를 가지고 postUpload 를 해줘 pSer 야
        boolean result = pSer.postUpload(pDto, realPath+"uploads/");
        //boolean 이니까 성공하면 true / 실패하면 false
        if (result) {
            //redirectAttributes.addFlashAttribute = msg 는 한번 쓰고 삭제할거니까
            redirectAttributes.addFlashAttribute("msg", "업로드 성공");
            return "redirect:/post/list";
        }else{
            redirectAttributes.addFlashAttribute("msg", "업로드 실패");
            return "redirect:/post/write";
        }
    }

    //list.html 에서 왔어요
    @GetMapping("/detail")
    //pDto 에는 p_number 만 들어있어요 (나머진 null)
    public String detail(PostDto pDto, Model model) {

        //p_number 를 키값으로 getPostDetail 하자 (DB 갔다오자)
        pDto = pSer.getPostDetail(pDto.getP_number());
        //최신화 된 pDto 를 post 라는 이름으로 모델에 담아
        model.addAttribute("post", pDto);

        //p_numnber 를 키값으로 getPostFiles 하자
        //근데 파일이 여러개 일 수 있자나? 그래서 List 선언
        List<String> postfiles = pSer.getPostFiles(pDto.getP_number());
        //첨부파일 가져온 postfiles 를 model 에 담아
        model.addAttribute("postfiles", postfiles);

        List<ReplyDto> replyies = pSer.getReplies(pDto.getP_number());
        model.addAttribute("replies", replyies);

        //detail.html 로 가 (model 2개 가지고 간다)
        return "post/detail";
    }

    @PostMapping("/reply")
    @ResponseBody
    public List<ReplyDto> reply(@RequestBody ReplyDto rDto, HttpSession session) {
        rDto.setA_id(session.getAttribute("a_id").toString());
        return pSer.insertReply(rDto);
    }

    @GetMapping("/delete")
    public String delete(PostDto pDto, HttpSession session) {
        try{
            pSer.deletePost(pDto.getP_number(), session.getServletContext().getRealPath("/") + "uploads/");
            return "redirect:/post/list?page_number=1&page_keyword=";
        }
        catch(Exception e){
            return "redirect:/post/detail?p_number=" + pDto.getP_number();
        }
    }

}
