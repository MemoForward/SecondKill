package com.memoforward.dto;

import com.memoforward.domain.SuccessKilled;
import com.memoforward.enums.SeckillStateEnum;

/**
 * 封装执行后的结果
 */
public class SeckillExecution {
    //秒杀ID
    private Long secId;
    //秒杀状态
    private int state;
    //秒杀状态说明
    private String stateInfo;
    //成功秒杀
    private SuccessKilled successKilled;

    public SeckillExecution(Long secId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
        this.secId = secId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(Long secId, SeckillStateEnum seckillStateEnum) {
        this.secId = secId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
    }

    public Long getSecId() {
        return secId;
    }

    public void setSecId(Long secId) {
        this.secId = secId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "secId=" + secId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
