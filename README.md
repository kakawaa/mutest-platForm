# mutest-platForm
测试平台

在本地部署项目的步骤如下：
1、在你自己的mysql中创建mutest库，执行mutest.sql（文件在resource目录下），修改yml配置文件，将localhost替换为你自己的mysql服务器地址
2、部署你自己的redis服务，并将服务器IP地址替换yml文件中的redis 的host（原配置为192.128.102.49），端口号视情况，一般默认端口都是6379
3、修改idea的maven配置，使项目使用你自己的本地maven仓库
4、在idea中打开项目后，等待依赖引用完成后，启动MutestApplication类，在浏览器中输入  IP:8086即可访问。管理员用户名：admin，密码：admin
