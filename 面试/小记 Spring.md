# Spring 基础



## Spring的重要模块

### Core Container（核心容器）

- **Spring Core**：提供了依赖注入（Dependency Injection, DI）和控制反转（Inversion of Control, IoC）的实现，所有其他Spring模块的基础，别的模块都会依赖此模块。
- **Spring Beans**：负责管理Bean的定义和生命周期。通过IoC容器完成Bean的创建、依赖注入、初始化、销毁等操作。
- **Spring Context**：基于Core和Beans的高级容器，提供了类似JNDI的上下文功能，还包含了国际化、事件传播、资源访问等功能。
  ApplicationContext主要提供了五大功能：
  - 核心容器 BeanFactory
  - 国际化 MessageSource：国际化就是把某个表示翻译为一种特定的语言，翻译为哪种语言通常通过浏览器请求头携带。
  - 资源获取 ResourceLoader：`context.getResources("file:xxx");`
  - 环境信息 EnvironmentCapable“：`context.getEnvironment().getProperty("java_home");`
  - 事件发布 ApplicationEventPublisher
- **Spring Expression Language（SpEL）**：一个强大的表达式语言，用于在运行时查询和操作对象的值。

### AOP（面向切面编程）

提供面向切面编程的功能，可以在方法执行前后或抛出异常时动态插入额外的逻辑，比如日志记录、权限验证、事务管理等。

### Data Access（数据访问）

- **Spring JDBC**：简化了原生JDBC的管理连接、资源释放和异常处理。
- **Spring ORM**：支持与主流ORM框架（如Hibernate、JPA、MyBatis等）集成，简化持久层开发。
- **Spring Transaction（事务管理）**：提供声明式和编程式的事务管理机制，与数据库操作密切结合。

### Web层

- **Spring Web**：提供基础的Web开发支持，包括Servlet API的集成，适用于构建MVC架构。
- **Spring MVC**：实现了Model-View-Controller（MVC）模式的框架，用于构建基于HTTP请求的Web应用。它是一个常用的模块，支持注解驱动的Web开发。
- **Spring WebFlux**：提供基于Reactive Streams的响应式编程模型，专为高并发的异步非阻塞请求设计。

### 扩展模块

- **Spring Batch**：用于批处理的框架，支持大规模数据的处理与分块执行。
- **Spring Integration**：提供消息驱动的应用程序集成方案，适用于构建企业集成架构（EAI）。
- **Spring Cloud**：用于构建微服务架构的模块集合，支持分布式系统中的服务注册、配置管理、服务调用等功能。



## Spring、SpringMVC、SpringBoot的区别

1. Spring（SpringFramework）：包含多个核心模块、简化了J2EE的开发
2. SpringMVC：多个功能模块之一，依赖于spring-Core
3. SpringBoot：自动配置常用Spring模块，简化初始化和配置工作。



---



# 🍃Spring IoC



## 控制反转 IoC

控制反转 IoC是【思想】：指将对象的创建、初始化及依赖管理的控制权从程序代码转移到 Spring 容器。

- **控制**的是对象的创建，创建对象且注入依赖的动作被**反转**

> - **导入和配置。** 导入坐标`spring-context`后新建配置文件`applicationContext.xml`并配置bean（包括id，class）
> - **获取IoC容器。** `new ClassPathXmlApplicationContext("applicationContext.xml")`
> - **获取bean。** `ctx.getBean("bookDao")`



## 依赖注入DI

依赖注入 DI是【实现方式】：指容器根据配置将所需的依赖注入到对象中

### 依赖注入的3种方式

1. Setter 注入
   - 适用于可选依赖的注入、自己开发的模块的注入

   - 如果只有Setter方法，没有注入，会导致null对象出现。

   - 配置`<bean>`用`<property>`标签的`value`或`ref`属性。
2. 构造器注入（推荐）

   - 适用于强制依赖的注入、第三方不含setter的模块的注入

   - 构造器注入结合 `@Qualifier` 或策略模式明确依赖关系，可以避免多个依赖注入混乱。

   - 配置`<bean>`用`<constructor-arg>`标签的`value`或`ref`属性。
3. 自动装配（不推荐）

   - **不推荐原因：**隐式依赖关系导致的可测试性差和不易维护。具体来说，不能通过构造器或 setter 方法修改依赖关系，因为 Spring 会在创建 Bean 时自动完成依赖注入，测试代码无法显式地控制 Mock （模仿）对象的注入，只能使用 `@InjectMocks` 和 `@Mock` 等注解来让 Mockito 和 Spring 协作进行注入，或者采用构造器注入来手动控制 Mock 对象的注入。
   - 如果必须使用字段注入，可以通过一些工具（如 `ReflectionTestUtils`）来在测试中设置字段值
   - 配置`<bean>`用`autowire="xx"`。
4. 接口回调注入，就是实现 Spring 定义的一些内建接口，例如 BeanFactoryAware，会进行 BeanFactory 的注入



