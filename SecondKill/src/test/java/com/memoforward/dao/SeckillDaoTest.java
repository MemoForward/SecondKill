package com.memoforward.dao;

import com.memoforward.domain.SeckillProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class SeckillDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    SeckillDao seckillDao;

    @Test
    public void testQueryById(){
        SeckillProduct sp01 = seckillDao.queryById(1000l);
        System.out.println(sp01);
    }

    @Test
    public void testReduceNumber(){
        Date date = new Date();
        int i = seckillDao.reduceNumber(1000L,date);
        System.out.println(i);
    }

    @Test
    public void testQueryAll(){
        List<SeckillProduct> spList = seckillDao.queryAll(0, 100);
        System.out.println(spList);
    }
}
