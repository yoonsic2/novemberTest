package com.namesic.novembertest.dao;

import com.namesic.novembertest.dto.AccountDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface AccountDao {

    AccountDto getId(String aId);

    boolean join(AccountDto aDto);
}
