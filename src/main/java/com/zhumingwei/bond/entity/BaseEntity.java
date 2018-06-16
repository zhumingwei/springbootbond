package com.zhumingwei.bond.entity;

import java.sql.Date;

public class BaseEntity  {
    private Date createdate;
    private Date updatedate;
    private long createby;
    private long updateby;

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public long getCreateby() {
        return createby;
    }

    public void setCreateby(long createby) {
        this.createby = createby;
    }

    public long getUpdateby() {
        return updateby;
    }

    public void setUpdateby(long updateby) {
        this.updateby = updateby;
    }

    public Date now(){
        return new Date(System.currentTimeMillis());
    }
}
