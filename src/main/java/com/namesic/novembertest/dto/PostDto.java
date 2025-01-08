package com.namesic.novembertest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain=true)

public class PostDto {
    private int p_number;
    private String a_id;
    private String p_title;
    private String p_content;
}
