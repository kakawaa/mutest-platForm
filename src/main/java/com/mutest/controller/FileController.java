package com.mutest.controller;

import com.alibaba.fastjson.JSONObject;
import com.mutest.annotation.LogAnnotation;
import com.mutest.dto.LayuiFile;
import com.mutest.dto.LayuiFile.LayuiFileData;
import com.mutest.model.FileInfo;
import com.mutest.model.JsonResult;
import com.mutest.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/fileList", method = RequestMethod.GET)
    public JsonResult getFileList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return fileService.getFileList(pageNum, pageSize);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FileInfo uploadFile(MultipartFile file, String description) throws IOException {
        return fileService.save(file, description);
    }

    /**
     * layui富文本文件自定义上传
     *
     * @param file
     * @param domain
     * @return
     * @throws IOException
     */

    @PostMapping("/layui")
    @ApiOperation(value = "layui富文本文件自定义上传")
    public LayuiFile uploadLayuiFile(MultipartFile file, String domain) throws IOException {
        FileInfo fileInfo = fileService.save(file, "请输入图片描述");

        LayuiFile layuiFile = new LayuiFile();
        layuiFile.setCode(0);
        LayuiFileData data = new LayuiFileData();
        layuiFile.setData(data);
        data.setSrc(domain + "/statics" + fileInfo.getUrl());
        data.setTitle(file.getOriginalFilename());

        return layuiFile;
    }

//	@GetMapping
//	@ApiOperation(value = "文件查询")
//	@PreAuthorize("hasAuthority('sys:file:query')")
//	public PageTableResponse listFiles(PageTableRequest request) {
//		return new PageTableHandler(new CountHandler() {
//
//			@Override
//			public int count(PageTableRequest request) {
//				return fileInfoDao.count(request.getParams());
//			}
//		}, new ListHandler() {
//
//			@Override
//			public List<FileInfo> list(PageTableRequest request) {
//				List<FileInfo> list = fileInfoDao.list(request.getParams(), request.getOffset(), request.getLimit());
//				return list;
//			}
//		}).handle(request);
//	}

    @LogAnnotation
    @DeleteMapping("/{id}")
    @ApiOperation(value = "文件删除")
    @PreAuthorize("hasAuthority('sys:file:del')")
    public JsonResult delete(@PathVariable String id) {
        return fileService.delete(id);
    }

    @RequestMapping(value = "/getUploaders", method = RequestMethod.GET)
    public List<String> getUploaders() {
        return fileService.getUploaders();
    }

    @RequestMapping(value = "/getContentType", method = RequestMethod.GET)
    public List<String> getContentType() {
        return fileService.getContentType();
    }

    @RequestMapping(value = "/updateFileInfo", method = RequestMethod.POST)
    public JsonResult updateFileInfo(@RequestBody JSONObject request) {
        return fileService.updateFileInfo(request);
    }

    @RequestMapping(value = "/searchFile", method = RequestMethod.POST)
    public JsonResult searchFile(@RequestBody JSONObject request) {
        return fileService.searchFile(request);
    }
}
