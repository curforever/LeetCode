# Spring 基础



## Spring的重要模块（4）

### Core Container（核心容器）

- **Spring Core**：提供了IoC和DI，所有其他Spring模块的基础。
- **Spring Beans**：负责管理Bean及其生命周期。
- **Spring Context**：基于Core和Beans的高级容器，ApplicationContext主要提供了五大功能（5）：
  - 核心容器 BeanFactory
  - 国际化 MessageSource：根据浏览器请求头携带的信息翻译为某一特定的语言。
  - 资源获取 ResourceLoader：`context.getResources("file:xxx");`
  - 环境信息 EnvironmentCapable：`context.getEnvironment().getProperty("java_home");`
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

1. Spring（SpringFramework）：包含多个核心模块、简化了J2EE（Java 2 平台企业版）的开发
2. SpringMVC：多个功能模块之一，基于Spring提供的IoC、AOP 等功能来实现 Web 层的处理
3. SpringBoot：自动配置常用Spring模块，简化初始化和配置工作。



## Spring的启动过程

1. **加载配置文件，初始化容器**
   读取配置文件，配置数据库连接、事务管理、AOP 配置等。

2. **实例化容器**

   根据配置文件中的信息创建容器 ApplicationContext，在容器启动阶段实例化 BeanFactory，并加载容器中的 BeanDefinitions。

3. **解析 BeanDefinitions**

   Spring 容器会解析配置文件中的 BeanDefinitions，即声明的 Bean 元数据，包括 Bean 的作用域、依赖关系等信息。

4. **Bean的生命周期**

   1. 实例化 Bean
   2. 依赖注入
   3. 初始化

5. **发布事件**

   Spring 可能会在启动过程中发布一些事件，比如容器启动事件。

6. **完成启动**



## Spring国际化MessageSource



### Spring框架中如何实现国际化（i18n）和本地化（l10n）？

Spring 框架通过 `MessageSource` 接口实现国际化（i18n）和本地化（l10n），支持基于语言环境动态加载消息资源文件。结合 `LocaleResolver` 和 `@RequestMapping`，可根据用户的语言偏好或请求参数动态切换语言。

**国际化与本地化的核心组件**

1. **`MessageSource`**
   - 用于加载和解析国际化资源文件（如 `messages_en.properties`、`messages_zh.properties`）。
   - Spring 提供 `ResourceBundleMessageSource` 实现，支持从类路径加载资源文件。
2. **`LocaleResolver`**
   - 确定当前请求的语言环境（`Locale`）。
   - 常用实现：`CookieLocaleResolver` 和 `SessionLocaleResolver`。



### Spring框架中的消息源（MessageSource）是如何工作的？

`MessageSource` 是 Spring 提供的国际化支持接口，用于加载和解析消息资源文件，动态返回对应语言环境（`Locale`）的消息。通过 `getMessage` 方法，开发者可以根据消息键（`code`）、参数（`args`）和语言环境（`Locale`）获取国际化消息。

**`MessageSource` 的作用**
Spring 的 `MessageSource` 接口定义了一种机制，用于根据语言环境（`Locale`）动态加载消息。
- **主要功能：**
	- 管理多语言资源文件（如 `messages_en.properties` 和 `messages_zh.properties`）。
	- 根据用户语言偏好提供对应的消息内容。



# 🍃Spring IoC



## 控制反转 IoC

控制反转 IoC是【**思想**】：指将**对象的创建和管理**的控制权从**程序代码**转移到 **Spring 容器**。

- **控制**的是**对象的创建和管理**，**创建对象、注入依赖的动作**被**反转**

> - **导入和配置。** 导入坐标`spring-context`后新建配置文件`applicationContext.xml`并配置bean（包括id，class）
> - **获取IoC容器。** `new ClassPathXmlApplicationContext("applicationContext.xml")`
> - **获取bean。** `ctx.getBean("bookDao")`



## 依赖注入DI

依赖注入 DI是【**实现方式**】：指容器**根据配置**将**所需的依赖**注入到对象中

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

   - **不推荐原因：**隐式依赖关系导致的可测试性差和不易维护。
     具体来说，不能通过构造器或 setter 方法修改依赖关系，因为 Spring 会在创建 Bean 时自动完成依赖注入，测试代码无法显式地控制 Mock （模仿）对象的注入，只能使用 `@InjectMocks` 和 `@Mock` 等注解来让 Mockito 和 Spring 协作进行注入，或者采用构造器注入来手动控制 Mock 对象的注入。
   - 如果必须使用字段注入，可以通过一些工具（如 `ReflectionTestUtils`）来在测试中设置字段值
   - 配置`<bean>`用`autowire="xx"`。
4. 接口回调注入，就是实现 Spring 定义的一些内建接口，例如 BeanFactoryAware，会进行 BeanFactory 的注入



### 属性注入的3种情况

1. **简单类型（如int、String）**。用`<property>`标签的`value`属性
2. **引用类型**。用`<property>`标签的`ref`属性
3. **集合**。用`<property>`标签的`<array>``<list>``<set>``<map>``<props>`子标签



### 配置文件的注入

#### @PropertySource加载配置文件

`@PropertySource` 可以加载指定路径的 `.properties` 文件，将其内容注入到 Spring 的 `Environment` 中，方便通过 `@Value` 或 `Environment` 对象获取属性值。

#### @Value注入值

1. **配置文件注入：**将属性文件中的值注入到 Bean 中。
2. **系统属性和环境变量：**将系统属性或环境变量的值注入到 Bean 中。
3. **默认值设置：**在属性不可用时，提供默认值。

#### Environment对象注入值

除了使用 `@Value` 注解之外，Spring 还可以通过 `Environment` 对象获取属性文件中的属性值。`Environment` 提供了一种更加灵活的方式来访问属性，并支持对属性的存在进行检查。

```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        return dataSource;
    }
}
```



## Bean的配置

### Bean注册到容器的4种方式

#### Bean注册到容器的4种方式

1. 基于 XML 的配置

2. 基于 `@Component` 注解及其衍生注解 `@Component`、`@Service` 、`@Controller`、`@Repository` 
3. 基于 `@Configuration`声明配置类 和 `@Bean` 定义 Bean
4. 基于 `@Import` 注解将普通类导入到 Spring 容器中，这些类会自动被注册为 Bean。

#### @Component及其衍生注解

其它三个都是 `@Component` 的衍生注解，目的是更好地组织管理应用的各个层次，提高代码可读性。

- **`@Component`**：通用注解，用于没有明确职责的类或通用组件，比如工具类、任务调度器等。
- **`@Controller`**：处理 Web 层请求。
- **`@Service`**：处理服务层逻辑。
- **`@Repository`**：处理持久层数据访问对象。
  - **异常转换**简化异常处理流程：@Repository 注解会自动将**低层次的数据库异常（如 SQLException）**转换为 **Spring 统一的 DataAccessException**。

