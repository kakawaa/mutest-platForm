package com.mutest.service;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.FileInfo;
import com.mutest.model.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    JsonResult getFileList(int pageNum, int pageSize);

    FileInfo save(MultipartFile file, String description) throws IOException;

    JsonResult delete(String id);

    List<String> getUploaders();

    List<String> getContentType();

    JsonResult updateFileInfo(JSONObject request);

    JsonResult searchFile(JSONObject request);
}
