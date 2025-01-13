package com.namesic.novembertest.service;

import com.namesic.novembertest.dao.PostDao;
import com.namesic.novembertest.dto.PostDto;
import com.namesic.novembertest.dto.PostFileDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class PostService {
    private final PostDao pDao;

    public boolean postUpload(PostDto pDto, String realPath) {
        if(pDao.postUpload(pDto)){
            if(!pDto.getP_multipartFiles().get(0).isEmpty()){
                for(MultipartFile multipartFile : pDto.getP_multipartFiles()){
                    String pf_originalname = multipartFile.getOriginalFilename();
                                            //getExtension 얜, FilenameUtils 안에 있는 함순데, 이름을 16진수로 바꿔줘
                    String pf_systemname = UUID.randomUUID()+"."+ FilenameUtils.getExtension(pf_originalname);
                    try{

                        multipartFile.transferTo(new File(realPath + pf_systemname));
                        if(!pDao.postFileInsert(new PostFileDto(pf_originalname,pDto.getP_number(),pf_systemname))){
                            return false;
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        return false;
                    }
                }
                return true;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
}
