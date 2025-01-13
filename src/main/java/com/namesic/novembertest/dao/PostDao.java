package com.namesic.novembertest.dao;

import com.namesic.novembertest.dto.PostDto;
import com.namesic.novembertest.dto.PostFileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostDao {
    boolean postUpload(PostDto pDto);

    boolean postFileInsert(PostFileDto postFileDto);
}
