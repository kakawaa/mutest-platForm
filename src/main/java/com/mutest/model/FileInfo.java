package com.mutest.model;

import lombok.Data;

@Data
public class FileInfo {
    private String id;
    private String contentType;
    private long size;
    private String path;
    private String url;
    private Integer type;
    private String uploader;
    private String description;
    private String createTime;
    private String modifyTime;
}
