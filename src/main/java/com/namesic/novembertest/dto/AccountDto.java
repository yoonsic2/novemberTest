package com.namesic.novembertest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//getter, setter 등 을 담고있어 웬만하면 Dto에 다 들어가
@Data

//생성자는 있지만, 매개변수가 없는 빈 깡통 / 저장할 공간은 있다.
@NoArgsConstructor

//모든 멤버변수(ex:this.a_id=a_id)를 받는 생성자를 만들어줌
@AllArgsConstructor

//편의성주의): Dto 에서 아니라도 클래스 생성시 편리하게 해줌
@Builder

//편의성주의): Builder 생성시 .get~.get 하면서 연속으로 가능 (Builder 가 아니라도)
@Accessors(chain = true)

public class AccountDto {
    private String a_id;
    private String a_pw;
    private String a_name;
    private String a_birth;
}
