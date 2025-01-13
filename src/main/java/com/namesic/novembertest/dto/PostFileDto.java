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

public class PostFileDto {
    private String pf_originalname;
    private int p_number;
    private String pf_systemname;

}
