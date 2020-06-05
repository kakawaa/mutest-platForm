package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.interfaces.CaseInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InterfaceCaseDao {
    /**
     * 获取接口用例列表
     *
     * @return
     */
    List<CaseInfo> getCaseList(@Param("projectId") Long projectId, @Param("interfaceId") Long interfaceId);

    /**
     * @param id 用例id
     * @return
     */
    CaseInfo getCaseInfoById(@Param("id") Long id);

    CaseInfo getCaseMainById(@Param("id") Long id);

    /**
     * 修改测试用例首页内容
     *
     * @param interfaceId 用例id
     * @param caseType    用例类型
     * @param delay       前置延迟
     * @param description 用例描述
     * @param id          用例id
     * @return
     */
    @Update("UPDATE mutest.interface_case SET interface_id=#{interfaceId},case_type=#{caseType},delay=#{delay},description=#{description} WHERE id=#{id}")
    int updateCaseMain(@Param("interfaceId") Long interfaceId, @Param("caseType") String caseType, @Param("delay") int delay, @Param("description") String description, @Param("id") Long id);

    /**
     * 修改用例详情
     *
     * @param request:headerData,bodyData,resultDemo,caseType,correlation,assertion,id
     * @return
     */
    int updateCaseInfo(JSONObject request);

    /**
     * 新增测试用例
     */

    void addCase(CaseInfo caseInfo);

    /**
     * 搜索用例
     *
     * @param request 前端搜索条件
     * @return
     */
    List<CaseInfo> searchCase(JSONObject request);

    /**
     * 查询特定接口标准用例的数量
     *
     * @param interfaceId 接口id
     * @return
     */
    @Select("SELECT COUNT(*) FROM mutest.interface_case WHERE interface_id=#{interfaceId} AND case_type='标准用例'")
    int getCaseCount(@Param("interfaceId") Long interfaceId);

    /**
     * 获取特定接口的标准用例部分信息
     *
     * @param interfaceId 接口id
     * @return
     */
    CaseInfo getStandardCaseByInterfaceId(@Param("interfaceId") Long interfaceId);

    /**
     * 根据接口信息查询创建者
     *
     * @param interfaceName 接口
     * @return 去重的创建者
     */
    @Select("SELECT DISTINCT(a.creator) FROM mutest.interface_case a JOIN mutest.interface_list b ON a.interface_id=b.id JOIN mutest.interface_module c ON b.module_id=c.id JOIN mutest.interface_project d ON c.project_id = d.id WHERE project_name = #{projectName} AND module_name= #{moduleName} AND interface_name= #{interfaceName}")
    List<String> getCreators(@Param("projectName") String projectName, @Param("moduleName") String moduleName, @Param("interfaceName") String interfaceName);

    /**
     * 根据接口信息和创建者信息获取用例特征
     *
     * @param request 查询信息：projectName moduleName interfaceName
     * @return
     */
    List<CaseInfo> getCaseFeature(JSONObject request);

    @Delete("DELETE FROM mutest.interface_case WHERE id=#{caseId}")
    int deleteCaseById(@Param("caseId") int caseId);
}