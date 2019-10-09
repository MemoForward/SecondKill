package com.memoforward.dao;

import com.memoforward.domain.SuccessKilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {

    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") String userPhone);

    SuccessKilled queryByIdWithSecKill(@Param("seckillId") long seckillId,@Param("userPhone") String userPhone);
}
