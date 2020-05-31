package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.FileInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileInfoDao {
    List<FileInfo> getFileList();

    List<FileInfo> searchFile(JSONObject request);

    @Select("select * from mutest.file_info t where t.id = #{id}")
    FileInfo getById(String id);

    @Insert("insert into mutest.file_info(id, content_type, size, uploader, path, url, type, description, create_time, modify_time) values(#{id}, #{contentType}, #{size}, #{uploader}, #{path}, #{url}, #{type}, #{description}, now(), now())")
    int save(FileInfo fileInfo);

    @Update("update mutest.file_info t set t.modify_time = now() where t.id = #{id}")
    int update(FileInfo fileInfo);

    @Delete("delete from mutest.file_info where id = #{id}")
    int delete(String id);

    int count(@Param("params") Map<String, Object> params);

    List<FileInfo> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
                        @Param("limit") Integer limit);

    @Select("SELECT DISTINCT(uploader) FROM mutest.file_info")
    List<String> getUploaders();

    @Select("SELECT DISTINCT(content_type) FROM mutest.file_info")
    List<String> getContentType();

    int updateFileInfo(JSONObject request);
}
