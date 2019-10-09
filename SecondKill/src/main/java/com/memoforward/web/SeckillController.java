package com.memoforward.web;

import com.memoforward.domain.SeckillProduct;
import com.memoforward.dto.Exposer;
import com.memoforward.dto.SeckillExecution;
import com.memoforward.dto.SeckillResult;
import com.memoforward.enums.SeckillStateEnum;
import com.memoforward.exception.RepeatKillException;
import com.memoforward.exception.SeckillCloseException;
import com.memoforward.exception.SeckillException;
import com.memoforward.service.SeckillService;
import org.apache.taglibs.standard.tag.common.fmt.RequestEncodingSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getSeckillList(Model model){
        List<SeckillProduct> seckillProductList = seckillService.getSeckillProductList();
        model.addAttribute("seckillProductList", seckillProductList);
        return "list";
    }

    @RequestMapping(value = "/{secId}/detail", method = RequestMethod.GET)
    public String getResultDetail(@PathVariable("secId") Long secId, Model model){
        SeckillProduct sp = seckillService.getSeckillProductById(secId);
        if(sp == null) return "redirect:/seckill/list";
        else{
            model.addAttribute("seckillProduct", sp);
        }
        return "detail";
    }

    /**
     * ajax异步返回结果
     * @return
     */
    @RequestMapping(value = "/{secId}/exposer",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("secId") Long secId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer = seckillService.exportSeckillUrl(secId);
            result = new SeckillResult<>(true, exposer);
        }catch (Exception e){
            logger.error("暴露秒杀地址时发生异常：{}",e.getMessage());
            result = new SeckillResult<>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{secId}/{md5}/execute",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("secId") Long secId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "userPhone", required = false) String userPhone){
        if(userPhone == null) return new SeckillResult<>(false, "未注册");
        SeckillResult<SeckillExecution> result;
        SeckillExecution  seckillExecution;
        try{
            seckillExecution = seckillService.excuteSeckill(secId, userPhone, md5);
            result = new SeckillResult<>(true, seckillExecution);
        }catch(RepeatKillException e){
            seckillExecution = new SeckillExecution(secId, SeckillStateEnum.REPEAT_KILL);
            result = new SeckillResult<>(true, seckillExecution);
        }catch(SeckillCloseException e){
            seckillExecution = new SeckillExecution(secId, SeckillStateEnum.END);
            result = new SeckillResult<>(true, seckillExecution);
        }catch(Exception e){
            logger.error("执行秒杀的时候出现异常：{}",e.getMessage());
            seckillExecution = new SeckillExecution(secId, SeckillStateEnum.INNER_ERROR);
            result = new SeckillResult<>(true, seckillExecution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> getTime(){
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }
}
