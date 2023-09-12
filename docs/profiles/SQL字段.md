# SQL字段

## 全局表

| 字段名       | 类型（以Java描述）                     | 说明                                |
| ------------ | -------------------------------------- | ----------------------------------- |
| 全局流水号   | long，从10000开始，至一个java long大小 | 外部请求调用生成                    |
| 调用方       | String                                 | 长度不会太长                        |
| 规范流水号   | int                                    | 公司内部的规范的流水号              |
| 状态         | int                                    | 调用的情况展示，0成功、1异常、2失败 |
| 调用的结果码 | int                                    | 外部系统返回的结果码                |
| ip           | String                                 | 调用方的ip地址                      |
| 交易码       | String                                 | 外部系统调用时传递的交易码          |
| 创建时间     | Timestamp                              | 记录数据创建的时间                  |
| 修改时间     | Timestamp                              | 记录数据最后修改的时间              |

```sql
CREATE TABLE global_sid (
  id int(8) NOT NULL AUTO_INCREMENT COMMENT '全局流水号,从10000开始',
  caller varchar(255) COMMENT '调用方', 
  flow_id int NOT NULL COMMENT '规范流水号',
  status int COMMENT '状态,0成功、1异常、2失败',
  result_code int COMMENT '调用的结果码',
  ip varchar(255) COMMENT '调用方的ip地址',
  tx_code varchar(255) COMMENT '交易码',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='全局流水号';
```



## 局部表

| 字段名     | 类型(以Java描述) | 说明                               |
| ---------- | ---------------- | ---------------------------------- |
| 局部流水号 | int              | 内部调用的请求                     |
| 全局流水号 | int              | 从10000开始,至int的最大值          |
| 父请求id   | int              | 表示本次调用的父级请求id           |
| ip         | String           | 调用方的ip地址                     |
| 规范流水号 | int              | 公司内部的规范的流水号             |
| 调用结果   | String           | json字符串                         |
| 调用状态   | int              | 调用的情况展示,0成功、1异常、2失败 |
| 码值       | int              | 外部系统返回的结果码               |
| 调用接口名 | String           | 被调用的哪一个接口                 |
| 创建时间   | Timestamp        | 记录数据创建的时间                 |
| 修改时间   | Timestamp        | 记录数据最后修改的时间             |

```sql
CREATE TABLE local_sid (

                                  id int NOT NULL AUTO_INCREMENT COMMENT '自增ID',

                                  local_id int NOT NULL COMMENT '内部调用的请求',

                                  global_id int NOT NULL COMMENT '全局流水号,从10000开始,至int的最大值',

                                  parent_id int COMMENT '表示本次调用的父级请求id',

                                  ip varchar(255) COMMENT '调用方的ip地址',

                                  flow_id int NOT NULL COMMENT '公司内部的规范的流水号',

                                  result varchar(255) COMMENT 'json字符串',

                                  status int COMMENT '调用的情况展示,0成功、1异常、2失败',

                                  code_value int COMMENT '外部系统返回的结果码',

                                  api_name varchar(255) COMMENT '被调用的哪一个接口',

                                  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

                                  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

                                  PRIMARY KEY (id),

                                  UNIQUE KEY unique_local_id (local_id)

) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='局部流水号';
```

