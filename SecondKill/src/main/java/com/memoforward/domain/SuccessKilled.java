package com.memoforward.domain;

import java.util.Date;

public class SuccessKilled {
    private Long secId;
    private String userPhone;
    private Short state;
    private Date createTime;

    private SeckillProduct seckillProduct;

    public Long getSecId() {
        return secId;
    }

    public void setSecId(Long secId) {
        this.secId = secId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SeckillProduct getSeckillProduct() {
        return seckillProduct;
    }

    public void setSeckillProduct(SeckillProduct seckillProduct) {
        this.seckillProduct = seckillProduct;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "secId=" + secId +
                ", userPhone='" + userPhone + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