### 属性注入的三种情况

1. **简单类型（如int、String）**。用`<property>`标签的`value`属性
2. **引用类型**。用`<property>`标签的`ref`属性
3. **集合**。用`<property>`标签的`<array>``<list>``<set>``<map>``<props>`子标签



### 自动装配的4种方式

1. no（默认）：不自动装配，需要显式地定义依赖。 
2. byName：通过 Bean 名称进行自动装配。
3. byType：通过 Bean 类型进行自动装配。 
4. constructor：通过构造函数进行自动装配。



### 【自动装配】@Qualifier和@Primary的区别

- @Qualifier在依赖注入时**消除歧义**，当一个类型有多个实现时，可以通过 @Qualifier 指定名称选择对应的实现 Bean。

- @Primary 注解用于指定当有多个候选 Bean 时默认注入哪个 Bean，也就是指定了第一顺位。

- 当结合 @Qualifier 使用时，可以覆盖 @Primary 的默认行为。





## Bean注册到容器的4种方式

1. 基于 XML 的配置

2. 基于 `@Component` 注解及其衍生注解 `@Component`、`@Service` 、`@Controller`、`@Repository` 
3. 基于 `@Configuration`声明配置类 和 `@Bean` 定义 Bean
4. 基于 `@Import` 注解将普通类导入到 Spring 容器中，这些类会自动被注册为 Bean。



## Bean 的作用域（Scope）

通过 Spring 容器实例化、组装和管理的 Java 对象都可以被称为 Spring Bean。

### 作用域及其适用场景

**2基础作用域 + 4仅Web 应用可用：**

1. **singleton（默认）：** IoC 容器中只有唯一的 bean 实例，适用于无状态的共享资源。
2. **prototype：** 每次获取bean都创建一个新实例，适用于短期使用的有状态且非线程安全的对象。
   - 应用：唯一标识符（UUID 、验证码、Token）、用户临时数据（表单数据缓存、文件上传临时存储）
3. **request：** 每个 HTTP 请求创建一个实例，如表单数据处理。
4. **session：** 每个会话创建一个实例，如用户信息缓存。
5. **application/global-session：** 在Web应用启动时创建一个bean，如统计数据。
6. **websocket：** 每个 WebSocket 会话创建一个实例，适用于WebSocket 连接状态管理。

### 作用域与生命周期的关系

**singleton**：Bean 的生命周期与 Spring 容器的生命周期一致。在容器启动时创建，在容器关闭时销毁。

**prototype**：每次请求时创建一个新的 Bean 实例，容器只负责创建，不管理其生命周期（不调用销毁方法），由客户端决定何时销毁。

Bean 的生命周期分别与 HTTP 请求、会话、应用或 WebSocket 的生命周期一致。

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

### ❓配置和使用自定义的Scope

1. 实现 `org.springframework.beans.factory.config.Scope` 接口
2. 使用 `CustomScopeConfigurer` 或 `ConfigurableBeanFactory` 注册自定义 Scope
3. `@Scope("")`使用自定义 Scope



## Bean 的生命周期

### 生命周期阶段

`BeanFactoryPostProcessor` 允许开发者在 Bean 实例化之前，修改 Bean 的定义信息（如属性值），它在所有 Bean 实例化之前执行。

1. **实例化：** 过反射机制创建，Spring 容器根据配置文件或注解（ `@Component`、`@Bean` 或者 XML 中的 `<bean>` ）实例化 Bean 对象。
2. **依赖注入：**Spring 将依赖（通过构造器、setter 方法或字段注入）注入到 Bean 实例中。
3. **初始化：** 如果 Bean 实现了`InitializingBean接口的afterPropertiesSet()方法` 或者使用了 `@PostConstruct` 注解，Spring 会在**依赖注入完成后**调用相应的初始化方法。

   1. **Aware注入**：Bean 可以实现 Aware 接口，从而在初始化之前获取 Bean 的名称、BeanFactory、ApplicationContext 等容器资源。
      - BeanNameAware 
      - BeanClassLoaderAware 
      - BeanFactoryAware 
   2. **BeanPostProcessor的前置方法postProcessBeforeInitialization**：在 Bean 初始化前执行前置操作，如代理包装、AOP 切面等。
   3. **初始化**：调用 InitializingBean 接口的 afterPropertiesSet() 方法或通过 init-method 属性指定的初始化方法。
   4. **BeanPostProcessor后置方法postProcessAfterInitialization**：在 Bean 初始化后，进一步修改或替换 Bean 实例。

4. **使用**
5. **销毁：**如果 Bean 实现了 `DisposableBean接口的destroy()方法` 或使用了 `@PreDestroy` 注解，Spring 会在**容器关闭时**调用销毁方法。

   注意：容器关闭时才调用销毁逻辑，所以看不到，要想看到destroy-method执行：
   1. 暴力手动关闭：在JVM结束前通过`ClassPathXmlApplication接口close()方法`关闭容器
   2. 注册关闭钩子：`ClassPathXmlApplication接口registerShutdownHook()方法`



