package com.mutest.model.interfaces;

import lombok.Data;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/12 11:17
 * @description
 * @modify
 */
@Data
public class TestSetInfo {
    private Long id;
    private Long projectId;
    private String projectName;
    private String caseIdQueue;
    private String caseNameQueue;
    private String creator;
    private String description;
    private String createTime;
    private String modifyTime;
}
