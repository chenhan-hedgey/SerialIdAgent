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
CREATE TABLE table_name (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '全局流水号,从10000开始,至一个java long大小',
  caller VARCHAR(255) COMMENT '调用方', 
  flow_id INT NOT NULL COMMENT '规范流水号',
  status TINYINT COMMENT '状态,0成功、1异常、2失败',
  result_code INT COMMENT '调用的结果码',
  ip VARCHAR(255) COMMENT '调用方的ip地址',
  tx_code VARCHAR(255) COMMENT '交易码',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
);
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
CREATE TABLE api_log_local_id (

                                  id INT NOT NULL AUTO_INCREMENT COMMENT '自增ID',

                                  local_id INT NOT NULL COMMENT '内部调用的请求',

                                  global_id INT NOT NULL COMMENT '全局流水号,从10000开始,至int的最大值',

                                  parent_id INT COMMENT '表示本次调用的父级请求id',

                                  ip VARCHAR(255) COMMENT '调用方的ip地址',

                                  flow_id INT NOT NULL COMMENT '公司内部的规范的流水号',

                                  result VARCHAR(255) COMMENT 'json字符串',

                                  status INT COMMENT '调用的情况展示,0成功、1异常、2失败',

                                  code_value INT COMMENT '外部系统返回的结果码',

                                  api_name VARCHAR(255) COMMENT '被调用的哪一个接口',

                                  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

                                  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

                                  PRIMARY KEY (id),

                                  UNIQUE KEY unique_local_id (local_id)

) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='API日志表';
```

