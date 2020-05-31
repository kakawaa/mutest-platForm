package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.interfaces.CaseResult;
import com.mutest.model.interfaces.TestSetInfo;
import com.mutest.model.interfaces.TestSetResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InterfaceTestSetDao {
    /**
     * 获取测试集合列表
     *
     * @return
     */
    List<TestSetInfo> getTestSetList();

    /**
     * 根据测试集合id获取测试集合
     *
     * @param id 测试集合id
     * @return
     */
    TestSetInfo getTestSetById(@Param("id") Long id);

    /**
     * 搜索测试集合
     *
     * @param request 搜索条件：platform,project,creator
     * @return
     */
    List<TestSetInfo> searchTestSet(JSONObject request);

    /**
     * 保存测试集合执行结果
     *
     * @param testSetId   集合id
     * @param executeId   执行id
     * @param caseCount   用例数量
     * @param failedCount 失败用例数量
     * @param failedList  失败用例列表
     * @param executor    执行者
     */
    @Insert("INSERT INTO mutest.interface_set_result(test_set_id,execute_id,case_count,failed_count,failed_list,executor)VALUES(#{testSetId},#{executeId},#{caseCount},#{failedCount},#{failedList},#{executor})")
    void savaTestSetResult(@Param("testSetId") Long testSetId, @Param("executeId") String executeId, @Param("caseCount") int caseCount, @Param("failedCount") int failedCount, @Param("failedList") String failedList, @Param("executor") String executor);

    /**
     * 保存用例执行结果
     *
     * @param saveCaseResultSql 语句
     */
    @Insert("INSERT INTO mutest.interface_case_result(execute_id,case_id,interface_name,case_request,header_data,body_data,case_response,case_assertion,assertion_detail)VALUES ${saveCaseResultSql}")
    void saveCaseResult(@Param("saveCaseResultSql") String saveCaseResultSql);

    /**
     * 获取测试集合执行结果列表
     *
     * @return
     */
    List<TestSetResult> getTestSetResultList();

    List<TestSetResult> searchTestSetResult(JSONObject request);

    /**
     * 获取某个测试集合下的测试用例执行结果
     *
     * @param executeId 执行id
     * @return
     */
    List<CaseResult> getCaseResultList(@Param("executeId") String executeId);

    @Select("SELECT COUNT(id) FROM mutest.interface_test_set WHERE project_id=#{projectId} AND case_id_queue=#{caseIdQueue}")
    int testSetInsertCount(@Param("projectId") Long projectId, @Param("caseIdQueue") String caseIdQueue);

    @Select("SELECT COUNT(id) FROM mutest.interface_test_set WHERE id != #{testSetId} AND project_id=#{projectId} AND case_id_queue=#{caseIdQueue}")
    int testSetUpdateCount(@Param("testSetId") Long id, @Param("projectId") Long projectId, @Param("caseIdQueue") String caseIdQueue);

    /**
     * 修改测试集合
     *
     * @param projectId     项目id
     * @param caseIdQueue   用例id队列
     * @param caseNameQueue 用例名称队列
     * @param description   描述
     * @param id            集合id
     * @return
     */
    @Update("UPDATE mutest.interface_test_set SET project_id=#{projectId},case_id_queue=#{caseIdQueue},case_name_queue=#{caseNameQueue},description=#{description} WHERE id=#{id}")
    int updateTestSet(@Param("projectId") Long projectId, @Param("caseIdQueue") String caseIdQueue, @Param("caseNameQueue") String caseNameQueue, @Param("description") String description, @Param("id") Long id);

    /**
     * 新增测试集合
     *
     * @param projectId     项目id
     * @param caseIdQueue   用例id队列
     * @param caseNameQueue 用例名称队列
     * @param creator       创建者
     * @param description   描述
     */
    @Insert("INSERT INTO mutest.interface_test_set(project_id,case_id_queue,case_name_queue,creator,description)VALUES(#{projectId},#{caseIdQueue},#{caseNameQueue},#{creator},#{description})")
    void addTestSet(@Param("projectId") Long projectId, @Param("caseIdQueue") String caseIdQueue, @Param("caseNameQueue") String caseNameQueue, @Param("creator") String creator, @Param("description") String description);

    void deleteTestSetBatch(List<Long> testSetIds);
}
