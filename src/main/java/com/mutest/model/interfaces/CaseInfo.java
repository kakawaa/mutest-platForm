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
public class CaseInfo {
    private Long id;
    private Long projectId;
    private Long moduleId;
    private Long interfaceId;
    private int delay;
    private String projectName;
    private String moduleName;
    private String interfaceName;
    private String url;
    private String path;
    private String headerData;
    private String bodyData;
    private String assertion;
    private String shellStatus;
    private String assertionShell;
    private String creator;
    private String caseType;
    private String paramType;
    private String method;
    private String description;
    private String resultDemo;
    private String correlation;
    private String createTime;
    private String modifyTime;

}
