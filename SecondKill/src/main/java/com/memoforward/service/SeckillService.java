package com.memoforward.service;

import com.memoforward.domain.SeckillProduct;
import com.memoforward.dto.Exposer;
import com.memoforward.dto.SeckillExecution;
import com.memoforward.exception.RepeatKillException;
import com.memoforward.exception.SeckillCloseException;
import com.memoforward.exception.SeckillException;

import java.util.List;

/**
 * 站在使用者的角度设计
 * 三个方面：方法定义粒度，参数，返回类型要友好
 */
public interface SeckillService {
    /**
     * 查询所有秒杀的产品
     * @return
     */
    List<SeckillProduct> getSeckillProductList();

    /**
     * 查询一个
     * @param secId
     * @return
     */
    SeckillProduct getSeckillProductById(long secId);

    /**
     * 秒杀开启时输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param secId
     */
    Exposer exportSeckillUrl(long secId);

    SeckillExecution excuteSeckill(long secId, String userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException ;
}
