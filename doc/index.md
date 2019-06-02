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
* ManyToMany: 多对多
* Embedded: 多个entity 映射到一张表
* ElementCollection: 与 `OneToMany` 相似，但是引用的entity是`Embedded`类型