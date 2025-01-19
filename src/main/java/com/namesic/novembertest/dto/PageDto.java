package com.namesic.novembertest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain=true)

public class PageDto {
    private int page_number;
    private String page_keyword;
    private int start_postNum;
}