### 常用的生命周期应用场景

1. **连接池管理**：在初始化阶段创建数据库连接池，在销毁阶段关闭连接池，以确保资源的有效管理。

2. **缓存初始化**：在 `afterPropertiesSet` 或 `@PostConstruct` 中加载缓存数据，在销毁阶段清理缓存。

3. **动态代理创建**：在 `postProcessBeforeInitialization` 中为 Bean 创建动态代理，以实现 AOP 功能。



### ❓实例化/初始化的基本流程/具体过程

1. **启动阶段：**

   - 配置加载：加载配置文件或配置类，IoC 容器首先需要加载应用程序的配置信息，这些配置信息可以是 XML 配置文件、Java 配置类或注解配置等方式。

   - 创建容器：Spring 创建 IOC 容器（BeanFactory 、 ApplicationContext），准备加载和管理 Bean。

   - > 细节：
     >
     > - 配置文件解析：Spring 使用 XmlBeanDefinitionReader 解析 XML 配置文件，使用 AnnotatedBeanDefinitionReader 解析注解配置类。
     > - 上下文初始化：是通过创建具体的 ApplicationContext 实现（如 ClassPathXmlApplicationContext、AnnotationConfigApplicationContext），调用其 refresh 方法启动容器。

2. **Bean 定义注册阶段：**

   - 解析和注册：BeanDefinitionReader 读取解析配置中的 Bean 定义，并将其注册到容器中，形成 BeanDefinition 对象。

   - > 细节：
     >
     > - BeanDefinition：每个 BeanDefinition 对象包含 Bean 的类名、作用域（singleton/prototype）、依赖关系、初始化方法、销毁方法等信息。
     > - BeanDefinitionRegistry：Spring 将 BeanDefinition 对象注册到 BeanDefinitionRegistry，形成 Bean 的定义数据结构。

3. **实例化和依赖注入：**

   - 实例化：根据 BeanDefinition 创建 Bean 的实例。

   - 依赖注入：根据 BeanDefinition 中的依赖关系，可以通过构造函数注入、Setter 注入或字段注入，将依赖注入到 Bean 中。

   - > 细节：
     >
     > - 实例化策略：Spring 使用 InstantiationStrategy 接口来实例化 Bean，常用的实现是 CglibSubclassingInstantiationStrategy。（还有一个是jdk的）
     > - 依赖解析：Spring 通过 DependencyDescriptor 对象描述依赖项，并在容器中查找匹配的 Bean 进行注入。

4. **初始化：**

   - BeanPostProcessor 处理：这些处理器会在 Bean 初始化生命周期中加入定义的处理逻辑，postProcessBeforeInitialization 和 postProcessAfterInitialization 分别在 Bean 初始化前后被调用。

   - Aware 接口调用：如果 Bean 实现了 Aware 接口（如 BeanNameAware、BeanFactoryAware），Spring 会回调这些接口，传递容器相关信息。

   - 初始化方法调用：调用 Bean 的初始化方法（如通过 @PostConstruct 注解标注的方法，或实现 InitializingBean 接口的 bean 会被调用 afterPropertiesSet 方法）。



### ❓初始化方法的执行顺序

1）BeanPostProcessor#postProcessBeforeInitialization

2）@PostConstruct：标注在方法上的 @PostConstruct 注解会在 Bean 的属性设置完成之后立即被调用。

3）afterPropertiesSet：实现 InitializingBean 接口的 afterPropertiesSet 方法会在 @PostConstruct 方法之后被调用。

4）initMethod：在 xml 和 @Bean 注解里，都可以通过 initMethod 执行初始化方法。

5）BeanPostProcessor#postProcessAfterInitialization。



### ❓初始化`Aware` 接口的依赖注入



### ❓生命周期扩展点、Bean的后处理器

- 正常代理对象的生成是基于后置处理器，是**在被代理的对象初始化后期调用生成的**，**所以如果你提早代理了其实是违背了 Bean 定义的生命周期**。
- 使用 `BeanPostProcessor` 在初始化前后执行自定义逻辑。
- 使用 `BeanFactoryPostProcessor` 修改 Bean 的定义或属性。



### BeanFactory

- BeanFactory是 **IOC 的底层容器**，提供了创建和管理 Bean 的基本功能。本身只是一个接口，一般我们所述的 BeanFactory 指的是它实现类：
  - **DefaultListableBeanFactory**：BeanFactory 的默认实现，支持所有基本的依赖注入特性，如构造器注入、setter 注入等。
  - **XmlBeanFactory（已废弃）**：已经在 Spring 3.x 中被淘汰，现推荐使用 ApplicationContext。

