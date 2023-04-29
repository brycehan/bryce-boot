# bryce-boot

Bryce Boot Project
Spring Boot, Mybatis Plus Project

# jpa

- ddl 参数:
  create：每次运行程序时，都会重新创建表，故而数据会丢失
  create-drop：每次运行程序时会先创建表结构，然后待程序结束时清空表
  upadte：每次运行程序，没有表时会创建表，如果对象发生改变会更新表结构，原有数据不会清空，只会更新（推荐使用）
  validate：运行程序会校验数据与数据库的字段类型是否相同，字段不同会报错
  none: 禁用DDL处理

Spring Data JPA 使用文档
[https://www.jianshu.com/p/c23c82a8fcfc](https://www.jianshu.com/p/c23c82a8fcfc)

- 数据源
  https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter

## Spring Security

默认用户 user
密码看控制台输出

```
Using generated security password: 255e01b4-089b-4631-aacf-7a60dac3f766
```

```
# token 命名
{
    "access_token": "57_jHmYMLW6ekaIix99PIyQNxpgSFvO_AP233-Cj2bS27zEXdYAWVXJskCvJ6iuZS4mUZ6uA-PVeUzn5_cgNem69qJ8JzF_bO-kuEPa4Vg13kq3_ahYpeXP9j64KPkyQhqaT38nBhDkhvqPeLhYWIFhACALXU", 
    "expires_in": 7200
}
```

## Jackson

```
# to Json 字符串
ObjectMapper objectMapper
objectMapper.writeValueAsString(new SysUser());

# to Object 对象
objectMapper
String sysUserStr = "{\"userName\":\"张三\"}";
objectMapper.readValue(sysUserStr, SysUser.class);
```