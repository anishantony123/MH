DROP database `hospital`;

CREATE DATABASE `hospital` ;


CREATE TABLE `hospital`.`admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `hospital`.`users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `reg_no` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `company` varchar(45) DEFAULT NULL,
  `address_1` varchar(1000) DEFAULT NULL,
  `address_2` varchar(1000) DEFAULT NULL,
  `pincode` varchar(1000) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `diaganosis` varchar(45) DEFAULT NULL,
  `center` varchar(45) DEFAULT NULL,
  `active_YN` char(1) NOT NULL DEFAULT 'A',
  `created_by` int(11) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `care_off` varchar(45) DEFAULT NULL,
  `informer` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `created_fk` (`created_by`),
  KEY `modified_fk` (`modified_by`),
  CONSTRAINT `created_fk` FOREIGN KEY (`created_by`) REFERENCES `hospital`.`admin` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_fk` FOREIGN KEY (`modified_by`) REFERENCES `hospital`.`admin` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `hospital`.`user_data` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL DEFAULT '-1',
  `DATAFILE_NAME` varchar(45) DEFAULT NULL,
  `DATAFILE_CONTENT` longblob,
  `SEQUENCE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `users_fk` (`USER_ID`),
  CONSTRAINT `users_fk` FOREIGN KEY (`USER_ID`) REFERENCES `hospital`.`users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO `hospital`.`admin` (`id`, `username`, `password`) VALUES
	(1, 'admin', 'admin');






