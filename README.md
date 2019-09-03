## 码匠社区

## 资料
[Spring 文档](https://spring.io/guides)        
[Spring_web文档](https://spring.io/guides/gs/serving-web-content/)         
[es社区](https://elasticsearch.cn/explore/category-2)      
[GitHub_deploy_key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)        
[GitHub_OAuth_api](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)        
[BootStrop_v3文档](https://getbootstrap.com/docs/3.3/components/#navbar)  
[OKHttp_官网](https://square.github.io/okhttp/)      
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)    
[MyBatis-Spring-Bot](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html)
## 工具
[GitHub下载](https://git-scm.com/download)        
[Visual-Paradigm](https://www.visual-paradigm.com)      
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)
[Lombok](https://www.projectlombok.org/)    
[MyBatis Generator](http://www.mybatis.org/generator/)

##  脚本
```sql
---用户表
create table USER
(
	ID INTEGER auto_increment,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(64),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);
---问题表
create table question
(
	id int auto_increment,
	title varchar(50),                          ---标题
	description text,                           ---描述
	gmt_create bigint,                          ---创建时间
	gmt_modified bigint,                        ---修改时间
	creator int,                                ---创建人
	comment_count int default 0,                ---评论次数
	view_count int default 0,                   ---查看次数
	like_count int default 0,                   ---点赞次数
	tag varchar(256),                           ---标签
	constraint question_pk
		primary key (id)
);

```

##  说明 
### 1.使用技术：
#### 后端 
    Spring Boot     快速构建项目
    OKHttp          http连接
    GitHub API      登录授权
    FlyWay          数据库管理工具(执行migration命令  mvn flyway:migrate)
#### 前端
    BootStrop
 
## Bash
    FlyWay          mvn flyway:migrate
    MBG             mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate