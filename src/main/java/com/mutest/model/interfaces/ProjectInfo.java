package com.mutest.model.interfaces;

import lombok.Data;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/5/9 14:42
 * @description
 * @modify
 */
@Data
public class ProjectInfo {
    private Long id;
    private String projectName;
    private String url;
    private String creator;
    private int interfaceCount;
    private int caseCount;
    private String description;
    private String createTime;
    private String modifyTime;
}