- **核心概念**：BeanFactory 负责从配置源（XML、Java 配置类、注解等）中读取 Bean 的定义，并负责创建、管理这些 Bean 的生命周期。
- **延迟加载**：BeanFactory 的一个重要特性是**延迟初始化**，即它只会在 Bean 首次请求时才会实例化该 Bean，而不是在容器启动时就立即创建所有的 Bean。



### FactoryBean

#### FactoryBean 的使用场景

- **复杂对象创建**：如果某个 Bean 的创建过程比较复杂，比如需要**动态加载配置文件**或**执行其他逻辑**才能实例化对象。
- **代理对象生成**：Spring AOP 使用 FactoryBean 来生成代理对象，使得 AOP 切面能够透明地应用于目标对象。
- **条件性 Bean**：在**某些条件下返回不同的 Bean 实例**，例如**根据应用的环境配置不同的数据库连接池或者日志框架实现**。

#### FactoryBean 与普通 Bean 的区别

- **创建逻辑不同**：普通的 Bean 直接由 Spring 容器管理，而 FactoryBean 通过自定义的 `getObject()` 方法创建实际的对象。
- **动态代理和复杂对象**：FactoryBean 适用于创建动态代理或复杂的 Bean，而普通 Bean 通常只处理简单的对象创建。







## 其他

### @Component 和 @Bean 的区别是什么？

声明Bean的注解

### @Autowired 和 @Resource 的区别是什么？

注入Bean的注解

1. **`@Autowired`：** Spring 特有，默认**按类型（byType）** 自动注入。
   - 注解来源：Spring 特有，支持`@Autowired(required = false)`表示依赖项可选，即没有匹配到 Bean 时，不抛异常，而是注入 `null`。
   - 如果存在多个相同类型的 Bean，可结合 `@Qualifier` 注解指定注入的 Bean 名称。
2. **`@Resource`：** JSR-250 标准，默认**按名称（byName）** 注入。
   - JSR-250 标准，更适合标准化和跨框架使用。
   - 如果未指定名称且没有匹配的 Bean，会按类型注入。

### ？profile属性切换环境

### ？import标签

### ？alias标签

### ？自定义命名空间

### ？多线程与异步处理@Async



# 🍃Spring AOP



## AOP核心思想、通俗理解、应用场景

AOP（Aspect-Oriented Programming，面向切面编程）

- 核心思想：通过切面Aspect将与业务逻辑无关的通用功能（如日志记录、安全检查、事务管理等）模块化，并将其应用到应用程序中的多个地方，避免代码重复。
- 通俗理解：通过代理的方式，在调用想要的对象方法时候，进行拦截处理，执行切入的逻辑，然后再调用真正的方法实现。
- 应用场景
  1. **日志记录**：通过 AOP 可以将日志逻辑分离到切面中，使日志代码与业务代码解耦。
  2. **事务管理**：可以通过 AOP 实现事务管理，确保在特定方法执行时开启事务，并在方法执行成功或失败后提交或回滚事务。
  3. **安全检查**：AOP 可以用于权限验证，在方法执行前检查用户是否具有相应权限。
  4. **性能监控**：通过环绕通知（Around advice），可以记录方法的执行时间，帮助监控应用性能。



## AOP相关术语、主要组成

**目标/代理 <—织入— 切面(Aspect) = 切点(Pointcut) + 通知(Advice)**

- **切面（Aspect）：**定义横切关注点的模块，封装了不同模块共享的功能
- **切点（Pointcut）：** 切点是一个表达式，通过这个表达式可以找到想要织入的哪些方法
  - **连接点（Join Point）：** 目标对象的所属类中，定义的所有方法均为连接点
  - 切入点一定是连接点，连接点不一定是切入点
- **通知（Advice）：** 拦截到目标对象的连接点之后要执行的操作
- **织入（Weaving）：** 将切面应用到目标对象上的过程，Spring AOP 是在运行时进行织入的
- **目标（Target）：** 被切面增强的对象，也就是原本的业务类
- **代理（Proxy）：** AOP 通过生成代理对象来增强目标对象的方法，代理对象包含目标对象的原始方法和增强逻辑



## AOP常见的通知类型

- **前置通知（Before）**：在方法执行之前执行的操作。
- **后置通知（After）**：在方法执行之后执行的操作。
- **环绕通知（Around）**：在方法执行前后都可以执行的操作，可以控制方法是否执行。
- **异常通知（AfterThrowing）**：在方法抛出异常后执行的操作。
- **返回通知（AfterReturning）**：在方法成功返回后执行的操作。

> Spring4版本
>
> 1. 环绕之前通知
> 2. 前置通知Before
> 3. 被增强的方法
> 4. **环绕之后通知 / 无（异常时）**
> 5. **After最终通知**
> 6. **AfterReturning 后置通知 / AfterThrowing 异常通知（异常时）**

