CREATE TABLE `table_conf` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT 'BINLOG',
  `name` varchar(225) NOT NULL,
  `topic` varchar(225) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `offline_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creater` varchar(225) NOT NULL DEFAULT 'dafault',
  `is_online` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `attr_conf` (
  `aid` varchar(100) NOT NULL,
  `table_id` int(100) NOT NULL,
  `attr_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:实时 2:复合',
  `cal_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:拼接 2:求和 3:最大 4:最小 5:最新 6:avexp',
  `db_name` varchar(100) DEFAULT NULL,
  `tbl_name` varchar(100) DEFAULT NULL,
  `dimension_type` varchar(100) NOT NULL,
  `dimension_key` varchar(225) NOT NULL,
  `field` varchar(225) DEFAULT NULL,
  `cal_expression` varchar(225) DEFAULT NULL COMMENT 'av表达式',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_online` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;