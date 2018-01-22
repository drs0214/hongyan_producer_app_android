package com.aerozhonghuan.hongyan.producer.modules.home.entity;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/7/16 0016 on 下午 3:53
 */

public class QueryTaskNumAndScoreTotalEvent extends EventBusEvent {
    private int taskNum;
    private int scoreTotal;

    public QueryTaskNumAndScoreTotalEvent(int taskNum, int scoreTotal) {
        this.taskNum = taskNum;
        this.scoreTotal = scoreTotal;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }
}