> Spring5版本
>
> 1. 环绕之前通知
> 2. 前置通知Before
> 3. 被增强的方法
> 4. **AfterReturning 后置通知 / AfterThrowing 异常通知（异常时）**
> 5. **After最终通知**
> 6. **环绕之后通知 / 无（异常时）**



## AOP的动态代理

### JDK动态代理和CGLIB 动态代理的区别

**JDK动态代理**

- 基于**接口**实现，通过 Java 的**反射**机制由 `java.lang.reflect.Proxy` 类和 `InvocationHandler` 接口实现。
- 推荐用于代理**接口**的场景，适合代理的类实现了接口。

**CGLIB 动态代理**

- 基于**类继承**，通过**ASM 字节码生成工具**通过继承的方式来生成目标类的子类，来实现对目标方法的代理。
- 适合**没有接口的类**，或需要代理具体类中的非接口方法的场景。由于基于继承实现，不能代理 `final` 类和 `final` 方法。

### 为什么 SpringBoot 2.x 版本动态代理的默认实现改成了 CGLIB？

Spring Framework 默认使用的动态代理是 JDK 动态代理，SpringBoot 2.x 版本的默认动态代理改成了 CGLIB。

**原因：** JDK 动态代理要求接口，所以**没有接口的话会有报错**，而让 CGLIB 作为默认也没什么副作用且CGLIB 已经被重新打包为 Spring 的一部分了，所以就默认 CGLIB 。



## Spring AOP和Aspect AOP的区别

**Spring AOP**： Spring 框架提供，主要用于**运行时**的代理机制。

- **特点**：Spring AOP 是基于**动态代理**实现的，适用于 Spring 容器管理的 Bean，较轻量级，使用方便。
- **使用场景**：适合大部分业务场景，尤其是需要简单 AOP 功能的 Spring 应用。
- **局限性**：仅适用于 Spring 容器管理的对象，并且不支持对普通方法（非 Spring 管理的 Bean 方法）进行代理。

**AspectJ**：功能更强大，支持**编译时**、**类加载时**和**运行时**的 AOP 功能。

- **特点**：AspectJ 支持更加灵活的切点和增强操作，提供编译期和加载期的织入方式，性能较高。
  - **编译时织入**：在编译阶段将切面代码织入目标类中，生成**含有切面逻辑的字节码文件**，性能高。
  - **类加载时织入**：通过 AspectJ 提供的 `javaagent` 在类加载时织入切面逻辑。
  - **运行时织入**：支持动态代理，但主要在特殊场景下使用。
- **使用场景**：适合对性能要求较高或需要复杂切点匹配的场景，如日志、监控等。





## 多个切面的执行顺序如何控制



# 🍃Spring MVC

## M、V、C

- **Model（模型）：** 处理业务逻辑和数据，负责状态管理。
- **View（视图）：** 展示数据给用户，负责用户界面。
- **Controller（控制器）：** 接收用户输入，协调 Model 和 View 的交互。



## MVC的工作流程

1. **请求**：客户端发起请求 ->  DispatcherServlet

2. **（拦截、）映射、处理、返回（、拦截）** 

   - preHandle()拦截：如果返回 `false`，请求处理终止；否则，继续处理。
   - HandlerMapping映射：HandlerMapping根据请求的 URL、HTTP 方法映射合适的Controller。
     - `@Controller` 用于标记控制器类
     - `@RequestMapping` 用于定义控制器类中的方法与 URL 请求的映射关系
   - HandlerAdapter处理：用`HandlerAdapter`调用Controller处理
     - `@RequestParam` 用于获取请求参数
     - `@PathVariable` 用于获取 URL 中的路径变量
   - 返回：封装并返回 `ModelAndView` 对象（包含数据模型和视图名称）或 JSON 对象。
   - postHandle()拦截

3. **解析、渲染、响应**

   - ViewResolver解析

     - 对于视图，用`ViewResolver` 将逻辑视图名称解析为实际的视图，如 JSP、Thymeleaf 等模板引擎

     - 对于 JSON 响应，Spring MVC 会将对象序列化为 JSON，并返回给客户端。

   - 渲染：视图渲染引擎根据 `Model` 中的数据渲染HTML页面

   - 响应：将渲染的视图或 JSON 数据响应给客户端。



### MVC的核心组件

- **`DispatcherServlet`**：**中央处理器**，是一个 Servlet，负责接收 HTTP 请求并调度请求到适当的处理器。
- **`HandlerMapping`**：**处理器映射器**，根据 URL 去匹配查找能处理的 `Handler` ，并会将请求涉及到的拦截器和 `Handler` 一起封装。
  - `@Controller` 用于标记控制器类
  - `@RequestMapping` 用于定义控制器类中的方法与 URL 请求的映射关系
- **`HandlerAdapter`**：**处理器适配器**，根据 `HandlerMapping` 找到的 `Handler` ，适配执行对应的 `Handler`；
  - `@RequestParam` 用于获取请求参数
  - `@PathVariable` 用于获取 URL 中的路径变量
