package com.mutest.model.interfaces;

import lombok.Data;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/9/4 16:22
 * @description
 * @modify
 */
@Data
public class ModuleInfo {
    private Long id;
    private Long projectId;
    private String projectName;
    private String moduleName;
    private String creator;
    private String description;
    private String createTime;
    private String modifyTime;
}