#### @Component 和 @Bean 的区别

- @Bean方法级别（在 `@Configuration` 类中）、需手动注册、用于手动创建复杂的对象、第三方库或无法修改的类、更灵活，适合复杂初始化。

- @Component类级别、支持自动扫描（ `@ComponentScan` ）、用于自定义类、自动化更强，适合类的简单注册



### Bean自动装配的4种方式

#### Bean自动装配的4种方式

1. no（默认）：不自动装配，需要显式地定义依赖。 
2. byName：通过 Bean 名称进行自动装配。
3. byType：通过 Bean 类型进行自动装配。 
4. constructor：通过构造函数进行自动装配。

#### @Autowired 和 @Resource 的区别

都是注入Bean的注解

1. **`@Autowired`：** Spring 特有，默认**按类型（byType）** 自动注入。
   - 注解来源：Spring 特有，支持`@Autowired(required = false)`表示依赖项可选，即没有匹配到 Bean 时，不抛异常，而是注入 `null`。
   - 如果存在多个相同类型的 Bean，可结合 `@Qualifier` 注解指定注入的 Bean 名称。
2. **`@Resource`：** JSR-250 标准，默认**按名称（byName）** 注入。
   - JSR-250 标准，更适合标准化和跨框架使用。
   - 如果未指定名称且没有匹配的 Bean，会按类型注入。

#### @Qualifier和@Primary的区别

都是解决 Bean 注入时的歧义问题，即当一个接口有多个实现时，Spring 无法确定该注入哪个具体的 Bean

- @Qualifier指定名称选择对应的实现 Bean。

- @Primary 指定默认注入哪个 Bean。

-  @Qualifier 可以覆盖 @Primary 的默认行为。



### Bean的条件配置

#### @Profile定义配置文件所属环境

@Profile 用于定义一组 Bean 的配置文件所属的环境，比如 dev 通常表示开发环境，prod 表示生产环境。

```java
@Configuration
@Profile("dev") // 这个配置类只在 "dev" profile激活时加载
public class DevConfig {
    @Bean
    public DataSource dataSource() {
        // 返回开发环境的DataSource
        return new EmbeddedDatabaseDataSource();
    }
}

@Configuration
@Profile("prod") // 这个配置类只在 "prod" profile激活时加载
public class ProdConfig {
    @Bean
    public DataSource dataSource() {
        // 返回生产环境的DataSource
        return new SomeProductionDataSource();
    }
}
```

```shell
java -jar yourapp.jar --spring.profiles.active=prod
```

#### @Conditional条件加载Bean

**主要作用**

- **有条件地加载 Bean**：`@Conditional` 根据某个条件来决定某个 Bean 是否需要注入到 Spring 容器中。条件可以是操作系统类型、类路径是否存在某个类、某个属性的值等。
- **实现动态配置**：可以根据环境（如开发、测试、生产）或特定上下文条件动态装配 Bean，避免不必要的 Bean 被加载。

**使用场景**

- 在不同的操作系统或环境中注入不同的 Bean。
- 根据应用程序的配置文件、属性值来动态启用某些功能。
- 在条件满足时，才装配某些依赖或服务。

**常见的内置条件**

- `@ConditionalOnProperty` 用于根据配置文件中的某个属性值来决定是否装配 Bean。常用于启用或禁用某些功能模块。
- `@ConditionalOnClass` 用于判断类路径中是否存在某个类。如果存在，则装配相应的 Bean。
- `@ConditionalOnMissingBean` 用于当容器中不存在某个类型的 Bean 时，才注册当前的 Bean。可以用来避免重复注册 Bean。
- `@ConditionalOnBean` 的作用与 `@ConditionalOnMissingBean` 相反，用于当容器中存在某个类型的 Bean 时，才装配当前的 Bean。

```java
// 自定义条件类
public class OnLinuxCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return System.getProperty("os.name").contains("Linux");
    }
}
```

```java
// 在配置类中使用
@Configuration
public class AppConfig {

    @Bean
    @Conditional(OnLinuxCondition.class)
    public MyService myService() {
        return new MyService();
    }
}
```



## Bean 的作用域（Scope）

通过 Spring 容器实例化、组装和管理的 Java 对象都可以被称为 Spring Bean。

### 作用域及其适用场景

**2基础作用域 + 4仅Web 应用可用：**

1. **singleton（默认）：** IoC 容器中只有唯一的 bean 实例，适用于无状态的共享资源。
2. **prototype：** 每次获取bean都创建一个新实例，适用于短期使用的有状态且非线程安全的对象。应用：唯一标识符（UUID 、验证码、Token）、用户临时数据（表单数据缓存、文件上传临时存储）
3. **request：** 每个 HTTP 请求创建一个实例，如表单数据处理。
4. **session：** 每个会话创建一个实例，如用户信息缓存。
5. **application/global-session：** 在Web应用启动时创建一个bean，如统计数据。
6. **websocket：** 每个 WebSocket 会话创建一个实例，适用于WebSocket 连接状态管理。

### 作用域与生命周期的关系

**singleton**：Bean 的生命周期与 Spring 容器的生命周期一致。在容器启动时创建，在容器关闭时销毁。

**prototype**：每次请求时创建一个新的 Bean 实例，容器只负责创建，不管理其生命周期（不调用销毁方法），由客户端决定何时销毁。

Bean 的生命周期分别与 HTTP 请求、会话、应用或 WebSocket 的生命周期一致。

### 有状态的单例bean是非线程安全的

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

1. **避免在单例 Bean 中使用可变状态**
2. **使用 `@Scope("prototype")`**：对于有状态的 Bean，Spring 提供了原型作用域（`prototype`），每次请求都会创建一个新的 Bean 实例，从而避免共享同一个实例带来的并发问题。
3. **加锁**: 利用 `synchronized` 或 `ReentrantLock` 来进行同步控制。
4. **使用`ThreadLocal`**保存变量



### 自定义作用域

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
   2. **BeanPostProcessor#postProcessBeforeInitialization**：如代理包装、AOP 切面等。
   3. **初始化**
      1. @PostConstruct
      2. InitializingBean#afterPropertiesSet （应用：缓存初始化）
      3. initMethod
   4. **BeanPostProcessor#postProcessAfterInitialization**：在 Bean 初始化后，进一步修改或替换 Bean 实例。

4. **使用**
5. **销毁：**如果 Bean 实现了 `DisposableBean接口的destroy()方法` 或使用了 `@PreDestroy` 注解，Spring 会在**容器关闭时**调用销毁方法。

   注意：容器关闭时才调用销毁逻辑，所以看不到，要想看到destroy-method执行：
   1. 暴力手动关闭：在JVM结束前通过`ClassPathXmlApplication接口close()方法`关闭容器
   2. 注册关闭钩子：`ClassPathXmlApplication接口registerShutdownHook()方法`



