DROP TABLE IF EXISTS `daily_record`;
CREATE TABLE  `daily_record` (
  `RECORD_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `VOLUME` BIGINT(20) UNSIGNED DEFAULT NULL,
  `DATE` DATE NOT NULL,
  `STOCK_ID` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`RECORD_ID`) USING BTREE,
  KEY `FK_STOCK_TRANSACTION_STOCK_ID` (`STOCK_ID`),
  CONSTRAINT `FK_STOCK_TRANSACTION_STOCK_ID` FOREIGN KEY (`STOCK_ID`) 
  REFERENCES `stock` (`STOCK_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;