- **`ModelAndView`**：**模型和视图**，`Model` 用于传递数据，`View` 用于指定视图名称。
- **`ViewResolver`**：**视图解析器**，将逻辑视图名称解析为物理视图.



## MVC和MVVM的区别

|   **特性**   |                  **MVC**                   |                 **MVVM**                 |
| :----------: | :----------------------------------------: | :--------------------------------------: |
| **核心组件** |          Model, View, Controller           |          Model, View, ViewModel          |
| **交互方式** | View 和 Model 直接交互，由 Controller 调节 | View 和 Model 不直接交互，依赖 ViewModel |
| **数据绑定** |             手动通知 View 更新             |             双向绑定自动更新             |
| **适用场景** |        后端 Web 框架（Spring MVC）         |    前端框架（Angular、Vue、React 等）    |



### 拦截器（Interceptor）

拦截器比过滤器更具灵活性，常见的应用场景包括日志记录、用户认证、权限校验等。

1. **`preHandle()`**：在控制器方法执行之前调用。如果该方法返回 `false`，请求将被拦截，不会进入控制器方法。
2. **`postHandle()`**：在控制器方法执行之后，视图渲染之前调用。可以用于修改模型数据或视图。
3. **`afterCompletion()`**：在视图渲染完成后调用，用于清理资源或记录执行时间等。

#### Spring拦截链的实现

- **定义：**在 Spring 中，拦截链通常指的是一系列拦截器（如 AOP 切面、过滤器、拦截器等）依次作用于请求或方法调用，实现如日志记录、权限控制、事务管理等。

- **工作原理：**Spring 根据 @Before、@After、@AfterReturning、@AfterThrowing 这些注解，往集合里面加入对应的 Spring 提供的 MethodInterceptor 实现（比如上面的 MethodBeforeAdviceInterceptor ，如果你没用 @Before，集合里就没有 MethodBeforeAdviceInterceptor ）然后通过一个对象 CglibMethodInvocation 将这个集合封装起来，紧接着调用这个对象的 proceed 方法，具体是利用 currentInterceptorIndex 下标，利用递归顺序地执行集合里面的 MethodInterceptor ，这样就完成了拦截链的调用。

- **核心实现：**

  - **HandlerInterceptor（MVC 拦截器）**：用于拦截 HTTP 请求并进行预处理和后处理。通过实现 `HandlerInterceptor` 接口的 `preHandle`、`postHandle` 和 `afterCompletion` 方法，可以在请求到达控制器之前、控制器方法执行之后以及请求完成后进行处理。

  - **Filter（过滤器）**：基于 Servlet API 的过滤器，可对请求进行初步筛选，应用于安全验证、编码过滤、跨域处理等场景。过滤器通过 `Filter` 接口的 `doFilter` 方法拦截请求。

  - **AOP 拦截链（切面）**：Spring AOP 提供的方法级别的拦截，通过定义切面（Aspect）可以实现方法的前后处理。切面中的 `@Before`、`@After`、`@Around` 等注解用于控制拦截的执行顺序。



### DispatcherServlet的地位（角色）、作用

### 配置MultipartResolver实现文件上传和下载

### @EnableWebMVC 提供的默认配置和高级特性

- 全局异常处理：通过 `@ControllerAdvice` +`@ExceptionHandler ` 或 自定义 `HandlerExceptionResolver` 捕获和处理全局异常。

### 自定义WebMvcConfigurer



## ❓MVC中的表单处理和数据绑定

**数据绑定**：通过 `@ModelAttribute` 注解，Spring 自动将表单数据绑定到模型对象上，并将其传递给控制器方法。

**表单校验**：通过 `@Valid` 和 `BindingResult` 可以在处理表单数据之前进行数据验证，Spring MVC 集成了 JSR 303/JSR 380 Bean Validation 来进行数据校验。



## ❓MVC的异常处理机制

**全局异常处理**：通过 `@ControllerAdvice` 和 `@ExceptionHandler` 注解可以定义全局异常处理器，捕获应用中抛出的异常，并进行统一的异常处理。

1. 处理流程

2. 处理方式
   - 注解方式
     - @ControllerAdvice
       - @ExceptionHandler、@InitBinder、@ModelAttribute 


3. 机制原理

4. 常用的异常解析器



## REST风格

- @RequestMapping（@GetMapping @PostMapping @PutMapping @DeleteMapping）
- @PathVariable
- `@RequestBody`、`@RequestParam`、`@PathVariable`的区别和应用?
- @RestController = @Controller + @ResponseBody
- 其他
  - @ModelAttribute
  - @InitBinder





## SSM整合







# Spring 设计模式

