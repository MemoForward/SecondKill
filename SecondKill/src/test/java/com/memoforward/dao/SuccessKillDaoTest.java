package com.memoforward.dao;

import com.memoforward.domain.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class SuccessKillDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    SuccessKilledDao skd;

    @Test
    public void testInsertSuccessKilled(){
        int i = skd.insertSuccessKilled(1001L, "13160300003");
        System.out.println(i);
    }

    @Test
    public void testQueryByIdWithSeckill(){
        SuccessKilled sk = skd.queryByIdWithSecKill(1000L,"13160300003");
        System.out.println(sk);
        System.out.println(sk.getSeckillProduct());
    }
}
