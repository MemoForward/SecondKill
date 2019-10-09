package com.memoforward.dao;

import com.memoforward.domain.SeckillProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据ID查询商品
     * @param seckillId
     * @return
     */
    SeckillProduct queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品
     * @return
     */
    List<SeckillProduct> queryAll(@Param("offset") int offset,@Param("limit") int limit);


}