- **工厂模式（IoC）：** BeanFactory，整个IOC 就是一个工厂。
- **单例模式（Bean）：**默认情况下 Bean 都是单例的。
- **代理模式（AOP）：**AOP 整个都是代理模式。
- **责任链模式（MVC拦截器）：**多个拦截器串联起来就形成了责任链。
- **适配器模式（MVC适配器）：**在 Spring MVC 中提到的 handlerAdapter 其实就是适配器。
- **模板方法（Template）：**例如 JdbcTemplate、RestTemplate，名字是 xxxTemplate 的都是模板。
- **观察者模式（监听器）：**在 Spring 中的监听器实现。



# 🍃Spring 循环依赖

## 循环依赖的定义、举例

- 循环依赖是指多个 Bean **循环引用**导致 Spring 容器**无法正常初始化**它们。
- **例如**，A 要依赖 B，发现 B 还没创建，于是开始创建 B ，创建的过程发现 B 要依赖 A， 而 A 还没创建好呀，因为它要等 B 创建好，就这样它们俩就搁这卡 bug 了。

## 通过三级缓存解决循环依赖

### 哪三级缓存

1. **一级缓存（Singleton Objects Map 单例对象）**：存放已经实例化、属性填充、初始化的 Bean。

2. **二级缓存（Early Singleton Objects Map 早期单例对象）**：存放已实例化，但尚未完全初始化的 Bean。也就是三级缓存中`ObjectFactory`产生的对象，与三级缓存配合使用的，可以防止 AOP 的情况下，每次调用`ObjectFactory.getObject()`都是会产生新的代理对象的。

3. **三级缓存（Singleton Factories Map 单例工厂）**：存放对象工厂`ObjectFactory`，`ObjectFactory`的`getObject()`方法最终调用`getEarlyBeanReference()`方法创建早期Bean（特别是为了支持AOP代理对象的创建）。

### Spring创建Bean流程

1. 当 Spring 创建 A 时发现 A 依赖了 B ，又去创建 B，B 依赖了 A ，又去创建 A；

2. 在 B 创建 A 的时候， A 此时还没有初始化完成，因此在 一二级缓存 中肯定没有 A；

3. 那么此时就去三级缓存中调用 `getObject()` 方法最终调用`getEarlyBeanReference()` 方法去生成并获取 A 的前期暴露的对象。

4. 然后就将这个 `ObjectFactory` 从三级缓存中移除，并且将前期暴露对象放入到二级缓存中，那么 B 就将它注入到依赖，来支持循环依赖。

### 只用两级缓存够吗？

- 在没有 AOP 的情况下，确实可以只使用一级和三级缓存来解决循环依赖问题。
- **当涉及到 AOP 时，二级缓存非常重要，避免了同一个 Bean 有多个代理对象的问题。**
  - 直接使用二级缓存不做任何处理会导致我们拿到的 Bean 是未代理的原始对象。
  - 如果二级缓存内存放的都是代理对象，则违反了 Bean 的生命周期。

- getEarlyBeanReference会判断这个对象是否需要代理，如果否则直接返回，如果是则返回代理对象。

### ObjectFactory

#### ObjectFactory 的使用场景

- **懒加载 Bean**：当某个 Bean 的创建过程可能耗时较长、依赖的资源较重、或需要在运行时决定动态 Bean 使用时，可以通过 `ObjectFactory` 进行懒加载，避免容器启动时不必要的 Bean 创建。
- **避免循环依赖**：在某些情况下，两个 Bean 可能相互依赖，导致循环依赖问题。通过使用 `ObjectFactory`，可以延迟其中一个 Bean 的创建，避免循环依赖。在 Spring 的循环依赖的三级缓存的 map 里面存储的就是 ObjectFactory，用于延迟代理对象的创建。

#### ObjectFactory 与 Provider 的区别

二者在功能上非常相似，都提供了惰性获取 Bean 实例的机制。

- `ObjectFactory` 是 Spring 内部的接口。
-  `Provider` 则是 Java 标准中的一部分，适用于更通用的场景。



## 通过`@Lazy`解决循环依赖

`@Lazy` 延迟加载

- **没有 `@Lazy` 的情况下**：在 Spring 容器初始化 `A` 时会立即尝试创建 `B`，而在创建 `B` 的过程中又会尝试创建 `A`，最终导致循环依赖。
- **使用 `@Lazy` 的情况下**： A 的构造器上添加 `@Lazy` 注解之后（延迟 Bean B 的实例化），Spring 不会立即创建 `B`，而是会注入一个 `B` 的代理对象。由于此时 `B` 仍未被真正初始化，`A` 的初始化可以顺利完成。等到 `A` 实例实际调用 `B` 的方法时，代理对象才会触发 `B` 的真正初始化，在注入 B 中的 A 属性时，此时 A 已经创建完毕了，就可以将 A 给注入进去。



# Spring 事务



## 管理事务的方式





## 5种事务隔离级别（Isolation）

Spring 提供了五种事务隔离级别`@Transactional(isolation = Isolation.XXX)`：

