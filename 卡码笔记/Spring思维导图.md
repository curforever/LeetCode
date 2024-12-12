# Spring

## 1、Spring 基础

### 1.1、Spring模块

核心模块/核心组件/核心容器（Core Container）

- **spring-core**：Spring 框架基本的核心工具类。
- **spring-beans**：提供对 bean 的创建、配置和管理等功能的支持。
- **spring-context**：提供对国际化、事件传播、资源加载等功能的支持。
- **spring-expression**：提供对表达式语言（Spring Expression Language） SpEL 的支持，只依赖于 core 模块，不依赖于其他模块，可以单独使用。

### 1.2、Spring vs SpringMVC vs SpringBoot

1. SpringFramework包含多个功能模块，其中最重要的是Spring-Core
2. SpringMVC是多个功能模块之一，依赖于Spring-Core
3. Spring简化了J2EE的开发，SpringBoot简化了Spring开发，简化了MVC的配置



## 2、Spring IoC

### 2.1、IoC和DI

### 2.2、Spring Bean

#### （1）Bean的**声明**注解

@Component 和 @Bean 的区别是什么？	

#### （2） Bean 的注入方式和注入注解

@Autowired 和 @Resource 的区别是什么？

#### （3）Bean 的作用域（线程安全吗）

自定义scope

#### （4）Bean 的生命周期

BeanPostProcessor接口

初始化和销毁

FactoryBean

实例化的基本流程

属性注入的三种情况

循环依赖、三级缓存

Aware接口

#### （5）Bean的配置

profile属性切换环境

import标签

alias标签

自定义命名空间

多线程与异步处理@Async

#### （6）Bean后处理器





## 3、Spring AOP

### 3.1、AOP工作机制

定义、目标、工作机制

### 3.2、AOP专业术语

切面(Aspect) = 切入点(Pointcut) + 通知(Advice)

### 3.2、AOP常见通知类型



## 4、Spring MVC

### 4.1、简介：M、V、C

- MVC VS MVVM

### 4.2、MVC的工作流程（核心组件）

#### （1）DispatcherServlet的地位（角色）、作用

#### （2）配置MultipartResolver，实现文件上传和下载

#### （3）@EnableWebMVC 提供的默认配置和高级特性

- 全局异常处理：通过 `@ControllerAdvice` +`@ExceptionHandler ` 或 自定义 `HandlerExceptionResolver` 捕获和处理全局异常。

#### （4）自定义WebMvcConfigurer

### 4.3、请求与响应

#### （1）请求

* 请求映射路径：@RequestMapping("/user/save")
* 请求参数传递：@RequestParam
  * http://localhost/commonParam?name=itcast&age=15
  * 嵌套POJO参数：请求参数名与形参对象属性名相同，按照对象层次结构关系即可接收嵌套POJO属性参数
  * JSON：@RequestBody
    * @RequestBody与@RequestParam区别
  * 日期：@DateTimeFormat
    * Converter接口

#### （2）响应

* 响应json数据：@ResponseBody

### 4.4、REST风格

- @RequestMapping（@GetMapping @PostMapping @PutMapping @DeleteMapping）
- @PathVariable
- `@RequestBody`、`@RequestParam`、`@PathVariable`的区别和应用?
- @RestController = @Controller + @ResponseBody
- 其他
  - @ModelAttribute
  - @InitBinder

### 4.5、SSM整合（注解版）

### 4.6、拦截器

### 4.7、异常处理机制

（1）处理流程

（2）处理方式

- 注解方式
  - @ControllerAdvice
    - @ExceptionHandler、@InitBinder、@ModelAttribute 

（3）机制原理

（4）常用的异常解析器



## 5、Spring 设计模式



## 6、Spring 循环依赖

- 循环依赖

- @Lazy

- spring.main.allow-circular-references=true



## 7、Spring 事务

### 7.1、管理事务的方式

### 7.2、事务传播行为

### 7.3、事务隔离级别

### 7.4、@Transactional(rollbackFor = Exception.class)注解



## 8、Spring Data JPA

重要的是实战！

- 如何使用 JPA 在数据库中非持久化一个字段？
- JPA 的审计功能是做什么的？有什么用？
- 实体之间的关联关系注解有哪些？



## 9、Spring Security

### 9.1、控制请求访问权限的方法

### 9.2、hasRole 和 hasAuthority 的区别

- 如何对密码进行加密？
- 如何优雅更换系统使用的加密算法？





