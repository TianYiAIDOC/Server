package com.tianyi.bo.base;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@MappedSuperclass
public abstract class BaseBo implements Serializable {
    private static final long serialVersionUID = -2361282200900055337L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;


    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdOn;


    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedOn;

    private long updatedTimestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public long getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(long updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
}
