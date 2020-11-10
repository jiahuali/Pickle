# Pickle
基于APT的简单依赖注入实现



## 使用

#### 步骤1：接口继承Injectable

```java
public interface TestDao extends Injectable {
    void add();

    void delete();
}
```

#### 步骤2：接口实现类标注@Bean注解

如果需要注入的接口的实现类只有一个，@Bean注解的value可以为空，否则请按需分配name。

```java
@Bean("testDao1")
public class TestDaoImpl1 implements TestDao {
    private static final String TAG = "ljh:TestDaoImpl";

    @Override
    public void add() {
        Log.i(TAG, "add1: ");
    }

    @Override
    public void delete() {
        Log.i(TAG, "delete1: ");
    }
}
```

#### 步骤3：调用PickleApi注入

在依赖使用前，请先调用PickeApi注入，PickleLib提供了三种方式注入。

##### 方式1:基于Bridge的简单注入

该种方式可满足接口只有单一实现类且不使用name特性的需求，Bridge回调时，给属性赋值的顺序必须与申请依赖的顺序一致。

``` java
public class InjectWithBridgeSample {

    private TestService testService;
    private TestDao testDao;

    InjectWithBridgeSample() {
        PickleApi.inject((injection) -> {
            testService = (TestService) injection[0];
            testDao = (TestDao) injection[1];
        }, TestService.class, TestDao.class);
    }
}
```

##### 方式2：基于Bridge的复杂注入

该种方式可满足使用name特性的需求，Bridge回调时，给属性赋值的顺序必须与申请依赖的顺序一致。

``` java
public class InjectWithBridgeAndNameSample {
    private TestService testService;
    private TestDao testDao;
    private TestDao testDao1;
    private TestDao testDao2;

    InjectWithBridgeAndNameSample() {
        //inject without implantation name
        PickleApi.inject((injection) -> {
                    testService = (TestService) injection[0];
                    testDao1 = (TestDao) injection[1];
                    testDao2 = (TestDao) injection[2];
                    testDao = (TestDao) injection[3];
                }, new KeyClass("", TestService.class),
                new KeyClass("testDao1", TestDao.class),
                new KeyClass("testDao2", TestDao.class),
                new KeyClass("", TestDao.class));
    }
}
```

##### 方式3：基于Autowired注入

该种方式只需标注@Autowired注解及其对应的name（也可无），由Pickle自动完成注入，因为使用了反射特性，存在一定的性能开销。

``` java
public class InjectWithAutoWiredSample {

    @Autowired
    private TestService testService;

    @Autowired
    private TestDao testDao;

    @Autowired("testDao1")
    private TestDao testDao1;

    @Autowired("testDao2")
    private TestDao testDao2;

    InjectWithAutoWiredSample() {
        //inject with autoWired
        PickleApi.inject(this);
    }
}
```









