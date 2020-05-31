package com.mutest.model.interfaces;

import lombok.Data;

/**
 * @author muguozheng
 * @date 2020/5/18 19:17
 * @Description: 分组信息
 * @modify
 */
@Data
public class GroupInfo {
    private Long id;
    private String groupName;
    private String groupLeader;
    private String description;
    private String createTime;
    private String modifyTime;
}
