package com.mutest.model.interfaces;

import lombok.Data;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/16 19:29
 * @description 测试场景执行结果实体
 * @modify
 */
@Data
public class TestSetResult {
    private int id;
    private int testSetId;
    private String executeId;
    private String projectName;
    private String caseNameQueue;
    private int caseCount;
    private int failedCount;
    private String failedList;
    private String executor;
    private String create_time;
    private String modify_time;
}