### 生命周期的应用

1. **连接池管理**：在初始化阶段创建数据库连接池，在销毁阶段关闭连接池，以确保资源的有效管理。

2. **缓存初始化**：在 `afterPropertiesSet` 或 `@PostConstruct` 中加载缓存数据，在销毁阶段清理缓存。

3. **动态代理创建**：在 `postProcessBeforeInitialization` 中为 Bean 创建动态代理，以实现 AOP 功能。



### 生命周期的相关问题

#### 实例化、依赖注入、初始化的基本流程

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

#### 初始化方法的执行顺序

1. BeanPostProcessor#postProcessBeforeInitialization
2. @PostConstruct
3. InitializingBean#afterPropertiesSet

4. initMethod

5. BeanPostProcessor#postProcessAfterInitialization。

#### @PostConstruct 和 @PreDestroy 的区别

- **`@PostConstruct`**：当依赖注入完成后，在 Bean 初始化完成后调用。常用于设置默认值、检查依赖。
- **`@PreDestroy`**：在 Bean 即将被销毁时调用。常用于进行资源释放（关闭数据库连接、文件句柄、线程池）、会话管理（清理用户会话或缓存）。
  - 对于单例（`singleton`）作用域的 Bean，会在容器关闭时调用；
  - 对于原型（`prototype`）作用域的 Bean，不会调用销毁方法，因为容器不管理其生命周期。

#### ❓初始化`Aware` 接口的依赖注入

#### BeanFactory和FactoryBean的区别

**BeanFactory**是 **IOC 的底层容器**，负责从配置源中读取 Bean 的定义，并负责创建、管理这些 Bean 的生命周期。一个重要特性是**延迟初始化**，即它只会在 Bean 首次请求时才会实例化该 Bean，而不是在容器启动时就立即创建所有的 Bean。接口的实现类：

- **DefaultListableBeanFactory**：BeanFactory 的默认实现，支持所有基本的依赖注入特性，如构造器注入、setter 注入等。

- **XmlBeanFactory（已废弃）**：已经在 Spring 3.x 中被淘汰，现推荐使用 ApplicationContext。

**FactoryBean**：通过自定义的 `getObject()` 方法创建对象。使用场景：

- **复杂对象创建**：如果某个 Bean 的创建过程比较复杂，比如需要**动态加载配置文件**或**执行其他逻辑**才能实例化对象。

- **代理对象生成**：Spring AOP 使用 FactoryBean 来生成代理对象，使得 AOP 切面能够透明地应用于目标对象。

- **条件性 Bean**：在**某些条件下返回不同的 Bean 实例**，例如**根据应用的环境配置不同的数据库连接池或者日志框架实现**。

#### BeanFactory和ApplicationContext的区别

- **`BeanFactory`** 是 Spring 的基础 IoC 容器，提供 Bean 的创建与管理功能，延迟加载 Bean。
- **`ApplicationContext`** 是 `BeanFactory` 的扩展，添加了国际化、事件发布、AOP 等高级功能，默认预加载所有单例 Bean。
- 一般推荐使用 `ApplicationContext`，除非有资源受限的特殊需求。



# 🍃Spring AOP



## AOP核心思想、应用场景

AOP（Aspect-Oriented Programming，面向切面编程）

- 核心思想：通过切面Aspect将与业务逻辑无关的通用功能（如日志记录、安全检查、事务管理等）模块化，并将其应用到应用程序中的多个地方，避免代码重复。

  通俗理解：通过代理的方式，在调用想要的对象方法时候，进行拦截处理，执行切入的逻辑，然后再调用真正的方法实现。

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



## AOP的工作机制（动态代理）

### JDK动态代理和CGLIB 动态代理的区别

**JDK动态代理**

- 基于**接口**实现，通过 Java 的**反射**机制由 `java.lang.reflect.Proxy` 类和 `InvocationHandler` 接口实现。
- 推荐用于代理**接口**的场景，适合代理的类实现了接口。

**CGLIB 动态代理**

- 基于**类继承**，通过**ASM 字节码生成工具**通过继承的方式来生成目标类的子类，来实现对目标方法的代理。
- 适合**没有接口的类**，或需要代理具体类中的非接口方法的场景。由于基于继承实现，不能代理 `final` 类和 `final` 方法。

### 为什么 SpringBoot 2.x 默认实现改成了 CGLIB？

Spring Framework 默认使用的动态代理是 JDK 动态代理，SpringBoot 2.x 版本的默认动态代理改成了 CGLIB。

**原因：** JDK 动态代理要求接口，所以**没有接口的话会有报错**，而让 CGLIB 作为默认也没什么副作用且CGLIB 已经被重新打包为 Spring 的一部分了，所以就默认 CGLIB 。



## Spring AOP和AspectJ AOP的区别

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



## ❓多个切面的执行顺序如何控制



# 🍃Spring MVC



## M、V、C

- **Model（模型）：** 负责封装数据，可以是 POJO、DTO 或者其他形式的对象。
- **View（视图）：** 负责展示数据，通常是 JSP、Thymeleaf、Freemarker 等模板引擎。
- **Controller（控制器）：** 处理用户的请求，调用服务层处理业务逻辑，最终返回数据和视图。



## MVC、MVVM、WebFlux的区别

|   **特性**   |                  **MVC**                   |                 **MVVM**                 |
| :----------: | :----------------------------------------: | :--------------------------------------: |
| **核心组件** |          Model, View, Controller           |          Model, View, ViewModel          |
| **交互方式** | View 和 Model 直接交互，由 Controller 调节 | View 和 Model 不直接交互，依赖 ViewModel |
| **数据绑定** |             手动通知 View 更新             |             双向绑定自动更新             |
| **适用场景** |        后端 Web 框架（Spring MVC）         |    前端框架（Angular、Vue、React 等）    |



**Spring WebFlux**：

- **异步非阻塞框架**：Spring WebFlux 是 Spring 5 引入的响应式 Web 框架，旨在支持异步非阻塞编程模型。
- **基于 Reactor**：WebFlux 基于 Reactor 库，支持响应式流（Reactive Streams）规范，使用 Mono 和 Flux 来表示单个和多个异步序列。
- **适用于高并发**：WebFlux 适用于需要处理大量并发请求的场景，如实时数据流和高负载应用。

**Spring MVC**：

- **同步阻塞框架**：Spring MVC 是一个基于 Servlet 的传统 Web 框架，使用同步阻塞模型处理请求。
- **基于 Servlet API**：Spring MVC 使用标准的 Servlet API，通常每个请求对应一个线程。
- **广泛应用**：Spring MVC 适用于大多数 Web 应用，特别是传统的 CRUD 操作和企业应用。

