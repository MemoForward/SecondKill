package com.memoforward.dto;

/**
 * 暴露秒杀地址DTO
 */
public class Exposer {
    //是否开启秒杀
    private boolean exposed;
    //加密手段
    private String md5;

    private Long secId;
    //系统当前时间
    private Long now;
    //开启时间
    private Long start;
    //结束时间
    private Long end;


    public Exposer(boolean exposed, String md5, Long secId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.secId = secId;
    }

    public Exposer(boolean exposed, Long now, Long start, Long end) {
        this.exposed = exposed;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, Long secId) {
        this.exposed = exposed;
        this.secId = secId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getSecId() {
        return secId;
    }

    public void setSecId(Long secId) {
        this.secId = secId;
    }

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
        this.now = now;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", secId=" + secId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
