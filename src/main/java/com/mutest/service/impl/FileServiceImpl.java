package com.mutest.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.FileInfoDao;
import com.mutest.model.FileInfo;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.CaseInfo;
import com.mutest.service.FileService;
import com.mutest.utils.FileUtil;
import com.mutest.utils.UserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

//    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Value("${files.path}")
    private String filesPath;
    @Resource
    private FileInfoDao fileInfoDao;

    @Override
    public JsonResult getFileList(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<FileInfo> pageInfo = new PageInfo(fileInfoDao.getFileList());
            return new JsonResult(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
//            log.error("获取文件列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    @Override
    public FileInfo save(MultipartFile file, String description) throws IOException {
        String fileOrigName = file.getOriginalFilename();
        System.out.println("fileOrigName:" + fileOrigName);
        if (!fileOrigName.contains(".")) {
            throw new IllegalArgumentException("缺少后缀名");
        }

        String md5 = FileUtil.fileMd5(file.getInputStream());
        FileInfo fileInfo = fileInfoDao.getById(md5);
        if (fileInfo != null) {
            fileInfoDao.update(fileInfo);
            return fileInfo;
        }

        fileOrigName = fileOrigName.substring(fileOrigName.lastIndexOf("."));
        String pathname = FileUtil.getPath() + md5 + fileOrigName;
        String fullPath = filesPath + pathname;
        FileUtil.saveFile(file, fullPath);

        long size = file.getSize();
        String contentType = file.getContentType();

        fileInfo = new FileInfo();
        fileInfo.setId(md5);
        fileInfo.setContentType(contentType);
        fileInfo.setUploader(UserUtil.getLoginUser().getNickname());
        fileInfo.setSize(size);
        fileInfo.setPath(fullPath);
        fileInfo.setUrl(pathname);
        fileInfo.setType(contentType.startsWith("image/") ? 1 : 0);
        fileInfo.setDescription(description);

        fileInfoDao.save(fileInfo);

        return fileInfo;
    }

    @Override
    public JsonResult delete(String id) {
        try {
            FileInfo fileInfo = fileInfoDao.getById(id);
            if (fileInfo != null) {
                String fullPath = fileInfo.getPath();
                FileUtil.deleteFile(fullPath);

                fileInfoDao.delete(id);
                return new JsonResult("0", "删除成功！");
            }
            return new JsonResult("0", "文件不存在！");
        } catch (Exception e) {
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
//      log.debug("删除文件：{}", fileInfo.getPath());
    }

    @Override
    public List<String> getUploaders() {
        try {
            return fileInfoDao.getUploaders();
        } catch (Exception e) {
//            log.error("获取文件列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    @Override
    public List<String> getContentType() {
        try {
            return fileInfoDao.getContentType();
        } catch (Exception e) {
//            log.error("获取文件列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    @Override
    public JsonResult updateFileInfo(JSONObject request) {
        try {
            fileInfoDao.updateFileInfo(request);
            return new JsonResult(0, "修改成功！");
        } catch (Exception e) {
//            log.error("获取文件列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    @Override
    public JsonResult searchFile(JSONObject request) {
        try {
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");
            PageHelper.startPage(pageNum, pageSize);

            PageInfo<CaseInfo> pageInfo = new PageInfo(fileInfoDao.searchFile(request));
            return new JsonResult(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
//            log.error("获取文件列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }
}