**适用场景**：

- **Spring MVC：适用于 I/O 操作较少、请求数相对较少的应用。**
- **Spring WebFlux：适用于 I/O 操作频繁、高并发、低延迟的应用。**



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



## MVC的核心组件

- **`DispatcherServlet`**：**前端控制器**，是一个 Servlet，负责接收 HTTP 请求并调度请求到适当的处理器。
- **`HandlerMapping`**：**处理器映射器**，根据 URL 去匹配查找能处理的 `Handler` ，并会将请求涉及到的拦截器和 `Handler` 一起封装。
  - `@Controller` 用于标记控制器类
  - `@RequestMapping` 用于定义控制器类中的方法与 URL 请求的映射关系
- **HandlerInterceptor（拦截器）**：在请求处理的各个阶段拦截 HTTP 请求和响应，可以在控制器执行前后添加自定义逻辑，如日志记录、权限检查等。
- **`HandlerAdapter`**：**处理器适配器**，根据 `HandlerMapping` 找到的 `Handler` ，适配执行对应的 `Handler`；
  - `@RequestParam` 用于获取请求参数
  - `@PathVariable` 用于获取 URL 中的路径变量
- **`ModelAndView`**：**模型和视图**，`Model` 用于传递数据，`View` 用于指定视图名称。
- **`ViewResolver`**：**视图解析器**，将逻辑视图名称解析为物理视图.

### 拦截器（Interceptor）

**用途：**

拦截器（Interceptor）用于在请求处理流程的不同阶段拦截 HTTP 请求和响应，并对其进行预处理或后处理。

- **权限验证**：在用户请求到达控制器之前，可以通过拦截器检查用户是否有权限访问某个资源。
- **日志记录**：拦截器可以记录每次请求的日志信息，如请求的 URL、用户 IP、请求耗时等。
- **性能监控**：通过 `preHandle()` 和 `afterCompletion()` 记录请求的开始和结束时间，可以计算请求的处理时长。
- **全局异常处理**：拦截器可以捕获控制器中抛出的异常，并在 `afterCompletion()` 中处理。

---

**实现：**

**实现 `HandlerInterceptor` 接口**：自定义的拦截器需要实现 `HandlerInterceptor` 接口，并重写其三个核心方法：

- `preHandle()`：请求到达控制器之前的预处理。
- `postHandle()`：控制器执行之后但视图渲染之前的后处理。
- `afterCompletion()`：整个请求结束之后的回调。

**注册拦截器**：通过 `WebMvcConfigurer` 或 `xml` 配置来注册拦截器，并指定拦截的路径。

---

**拦截链：**

- **定义：**在 Spring 中，拦截链通常指的是一系列拦截器（如 AOP 切面、过滤器、拦截器等）依次作用于请求或方法调用，实现如日志记录、权限控制、事务管理等。

- **工作原理：**Spring 根据 @Before、@After、@AfterReturning、@AfterThrowing 这些注解，往集合里面加入对应的 Spring 提供的 MethodInterceptor 实现（比如上面的 MethodBeforeAdviceInterceptor ，如果你没用 @Before，集合里就没有 MethodBeforeAdviceInterceptor ）然后通过一个对象 CglibMethodInvocation 将这个集合封装起来，紧接着调用这个对象的 proceed 方法，具体是利用 currentInterceptorIndex 下标，利用递归顺序地执行集合里面的 MethodInterceptor ，这样就完成了拦截链的调用。

- **核心实现：**

  - **HandlerInterceptor（MVC 拦截器）**：用于拦截 HTTP 请求并进行预处理和后处理。通过实现 `HandlerInterceptor` 接口的 `preHandle`、`postHandle` 和 `afterCompletion` 方法，可以在请求到达控制器之前、控制器方法执行之后以及请求完成后进行处理。

  - **Filter（过滤器）**：基于 Servlet API 的过滤器，可对请求进行初步筛选，应用于安全验证、编码过滤、跨域处理等场景。过滤器通过 `Filter` 接口的 `doFilter` 方法拦截请求。

  - **AOP 拦截链（切面）**：Spring AOP 提供的方法级别的拦截，通过定义切面（Aspect）可以实现方法的前后处理。切面中的 `@Before`、`@After`、`@Around` 等注解用于控制拦截的执行顺序。

---

**拦截器和过滤器的区别**：

- **相同点**

  - 都是用于拦截 HTTP 请求。

  - 都可以在请求处理之前和之后执行逻辑。


- **不同点**

  - **拦截器**：依赖于 Spring MVC 的框架，通过 `HandlerInterceptor` 实现，可以访问控制器的对象和返回的数据模型。它主要用于处理应用程序级别的逻辑，如日志记录、权限验证等。

  - **过滤器（Filter）**：是 Servlet 规范中的一部分，拦截的是所有的 HTTP 请求，不依赖于 Spring。它通常用于处理全局的任务，如跨域请求处理（CORS）、编码转换等。




### 视图解析器（ViewResolver）

负责将逻辑视图名称解析为实际的视图对象（如 JSP、Thymeleaf、FreeMarker 模板等）。

**工作流程：**

- 接收视图名称：控制器返回一个逻辑视图名称。
- 视图解析：视图解析器根据逻辑视图名称和配置，解析并找到实际的视图文件。
- 渲染视图：将模型数据传递给视图对象，由视图对象生成最终的 HTML 响应。

**常见的视图解析器实现：**

- InternalResourceViewResolver：用于解析 JSP 文件视图。
- ThymeleafViewResolver：用于解析 Thymeleaf 模板视图。
- FreeMarkerViewResolver：用于解析 FreeMarker 模板视图。

**视图解析器相关配置：**

- 优先级：可以配置多个视图解析器，通过 order 属性设置解析器的优先级。
- 默认视图：可以指定默认视图解析器，当找不到匹配的视图名称时使用。



## MVC表单数据的绑定和校验

### MVC表单数据的绑定和校验

**表单数据绑定**：

- `@ModelAttribute` 注解可以自动将表单中的字段与 Java **对象**的属性进行绑定，通常用于处理复杂的表单提交。
- `@RequestParam` 注解用于处理表单中**单个**字段的数据。对于简单的表单，可以使用该注解从请求中获取参数值。
- `@RequestBody`，如果表单数据是以 **JSON** 格式提交的，可以使用 @RequestBody 注解将请求体中的 JSON 数据映射为 Java 对象。

**表单数据校验**：

- `@Valid` 注解用于触发表单对象的验证，`BindingResult` 用于检查验证结果。如果表单验证失败，控制器可以根据错误结果返回错误页面。

