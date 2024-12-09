# Spring



## 1、Spring 基础 √

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



## 2、Spring IoC √

### 2.1、IoC和DI

### 2.2、Spring Bean

#### （1）Bean的**声明**注解

- **@Component 和 @Bean** 的区别是什么？	

#### （2） Bean 的注入方式和注入注解

- **@Autowired 和 @Resource** 的区别是什么？

#### （3）Bean 的作用域（线程安全吗）

#### （4）Bean 的生命周期



## 3、Spring AOP √

### 3.1、AOP工作机制

定义、目标、工作机制

### 3.2、AOP专业术语

切面(Aspect) = 切入点(Pointcut) + 通知(Advice)

### 3.2、AOP常见通知类型



## 4、Spring MVC √

### 4.1、M、V、C

- MVC VS MVVM

### 4.2、MVC的工作流程（核心组件）

#### （1）DispatcherServlet的地位（角色）、作用

#### （2）配置MultipartResolver，实现文件上传和下载

#### （3）@EnableWebMVC 提供的默认配置和高级特性

- 全局异常处理：通过 `@ControllerAdvice` +`@ExceptionHandler ` 或 自定义 `HandlerExceptionResolver` 捕获和处理全局异常。

#### （4）自定义WebMvcConfigurer



## 5、Spring 设计模式 √



## 6、Spring 循环依赖



## 7、Spring 事务



## 8、Spring Data JPA



## 9、Spring Security