2020-06-02 19:46:54,106 [http-nio-8086-exec-5] ==>  Preparing: select * from mutest.sys_user t where t.username = ? 
2020-06-02 19:46:54,116 [http-nio-8086-exec-5] ==> Parameters: admin(String)
2020-06-02 19:46:54,235 [http-nio-8086-exec-5] <==      Total: 1
2020-06-02 19:46:54,240 [http-nio-8086-exec-5] ==>  Preparing: select distinct p.* from mutest.sys_permission p inner join mutest.sys_role_permission rp on p.id = rp.permissionId inner join mutest.sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? order by p.sort 
2020-06-02 19:46:54,241 [http-nio-8086-exec-5] ==> Parameters: 1(Long)
2020-06-02 19:46:54,371 [http-nio-8086-exec-5] <==      Total: 31
2020-06-02 19:46:54,562 [taskExecutor-1] ==>  Preparing: insert into mutest.sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2020-06-02 19:46:54,565 [taskExecutor-1] ==> Parameters: 1(Long), 登陆(String), true(Boolean), null
2020-06-02 19:46:54,718 [taskExecutor-1] <==    Updates: 1
2020-06-02 19:47:00,839 [http-nio-8086-exec-8] ==>  Preparing: SELECT count(0) FROM mutest.interface_group 
2020-06-02 19:47:00,840 [http-nio-8086-exec-8] ==> Parameters: 
2020-06-02 19:47:00,878 [http-nio-8086-exec-8] <==      Total: 1
2020-06-02 19:47:55,264 [http-nio-8086-exec-7] ==>  Preparing: INSERT INTO mutest.interface_group(group_name,group_leader,description) VALUES (?,?,?) 
2020-06-02 19:47:55,264 [http-nio-8086-exec-7] ==> Parameters: 基础组(String), 穆国政(String), 对接主站，包括注册、登录、资产等基础服务(String)
2020-06-02 19:47:55,290 [http-nio-8086-exec-7] <==    Updates: 1
2020-06-02 19:47:55,316 [http-nio-8086-exec-4] ==>  Preparing: SELECT count(0) FROM mutest.interface_group 
2020-06-02 19:47:55,317 [http-nio-8086-exec-4] ==> Parameters: 
2020-06-02 19:47:55,372 [http-nio-8086-exec-4] <==      Total: 1
2020-06-02 19:47:55,373 [http-nio-8086-exec-4] ==>  Preparing: SELECT * FROM mutest.interface_group LIMIT ? 
2020-06-02 19:47:55,374 [http-nio-8086-exec-4] ==> Parameters: 10(Integer)
2020-06-02 19:47:55,382 [http-nio-8086-exec-4] <==      Total: 1
2020-06-02 19:51:12,042 [http-nio-8086-exec-8] ==>  Preparing: SELECT * FROM mutest.sys_user WHERE status = 1 
2020-06-02 19:51:12,042 [http-nio-8086-exec-8] ==> Parameters: 
2020-06-02 19:51:12,049 [http-nio-8086-exec-8] <==      Total: 1
2020-06-02 19:51:12,051 [http-nio-8086-exec-8] ==>  Preparing: SELECT id FROM mutest.sys_user a JOIN mutest.interface_group_user b ON a.id=b.user_id WHERE group_id = ? 
2020-06-02 19:51:12,051 [http-nio-8086-exec-8] ==> Parameters: 1(Long)
2020-06-02 19:51:13,128 [http-nio-8086-exec-8] <==      Total: 0
2020-06-02 19:51:23,122 [http-nio-8086-exec-4] ==>  Preparing: SELECT count(0) FROM mutest.sys_user 
2020-06-02 19:51:23,123 [http-nio-8086-exec-4] ==> Parameters: 
2020-06-02 19:51:23,130 [http-nio-8086-exec-4] <==      Total: 1
2020-06-02 19:51:23,131 [http-nio-8086-exec-4] ==>  Preparing: SELECT * FROM mutest.sys_user LIMIT ? 
2020-06-02 19:51:23,131 [http-nio-8086-exec-4] ==> Parameters: 10(Integer)
2020-06-02 19:51:23,140 [http-nio-8086-exec-4] <==      Total: 1
2020-06-02 19:51:26,025 [http-nio-8086-exec-8] ==>  Preparing: SELECT DISTINCT(val) FROM mutest.t_dict WHERE type = 'department' 
2020-06-02 19:51:26,025 [http-nio-8086-exec-8] ==> Parameters: 
2020-06-02 19:51:26,071 [http-nio-8086-exec-10] ==>  Preparing: SELECT DISTINCT(nickname) FROM mutest.sys_role 
2020-06-02 19:51:26,071 [http-nio-8086-exec-10] ==> Parameters: 
2020-06-02 19:51:26,115 [http-nio-8086-exec-8] <==      Total: 8
2020-06-02 19:51:26,161 [http-nio-8086-exec-10] <==      Total: 6
2020-06-02 19:52:02,886 [http-nio-8086-exec-9] ==>  Preparing: select * from mutest.sys_user t where t.username = ? 
2020-06-02 19:52:02,886 [http-nio-8086-exec-9] ==> Parameters: tester(String)
2020-06-02 19:52:02,938 [http-nio-8086-exec-9] <==      Total: 0
2020-06-02 19:52:03,016 [http-nio-8086-exec-9] ==>  Preparing: insert into mutest.sys_user(username, password, nickname, department, role, headImgUrl, phone, telephone, email, birthday, sex, status, description) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
2020-06-02 19:52:03,016 [http-nio-8086-exec-9] ==> Parameters: tester(String), $2a$10$iptyzziHXp3/Rnkk9gNn7eed.bUUXEyuNOoRWRM./4ShhFEbw/54W(String), 测试用户(String), 测试部(String), 成员(String), (String), (String), null, null, null, 1(String), 1(Integer), (String)
2020-06-02 19:52:03,049 [http-nio-8086-exec-9] <==    Updates: 1
2020-06-02 19:52:03,050 [http-nio-8086-exec-9] ==>  Preparing: SELECT id FROM mutest.sys_role WHERE nickname = ? 
2020-06-02 19:52:03,051 [http-nio-8086-exec-9] ==> Parameters: 成员(String)
2020-06-02 19:52:03,058 [http-nio-8086-exec-9] <==      Total: 1
2020-06-02 19:52:03,059 [http-nio-8086-exec-9] ==>  Preparing: insert into mutest.sys_role_user(userId, roleId) values(?,?) on duplicate key update roleId = values(roleId), userId = values(userId) 
2020-06-02 19:52:03,060 [http-nio-8086-exec-9] ==> Parameters: 2(Long), 6(Long)
2020-06-02 19:52:03,077 [http-nio-8086-exec-9] <==    Updates: 2
2020-06-02 19:52:03,098 [taskExecutor-2] ==>  Preparing: insert into mutest.sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2020-06-02 19:52:03,099 [taskExecutor-2] ==> Parameters: 1(Long), 保存用户(String), true(Boolean), null
2020-06-02 19:52:03,127 [taskExecutor-2] <==    Updates: 1
2020-06-02 19:52:03,166 [http-nio-8086-exec-2] ==>  Preparing: SELECT count(0) FROM mutest.sys_user 
2020-06-02 19:52:03,167 [http-nio-8086-exec-2] ==> Parameters: 
2020-06-02 19:52:03,176 [http-nio-8086-exec-2] <==      Total: 1
2020-06-02 19:52:03,177 [http-nio-8086-exec-2] ==>  Preparing: SELECT * FROM mutest.sys_user LIMIT ? 
2020-06-02 19:52:03,178 [http-nio-8086-exec-2] ==> Parameters: 10(Integer)
2020-06-02 19:52:03,234 [http-nio-8086-exec-2] <==      Total: 2
2020-06-02 19:53:09,749 [http-nio-8086-exec-8] ==>  Preparing: SELECT * FROM mutest.sys_user WHERE status = 1 
2020-06-02 19:53:09,750 [http-nio-8086-exec-8] ==> Parameters: 
2020-06-02 19:53:09,804 [http-nio-8086-exec-8] <==      Total: 2
2020-06-02 19:53:09,805 [http-nio-8086-exec-8] ==>  Preparing: SELECT id FROM mutest.sys_user a JOIN mutest.interface_group_user b ON a.id=b.user_id WHERE group_id = ? 
2020-06-02 19:53:09,805 [http-nio-8086-exec-8] ==> Parameters: 1(Long)
2020-06-02 19:53:09,848 [http-nio-8086-exec-8] <==      Total: 0
2020-06-02 19:53:40,126 [http-nio-8086-exec-9] ==>  Preparing: select count(1) from mutest.sys_role t 
2020-06-02 19:53:40,126 [http-nio-8086-exec-9] ==> Parameters: 
2020-06-02 19:53:40,182 [http-nio-8086-exec-9] <==      Total: 1
2020-06-02 19:53:40,184 [http-nio-8086-exec-9] ==>  Preparing: select * from mutest.sys_role t order by name asc limit ?, ? 
2020-06-02 19:53:40,184 [http-nio-8086-exec-9] ==> Parameters: 0(Integer), 10(Integer)
2020-06-02 19:53:40,238 [http-nio-8086-exec-9] <==      Total: 6
2020-06-02 19:53:41,888 [http-nio-8086-exec-3] ==>  Preparing: select * from mutest.sys_permission t order by t.sort 
2020-06-02 19:53:41,888 [http-nio-8086-exec-3] ==> Parameters: 
2020-06-02 19:53:41,904 [http-nio-8086-exec-3] <==      Total: 32
2020-06-02 19:53:41,965 [http-nio-8086-exec-6] ==>  Preparing: select * from mutest.sys_role t where t.id = ? 
2020-06-02 19:53:41,965 [http-nio-8086-exec-6] ==> Parameters: 1(Long)
2020-06-02 19:53:42,008 [http-nio-8086-exec-6] <==      Total: 1
2020-06-02 19:53:42,127 [http-nio-8086-exec-10] ==>  Preparing: select p.* from mutest.sys_permission p inner join mutest.sys_role_permission rp on p.id = rp.permissionId where rp.roleId = ? order by p.sort 
2020-06-02 19:53:42,128 [http-nio-8086-exec-10] ==> Parameters: 1(Long)
2020-06-02 19:53:42,145 [http-nio-8086-exec-10] <==      Total: 31
2020-06-02 20:13:42,666 [http-nio-8086-exec-4] ==>  Preparing: INSERT INTO mutest.interface_group_user(group_id,user_id) VALUES (?,?) on duplicate key update group_id = values(group_id), user_id = values(user_id) 
2020-06-02 20:13:42,667 [http-nio-8086-exec-4] ==> Parameters: 1(Long), 2(Integer)
2020-06-02 20:13:42,708 [http-nio-8086-exec-4] <==    Updates: 1
2020-06-02 20:14:45,755 [http-nio-8086-exec-8] ==>  Preparing: SELECT id,project_name projectName FROM mutest.interface_project 
2020-06-02 20:14:45,755 [http-nio-8086-exec-8] ==> Parameters: 
2020-06-02 20:14:45,891 [http-nio-8086-exec-8] <==      Total: 0
2020-06-02 20:14:45,892 [http-nio-8086-exec-8] ==>  Preparing: SELECT project_id projectId FROM mutest.interface_group_project WHERE group_id = ? 
2020-06-02 20:14:45,892 [http-nio-8086-exec-8] ==> Parameters: 1(Long)
2020-06-02 20:14:45,948 [http-nio-8086-exec-8] <==      Total: 0
2020-06-02 20:14:52,066 [http-nio-8086-exec-6] ==>  Preparing: SELECT count(0) FROM mutest.interface_project 
2020-06-02 20:14:52,067 [http-nio-8086-exec-6] ==> Parameters: 
2020-06-02 20:14:52,120 [http-nio-8086-exec-6] <==      Total: 1
2020-06-02 20:15:29,376 [http-nio-8086-exec-10] ==>  Preparing: SELECT COUNT(id) FROM mutest.interface_project WHERE project_name=? 
2020-06-02 20:15:29,377 [http-nio-8086-exec-10] ==> Parameters: 主站(String)
2020-06-02 20:15:29,385 [http-nio-8086-exec-10] <==      Total: 1
2020-06-02 20:15:29,386 [http-nio-8086-exec-10] ==>  Preparing: INSERT INTO mutest.interface_project(project_name,url,creator,description)VALUES(?,?,?,?) 
2020-06-02 20:15:29,386 [http-nio-8086-exec-10] ==> Parameters: 主站(String), https://www.test.com(String), 管理员(String), 注册、登录、资产等(String)
2020-06-02 20:15:29,400 [http-nio-8086-exec-10] <==    Updates: 1
2020-06-02 20:15:29,424 [http-nio-8086-exec-1] ==>  Preparing: SELECT count(0) FROM mutest.interface_project 
2020-06-02 20:15:29,425 [http-nio-8086-exec-1] ==> Parameters: 
2020-06-02 20:15:29,476 [http-nio-8086-exec-1] <==      Total: 1
2020-06-02 20:15:29,476 [http-nio-8086-exec-1] ==>  Preparing: SELECT * FROM mutest.interface_project LIMIT ? 
2020-06-02 20:15:29,476 [http-nio-8086-exec-1] ==> Parameters: 10(Integer)
2020-06-02 20:15:29,486 [http-nio-8086-exec-1] <==      Total: 1
2020-06-02 20:16:24,556 [http-nio-8086-exec-5] ==>  Preparing: SELECT COUNT(id) FROM mutest.interface_project WHERE project_name=? 
2020-06-02 20:16:24,557 [http-nio-8086-exec-5] ==> Parameters: 期货交易(String)
2020-06-02 20:16:24,622 [http-nio-8086-exec-5] <==      Total: 1
2020-06-02 20:16:24,623 [http-nio-8086-exec-5] ==>  Preparing: INSERT INTO mutest.interface_project(project_name,url,creator,description)VALUES(?,?,?,?) 
2020-06-02 20:16:24,623 [http-nio-8086-exec-5] ==> Parameters: 期货交易(String), https://www.future.com(String), 管理员(String), 期货交易(String)
2020-06-02 20:16:24,655 [http-nio-8086-exec-5] <==    Updates: 1
2020-06-02 20:16:24,703 [http-nio-8086-exec-6] ==>  Preparing: SELECT count(0) FROM mutest.interface_project 
2020-06-02 20:16:24,703 [http-nio-8086-exec-6] ==> Parameters: 
2020-06-02 20:16:24,722 [http-nio-8086-exec-6] <==      Total: 1
2020-06-02 20:16:24,723 [http-nio-8086-exec-6] ==>  Preparing: SELECT * FROM mutest.interface_project LIMIT ? 
2020-06-02 20:16:24,723 [http-nio-8086-exec-6] ==> Parameters: 10(Integer)
2020-06-02 20:16:24,734 [http-nio-8086-exec-6] <==      Total: 2
2020-06-02 20:18:51,776 [http-nio-8086-exec-1] ==>  Preparing: SELECT id,project_name projectName FROM mutest.interface_project 
2020-06-02 20:18:51,786 [http-nio-8086-exec-1] ==> Parameters: 
2020-06-02 20:18:51,802 [http-nio-8086-exec-1] <==      Total: 2
2020-06-02 20:18:51,803 [http-nio-8086-exec-1] ==>  Preparing: SELECT project_id projectId FROM mutest.interface_group_project WHERE group_id = ? 
2020-06-02 20:18:51,804 [http-nio-8086-exec-1] ==> Parameters: 1(Long)
2020-06-02 20:18:51,822 [http-nio-8086-exec-1] <==      Total: 0
2020-06-02 20:18:55,055 [http-nio-8086-exec-2] ==>  Preparing: INSERT INTO mutest.interface_group_project(group_id,project_id) VALUES (?,?) on duplicate key update group_id = values(group_id), project_id = values(project_id) 
2020-06-02 20:18:55,056 [http-nio-8086-exec-2] ==> Parameters: 1(Long), 2(Integer)
2020-06-02 20:18:55,117 [http-nio-8086-exec-2] <==    Updates: 1
2020-06-02 20:18:57,412 [http-nio-8086-exec-5] ==>  Preparing: SELECT id,project_name projectName FROM mutest.interface_project 
2020-06-02 20:18:57,413 [http-nio-8086-exec-5] ==> Parameters: 
2020-06-02 20:18:57,465 [http-nio-8086-exec-5] <==      Total: 2
2020-06-02 20:18:57,465 [http-nio-8086-exec-5] ==>  Preparing: SELECT project_id projectId FROM mutest.interface_group_project WHERE group_id = ? 
2020-06-02 20:18:57,466 [http-nio-8086-exec-5] ==> Parameters: 1(Long)
2020-06-02 20:18:57,473 [http-nio-8086-exec-5] <==      Total: 1
2020-06-02 20:19:59,836 [http-nio-8086-exec-8] ==>  Preparing: SELECT * FROM mutest.sys_user WHERE status = 1 
2020-06-02 20:19:59,837 [http-nio-8086-exec-8] ==> Parameters: 
2020-06-02 20:19:59,896 [http-nio-8086-exec-8] <==      Total: 2
2020-06-02 20:19:59,897 [http-nio-8086-exec-8] ==>  Preparing: SELECT id FROM mutest.sys_user a JOIN mutest.interface_group_user b ON a.id=b.user_id WHERE group_id = ? 
2020-06-02 20:19:59,897 [http-nio-8086-exec-8] ==> Parameters: 1(Long)
2020-06-02 20:19:59,948 [http-nio-8086-exec-8] <==      Total: 1
2020-06-02 20:24:43,479 [http-nio-8086-exec-8] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:24:43,479 [http-nio-8086-exec-8] ==> Parameters: 
2020-06-02 20:24:43,505 [http-nio-8086-exec-4] ==>  Preparing: SELECT count(0) FROM mutest.interface_list a JOIN mutest.interface_module b ON a.module_id = b.id JOIN mutest.interface_project c ON b.project_id = c.id 
2020-06-02 20:24:43,505 [http-nio-8086-exec-4] ==> Parameters: 
2020-06-02 20:24:43,512 [http-nio-8086-exec-8] <==      Total: 2
2020-06-02 20:24:43,685 [http-nio-8086-exec-4] <==      Total: 1
2020-06-02 20:24:45,782 [http-nio-8086-exec-7] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:24:45,782 [http-nio-8086-exec-7] ==> Parameters: 
2020-06-02 20:24:45,789 [http-nio-8086-exec-7] <==      Total: 2
2020-06-02 20:24:47,463 [http-nio-8086-exec-5] ==>  Preparing: SELECT DISTINCT(module_name) FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id WHERE project_name = ? 
2020-06-02 20:24:47,463 [http-nio-8086-exec-5] ==> Parameters: 主站(String)
2020-06-02 20:24:47,547 [http-nio-8086-exec-5] <==      Total: 0
2020-06-02 20:24:53,094 [http-nio-8086-exec-9] ==>  Preparing: SELECT count(0) FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id 
2020-06-02 20:24:53,095 [http-nio-8086-exec-9] ==> Parameters: 
2020-06-02 20:24:53,141 [http-nio-8086-exec-7] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:24:53,141 [http-nio-8086-exec-7] ==> Parameters: 
2020-06-02 20:24:53,148 [http-nio-8086-exec-9] <==      Total: 1
2020-06-02 20:24:53,154 [http-nio-8086-exec-7] <==      Total: 2
2020-06-02 20:29:32,947 [http-nio-8086-exec-9] ==>  Preparing: SELECT count(0) FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id 
2020-06-02 20:29:32,958 [http-nio-8086-exec-9] ==> Parameters: 
2020-06-02 20:29:32,964 [http-nio-8086-exec-4] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:29:32,965 [http-nio-8086-exec-4] ==> Parameters: 
2020-06-02 20:29:32,980 [http-nio-8086-exec-9] <==      Total: 1
2020-06-02 20:29:33,020 [http-nio-8086-exec-4] <==      Total: 2
2020-06-02 20:29:34,073 [http-nio-8086-exec-8] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:29:34,074 [http-nio-8086-exec-8] ==> Parameters: 
2020-06-02 20:29:34,081 [http-nio-8086-exec-8] <==      Total: 2
2020-06-02 20:30:09,858 [http-nio-8086-exec-7] ==>  Preparing: INSERT INTO mutest.interface_module(platform,project,module,host,description) VALUES(?,?,?,?,?) 
2020-06-02 20:30:09,859 [http-nio-8086-exec-7] ==> Parameters: null, null, null, null, 账户相关模块：注册、登录、实名认证等(String)
2020-06-02 20:39:03,644 [http-nio-8086-exec-6] ==>  Preparing: SELECT count(0) FROM mutest.interface_list a JOIN mutest.interface_module b ON a.module_id = b.id JOIN mutest.interface_project c ON b.project_id = c.id 
2020-06-02 20:39:03,644 [http-nio-8086-exec-6] ==> Parameters: 
2020-06-02 20:39:03,650 [http-nio-8086-exec-10] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:39:03,650 [http-nio-8086-exec-10] ==> Parameters: 
2020-06-02 20:39:03,662 [http-nio-8086-exec-6] <==      Total: 1
2020-06-02 20:39:03,668 [http-nio-8086-exec-10] <==      Total: 2
2020-06-02 20:42:17,786 [http-nio-8086-exec-6] ==>  Preparing: SELECT count(0) FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id 
2020-06-02 20:42:17,797 [http-nio-8086-exec-6] ==> Parameters: 
2020-06-02 20:42:17,806 [http-nio-8086-exec-3] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:42:17,806 [http-nio-8086-exec-3] ==> Parameters: 
2020-06-02 20:42:17,819 [http-nio-8086-exec-6] <==      Total: 1
2020-06-02 20:42:17,861 [http-nio-8086-exec-3] <==      Total: 2
2020-06-02 20:42:18,820 [http-nio-8086-exec-10] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:42:18,820 [http-nio-8086-exec-10] ==> Parameters: 
2020-06-02 20:42:18,828 [http-nio-8086-exec-10] <==      Total: 2
2020-06-02 20:42:53,498 [http-nio-8086-exec-2] ==>  Preparing: SELECT id FROM mutest.interface_project WHERE project_name = ? LIMIT 1 
2020-06-02 20:42:53,499 [http-nio-8086-exec-2] ==> Parameters: 主站(String)
2020-06-02 20:42:53,550 [http-nio-8086-exec-2] <==      Total: 1
2020-06-02 20:42:53,551 [http-nio-8086-exec-2] ==>  Preparing: INSERT INTO mutest.interface_module(project_id,module_name,creator,description) VALUES(?,?,?,?) 
2020-06-02 20:42:53,551 [http-nio-8086-exec-2] ==> Parameters: 1(Long), 账户模块(String), 管理员(String), 包括注册、登录、实名认证等(String)
2020-06-02 20:42:53,574 [http-nio-8086-exec-2] <==    Updates: 1
2020-06-02 20:42:53,601 [http-nio-8086-exec-1] ==>  Preparing: SELECT count(0) FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id 
2020-06-02 20:42:53,601 [http-nio-8086-exec-1] ==> Parameters: 
2020-06-02 20:42:53,611 [http-nio-8086-exec-1] <==      Total: 1
2020-06-02 20:42:53,614 [http-nio-8086-exec-1] ==>  Preparing: SELECT a.id, project_id, module_name, a.creator, a.description, a.create_time, a.modify_time, project_name FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id ORDER BY project_id LIMIT ? 
2020-06-02 20:42:53,615 [http-nio-8086-exec-1] ==> Parameters: 10(Integer)
2020-06-02 20:42:53,616 [http-nio-8086-exec-7] ==>  Preparing: SELECT DISTINCT(project_name) FROM mutest.interface_project 
2020-06-02 20:42:53,617 [http-nio-8086-exec-7] ==> Parameters: 
2020-06-02 20:42:53,655 [http-nio-8086-exec-1] <==      Total: 1
2020-06-02 20:42:53,660 [http-nio-8086-exec-7] <==      Total: 2
