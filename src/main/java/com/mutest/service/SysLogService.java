package com.mutest.service;

import com.mutest.model.SysLogs;

/**
 * 日志service
 * 
 * @author 小威老师
 *
 *         2017年8月19日
 */
public interface SysLogService {

	void save(SysLogs sysLogs);

	void save(Long userId, String module, Boolean flag, String remark);

	void deleteLogs();
}
