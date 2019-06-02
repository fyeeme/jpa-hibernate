1. Access annotation
>jpa 提供丰富的注解，以取代xml配置，可以大大减少代码量，一般我们将注解写在字段上，但是有时候我们需要处理一些业务逻辑，此时需要将注解写在getter方法上。而jpa对两种方式都提供支持
 
    @Access(AccessType.PROPERTY)  设置从getter方法读取注解
    @Access(AccessType.FIELD)  设置从字段读取注解

2. Inheritance annotation 
>当entity需要继承时，jpa有三种逻辑。默认是通过字段来区分，通过对父类映射表增加字段 ` DTYPE varchar(31) not null` 来区别entity,以下是完整的三种情况说明

    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)     将所有的字段放在父类映射的表中，通过字段`DTYPE`区分对象类型，此模式下，数据库中的每一行都包含所有对象的属性。优点是不需要对sql进行`join`操作，查询速度更快

    @Inheritance(strategy = InheritanceType.JOINED)    每个entity都有自己的独立的映射表，不过每个entity仅包含自己定义的字段，查询时jpa会`join`所有需要的表。 优点是节省存储空间，缺点是查询有牺牲。

    @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)   每个entity会将自身以及继承的属性映射到自己表, 不过由于对象是继承关系，继承的对象公用主键。因此`id`不能重复，需要修改Id的注解策略。`@GeneratedValue(strategy = GenerationType.IDENTITY)` -> `@GeneratedValue(strategy = GenerationType.TABLE)`


3. Relationships

* OneToOne: 每一个entity与其他entity一一对应
* OneToMany / ManyToOne: 一对多或者多对一
* ManyToMany: 多对多 通过创建中间表关联两个entity之间的关系
* Embedded: 多个entity 映射到一张表 
* ElementCollection: 与 `OneToMany` 相似，但是引用的entity是`Embedded`类型


4. 类型映射


| Java type                                                                            | Database type                              |
| ------------------------------------------------------------------------------------ | ------------------------------------------ |
| String (char, char[])                                                                | VARCHAR (CHAR, VARCHAR2, CLOB, TEXT)       |
| Number (BigDecimal, BigInteger, Integer, Double, Long, Float, Short, Byte)           | NUMERIC (NUMBER, INT, LONG, FLOAT, DOUBLE) |
| int, long, float, double, short, byte                                                | NUMERIC (NUMBER, INT, LONG, FLOAT, DOUBLE) |
| byte[]                                                                               | VARBINARY (BINARY, BLOB)                   |
| boolean (Boolean)                                                                    | BOOLEAN (BIT, SMALLINT, INT, NUMBER)       |
| java.util.Date, java.sql.Date, java.sql.Time, java.sql.Timestamp, java.util.Calendar | TIMESTAMP (DATE, DATETIME)                 |
| java.lang.Enum                                                                       | NUMERIC (VARCHAR, CHAR)                    |
| java.lang.Enum                                                                       | NUMERIC (VARCHAR, CHAR)                    |
| java.util.Serializable                                                               | VARBINARY (BINARY, BLOB)                   |
	
这里面 enum 类型比较有趣， 在处理enum类型是，jpa提供了两种方式处理：

    @Enumerated(EnumType.ORDINAL) 将enum存储为数字类型，其数据库中存储的值按照索引从0开始

    @Enumerated(EnumType.STRING) 将enum存储为`VARCHAR`类型，其数据库存储的值为实际enum的值

    如果两种都不满意，jpa提供了`AttributeConverter`属性转换器，通过`@Converter`注解中指定转换器的名字自定义，示例如下：

```java
@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(Boolean aBoolean) {
        if (Boolean.TRUE.equals(aBoolean)) {
            return 1;
        } else {
            return -1;
        }
    }
 
    @Override
    public Boolean convertToEntityAttribute(Integer value) {
        if (value == null) {
            return Boolean.FALSE;
        } else {
            if (value == 1) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
    }
}

...
@Column(name = "VALID")
@Convert(converter = BooleanConverter.class)
private boolean valid;
```

5. Criteria API
    
通过`Criteria API` 我们可以使用 `JPQL`去查询数据库，而`JPQL`是与数据库具体实现无关的，因此我们可以通过它很容易实现跨数据库的应用程序，示例如下：

```java
CriteriaBuilder builder = entityManager.getCriteriaBuilder();

CriteriaQuery<Person> query = builder.createQuery(Person.class);

Root<Person> personRoot = query.from(Person.class);
query.where(builder.equal(personRoot.get("firstName"), "Homer"));
List<Person> resultList = entityManager.createQuery(query).getResultList();
```
    

6[参考链接 ](https://www.javacodegeeks.com/2015/02/jpa-tutorial.html#entitymanager)