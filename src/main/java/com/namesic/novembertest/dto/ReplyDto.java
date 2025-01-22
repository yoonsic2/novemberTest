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

public class ReplyDto {
    private int p_number;
    private String r_number;
    private String a_id;
    private String r_content;
}
