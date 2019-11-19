<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter-api</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter-params</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter-engine</artifactId>
			<scope>test</scope>
		</dependency># spring boot 项目开发模板
### 主要功能
##### 1、实现用户权限管理模块
##### 2、实现定时任务模板
##### 3、接入OSA3.0模板
##### 4、接入actuator指标监控模板
##### 5、mybatis自动生成dao，mapper，model
### 项目特点
    
    ·代码结构清晰， 设计合理，对公共部分进行了抽取·

### 新项目接入接入模板 davin-king-words


##### 项目导入后 进入到 hanfu-common  执行 mvn package
##### 项目导入后 进入到 hanfu-web-utils  执行 mvn package



<?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE generatorConfiguration
                PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
                "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
	<!--导入属性配置 -->
	<properties resource="generator.properties"></properties>

	<!--指定特定数据库的jdbc驱动jar包的位置 -->
	<classPathEntry location="${jdbc.driverLocation}" />

	<context id="default" targetRuntime="MyBatis3">
		<property name="xmlFormatter"
			value="org.mybatis.generator.api.dom.DefaultXmlFormatter" />
		<property name="javaFormatter"
			value="org.mybatis.generator.api.dom.DefaultJavaFormatter" />
		<property name="javaFileEncoding" value="UTF-8" />
		<property name="beginningDelimiter" value="`" />
		<property name="endingDelimiter" value="`" />
		<plugin type="org.mybatis.generator.plugins.ToStringPlugin" />
		<plugin type="org.mybatis.generator.plugins.SerializablePlugin" />

		<!-- 注释 -->
		<commentGenerator>
			<property name="suppressAllComments" value="true" /><!-- 是否取消注释 -->
		</commentGenerator>

		<!--jdbc的数据库连接 -->
		<jdbcConnection driverClass="${jdbc.driverClass}"
			connectionURL="${jdbc.connectionURL}" userId="${jdbc.userId}"
			password="${jdbc.password}">
		</jdbcConnection>

		<!-- 非必需，类型处理器，在数据库类型和java类型之间的转换控制 -->
		<javaTypeResolver>
			<property name="forceBigDecimals" value="false" />
		</javaTypeResolver>


		<!-- Model模型生成器,用来生成含有主键key的类，记录类 以及查询Example类 targetPackage 指定生成的model生成所在的包名 
			targetProject 指定在该项目下所在的路径 -->
		<javaModelGenerator
			targetPackage="com.cloudwise.dosm.activiti.db.model"
			targetProject="src/main/java">

			<!-- 是否允许子包，即targetPackage.schemaName.tableName -->
			<property name="enableSubPackages" value="false" />
			<!-- 是否对model添加 构造函数 -->
			<property name="constructorBased" value="false" />
			<!-- 是否对类CHAR类型的列的数据进行trim操作 -->
			<property name="trimStrings" value="true" />
			<!-- 建立的Model对象是否 不可改变 即生成的Model对象不会有 setter方法，只有构造方法 -->
			<property name="immutable" value="false" />
			<!-- <property name="rootClass" value="com.cloudwise.dosm.activiti.db.model.BaseModel"/> -->
		</javaModelGenerator>

		<!--在resources目录下的mapper文件，生成数据库的表对应的xml文件 -->
		<sqlMapGenerator targetPackage="mapper"
			targetProject="src/main/resources">
			<property name="enableSubPackages" value="false" />
		</sqlMapGenerator>

		<!-- 生成dao层的java代码 type="ANNOTATEDMAPPER",生成Java Model 和基于注解的Mapper对象 type="MIXEDMAPPER",生成基于注解的Java 
			Model 和相应的Mapper对象 type="XMLMAPPER",生成SQLMap XML文件和独立的Mapper接口 -->
		<javaClientGenerator
			targetPackage="com.cloudwise.dosm.activiti.db.dao"
			targetProject="src/main/java" type="XMLMAPPER">
			<property name="enableSubPackages" value="false" />
		</javaClientGenerator>

		<!-- 数据表名及实体类名称 -->
		<table  tableName="work_order_field_type"
			domainObjectName="WorkOrderFieldType" 
			enableCountByExample="false"
			enableDeleteByPrimaryKey="false"
			enableUpdateByExample="false" enableDeleteByExample="false"
			enableSelectByExample="false" selectByExampleQueryId="false">
			<columnOverride column="is_del">
				<property name="property" value="deleteDefault" />
			</columnOverride>
		</table>
		<table tableName="work_order_flow"
			domainObjectName="workOrderFlow"
			enableCountByExample="false"
			enableUpdateByExample="false" enableDeleteByExample="false"
			enableSelectByExample="false" selectByExampleQueryId="false">
			<columnOverride column="is_del">
				<property name="property" value="deleteDefault" />
				
			</columnOverride>
		</table>
	</context>
</generatorConfiguration>