- 定义表单验证规则:Spring MVC 支持使用 JSR-303/JSR-380 注解（如 `@NotNull`、`@Size`、`@Email` 等）在模型类中定义表单字段的验证规则。

  ```java
  // User 类的 name 字段被要求不能为空且长度在 2 到 30 个字符之间，age 字段必须至少为 18。
  public class User {
  
      @NotNull(message = "Name cannot be null")
      @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
      private String name;
  
      @Min(value = 18, message = "Age must be at least 18")
      private int age;
  
      // getters and setters
  }
  ```

  



### @ModelAttribute 数据绑定

- **用于方法参数**：将请求中的参数绑定到模型对象，并直接作为方法参数传入控制器方法。应用：处理表单提交时，将多个请求参数绑定到一个对象。
- **用于方法级别**：在控制器方法执行前，预先填充模型数据，将返回值加入到模型中供视图使用。应用：准备通用的模型数据（如下拉列表、表单数据等），以便在视图中共享这些数据。

#### @ModelAttribute 和 @RequestParam 的区别

都用于从请求中提取数据。

- **`@ModelAttribute`**：用于绑定复杂对象，将请求参数映射到 Java 对象中。适用于处理表单提交和对象映射。
- **`@RequestParam`**：用于绑定简单类型的请求参数（如 `String`、`int` 等），适合处理单个请求参数。

#### @ModelAttribute 与 @RequestBody 的区别

- `@ModelAttribute` 是基于传统的表单参数和 URL 请求参数进行数据绑定。
- `@RequestBody` 是用于处理 HTTP 请求体中的 JSON 或 XML 数据。



### @Validated 和 @Valid 数据校验

- **`@Valid`**：用于标准的 Java Bean 验证，适用于单一对象或嵌套对象，不能支持分组验证。
  - 在表单提交时，验证输入的用户数据是否符合要求。
  - 在 API 请求中，验证请求体的参数格式是否正确。
- **`@Validated`**：Spring 提供的注解，支持分组验证，允许根据不同的场景执行不同的验证逻辑。
  - 需要在不同场景下执行不同的验证规则，如创建时要求所有字段必须提供，而更新时可能只需要验证部分字段。（结合groups = XX.class）

```java
// 创建用户时，只需要验证 name 字段。
// 更新用户时，需要验证 id 字段是否存在。

public class User {

    public interface Create {}
    public interface Update {}

    @NotNull(groups = Create.class, message = "Name cannot be null")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @NotNull(groups = Update.class, message = "ID cannot be null for update")
    private Long id;

    // getters and setters
}

@RestController
public class UserController {

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@Validated(User.Create.class) @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@Validated(User.Update.class) @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
}
```



### 自定义 PropertyEditor 

自定义 `PropertyEditor` 用于扩展 Spring 的数据绑定机制，适配特殊的数据类型转换。实现步骤：
1. **实现 `PropertyEditorSupport` 子类**：定义转换逻辑。
2. **注册 `PropertyEditor`**：通过 `@InitBinder` 方法将自定义的 `PropertyEditor` 注册到 `WebDataBinder` 中。
3. **使用自定义类型**：自动将请求参数绑定到目标对象的复杂类型字段。



## MVC的异常处理机制

- **局部异常处理 - `@ExceptionHandler` 注解**：用于局部的异常处理，通常定义在控制器类中。它可以捕获特定的异常，并返回自定义的错误信息或视图。

- **全局异常处理 - `@ControllerAdvice`**：应用于所有的控制器。通过这个注解，可以定义全局的异常处理逻辑，避免在每个控制器中重复编写相同的异常处理代码。



### 局部异常处理 @ExceptionHandler

**使用步骤**：

- 在控制器类中定义处理异常的方法。
- 使用 `@ExceptionHandler` 指定要捕获的异常类型。
- 可以返回自定义的视图或 JSON 响应。

```java
// 该方法可以处理 UserNotFoundException 和 InvalidUserException 两种类型的异常。
@ExceptionHandler({UserNotFoundException.class, InvalidUserException.class})
public String handleMultipleExceptions(Exception ex, Model model) {
    model.addAttribute("errorMessage", ex.getMessage());
    return "errorPage";
}
```





### 全局异常处理 @ControllerAdvice

通过 `@ControllerAdvice` 和 `@ExceptionHandler` 注解可以定义全局的异常处理方法，适用于所有控制器。

**使用步骤**：

- 创建一个类并使用 `@ControllerAdvice` 注解标记。
- 在该类中定义带有 `@ExceptionHandler` 注解的方法，用于捕获异常。
- 全局异常处理类会捕获应用中所有控制器抛出的异常。

```java
// 在这个例子中，GlobalExceptionHandler 可以处理所有控制器中抛出的异常，包括 UserNotFoundException 和其他类型的异常。
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("Global error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>("User not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
```



### 局部与全局异常处理的优先级

- **局部异常处理**：当控制器类中有 `@ExceptionHandler` 注解的方法时，Spring 会优先调用该局部异常处理方法。
- **全局异常处理**：如果没有找到局部的异常处理方法，Spring 会调用全局的异常处理器（`@ControllerAdvice`）。



### 异常处理与 RESTful API（@ResponseStatus 、 @ResponseEntity ）

在处理异常时，可以使用 `@ResponseStatus` 注解或 `ResponseEntity` 来设置 HTTP 响应状态码。

@ResponseStatus用于定义一个方法或者异常类所对应的 HTTP 状态码和原因短语。

```java
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "面试鸭未找到")
public class UserNotFoundException extends RuntimeException {
}
```

在 RESTful API 中，`@ExceptionHandler` 常用于返回 `400 Bad Request` 或 `500 Internal Server Error` 等状态码，通知客户端具体的错误类型。开发者可以自定义响应结构 `ResponseEntity` 来返回标准化的错误格式，如 `{"error": "User not found"}`。

```java
// 自定义响应结构 `ResponseEntity` 
@ExceptionHandler(UserNotFoundException.class)
@ResponseBody
public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", "Mianshiya User not found");
    errorResponse.put("message", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
}
```

```json
// 此时，客户端会收到类似这样的 JSON 响应
{
    "error": "Mianshiya User not found",
    "message": "The user with ID 123 was not found"
}
```



## Rest风格

REST（Representational State Transfer, （资源的）表述性状态转移）是一种**无状态**的架构风格，用 URL 定位资源，用 HTTP 动词来描述所要做的操作，使得客户端和服务器之间的交互更加简单、清晰和高效。

### @RequestMapping请求映射

@GetMapping @PostMapping @PutMapping @DeleteMapping

```
假设有一个用户资源，URI 为 /users，RESTful API 设计如下：
    GET /users：获取所有用户。
    GET /users/{id}：获取指定 ID 的用户。
    POST /users：创建新用户。
    PUT /users/{id}：更新指定 ID 的用户。
    DELETE /users/{id}：删除指定 ID 的用户。
```

