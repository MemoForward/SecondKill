package com.memoforward.service;

import com.memoforward.domain.SeckillProduct;
import com.memoforward.dto.Exposer;
import com.memoforward.dto.SeckillExecution;
import com.memoforward.exception.RepeatKillException;
import com.memoforward.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

@ContextConfiguration("classpath:spring/applicationContext.xml")
public class SeckillServiceTest extends AbstractTestNGSpringContextTests {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillService seckillService;

    @Test
    public void testGetSeckillProductList(){
        List<SeckillProduct> spl = seckillService.getSeckillProductList();
        logger.info("list:{}",spl);
       // System.out.println(spl);
    }

    @Test
    public void testGetSeckillProductById(){
        SeckillProduct sp = seckillService.getSeckillProductById(1002l);
        logger.info("secProduct：{}", sp);
    }
    
    @Test
    public void testExportSecillUrl(){
        Exposer exposer = seckillService.exportSeckillUrl(1001l);
        logger.info("exposer:{}",exposer);
        //1903957c94398188be51047675eb8b4d
    }

    @Test
    public void testExcuteSeckill(){
        long id = 1001l;
        String userPhone = "13160300000";
        String md5 = "1903957c94398188be51047675eb8b4d";
        try{
            SeckillExecution seckillExecution = seckillService.excuteSeckill(id, userPhone, md5);
            logger.info("result:{}", seckillExecution);
        }catch(RepeatKillException e){
            logger.error(e.getMessage());
        }catch (SeckillCloseException e){
            logger.error(e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
    
    @Test
    public void testSeckillLogic(){
        long id = 1000l;
        String userPhone = "13160300003";
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            try{
                String md5 = exposer.getMd5();
                SeckillExecution seckillExecution = seckillService.excuteSeckill(id, userPhone, md5);
                logger.info("result:{}", seckillExecution);
            }catch(RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        }else{
            //返回秒杀时间
            logger.warn("秒杀开始时间：{} ~~ 结束时间：{}",new Date(exposer.getStart()),new Date(exposer.getEnd()));
        }
    }
}
