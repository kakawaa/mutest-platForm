package com.mutest.model.interfaces;

import lombok.Data;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/3 15:33
 * @description
 * @modify
 */
@Data
public class InterfaceInfo {
    private Long id;
    private Long projectId;
    private Long moduleId;
    private String projectName;
    private String moduleName;
    private String interfaceName;
    private String url;
    private String path;
    private String method;
    private String paramType;
    private String creator;
    private String description;
    private String createTime;
    private String modifyTime;
}