**幂等性**

RESTful API 中的操作应尽量遵循幂等性原则。幂等操作的含义是，无论请求执行多少次，其结果应该相同。

- **GET**、**PUT**、**DELETE**：这些操作应该是幂等的。
- **POST**：通常是非幂等的，因为每次调用 POST 可能都会创建新的资源。



### @PathVariable从URL路径获取值

- @PathVariable 在处理 HTTP 请求时，从请求的 URL 路径中捕获变量，并将其绑定到控制器方法的参数上。

```java
@GetMapping("/users/{id}")
public String getUserById(@PathVariable(name = "id", required = false) Long id) {
    if (id == null) {
        return "No user ID provided";
    }
    return "User ID: " + id;
}
```



**扩展知识：**

- **可选：**Spring 允许使用 `required = false` 标记路径变量为可选。如果路径变量缺失，Spring 将不会抛出异常。.

- **类型转换：**Spring 会自动将路径变量从字符串转换为方法参数的目标类型。例如，`@PathVariable` 可以将字符串的 `id` 转换为 `Long` 类型。

- 路径中的正则表达式：例如，限制 `id` 必须是数字。

  ```java
  // 路径变量 id 必须是一个数字（通过正则表达式 \\d+），否则请求将不会匹配该控制器方法。
  @GetMapping("/users/{id:\\d+}")
  public String getUserById(@PathVariable String id) {
      return "User ID: " + id;
  }
  ```

- 当路径变量包含特殊字符（如 `/`、`?` 等）时，这些字符需要进行 URL 编码。Spring 会自动处理路径变量的 URL 编码和解码过程。

  ```
  如果请求路径为 /products/hello%20mianshiya，productName 的值将会自动解码为 hello mianshiya。
  ```

  

### @PathVariable 与 @RequestParam 的区别

`@PathVariable` 和 `@RequestParam` 都是用于从 HTTP 请求中提取数据的注解，但它们的用法不同：

- **`@PathVariable`**：从 **URL 路径**中提取动态数据，常用于 **RESTful URL 模式**。如 `/users/{id}`。
- **`@RequestParam`**：从**请求的查询参数**中提取数据，通常用于处理**表单数据或查询参数**。如 `/users?id=1`。



### @RequestHeader 、 @CookieValue 、@SessionAttribute获取请求的值

@RequestHeader 注解用于提取 HTTP 请求头中的值，并将其注入到控制器方法的参数中。例如访问 Accept、Content-Type、User-Agent 等请求头信息。

```java
@GetMapping("/header-info")
public String getHeaderInfo(@RequestHeader("User-Agent") String userAgent) {
    // 使用 userAgent 进行业务处理
    return "headerInfoView";
}

```

@CookieValue 注解用于从 HTTP 请求的 Cookie 中提取值，并将其注入到控制器方法的参数中。

```java
@GetMapping("/cookie-info")
public String getCookieInfo(@CookieValue("sessionId") String sessionId) {
    // 使用 sessionId 进行业务处理
    return "cookieInfoView";
}
```

@SessionAttribute 是 Spring MVC 中的注解，用于从当前 HTTP 会话（Session）中获取属性值并将其绑定到控制器方法的参数上，而无需手动从 HttpSession 获取。

```java
// Spring 从会话中提取名为 "loggedInUser" 的属性值，并将其绑定到 User 对象中，传递给控制器方法。
@GetMapping("/profile")
public String getUserProfile(@SessionAttribute("loggedInUser") User user) {
    return "User Profile: " + user.getName();
}
```

`@SessionAttribute` 不能像 `@RequestParam` 或 `@RequestHeader` 那样直接设置默认值。如果指定的会话属性在当前会话中不存在，Spring 将抛出异常。如果需要处理这种情况，可以结合 `HttpSession` 来手动检查会话中的属性。

```java
@GetMapping("/checkout")
public String checkout(HttpSession session, Model model) {
    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
    if (cart == null) {
        // 如果 session 中没有 cart 属性，可以设置一个默认值
        cart = new ShoppingCart();
        session.setAttribute("cart", cart);
    }
    model.addAttribute("cart", cart);
    return "checkout";
}
```

`@SessionAttributes` 和 `@SessionAttribute` 都用于管理会话中的属性，但它们的应用场景和使用方式不同。

- **`@SessionAttributes`**：用于将模型中的某些属性暂时保存到会话中，常用于**表单分步骤处理**的场景。它是**类级别**的注解。
- **`@SessionAttribute`**：用于从当前会话中获取某个已存在的属性，适用于**已经存在于会话中的数据**。



### @RequestBody 和 @ResponseBody 请求和响应 

都是用于处理 HTTP 请求和响应的两个常用注解：

- **`@RequestBody`**：将 **HTTP 请求体中的数据**绑定到**方法参数**上。Spring 会将 JSON、XML 或其他格式的请求体映射为 Java 对象。

- **`@ResponseBody`**：将**Controller方法的返回结果**直接写入 **HTTP 响应体**中。通常用于返回 JSON 或 XML 格式的数据，而不是视图页面。
  - `@RestController` 是 `@Controller` 和 `@ResponseBody` 的组合注解，表示该类的所有控制器方法都默认返回 JSON 或 XML 数据，而不需要为每个方法单独加 `@ResponseBody`。
- Spring 根据请求头中的 `Content-Type`、响应头中的 `Accept`，选择合适的消息转换器（`HttpMessageConverter`），实现 JSON和 Java 对象的相互转换。



### 消息转换器（HttpMessageConverter）

**常见的消息转换器**

- **`MappingJackson2HttpMessageConverter`**：将 JSON 数据转换为 Java 对象，或将 Java 对象转换为 JSON 数据。默认使用 Jackson 库。

- **`Jaxb2RootElementHttpMessageConverter`**：将 XML 数据转换为 Java 对象，或将 Java 对象转换为 XML 数据。

- **`StringHttpMessageConverter`**：处理 `text/plain` 类型的请求和响应，将请求体转换为字符串。

**自定义消息转换器**

- 如果需要处理自定义格式的数据，可以通过实现 `HttpMessageConverter` 接口来自定义消息转换器。

- ```java
  @Configuration
  public class WebConfig extends WebMvcConfigurerAdapter {
      @Override
      public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
          // 添加自定义的消息转换器
          converters.add(new MyCustomMessageConverter());
      }
  }
  ```

  

## 其他

### SSM整合



### DispatcherServlet的地位（角色）、作用



### 配置MultipartResolver实现文件上传和下载



### @EnableWebMVC 提供的默认配置和高级特性



### 自定义WebMvcConfigurer



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

### 三级缓存

1. **一级缓存（Singleton Objects Map 单例对象）**：存放已经实例化、属性填充、初始化的 Bean。

