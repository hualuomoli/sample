CREATE DATABASE IF NOT EXISTS `springboot-mybatis` DEFAULT CHARSET utf8;
use `springboot-mybatis`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `money` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

