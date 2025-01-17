package com.namesic.novembertest.dao;

import com.namesic.novembertest.dto.PageDto;
import com.namesic.novembertest.dto.PostDto;
import com.namesic.novembertest.dto.PostFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {
    boolean postUpload(PostDto pDto);

    boolean postFileInsert(PostFileDto postFileDto);

    List<PostDto> getPostList(PageDto pageDto);

    PostDto getPostDetail(int pNumber);

    List<String> getPostFiles(int pNumber);
}
