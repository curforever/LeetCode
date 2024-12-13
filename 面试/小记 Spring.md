# Spring 基础

### 包含的模块

### Spring vs SpringMVC vs SpringBoot



# 🍃Spring IoC



## IoC与DI

### 控制反转 IoC

是【思想】：指将对象的创建、初始化及依赖管理的控制权从应用代码转移到 Spring 容器。

#### 传统方式vsIoC方式

- 传统方式： 对象由程序创建，依赖通过硬编码传递（代码写死而不是通过配置动态控制），高耦合。

- IoC 方式： Spring 容器负责对象创建和依赖，模块之间只需要声明依赖，低耦合。

#### IoC入门案例

- **导入和配置。** 导入坐标`spring-context`后新建配置文件`applicationContext.xml`并配置bean（包括id，class）
- **获取IoC容器。** `new ClassPathXmlApplicationContext("applicationContext.xml")`
- **获取bean。** `ctx.getBean("bookDao")`

### 依赖注入 DI

是【实现方式】：指容器根据配置将所需的依赖注入到对象中

#### 注入什么类型

- **简单类型（如int、String）**。用`<property>`标签的`value`属性
- **引用类型**。用`<property>`标签的`ref`属性
- **集合**。用`<property>`标签的`<array>``<list>``<set>``<map>``<props>`子标签

#### 3种注入方式

1. Setter 注入
2. 构造器注入（推荐）
3. 自动装配（不推荐）



## DI的3种注入方式

1. Setter 注入。

   - 适用于可选依赖的注入、自己开发的模块的注入

   - 如果只有Setter方法，没有注入，会导致null对象出现。

   - 配置`<bean>`用`<property>`标签的`value`或`ref`属性。

2. 构造器注入（推荐）。

   - 适用于强制依赖的注入、第三方不含setter的模块的注入

   - 构造器注入结合 `@Qualifier` 或策略模式明确依赖关系，可以避免多个依赖注入混乱。

   - 配置`<bean>`用`<constructor-arg>`标签的`value`或`ref`属性。

3. 自动装配（不推荐）

   - 不推荐原因：隐式依赖关系导致的可测试性差和不易维护。具体来说，不能通过构造器或 setter 方法修改依赖关系，因为 Spring 会在创建 Bean 时自动完成依赖注入，测试代码无法显式地控制 Mock （模仿）对象的注入，只能使用 `@InjectMocks` 和 `@Mock` 等注解来让 Mockito 和 Spring 协作进行注入，或者采用构造器注入来手动控制 Mock 对象的注入。

   - 如果必须使用字段注入，可以通过一些工具（如 `ReflectionTestUtils`）来在测试中设置字段值

   - 配置`<bean>`用`autowire="xx"`。



## Bean 的作用域

### Bean 的作用域和适用场景

**2基础作用域 + 4仅Web 应用可用：**

1. **singleton（默认）：** IoC 容器中只有唯一的 bean 实例，适用于无状态的共享资源。
   - 作用域对生命周期的影响：Spring 管理整个生命周期，容器会自动调用销毁逻辑。
2. **prototype：** 每次获取bean都创建一个新实例，适用于短期使用的有状态且非线程安全的对象。
   - 作用域对生命周期的影响：Spring 只负责创建，由客户端决定何时销毁。
   - 应用：唯一标识符（UUID 、验证码、Token）、用户临时数据（表单数据缓存、文件上传临时存储）
3. **request：** 每个 HTTP 请求创建一个实例，如表单数据处理。
4. **session：** 每个会话创建一个实例，如用户信息缓存。
5. **application/global-session：** 在Web应用启动时创建一个bean，如统计数据。
6. **websocket：** 每个 WebSocket 会话创建一个实例，适用于WebSocket 连接状态管理。

### 有状态单例bean是非线程安全的

```java
// 定义了一个购物车类，其中包含一个保存用户的购物车里商品的 List
@Component
public class ShoppingCart {
    private List<String> items = new ArrayList<>();
    public void addItem(String item) {
        items.add(item);
    }
    public List<String> getItems() {
        return items;
    }
}
```


解决：

1. **避免可变成员变量**: 尽量设计 Bean 为无状态。
2. **使用`ThreadLocal`**: 将可变成员变量保存在 `ThreadLocal` 中，确保线程独立。
3. **使用同步机制**: 利用 `synchronized` 或 `ReentrantLock` 来进行同步控制，确保线程安全。

### ？如何配置和使用自定义的Scope

1. 实现 `org.springframework.beans.factory.config.Scope` 接口
2. 使用 `CustomScopeConfigurer` 或 `ConfigurableBeanFactory` 注册自定义 Scope
3. `@Scope("")`使用自定义 Scope



## Bean 的生命周期

### 生命周期阶段

