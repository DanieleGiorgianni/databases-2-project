CREATE DATABASE IF NOT EXISTS `telcodb`   /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `telcodb`;

-- Host: localhost  Database: telcodb



/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `id` INT NOT NULL AUTO_INCREMENT, 
    `username` VARCHAR(45) NOT NULL, 
    `password`VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) DEFAULT NULL,
    `insolvent` BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `user`
--
LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1, 'user', 'user', 'user@mail.com', FALSE);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `sas`
--
DROP TABLE IF EXISTS `sas`;

CREATE TABLE `sas` (
    `id` INT NOT NULL AUTO_INCREMENT, 
    `deactivationdate` DATE DEFAULT NULL, 
    `userid` INT NOT NULL,
    `orderid` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`userid`)
        REFERENCES `user` (`id`),
    FOREIGN KEY (`orderid`)
        REFERENCES `order` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;



--
-- Table structure for table `order`
--
DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `monthlyfee` INT DEFAULT NULL,
    `purchasedate` DATETIME DEFAULT NULL,
    `startdate` DATE DEFAULT NULL,
    `fails` INT DEFAULT NULL,
    `valid` BOOLEAN DEFAULT NULL,
    `userid` INT DEFAULT NULL,
    `packageid` INT DEFAULT NULL,
    `validityfeeid` INT DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`userid`)
        REFERENCES `user` (`id`),
	FOREIGN KEY (`packageid`)
        REFERENCES `package` (`id`),
    FOREIGN KEY (`validityfeeid`)
        REFERENCES `validityfee` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;



--
-- Table structure for table `validityfee`
--
DROP TABLE IF EXISTS `validityfee`;

CREATE TABLE `validityfee` (
    `id` INT NOT NULL AUTO_INCREMENT, 
    `months` INT DEFAULT NULL, 
    `monthlyfee` INT DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `validityfee`
--
LOCK TABLES `validityfee` WRITE;
/*!40000 ALTER TABLE `validityfee` DISABLE KEYS */;
INSERT INTO `validityfee` VALUES (1, 12, 20);
INSERT INTO `validityfee` VALUES (2, 24, 18);
INSERT INTO `validityfee` VALUES (3, 36, 15);
/*!40000 ALTER TABLE `validityfee` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `package`
--
DROP TABLE IF EXISTS `package`;

CREATE TABLE `package` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `employeeid` INT DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`employeeid`)
        REFERENCES `employee` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `package`
--
-- LOCK TABLES `package` WRITE;
-- /*!40000 ALTER TABLE `package` DISABLE KEYS */;
-- INSERT INTO `package` VALUES (1, 'Pack 1 FX PHONE', NULL);
-- /*!40000 ALTER TABLE `package` ENABLE KEYS */;
-- UNLOCK TABLES;



--
-- Table structure for table `service`
--
DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(45) DEFAULT  NULL,
    `minsnum` INT DEFAULT NULL,
    `minsfee` INT DEFAULT NULL,
    `smsnum` INT DEFAULT NULL,
    `smsfee` INT DEFAULT NULL,
    `giganum` INT DEFAULT NULL,
    `gigafee` INT DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `service`
--
LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1, 'fixed phone', 1000, 1, NULL, NULL, NULL, NULL);
INSERT INTO `service` VALUES (2, 'mobile phone', 100, 1, 100, 2, NULL, NULL);
INSERT INTO `service` VALUES (3, 'fixed internet', NULL, NULL, NULL, NULL, 500, 3);
INSERT INTO `service` VALUES (4, 'mobile internet', NULL, NULL, NULL, NULL, 50, 4);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `product`
--
DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) DEFAULT NULL,
    `monthlyfee` INT DEFAULT NULL,
    `employeeid` INT DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`employeeid`)
        REFERENCES `employee` (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `product`
--
-- LOCK TABLES `product` WRITE;
-- /*!40000 ALTER TABLE `product` DISABLE KEYS */;
-- INSERT INTO `product` VALUES (1, 'SMS news feed', 10, NULL);
-- INSERT INTO `product` VALUES (2, 'Internet TV channel', 20, NULL);
-- /*!40000 ALTER TABLE `product` ENABLE KEYS */;
-- UNLOCK TABLES;



--
-- Table structure for table `alert`
--
DROP TABLE IF EXISTS `alert`;

CREATE TABLE `alert` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `amount`INT DEFAULT  NULL,
    `lastdatetime` DATETIME DEFAULT NULL,
    `userid` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`userid`)
        REFERENCES `user` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;



