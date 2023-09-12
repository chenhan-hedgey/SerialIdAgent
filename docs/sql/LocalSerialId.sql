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