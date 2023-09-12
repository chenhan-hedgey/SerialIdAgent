DROP TABLE IF EXISTS `global_sid`;
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