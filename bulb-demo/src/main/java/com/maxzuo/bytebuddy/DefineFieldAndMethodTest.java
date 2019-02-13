package com.maxzuo.bytebuddy;

import com.maxzuo.bytebuddy.model.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.modifier.FieldManifestation;
import net.bytebuddy.description.modifier.MethodManifestation;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * 使用ByteBuddy定义属性和方法
 * Created by zfh on 2019/01/28
 */
@Token("hello")
class DefineFieldAndMethodTest {

    private static final Logger logger = LoggerFactory.getLogger(DefineFieldAndMethodTest.class);

    @DisplayName("增加类")
    @Test
    void testEnhanceClass () throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        Token token = getClass().getAnnotation(Token.class);
        DynamicType.Unloaded<Boss> dynamicType = new ByteBuddy()
                .subclass(Boss.class)
                // 序列号 serialVersionUID
                .serialVersionUid(123456L)
                // 继承接口
                .implement(Employee.class)
                // 定义构造函数
                //.defineConstructor(Visibility.PUBLIC)
                // 构造函数的参数
                //.withParameters(String.class)
                // 添加字段，设置修饰符 public static final
                .defineField("name", String.class, Visibility.PUBLIC, Ownership.STATIC, FieldManifestation.FINAL)
                // 设置默认值（只有在字段声明为 static final时，该值才对代码可见）；还可以为代码不可见的非静态字段设置默认值
                .value("dazuo")
                // 字段添加注解
                .annotateField(token)
                // 定义Bean的 age 属性（setter/getter），如果设置为 true 属性添加 final 关键字，默认为false
                .defineProperty("age", Integer.class, false)
                // 字段添加注解
                .annotateField(token)
                // 添加方法，返回值类型void
                .defineMethod("methodOne", void.class, Visibility.PUBLIC)
                // 当前方法添加 String 类型的参数
                .withParameters(String.class)
                // 方法抛出的异常
                .throwing(Exception.class)
                .intercept(FixedValue.value("hello"))
                // 类的可见性
                .modifiers(Visibility.PUBLIC)
                // 类的注解
                .annotateType(token)
                // 类的泛型
                .typeVariable("T")
                .name("com.maxzuo.demo.ByteBuddyDemo")
                .make();

        writeToFile(dynamicType.getBytes());