2. **二级缓存（Early Singleton Objects Map 早期单例对象）**：存放已实例化，但尚未完全初始化的 Bean。也就是三级缓存中`ObjectFactory`产生的对象，与三级缓存配合使用的，可以防止 AOP 的情况下，每次调用`ObjectFactory.getObject()`都是会产生新的代理对象的。

3. **三级缓存（Singleton Factories Map 单例工厂）**：存放对象工厂`ObjectFactory`，`ObjectFactory`的`getObject()`方法最终调用`getEarlyBeanReference()`方法创建早期Bean（特别是为了支持AOP代理对象的创建）。

### Spring创建Bean流程

1. 当 Spring 创建 A 时发现 A 依赖了 B ，又去创建 B，B 依赖了 A ，又去创建 A；

2. 在 B 创建 A 的时候， A 此时还没有初始化完成，因此在 一二级缓存 中肯定没有 A；

3. 那么此时就去三级缓存中调用 `getObject()` 方法最终调用`getEarlyBeanReference()` 方法去生成并获取 A 的前期暴露的对象。

4. 然后就将这个 `ObjectFactory` 从三级缓存中移除，并且将前期暴露对象放入到二级缓存中，那么 B 就将它注入到依赖，来支持循环依赖。

### 只用两级缓存够不够

- 在没有 AOP 的情况下，确实可以只使用一级和三级缓存来解决循环依赖问题。
- **当涉及到 AOP 时，二级缓存非常重要，避免了同一个 Bean 有多个代理对象的问题。**
  - 直接使用二级缓存不做任何处理会导致我们拿到的 Bean 是未代理的原始对象。
  - 如果二级缓存内存放的都是代理对象，则违反了 Bean 的生命周期。

- getEarlyBeanReference会判断这个对象是否需要代理，如果否则直接返回，如果是则返回代理对象。

### ObjectFactory

**ObjectFactory 的使用场景**

- **懒加载 Bean**：当某个 Bean 的创建过程可能耗时较长、依赖的资源较重、或需要在运行时决定动态 Bean 使用时，可以通过 `ObjectFactory` 进行懒加载，避免容器启动时不必要的 Bean 创建。
- **避免循环依赖**：在某些情况下，两个 Bean 可能相互依赖，导致循环依赖问题。通过使用 `ObjectFactory`，可以延迟其中一个 Bean 的创建，避免循环依赖。在 Spring 的循环依赖的三级缓存的 map 里面存储的就是 ObjectFactory，用于延迟代理对象的创建。

❓**ObjectFactory和Provider 的区别**

二者在功能上非常相似，都提供了惰性获取 Bean 实例的机制。

- `ObjectFactory` 是 Spring 内部的接口。
-  `Provider` 则是 Java 标准中的一部分，适用于更通用的场景。



## 通过`@Lazy`解决循环依赖

`@Lazy` 延迟加载

- **没有 `@Lazy` 的情况下**：在 Spring 容器初始化 `A` 时会立即尝试创建 `B`，而在创建 `B` 的过程中又会尝试创建 `A`，最终导致循环依赖。

- **使用 `@Lazy` 的情况下**： A 的构造器上添加 `@Lazy` 注解之后（延迟 Bean B 的实例化），Spring 不会立即创建 `B`，而是会注入一个 `B` 的代理对象。由于此时 `B` 仍未被真正初始化，`A` 的初始化可以顺利完成。等到 `A` 实例实际调用 `B` 的方法时，代理对象才会触发 `B` 的真正初始化，在注入 B 中的 A 属性时，此时 A 已经创建完毕了，就可以将 A 给注入进去。





# Spring 事务



## 事务隔离级别（Isolation）（5）

Spring 提供了五种事务隔离级别`@Transactional(isolation = Isolation.XXX)`：

1. **DEFAULT：**通常默认为 `READ_COMMITTED`。
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



## 事务传播行为（Propagation）（7）

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



## 事务失效的情况（8）

一般而言失效的情况都是用了声明式事务即 @Transactional 注解，如果使用了这个注解那么在以下几个情况下会导致事务失效：

1. **异常未被回滚。**rollbackFor 没设置对，比如默认没有任何设置（RuntimeException 或者 Error 才能捕获），则方法内抛出 IOException 则不会回滚，需要配置 `@Transactional(rollbackFor = Exception.class)`。
2. **异常被捕获。**异常被捕获了，比如代码抛错，但是被 catch 了，仅打了 log 没有抛出异常，这样事务无法正常获取到错误，因此不会回滚。
3. **同类方法直接调用。**同一个类中方法调用，因此事务是基于动态代理实现的，同类的方法调用不会走代理方法，因此事务自然就失效了。
4. **非 public 方法。**事务仅适用于公共方法。@Transactional 应用在非 public 修饰的方法上，Spring 事务管理器判断非公共方法则不应用事务。
5. **final 或 static 方法。**@Transactional 应用在 final 和 static 方法上，因为 aop （Spring Boot2.x版本默认是 cglib，Spring 自身默认是 jdk，一般现在用的都是 SpringBoot）默认是 cglib 代理，无法对 final 方法子类化。static 是静态方法，属于类，不属于实例对象，无法被代理！
6. **事务隔离导致数据不一致。**propagation 传播机制配置错误，例如以下的代码（伪代码，忽略同一个类中的方法调用影响代理的情况）因为配置了 Propagation.REQUIRES_NEW，是新起了一个事务，即 addAddress 的事务和 addUserAndAddress 其实不是一个事务，因此两个事务之间当然就无法保证数据的一致性了。
7. **线程上下文无法同步事务。**多线程环境，因为 @Transactional 是基于 ThreadLocal 存储上下文的，多线程情况下每个线程都有自己的上下文，那么之间如何保持事务同步？保持不了，因此事务失效。
8. **不支持事务的引擎。**用的是MyISAM，这个引擎本身不支持事务!



## JTA（Java Transaction API）进行分布式事务管理

JTA（Java Transaction API）是一种标准的事务管理 API，用于分布式事务管理。Spring 通过 `PlatformTransactionManager` 和 JTA 的实现（如 Atomikos、Bitronix 或 JTA 的默认实现）支持分布式事务。配置步骤：
1. 配置 JTA 事务管理器（如 Atomikos）。
2. 设置 `JtaTransactionManager` 并整合数据源。
3. 在代码中通过注解（`@Transactional`）或编程方式管理分布式事务。



# Spring Data JPA（重在实战）

JPA 是 Java 官方定义的**持久化规范**，用于将 Java 对象映射到数据库表中，并提供了与数据库进行交互的标准 API。JPA 是一种规范，不是一种具体的实现，它规定了如何定义实体类、如何与数据库交互，但它本身并不提供具体的实现。



## JPA 的主要功能

