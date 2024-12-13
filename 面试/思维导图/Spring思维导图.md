# Spring 基础

### 包含的模块

### Spring vs SpringMVC vs SpringBoot



# 🍃Spring IoC

## 谈谈对IoC的理解

IoC和DI

## Spring Bean

- 声明Bean的注解
  - @Component 和 @Bean 的区别是什么？	

- 注入Bean 的方式
  - 构造注入还是Setter注入
- 注入Bean的注解
  - @Autowired 和 @Resource 的区别是什么？

- Bean 的作用域
  - 线程安全吗
  - 自定义scope

- Bean 的生命周期
  - BeanPostProcessor接口
  - 初始化和销毁
  - FactoryBean
  - 实例化的基本流程
  - 属性注入的三种情况
  - 循环依赖、三级缓存
  - Aware接口

- Bean的配置
- Bean后处理器





# 🍃Spring AOP

## 谈谈对AOP的理解

- AOP专业术语：切面(Aspect) = 切入点(Pointcut) + 通知(Advice)
- AOP工作机制

- 区别：Spring AOP vs Aspect AOP

## AOP常见的通知类型

## 多个切面的执行顺序如何控制



# 🍃Spring MVC

## 谈谈对SpringMVC的理解

简介：M、V、C

MVC VS MVVM

## MVC的工作流程、MVC的工作原理、MVC的核心组件

DispatcherServlet的地位（角色）、作用

配置MultipartResolver，实现文件上传和下载

@EnableWebMVC 提供的默认配置和高级特性

- 全局异常处理：通过 `@ControllerAdvice` +`@ExceptionHandler ` 或 自定义 `HandlerExceptionResolver` 捕获和处理全局异常。

自定义WebMvcConfigurer

请求与响应

REST风格



## SSM整合（注解版）



## 拦截器



## 异常处理机制

- 统一异常处理怎么做

（1）处理流程

（2）处理方式

- 注解方式
  - @ControllerAdvice
    - @ExceptionHandler、@InitBinder、@ModelAttribute 

（3）机制原理

（4）常用的异常解析器





# Spring 设计模式



# Spring 循环依赖

谈谈你对的Spring 循环依赖理解

@Lazy

spring.main.allow-circular-references=true



# Spring 事务

## 管理事务的方式

## 事务传播行为

## 事务隔离级别

## @Transactional(rollbackFor = Exception.class)注解



# Spring Data JPA（重在实战）

如何使用 JPA 在数据库中非持久化一个字段？

JPA 的审计功能是做什么的？有什么用？

实体之间的关联关系注解有哪些？



# Spring Security

- 控制请求访问权限的方法
- hasRole 和 hasAuthority 的区别

- 如何对密码进行加密？
- 如何优雅更换系统使用的加密算法？