1. **实例化：** 执行构造方法创建对象，分配内存
2. **属性注入：** set操作
3. **初始化** `implements InitializingBean` ，调用其 `afterPropertiesSet()` 方法。
  1. `Aware` 接口的依赖注入
  2. `BeanPostProcessor` 在初始化前后的处理
  3. `InitializingBean` 和 `init-method` 的初始化操作
4. 使用bean执行业务操作
5. **销毁** `implements DisposableBean` ，调用其 `destroy()` 方法。

   注意：容器关闭时才调用销毁逻辑，所以看不到，要想看到destroy-method执行：
   1. 暴力手动关闭：在JVM结束前通过`ClassPathXmlApplication接口close()方法`关闭容器
   2. 注册关闭钩子：`ClassPathXmlApplication接口registerShutdownHook()方法`

- 

### ？实例化的基本流程、FactoryBean



### ？属性注入的三种情况



### ？初始化`Aware` 接口的依赖注入



### 不同作用域的生命周期

- `singleton`：与容器生命周期一致，容器关闭时销毁。
- `prototype`：每次获取新实例，无全局生命周期管理，不执行销毁方法。

### ？生命周期扩展点、Bean的后处理器

- 使用 `BeanPostProcessor` 在初始化前后执行自定义逻辑。
- 使用 `BeanFactoryPostProcessor` 修改 Bean 的定义或属性。



## 其他

### 【声明Bean的注解】@Component 和 @Bean 的区别是什么？



### 【注入Bean的注解】@Autowired 和 @Resource 的区别是什么？

1. **`@Autowired`：** Spring 特有，默认**按类型（byType）** 自动注入。
   - 注解来源：Spring 特有，支持`@Autowired(required = false)`表示依赖项可选，即没有匹配到 Bean 时，不抛异常，而是注入 `null`。
   - 如果存在多个相同类型的 Bean，可结合 `@Qualifier` 注解指定注入的 Bean 名称。
2. **`@Resource`：** JSR-250 标准，默认**按名称（byName）** 注入。
   - JSR-250 标准，更适合标准化和跨框架使用。
   - 如果未指定名称且没有匹配的 Bean，会按类型注入。





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



# 🍃Spring 循环依赖

## 循环依赖的定义、举例

- 循环依赖是指多个 Bean **循环引用**导致 Spring 容器**无法正常初始化**它们。
- **例如**，A 要依赖 B，发现 B 还没创建，于是开始创建 B ，创建的过程发现 B 要依赖 A， 而 A 还没创建好呀，因为它要等 B 创建好，就这样它们俩就搁这卡 bug 了。

## 通过三级缓存解决循环依赖

### 哪三级缓存

1. **一级缓存（singletonObjects 单例对象）**：存放已经实例化、属性填充、初始化最终形态的 Bean。

2. **二级缓存（earlySingletonObjects 早期单例对象）**：存放提前暴露的、尚未属性填充的过渡 Bean。也就是三级缓存中`ObjectFactory`产生的对象，与三级缓存配合使用的，可以防止 AOP 的情况下，每次调用`ObjectFactory.getObject()`都是会产生新的代理对象的。

3. **三级缓存（singletonFactories 单例工厂）**：存放`ObjectFactory`，`ObjectFactory`的`getObject()`方法最终调用`getEarlyBeanReference()`方法可以生成原始 Bean 对象或者AOP代理对象。.

### Spring创建Bean流程

1. 当 Spring 创建 A 时发现 A 依赖了 B ，又去创建 B，B 依赖了 A ，又去创建 A；

2. 在 B 创建 A 的时候， A 此时还没有初始化完成，因此在 一二级缓存 中肯定没有 A；

3. 那么此时就去三级缓存中调用 `getObject()` 方法最终调用`getEarlyBeanReference()` 方法去生成并获取 A 的前期暴露的对象。

4. 然后就将这个 `ObjectFactory` 从三级缓存中移除，并且将前期暴露对象放入到二级缓存中，那么 B 就将它注入到依赖，来支持循环依赖。

### 只用两级缓存够吗？

- 在没有 AOP 的情况下，确实可以只使用一级和三级缓存来解决循环依赖问题。
- **当涉及到 AOP 时，二级缓存非常重要，避免了同一个 Bean 有多个代理对象的问题。**

## 通过`@Lazy`解决循环依赖

`@Lazy` 延迟加载

- **没有 `@Lazy` 的情况下**：在 Spring 容器初始化 `A` 时会立即尝试创建 `B`，而在创建 `B` 的过程中又会尝试创建 `A`，最终导致循环依赖。
- **使用 `@Lazy` 的情况下**： A 的构造器上添加 `@Lazy` 注解之后（延迟 Bean B 的实例化），Spring 不会立即创建 `B`，而是会注入一个 `B` 的代理对象。由于此时 `B` 仍未被真正初始化，`A` 的初始化可以顺利完成。等到 `A` 实例实际调用 `B` 的方法时，代理对象才会触发 `B` 的真正初始化，在注入 B 中的 A 属性时，此时 A 已经创建完毕了，就可以将 A 给注入进去。



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