1. **Repository 接口。**在 Spring Data JPA 中，开发者不再需要手动编写 DAO 层代码，只需要定义一个接口继承 `JpaRepository` 或 `CrudRepository`，Spring Data JPA 会自动提供常见的 CRUD 操作实现。
2. **方法名称推导查询。**Spring Data JPA 通过方法名解析机制，根据接口方法名称推导出相应的查询语句。开发者只需定义方法名，无需手动编写查询逻辑。
3. **动态查询。**Spring Data JPA 还支持复杂的动态查询，通过 `@Query` 注解或 `Criteria API` 实现自定义查询。



## JPA 的关键特性

- **实体映射**：将 Java 类映射为数据库表。
- **查询**：通过 JPQL（Java Persistence Query Language）编写查询语句。
- **缓存管理**：提供一级缓存的支持。
- **事务管理**：与数据库事务集成。

JPA 的主要实现包括 Hibernate、EclipseLink 等。



## JPA 和 Hibernate 的区别

- **Spring Data JPA**：是 Spring 框架的一个模块，提供了简化的 JPA 数据访问接口，并支持与不同 JPA 实现集成（包括 Hibernate）。它的目标是让开发者可以更加方便地进行 JPA 相关的操作。
- **Hibernate**：是一个具体的 ORM 框架，**实现了 JPA 规范**，并在此基础上扩展了很多额外功能。Hibernate 是一个完整的 ORM 解决方案，Spring Data JPA 可以使用 Hibernate 作为其底层的 JPA 实现。



## Hibernate 的优势和功能扩展

1. **HQL（Hibernate Query Language）。**Hibernate 提供了 HQL（Hibernate Query Language），它是一种面向对象的查询语言，支持通过实体类字段进行查询。HQL 比 JPQL 提供了更多的功能，并且直接与 Hibernate 的底层机制结合。
2. **延迟加载。**Hibernate 支持延迟加载，即当访问到某个实体的关联对象时才从数据库中加载该对象的数据，提升性能。JPA 也支持延迟加载，但 Hibernate 提供了更多控制选项。
3. **缓存机制。**Hibernate 提供了一级和二级缓存的支持。一级缓存是默认开启的，并且是与当前会话绑定的。二级缓存可以配置为应用级别的缓存，以提升查询性能。
4. **批量操作和批量获取。**Hibernate 提供了批量插入、更新和删除操作的支持，帮助优化大批量数据操作的性能。开发者可以通过配置批量大小来优化这些操作。
5. **自动生成数据库架构。**Hibernate 可以根据实体类的定义自动生成数据库表和外键关系，帮助开发者快速构建数据库结构。通过 `hibernate.hbm2ddl.auto` 配置项可以控制自动生成表结构的行为。



### 其他

- 如何使用 JPA 在数据库中非持久化一个字段？

- JPA 的审计功能是做什么的？有什么用？

- 实体之间的关联关系注解有哪些？



# Spring Security

- 控制请求访问权限的方法
- hasRole 和 hasAuthority 的区别

- 如何对密码进行加密？
- 如何优雅更换系统使用的加密算法？



# 范围之外



## Spring MVC 中的国际化支持是如何实现的？ 

主要通过 `LocaleResolver` 和 `ResourceBundleMessageSource` 实现，它可以根据用户的语言环境（Locale）来动态选择和显示对应语言的文本或内容，从而支持多语言的 Web 应用程序。

1. **定义国际化资源文件**：使用 `messages.properties` 等资源文件来存储不同语言的文本内容。Spring MVC 会根据当前用户的 Locale（区域设置）加载对应的资源文件。
2. **配置 `LocaleResolver`**：`LocaleResolver` 用于确定用户的语言环境（Locale），可以基于请求参数、会话、Cookie 等来解析用户的 Locale。
3. **配置 `ResourceBundleMessageSource`**：Spring 使用 `ResourceBundleMessageSource` 来加载国际化资源文件，并根据用户的 Locale 返回相应的语言内容。
4. **使用 `@RequestMapping` 或 Thymeleaf 等模板引擎的国际化标签来实现动态内容切换**。



## Spring父子容器

**父容器（管理Service层、DAO层）**：通常是 Spring 应用上下文（`ApplicationContext`），如 `ContextLoaderListener` 加载的根容器。它主要用于管理应用程序的全局 Bean，如服务层（Service）、数据访问层（DAO）等。

**子容器（管理Web层）**：每个 `DispatcherServlet` 实例都会创建一个子容器，用于管理 Web 层（如控制器和拦截器）中的 Bean。

**关系**：子容器可以访问父容器的 Bean，反之不能。

**意义：**

- **Service层与 Web 层分离**：全局的 Bean，如数据库连接池、事务管理等，可以放在父容器中，而与 Web 相关的控制器放在子容器中。
- **避免 Bean 重复定义**：例如，DAO的配置可以在父容器中定义，Web 层的配置可以在子容器中定义，这样可以防止冲突。
- **分模块管理**



## @Scheduled 注解的作用是什么？ 

**主要作用：**

- **定时任务执行**：`@Scheduled` 注解允许开发者定义一个方法，该方法会按照指定的时间规则定期执行。
- **支持多种时间配置**：支持固定延迟、固定速率以及基于 Cron 表达式的任务调度。

**使用场景：**

- 自动备份数据或定时清理系统资源。
- 定时发送通知或报告。
- 定时同步数据，如从第三方服务中定期拉取数据。

```java
@Scheduled(fixedRate = 5000)  // 每隔 5 秒执行一次
public void performTask() {
    System.out.println("Task executed at: " + new Date());
}
```



## Spring框架中的@Cacheable、@CachePut、@CacheEvict注解的作用是什么？如何配置Spring Cache？

- **`@Cacheable`**：方法执行前检查缓存，若存在缓存数据则直接返回，否则执行方法并将结果缓存。
- **`@CachePut`**：每次执行方法并将结果更新到缓存中。
- **`@CacheEvict`**：用于移除缓存数据，可清除单个或多个缓存条目。



**配置 Spring Cache：**

1. 启用缓存：使用 `@EnableCaching`。
2. 配置缓存实现（如 ConcurrentMap、Redis、EhCache）。
3. 使用注解标记缓存逻辑。





## @EventListener 注解的作用是什么？ 

用于监听和处理事件。通过标注在方法上，`@EventListener` 可以使方法自动监听特定类型的事件，并在事件发布时触发执行。

- 当系统中某个状态变化时触发特定操作。
- 日志记录、监控系统、通知系统等需要响应事件的模块。

```java
// 当 MyCustomEvent 事件被发布时，handleEvent() 方法会自动触发来处理该事件。.
@Component
public class MyEventListener {
    @EventListener
    public void handleEvent(MyCustomEvent event) {
        System.out.println("Handling event: " + event.getMessage());
    }
}
```

事件监听相关扩展知识。
