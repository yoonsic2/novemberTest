package com.namesic.novembertest.service;

import com.namesic.novembertest.dao.PostDao;
import com.namesic.novembertest.dto.PageDto;
import com.namesic.novembertest.dto.PostDto;
import com.namesic.novembertest.dto.PostFileDto;
import com.namesic.novembertest.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor

public class PostService {
    private final PostDao pDao;

    //Transactional pDao 로 DB 에 가는 쿼리문들중 하나라도 실패하면 전부 롤백
    @Transactional
    public boolean postUpload(PostDto pDto, String realPath) {
        //pDto(p_multipartFiles 제외) 가지고 postUpload(INSERT) 를 pDao 가 성공? 실패?
        boolean result = pDao.postUpload(pDto);
        if(result) {
            //첨부파일이 있냐 없냐 = p_multipartFiles 가 List 니까 0번째 index 가 isEmpty 아니면!
            if(!pDto.getP_multipartFiles().get(0).isEmpty()){
                //첨부파일 있어 -> List 를 multipartFile 로 찢어 = for each
                for(MultipartFile multipartFile : pDto.getP_multipartFiles()){
                    //multipartFile.getOriginalFilename() 외워 함수야 , 실제이름이야
                    String pf_originalname = multipartFile.getOriginalFilename();
                                            //getExtension 얜, FilenameUtils 안에 있는 함순데, 이름을 16진수로 바꿔줘
                    String pf_systemname = UUID.randomUUID()+"."+ FilenameUtils.getExtension(pf_originalname);
                    //try catch 로 묶은건 용량이커서든 업로드가 실패 할 수 있자나
                    try{
                        //multipartFile.transferTo 함수 - 이동할거야(복사)
                        // new File 로 realPath(uploads) 에 pf_systemname 를
                        multipartFile.transferTo(new File(realPath + pf_systemname));
                        //postFileDto 생성
                        PostFileDto postFileDto = new PostFileDto(pf_originalname,pDto.getP_number(),pf_systemname);
                        //postFileDto 를 가지고 postFileInsert 를 해라 pDao 야
                        boolean result2 = pDao.postFileInsert(postFileDto);
                        if(!result2){
                            //INSERT 가 실패
                            return false;
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        return false;
                    }
                }
                //for (모든 첨부파일을 INSERT 를 성공) 통과
                return true;
            } else {
                //첨부파일이 없어도 INSERT 는 OK
                return true;
            }
        }else {
            //INSERT 실패
            return false;
        }
    }

    //PostController ("/list") 에서 getPostList 기능 만들랍니다! List<PostDto> 로!
    public List<PostDto> getPostList(PageDto pageDto) {
        //Dto 에 getPage_number 를 10의 배수로 start_postNum 에 설정
        int start_postNum = (pageDto.getPage_number() - 1) * 10;
        //pageDto 에서 Page_number 를 가지고 start_postNum 을 재정의 하고 업로드
        pageDto.setStart_postNum(start_postNum);
        //최신화가 된 pageDto 를 가지고 Dao 에 가서 게시글을 가져오는 쿼리 짜와
        return pDao.getPostList(pageDto);
    }

    //첨부파일 제외한 필수적인 것들만 먼져 가져오자 (게시글 1개)
    public PostDto getPostDetail(int pNumber) {
        return pDao.getPostDetail(pNumber);
    }

    //첨부파일 가져오기 (있든 없든 우선 다)
    public List<String> getPostFiles(int pNumber) {
        return pDao.getPostFiles(pNumber);
    }

    public List<ReplyDto> insertReply(ReplyDto rDto) {
        pDao.insertReply(rDto);
        return pDao.getReplyList(rDto.getP_number());
    }

    public List<ReplyDto> getReplies(int pNumber) {
        return pDao.getReplyList(pNumber);
    }

    @Transactional
    public void deletePost(int pNumber, String realPath) throws Exception {
        List<ReplyDto> replyList = pDao.getReplyList(pNumber);
        if(replyList != null && replyList.size() > 0) {
            if(!pDao.deleteReplies(pNumber)){
                throw new Exception();
            }
        }

        List<String> postFiles = pDao.getPostFiles(pNumber);
        if(postFiles != null && postFiles.size() > 0) {
            if(!pDao.deleteSystemFiles(pNumber)){
                throw new Exception();
            }
        }

        if(!pDao.deletePost(pNumber)){
            throw new Exception();
        }

        if(postFiles != null && postFiles.size() > 0) {
            for(String postFile : postFiles){
                File file = new File(realPath + postFile);
                file.delete();
            }
        }
    }
}