--
-- Table structure for table `employee`
--
DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `employeename` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `employee`
--
LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1, 'max', 'max', 'max@mail.com');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;




-- RELATIONS "MANY TO MANY"

--
-- Table structure for table `package_contains_product`
--
DROP TABLE IF EXISTS `package_contains_product`;

CREATE TABLE `package_contains_product` (
    `packageid` INT NOT NULL,
    `productid` INT NOT NULL,
    PRIMARY KEY (`packageid`, `productid`),
    FOREIGN KEY (`packageid`)
        REFERENCES  `package` (`id`),
    FOREIGN KEY (`productid`)
        REFERENCES  `product` (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `package_contains_product`
--
-- LOCK TABLES `package_contains_product` WRITE;
-- /*!40000 ALTER TABLE `package_contains_product` DISABLE KEYS */;
-- INSERT INTO `package_contains_product` VALUES (1, 1);	/*Pack 1 FX PHONE contains SMS news feed*/
-- INSERT INTO `package_contains_product` VALUES (1, 2);	/*Pack 1 FX PHONE contains Internet TV channel*/
-- /*!40000 ALTER TABLE `package_contains_product` ENABLE KEYS */;
-- UNLOCK TABLES;



--
-- Table structure for table `package_includes_service`
--
DROP TABLE IF EXISTS `package_includes_service`;

CREATE TABLE `package_includes_service` (
    `packageid` INT NOT NULL,
    `serviceid` INT NOT NULL,
    PRIMARY KEY (`packageid`,`serviceid`),
    FOREIGN KEY (`packageid`)
        REFERENCES  `package` (`id`),
    FOREIGN KEY (`serviceid`)
        REFERENCES  `service` (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `package_includes_service`
--
-- LOCK TABLES `package_includes_service` WRITE;
-- /*!40000 ALTER TABLE `package_includes_service` DISABLE KEYS */;
-- INSERT INTO `package_includes_service` VALUES (1, 1);	/*Pack 1 FX PHONE includes fixed phone*/
-- INSERT INTO `package_includes_service` VALUES (1, 2);	/*Pack 1 FX PHONE includes mobile phone*/
-- /*!40000 ALTER TABLE `package_includes_service` ENABLE KEYS */;
-- UNLOCK TABLES;



--
-- Table structure for table `order_comprises_product`
--
DROP TABLE IF EXISTS `order_comprises_product`;

CREATE TABLE `order_comprises_product` (
    `orderid` INT NOT NULL,
    `productid` INT NOT NULL,
    PRIMARY KEY (`orderid`,`productid`),
    FOREIGN KEY (`orderid`)
        REFERENCES  `order` (`id`),
    FOREIGN KEY (`productid`)
        REFERENCES  `product` (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;



--
-- Table structure for table `package_offers_validityfee`
--
DROP TABLE IF EXISTS `package_offers_validityfee`;

CREATE TABLE `package_offers_validityfee` (
    `packageid` INT NOT NULL,
    `validityfeeid` INT NOT NULL,
    PRIMARY KEY (`packageid`,`validityfeeid`),
    FOREIGN KEY (`packageid`)
        REFERENCES  `package` (`id`),
    FOREIGN KEY (`validityfeeid`)
        REFERENCES  `validityfee` (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

--
-- Dumping data for table `package_offers_validityfee`
--
-- LOCK TABLES `package_offers_validityfee` WRITE;
-- /*!40000 ALTER TABLE `package_offers_validityfee` DISABLE KEYS */;
-- INSERT INTO `package_offers_validityfee` VALUES (1, 1);	/*Pack 1 FX PHONE offers 12 months at 20 euros per month*/
-- INSERT INTO `package_offers_validityfee` VALUES (1, 2);	/*Pack 1 FX PHONE offers 24 months at 18 euros per month*/
-- INSERT INTO `package_offers_validityfee` VALUES (1, 3);	/*Pack 1 FX PHONE offers 36 months at 15 euros per month*/
-- /*!40000 ALTER TABLE `package_offers_validityfee` ENABLE KEYS */;
-- UNLOCK TABLES;



/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- -----------------------------------------TRIGGERS-----------------------------------------------------
-- Insolvent Users --
-- Event 1: new Order with payment failed
-- Event: Insert Order
-- Condition: valid = FALSE
-- Action: Update user.insolvent



-- Rejected Orders --
-- Event 1: new Order with validity = FALSE
-- Event: Insert Order
-- Condition: valid = FALSE
-- Action: Update number of fails



-- Alerts --
-- Event 1: User failed payments 
-- Event: Insert Order
-- Condition: valid = FALSE and fails = 3 (2+1)
-- Action: alert --> not possible to connect table order to alert



--
-- Table : `purchase_per_package`
--
DROP TABLE IF EXISTS `purchase_per_package`;

CREATE TABLE `purchase_per_package` (
    `packageid` INT NOT NULL,
    `packagepurchases` INT DEFAULT NULL,
    PRIMARY KEY (`packageid`)
);

-- Event 1: New package Creation
-- Event: New Package
-- Condition: None
-- Action: Insert values into purchase_per_package

CREATE TRIGGER purchase_per_package_new_package 
AFTER INSERT ON `package`
FOR EACH ROW
INSERT INTO purchase_per_package VALUES (new.id, 0);


-- Event 2: New order Creation
-- Event: Insert new order into Order table
-- Condition: Order must be valid
-- Action: Update purchase_per_package and increment packagepurchases
delimiter //
CREATE TRIGGER purchase_per_package_new_order
AFTER INSERT ON `order`
FOR EACH ROW
BEGIN
	IF new.valid = TRUE
	THEN
		UPDATE purchase_per_package
		SET packagepurchases = packagepurchases + 1
		WHERE purchase_per_package.packageid = new.packageid;
	END IF;
END;
//

-- Event 3: Failed order into valid
-- Event: Update existing order
-- Condition: old.valid=false and new.valid=true
-- Action: Update purchase_per_package and increment packagepurchases
delimiter //
CREATE TRIGGER purchase_per_package_fix_order
AFTER UPDATE ON `order`
FOR EACH ROW
BEGIN
	IF old.valid = FALSE AND new.valid = TRUE
	THEN
		UPDATE purchase_per_package
		SET packagepurchases = packagepurchases + 1
		WHERE purchase_per_package.packageid = new.packageid;
	END IF;
END;
//


-- 
-- Table : `purchase_per_package_and_validityperiod`
--
delimiter //
DROP TABLE IF EXISTS `purchase_per_package_and_validityperiod`;
//
delimiter //
CREATE TABLE `purchase_per_package_and_validityperiod` (
    `packageid` INT NOT NULL,
    `packagepurchases` INT DEFAULT NULL,
    `validityfeeid` INT NOT NULL,
    `validityfeemonths` INT DEFAULT NULL,
    PRIMARY KEY (`packageid`, `validityfeeid`)
);
//

-- Event 1: New package Creation with validity period
-- Event: New Package with VP
-- Condition: None 
-- Action: Insert into package_offers_validityfee
delimiter //
CREATE TRIGGER purchase_per_package_and_validityperiod
AFTER INSERT ON `package_offers_validityfee`
FOR EACH ROW
BEGIN
	DECLARE feemonths INT;
	SET feemonths = (SELECT vf.months FROM validityfee vf WHERE vf.id = new.validityfeeid);
	INSERT INTO `purchase_per_package_and_validityperiod` VALUES (new.packageid, 0, new.validityfeeid, feemonths);
END;
//

-- Event 2: New order WITH validity period 
-- Event: Insert new order into Order table 
-- Condition: Order must be valid
-- Action: Update purchase_per_package_and_validityperiod and increment packagepurchases 
delimiter //
CREATE TRIGGER purchase_per_package_and_validityperiod_new_order
AFTER INSERT ON `order`
FOR EACH ROW
BEGIN
	IF new.valid = TRUE
	THEN
		UPDATE purchase_per_package_and_validityperiod
		SET packagepurchases = packagepurchases + 1
		WHERE purchase_per_package_and_validityperiod.packageid = new.packageid 
			AND purchase_per_package_and_validityperiod.validityfeeid = new.validityfeeid;
	END IF;
END;
//

-- Event 3: Failed order into valid
-- Event: Update existing order
-- Condition: old.valid=false and new.valid=true
-- Action: Update purchase_per_package and increment packagepurchases
delimiter //
CREATE TRIGGER purchase_per_package_and_validityperiod_fix_order
AFTER UPDATE ON `order`
FOR EACH ROW
BEGIN
	IF old.valid = FALSE AND new.valid = TRUE
	THEN
		UPDATE purchase_per_package_and_validityperiod
		SET packagepurchases = packagepurchases + 1
		WHERE purchase_per_package_and_validityperiod.packageid = new.packageid
			AND purchase_per_package_and_validityperiod.validityfeeid = new.validityfeeid;
	END IF;
END;
//



-- 
-- Table : `total_sales_per_package`
--
delimiter //
DROP TABLE IF EXISTS `total_sales_per_package`;
//
delimiter //
CREATE TABLE `total_sales_per_package` (
    `packageid` INT NOT NULL,
    `packagesoldwithproducts` INT DEFAULT NULL,
    `packagesoldwithoutproducts` INT DEFAULT NULL,
    PRIMARY KEY (`packageid`)
);
//

-- Event 1: New package puchase with validity period
-- Event: Insert Package
-- Condition: None
-- Action: Insert values into total_sales_per_package
delimiter //
CREATE TRIGGER total_sales_per_package_new_package
AFTER INSERT ON `package`
FOR EACH ROW
INSERT INTO total_sales_per_package VALUES (new.id, 0, 0);
//


-- Event 2: New order with validity period
-- Event: Insert Order
-- Condition: Order must be valid
-- Action: Update total_sales_per_package 
delimiter //
CREATE TRIGGER total_sales_per_package_new_order
AFTER INSERT ON `order`
FOR EACH ROW
BEGIN
	IF new.valid = TRUE AND (SELECT COUNT(*) FROM total_sales_per_package ts WHERE new.packageid = ts.packageid) > 0
    THEN
		IF new.monthlyfee = (SELECT vf.monthlyfee FROM validityfee vf WHERE new.validityfeeid = vf.id)
        THEN
			UPDATE total_sales_per_package
            SET packagesoldwithoutproducts = packagesoldwithoutproducts + 1
            WHERE total_sales_per_package.packageid = new.packageid;
		ELSE
			UPDATE total_sales_per_package
            SET packagesoldwithproducts = packagesoldwithproducts + 1
            WHERE total_sales_per_package.packageid = new.packageid;
		END IF;
	END IF;
END;
//

-- Event 3: Order from invalid to valid
-- Event: Update Order
-- Condition: order invalid become valid
-- Action: Update total_sales_per_package 
delimiter //
CREATE TRIGGER total_sales_per_package_fix_order
AFTER UPDATE ON `order`
FOR EACH ROW
BEGIN
	IF old.valid = FALSE AND new.valid = TRUE AND (SELECT COUNT(*) FROM total_sales_per_package ts WHERE new.packageid = ts.packageid) > 0
    THEN
		IF new.monthlyfee = (SELECT vf.monthlyfee FROM validityfee vf WHERE new.validityfeeid = vf.id)
        THEN
			UPDATE total_sales_per_package
            SET packagesoldwithoutproducts = packagesoldwithoutproducts + 1
            WHERE total_sales_per_package.packageid = new.packageid;
		ELSE
			UPDATE total_sales_per_package
            SET packagesoldwithproducts = packagesoldwithproducts + 1
            WHERE total_sales_per_package.packageid = new.packageid;
		END IF;
	END IF;
END;
//



-- 
-- Table : `average_optional_products_per_package`
--
delimiter //
DROP TABLE IF EXISTS `average_optional_products_per_package`;
//
delimiter //
CREATE TABLE `average_optional_products_per_package` (
    `packageid` INT NOT NULL, 
    `orderwithproduct` INT DEFAULT NULL,
    `totalorder` INT DEFAULT NULL,
    `averageproduct` FLOAT DEFAULT NULL,
    PRIMARY KEY (`packageid`)
)
//

-- Event 1: new Package Creation
-- Event: New Package is created
-- Condition: none
-- Action: Insert values into average_optional_products_per_package
delimiter //
CREATE TRIGGER average_optional_products_per_package_new_package
AFTER INSERT ON `package_contains_product`
FOR EACH ROW
IF (SELECT COUNT(*) FROM average_optional_products_per_package ao WHERE ao.packageid = new.packageid) = 0
THEN
	INSERT INTO average_optional_products_per_package VALUES (new.packageid, 0, 0, 0);
END IF;
//

-- Event 2: new Order with/without products
-- Event: new Order is created
-- Condition: Order MUST be valid
-- Action:  Update average_optional_products_per_package
delimiter //
CREATE TRIGGER average_optional_products_per_package_new_order
AFTER INSERT ON `order`
FOR EACH ROW
BEGIN
    IF new.valid = TRUE AND (SELECT COUNT(*) FROM average_optional_products_per_package ao WHERE new.packageid = ao.packageid) > 0
    THEN
		IF new.monthlyfee = (SELECT vf.monthlyfee FROM validityfee vf WHERE new.validityfeeid = vf.id)
	    THEN
			UPDATE average_optional_products_per_package
			SET totalorder = totalorder + 1,
			averageproduct = orderwithproduct / totalorder
            WHERE average_optional_products_per_package.packageid = new.packageid;
		ELSE
			UPDATE average_optional_products_per_package
			SET totalorder = totalorder + 1,
            orderwithproduct = orderwithproduct + 1,
			averageproduct = orderwithproduct / totalorder
            WHERE average_optional_products_per_package.packageid = new.packageid;
		END IF;		
    END IF;
END;
//

-- Event 3: Order with/without products from invalid to valid
-- Event: Update Order
-- Condition: order.valid from invalid to valid
-- Action:  Update average_optional_products_per_package
delimiter //
CREATE TRIGGER average_optional_products_per_package_fix_order
AFTER UPDATE ON `order`
FOR EACH ROW
BEGIN
    IF old.valid = FALSE AND new.valid = TRUE AND (SELECT COUNT(*) FROM average_optional_products_per_package ao WHERE new.packageid = ao.packageid) > 0
    THEN
		IF new.monthlyfee = (SELECT vf.monthlyfee FROM validityfee vf WHERE new.validityfeeid = vf.id)
	    THEN
			UPDATE average_optional_products_per_package
			SET totalorder = totalorder + 1,
			averageproduct = orderwithproduct / totalorder
            WHERE average_optional_products_per_package.packageid = new.packageid;
		ELSE
			UPDATE average_optional_products_per_package
			SET totalorder = totalorder + 1,
            orderwithproduct = orderwithproduct + 1,
			averageproduct = orderwithproduct / totalorder
            WHERE average_optional_products_per_package.packageid = new.packageid;
		END IF;		
    END IF;	
END;
//



-- 
-- Table : `optional_product_best_seller`
--
delimiter //
DROP TABLE IF EXISTS `optional_product_best_seller`;
//
delimiter //
CREATE TABLE `optional_product_best_seller` (
    `productid` INT NOT NULL,
    `productsales` INT DEFAULT NULL,
    PRIMARY KEY (`productid`)
)
//

-- Event 1: new Product Creation
-- Event: new product is created
-- Condition: none
-- Action: Insert new Product into optional_product_best_seller
delimiter //
CREATE TRIGGER optional_product_best_seller_new_product
AFTER INSERT ON `product` 
FOR EACH ROW 
INSERT INTO optional_product_best_seller VALUES (new.id, 0)
//

-- Event 2: new Order WITH Product(s) Creation
-- Event : Insert new order into Order table
-- Condition : order must be valid
-- Action : Update optional_product_best_seller in particular increment the number of productsales
delimiter //
CREATE TRIGGER optional_product_best_seller_new_order
AFTER INSERT ON `order_comprises_product`
FOR EACH ROW
BEGIN
	DECLARE valid BOOLEAN;
    SET valid = (SELECT o.valid FROM `order` o WHERE o.id = new.orderid);
	IF (SELECT COUNT(*) FROM optional_product_best_seller op WHERE op.productid = new.productid) > 0 AND valid = TRUE
    THEN
		UPDATE optional_product_best_seller
        SET productsales = productsales + 1
        WHERE new.productid = optional_product_best_seller.productid;
    END IF;
END;
//

-- Event 3: Order WITH Product(s) became valid
-- Event : Update Order 
-- Condition : order from invalid to valid
-- Action : Update optional_product_best_seller in particular increment the number of productsales
delimiter //
CREATE TRIGGER optional_product_best_seller_fix_order
AFTER UPDATE ON `order`
FOR EACH ROW
BEGIN
	DECLARE i INT DEFAULT 0;
	IF old.valid = FALSE AND new.valid = TRUE AND (SELECT COUNT(*) FROM `order_comprises_product` oc WHERE oc.orderid = new.id) > 0
    THEN
			SET i = (SELECT COUNT(*) FROM order_comprises_product oc);
			SET i = i - 1;
            WHILE i >= 0 DO
				UPDATE optional_product_best_seller
				SET productsales = productsales + 1
				WHERE optional_product_best_seller.productid = (SELECT oc.productid FROM order_comprises_product oc LIMIT i,1)
					AND new.id = (SELECT oc.orderid FROM order_comprises_product oc LIMIT i,1);
				SET i = i - 1;
			END WHILE;
	END IF;
END;
//
