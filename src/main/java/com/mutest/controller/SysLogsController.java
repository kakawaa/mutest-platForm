package com.mutest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mutest.dao.base.SysLogsDao;
import com.mutest.model.SysLogs;
import com.mutest.page.table.PageTableHandler;
import com.mutest.page.table.PageTableHandler.CountHandler;
import com.mutest.page.table.PageTableHandler.ListHandler;
import com.mutest.page.table.PageTableRequest;
import com.mutest.page.table.PageTableResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/3/26 14:15
 * @description 系统日志
 * @modify
 */
@Api(tags = "日志")
@RestController
@RequestMapping("/logs")
public class SysLogsController {

    @Autowired
    private SysLogsDao sysLogsDao;

    @GetMapping
    @PreAuthorize("hasAuthority('sys:log:query')")
    @ApiOperation(value = "日志列表")
    public PageTableResponse list(PageTableRequest request) {
        return new PageTableHandler(new CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return sysLogsDao.count(request.getParams());
            }
        }, new ListHandler() {

            @Override
            public List<SysLogs> list(PageTableRequest request) {
                return sysLogsDao.list(request.getParams(), request.getOffset(), request.getLimit());
            }
        }).handle(request);
    }

}
