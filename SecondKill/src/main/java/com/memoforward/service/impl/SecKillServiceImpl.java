package com.memoforward.service.impl;

import com.memoforward.dao.SeckillDao;
import com.memoforward.dao.SuccessKilledDao;
import com.memoforward.domain.SeckillProduct;
import com.memoforward.domain.SuccessKilled;
import com.memoforward.dto.Exposer;
import com.memoforward.dto.SeckillExecution;
import com.memoforward.enums.SeckillStateEnum;
import com.memoforward.exception.RepeatKillException;
import com.memoforward.exception.SeckillCloseException;
import com.memoforward.exception.SeckillException;
import com.memoforward.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SecKillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillDao seckillDao;

    @Autowired
    SuccessKilledDao successKilledDao;

    //md5盐值字符串，用于混淆md5
    private final String slat = "memoforward@gmail.com";

    private String getMd5(long secId){
        String base = secId + '/' + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public List<SeckillProduct> getSeckillProductList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public SeckillProduct getSeckillProductById(long secId) {
        return seckillDao.queryById(secId);
    }

    @Override
    public Exposer exportSeckillUrl(long secId) {
        SeckillProduct seckillProduct = seckillDao.queryById(secId);
        if(seckillProduct == null){
            return new Exposer(false,secId);
        }
        Date startTime = seckillProduct.getStartTime();
        Date endTime = seckillProduct.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime()
            || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转化特定字符串的过程（不可逆）
        String md5 = getMd5(secId);
        return new Exposer(true, md5, secId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SeckillExecution excuteSeckill(long secId, String userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        try{
            if(md5 == null || !md5.equals(getMd5(secId)))
                throw new SeckillException("ID 被篡改");
            //执行秒杀逻辑：减库存+记录购买行为
            Date nowTime = new Date();
            int updateCount = seckillDao.reduceNumber(secId, nowTime);
            if(updateCount <= 0) throw new SeckillCloseException("秒杀结束");
            else{
                //记录购买行为
                int insertSuccessKill = successKilledDao.insertSuccessKilled(secId,userPhone);
                if(insertSuccessKill <= 0 ) throw new RepeatKillException("重复秒杀");
                else{
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(secId, userPhone);
                    return new SeckillExecution(secId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        }catch(SeckillCloseException e1){
            throw e1;
        }catch(RepeatKillException e2){
            throw e2;
        }catch (Exception e){
           logger.error(e.getMessage(),e);
           //所有编译期异常转为运行期异常
           throw new SeckillException("Seckill 内部异常：" + e.getMessage());
        }
    }
}
