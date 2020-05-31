package com.mutest.service.interfaces;

import org.springframework.stereotype.Service;

/**
 * @author muguozheng
 * @date 2020/5/21 15:47
 * @Description: 用户权限校验
 * @modify
 */
@Service
public interface PermissionService {
    void interfaceCaseEditAuth(Long projectId, String creator);

    void interfaceCaseAddAuth(String projectName);
}
