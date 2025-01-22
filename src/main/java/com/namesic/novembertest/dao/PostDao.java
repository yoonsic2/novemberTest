package com.namesic.novembertest.dao;

import com.namesic.novembertest.dto.PageDto;
import com.namesic.novembertest.dto.PostDto;
import com.namesic.novembertest.dto.PostFileDto;
import com.namesic.novembertest.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {
    boolean postUpload(PostDto pDto);

    boolean postFileInsert(PostFileDto postFileDto);

    List<PostDto> getPostList(PageDto pageDto);

    PostDto getPostDetail(int pNumber);

    List<String> getPostFiles(int pNumber);

    void insertReply(ReplyDto rDto);

    List<ReplyDto> getReplyList(int pNumber);

    boolean deleteReplies(int pNumber);

    boolean deleteSystemFiles(int pNumber);

    boolean deletePost(int pNumber);
}