        // 实例化
        dynamicType.load(getClass().getClassLoader()).getLoaded().newInstance();
    }

    @DisplayName("方法固定返回值")
    @Test
    void testMatcherMethod () {
        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("com.maxzuo.demo.ByteBuddyDemo")
                // 按照方法名称匹配 方法
                .method(named("toString"))
                // 覆盖toString方法先前的默认值
                .intercept(FixedValue.value("Hello World!"))
                .make();

        writeToFile(dynamicType.getBytes());
    }

    @DisplayName("使用多个规则")
    @Test
    void testUseMultipleRules () {
        try {
            Foo foo = new ByteBuddy()
                    .subclass(Foo.class)
                    // 规则一：匹配已声明元素的原始声明类型。
                    .method(isDeclaredBy(Foo.class))
                    .intercept(FixedValue.value("one！"))
                    // 规则二：匹配foo命名的方法
                    .method(named("foo"))
                    .intercept(FixedValue.value("two!"))
                    // 规则三：匹配food命名且参数个数为 1 个的方法
                    .method(named("foo").and(takesArguments(1)))
                    .intercept(FixedValue.value("three！"))
                    // 忽略匹配的方法，不会被覆盖
                    .ignoreAlso(named("foo").and(takesArguments(1)))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded()
                    .newInstance();

            // 按照规则顺序匹配，依次覆盖上一级方法
            System.out.println("methodOne：" + foo.bar());
            System.out.println("methodTwo：" + foo.foo());
            System.out.println("methodThree：" + foo.foo("hello"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("参数类型无法转换")
    @Test
    void testIllegalAssignment () {
        try {
            new ByteBuddy()
                    .subclass(Foo.class)
                    // 匹配目标方法
                    .method(named("foo"))
                    // 目标方法的返回值是 String 类型，此时赋值Integer类型，将由于 参数类型无法转换 抛出异常
                    .intercept(FixedValue.value(111))
                    .make();

        } catch (Exception e) {
            logger.error("异常信息", e);
        }
    }

    @DisplayName("委派静态方法调用")
    @Test
    void testInvokeDelegationMethod () {
        /*
            在许多情况下，从方法返回固定值当然是不够的。为了获得更大的灵活性，Byte Buddy提供了MethodDelegation实现，它为方法调用
            提供了最大的自由度。方法委托定义了动态创建类型的方法，以将任何调用转发给可能位于动态类型之外的另一个方法。这样，动态类
            的逻辑可以使用普通Java表示，而只有通过代码生成实现与另一个方法的绑定。

            目标方法的注解：
              @Argument(0)：表示源方法参数的位置
              @AllArguments：目标方法的参数必须是一个数组，源方法的参数必须可分配给目标方法的数组；否则不将当前目标方法视为绑定到源方法的候选方法。
                           这里说明 候选方法 不代表一定绑定，如果有其它更符合的绑定，将优先匹配。
              @this：源实例对象的引用 将分配到 目标方法的参数。生成代码：return Target.print3(this)
              @SuperCall：

            总结：
              1.Byte Buddy不要求将目标方法与 源方法 相同命名。String类型比Object类型更具体，因此匹配第一个方法。
              2.Target类如果存在 多个 相同参数不同命名的方法，将抛出不能匹配模糊性异常。
              3.Target类的方法必须是 public static 修饰的方法。
         */
        DynamicType.Unloaded<Source> dynamicType = new ByteBuddy()
                .subclass(Source.class)
                .method(named("hello"))
                .intercept(MethodDelegation.to(Target.class))
                .name("com.maxzuo.bytebuddy.SourceSub")
                .make();

        writeToFile(dynamicType.getBytes());
    }

    @DisplayName("@SuperCall注解")
    @Test
    void testInvokeDelegationMethodAndAnnotationArgument () {
        try {
            MemoryDatabase memoryDatabase = new ByteBuddy()
                    .subclass(MemoryDatabase.class)
                    .method(named("load"))
                    // 调用切面
                    .intercept(MethodDelegation.to(LoggerInterceptor.class))
                    // 调用父类方法
                    //.intercept(MethodDelegation.to(ChangingLoggerInterceptor.class))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded()
                    .newInstance();
            List<String> list = memoryDatabase.load("args");
            System.out.println("list: " + list);
        } catch (Exception e) {
            logger.error("异常信息", e);
        }
    }

    @DisplayName("委托实例方法")
    @Test
    void testDelegationInstanceMethod () {
        DynamicType.Unloaded<Source> dynamicType = new ByteBuddy()
                .subclass(Source.class)
                .method(named("hello"))
                .intercept(MethodDelegation.to(new Target()))
                .name("com.maxzuo.bytebuddy.SourceSub")
                .make();

        writeToFile(dynamicType.getBytes());
    }

    @DisplayName("调用接口的默认方法")
    @Test
    void testInvokeDefaultMethod () {
        try {
            Class<?> aClass = new ByteBuddy(ClassFileVersion.JAVA_V8)
                    .subclass(Object.class)
                    .implement(IFast.class)
                    .implement(ISlow.class)
                    // 实现不同接口的相同方法，定义优先加载
                    .method(named("m")).intercept(DefaultMethodCall.prioritize(IFast.class))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded();

            Object o1 = aClass.newInstance();
            Method m = aClass.getMethod("m");
            Object invoke = m.invoke(o1);
            System.out.println("invoke: " + invoke);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("调用指定的方法")
    @Test
    void testInvokeTargetMethod () throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        DynamicType.Unloaded<Boss> dynamicType = new ByteBuddy()
                .subclass(Boss.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
                // 命中方法
                .defineConstructor(Visibility.PUBLIC)
                //.withParameters(System.class)
                .intercept(MethodCall.construct(Boss.class.getDeclaredConstructor()))
                .name("com.maxzuo.bytebuddy.SourceSub")
                .make();

        writeToFile(dynamicType.getBytes());

        dynamicType.load(getClass().getClassLoader()).getLoaded().newInstance();
    }

    @DisplayName("访问字段")
    @Test
    void testFieldAccessor () {
        // TODO:
        try {
            Class<? extends UserType> dynamicUserType = new ByteBuddy()
                    .subclass(UserType.class)
                    .method(not(isDeclaredBy(Object.class)))
                    .intercept(MethodDelegation.toField("interceptor"))
                    .defineField("interceptor", Interceptor.class, Visibility.PRIVATE)
                    .implement(InterceptionAccessor.class)
                    .intercept(FieldAccessor.ofBeanProperty())
                    .make()
                    .load(getClass().getClassLoader())
                    .getLoaded();

            InstanceCreator factory = new ByteBuddy()
                    .subclass(InstanceCreator.class)
                    .method(not(isDeclaredBy(Object.class)))
                    .intercept(MethodDelegation.toConstructor(dynamicUserType))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded()
                    .newInstance();

            // 创建动态增强的新实例
            UserType userType = (UserType) factory.makeInstance();
            // 应用于新创建的实例
            ((InterceptionAccessor) userType).setInterceptor(new HelloWorldInterceptor());
            System.out.println(((InterceptionAccessor) userType).getInterceptor().doSomethingElse());

        } catch (Exception e) {
            logger.error("异常信息", e);
        }
    }

    /**
     * 将字节数组写入.class文件中
     * @param bytes 字节数组
     */
    private void writeToFile (byte[] bytes) {
        File file = new File("ByteBuddy.class");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.flush();
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