1. **DEFAULT（默认）：**通常默认为 `READ_COMMITTED`。
2. **READ_UNCOMMITTED（读未提交）：**最低的隔离级别，允许事务读取尚未提交的数据，可能导致脏读、不可重复读和幻读。
3. **READ_COMMITTED（读已提交，默认）：**仅允许读取已经提交的数据，**避免了脏读**，但可能会出现不可重复读和幻读问题。
4. **REPEATABLE_READ（可重复读）：**确保在同一个事务内的多次读取结果一致，**避免脏读和不可重复读**，但可能会有幻读问题。
5. **SERIALIZABLE（可串行化）：**最高的隔离级别，通过强制事务按顺序执行，**完全避免脏读、不可重复读和幻读**，代价是性能显著下降。

| 隔离级别         | 脏读 | 不可重复读 | 幻读 |
| ---------------- | ---- | ---------- | ---- |
| READ_UNCOMMITTED | 是   | 是         | 是   |
| READ_COMMITTED   | 否   | 是         | 是   |
| REPEATABLE_READ  | 否   | 否         | 是   |
| SERIALIZABLE     | 否   | 否         | 否   |

- **脏读（Dirty Read）**：一个事务读取了另一个**尚未提交的事务的数据**，如果该事务**回滚**，则数据是**不一致**的。
- **不可重复读（Non-repeatable Read）**：在同一事务内的多次读取，前后**数据**不一致，因为其他事务**修改了**该数据并提交。
- **幻读（Phantom Read）**：在一个事务内的多次查询，查询**结果集**不同，因为其他事务**插入或删除了数据**。



## 7种事务传播行为（Propagation）

`@Transactional(propagation = Propagation.REQUIRES_NEW)`

1. **PROPAGATION_REQUIRED（默认）：** 如果当前存在事务，则用当前事务，如果没有事务则新起一个事务
2. **PROPAGATION_SUPPORTS：** 支持当前事务，如果不存在，则以非事务方式执行
3. **PROPAGATION_MANDATORY：** 支持当前事务，如果不存在，则抛出异常
   - 应用场景：必须在现有事务中执行的场景。常用于**确保方法调用链的一致性**。
4. **PROPAGATION_REQUIRES_NEW：** 创建一个新事务，如果存在当前事务，则挂起当前事务
   - 应用场景：**日志记录、通知服务**等。即使主事务失败，独立事务的操作也应该成功执行。
5. **PROPAGATION_NOT_SUPPORTED：** 不支持当前事务，始终以非事务方式执行
   - 应用场景：需要明确禁止事务的场景，比如**读取配置信息、不需要事务控制的数据查询**。
6. **PROPAGATION_NEVER：** 不支持当前事务，如果当前存在事务，则抛出异常
   - 应用场景：需要保证绝对没有事务的场景，比如某些**不允许在事务中执行的数据库操作**。
7. **PROPAGATION_NESTED：** 如果当前事务存在，则在嵌套事务中执行，内层事务依赖外层事务，如果外层失败，则会回滚内层，内层失败不影响外层。
   - 应用场景：需要部分回滚或局部事务的业务逻辑。比如，**订单中的部分操作可能会失败，但不希望整个订单回滚**。

**用途：**

1. **控制事务的传播和嵌套**：根据具体业务需求，可以指定是否使用现有事务或开启新的事务，解决事务的传播问题。
2. **确保独立操作的事务隔离**：某些操作（如日志记录、发送通知）应当独立于主事务执行，即使主事务失败，这些操作也可以成功完成。
3. **控制事务的边界和一致性**：不同的业务场景可能需要不同的事务边界，例如强制某个方法必须在事务中执行，或者确保某个方法永远不在事务中运行。



❓@Transactional(rollbackFor = Exception.class)注解



# Spring Data JPA（重在实战）

如何使用 JPA 在数据库中非持久化一个字段？

JPA 的审计功能是做什么的？有什么用？

实体之间的关联关系注解有哪些？



# Spring Security

- 控制请求访问权限的方法
- hasRole 和 hasAuthority 的区别

- 如何对密码进行加密？
- 如何优雅更换系统使用的加密算法？





# Spring父子容器

**父容器（管理Service层、DAO层）**：通常是 Spring 应用上下文（`ApplicationContext`），如 `ContextLoaderListener` 加载的根容器。它主要用于管理应用程序的全局 Bean，如服务层（Service）、数据访问层（DAO）等。

**子容器（管理Web层）**：每个 `DispatcherServlet` 实例都会创建一个子容器，用于管理 Web 层（如控制器和拦截器）中的 Bean。

**关系**：子容器可以访问父容器的 Bean，反之不能。

**意义：**

- **Service层与 Web 层分离**：全局的 Bean，如数据库连接池、事务管理等，可以放在父容器中，而与 Web 相关的控制器放在子容器中。
- **避免 Bean 重复定义**：例如，DAO的配置可以在父容器中定义，Web 层的配置可以在子容器中定义，这样可以防止冲突。
- **分模块管理**
