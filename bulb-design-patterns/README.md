## JAVA 设计模式

主要记录一些设计模式的入门示例，以及收藏一些经典绝妙的应用案例

### [设计模式之六大原则](https://www.cnblogs.com/dolphin0520/p/3919839.html)

#### 一.单一职责原则

> **单一职责原则(Single Responsibility Principle, SRP)：一个类只负责一个功能领域中的相应职责。**

单一职责原则是实现高内聚、低耦合的指导方针，它是最简单但又最难运用的原则，需要设计人员发现类的不同职责并将其分离，
而发现类的多重职责需要设计人员具有较强的分析设计能力和相关实践经验。

#### 二.开闭原则

> **开闭原则(Open-Closed Principle, OCP)：一个软件实体应当对扩展开放，对修改关闭。即软件实体应尽量在不修改原有代码的情况下进行扩展。**

在开闭原则的定义中，**软件实体可以指一个软件模块、一个由多个类组成的局部结构或一个独立的类**。

任何软件都需要面临一个很重要的问题，即它们的需求会随时间的推移而发生变化。当软件系统需要面对新的需求时，我们应该尽量保证系统的设计框架
是稳定的。如果一个软件设计符合开闭原则，那么可以非常方便地对系统进行扩展，而且在扩展时无须修改现有代码，使得软件系统在拥有适应性和灵活
性的同时具备较好的稳定性和延续性。随着软件规模越来越大，软件寿命越来越长，软件维护成本越来越高，设计满足开闭原则的软件系统也变得越来越重要。

为了满足开闭原则，需要对系统进行抽象化设计，**抽象化是开闭原则的关键**。在Java、C#等编程语言中，可以为系统定义一个相对稳定的抽象层，而将
不同的实现行为移至具体的实现层中完成。在很多面向对象编程语言中都提供了接口、抽象类等机制，可以通过它们定义系统的抽象层，再通过具体类
来进行扩展。如果需要修改系统的行为，无须对抽象层进行任何改动，只需要增加新的具体类来实现新的业务功能即可，实现在不修改已有代码的基础
上扩展系统的功能，达到开闭原则的要求。

#### 三.里氏替换原则

> **里氏代换原则(Liskov Substitution Principle, LSP)：所有引用基类（父类）的地方必须能透明地使用其子类的对象。**

里氏代换原则是实现开闭原则的重要方式之一，由于使用基类对象的地方都可以使用子类对象，因此**在程序中尽量使用基类类型来对对象进行定义，
而在运行时再确定其子类类型，用子类对象来替换父类对象。**

#### 四.依赖倒置原则

> **依赖倒转原则(Dependency Inversion  Principle, DIP)：抽象不应该依赖于细节，细节应当依赖于抽象。换言之，要针对接口编程，而不是针对实现编程。**

依赖倒转原则要求我们在程序代码中传递参数时或在关联关系中，尽量引用层次高的抽象层类，即使用接口和抽象类进行变量类型声明、参数类型声明、方法返回类型声明，
以及数据类型的转换等，而不要用具体类来做这些事情。为了确保该原则的应用，一个具体类应当只实现接口或抽象类中声明过的方法，而不要给出多余的方法，
否则将无法调用到在子类中增加的新方法。

#### 五.接口隔离原则

> **接口隔离原则(Interface  Segregation Principle, ISP)：使用多个专门的接口，而不使用单一的总接口，即客户端不应该依赖那些它不需要的接口。**

根据接口隔离原则，当一个接口太大时，我们需要将它分割成一些更细小的接口，使用该接口的客户端仅需知道与之相关的方法即可。每一个接口应该承担
一种相对独立的角色，不干不该干的事，该干的事都要干。这里的“接口”往往有两种不同的含义：一种是指一个类型所具有的方法特征的集合，仅仅是一种
逻辑上的抽象；另外一种是指某种语言具体的“接口”定义，有严格的定义和结构，比如Java语言中的interface。

#### 六.迪米特法则

> **迪米特法则(Law of  Demeter, LoD)：一个软件实体应当尽可能少地与其他实体发生相互作用。**

如果一个系统符合迪米特法则，那么当其中某一个模块发生修改时，就会尽量少地影响其他模块，扩展会相对容易，这是对软件实体之间通信的限制，
迪米特法则要求限制软件实体之间通信的宽度和深度。迪米特法则可降低系统的耦合度，使类与类之间保持松散的耦合关系。

### 设计模式分类

- 观察者模式
- 代理模式
- 适配器模式
- 单例模式
- 工厂模式
- 建造者模式
- 装饰者模式
- 策略模式
- 责任链模式
- callback
- 模板方法

### 参考文献

1.[Java 语言的各种编程模式](https://java-design-patterns.com/patterns/)
2.[iluwatar/java-design-patterns](https://github.com/iluwatar/java-design-patterns)