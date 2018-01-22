package com.aerozhonghuan.hongyan.producer.dao.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangyunfei on 17/6/19.
 */
@Entity
public class Entity1 {

    @Id
    private Long id;
    private String name;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 870613580)
    public Entity1(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 520801375)
    public Entity1() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
