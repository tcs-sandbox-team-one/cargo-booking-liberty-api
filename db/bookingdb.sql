CREATE SCHEMA `bookingmsdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
use bookingmsdb;

CREATE USER 'bookingmsdb'@'%' IDENTIFIED BY 'bookingmsdb';
GRANT ALL PRIVILEGES ON bookingmsdb.* TO 'bookingmsdb'@'%';

##Cargo Table DDL
CREATE TABLE `cargo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BOOKING_ID` varchar(20) NOT NULL,
  `TRANSPORT_STATUS` varchar(100) NOT NULL,
  `ROUTING_STATUS` varchar(100) NOT NULL,
  `spec_origin_id` varchar(20) DEFAULT NULL,
  `spec_destination_id` varchar(20) DEFAULT NULL,
  `SPEC_ARRIVAL_DEADLINE` date DEFAULT NULL,
  `origin_id` varchar(20) DEFAULT NULL,
  `BOOKING_AMOUNT` int(11) NOT NULL,
  `handling_event_id` int(11) DEFAULT NULL,
  `next_expected_location_id` varchar(20) DEFAULT NULL,
  `next_expected_handling_event_type` varchar(20) DEFAULT NULL,
  `next_expected_voyage_id` varchar(20) DEFAULT NULL,
  `last_known_location_id` varchar(20) DEFAULT NULL,
  `current_voyage_number` varchar(100) DEFAULT NULL,
  `last_handling_event_id` int(11) DEFAULT NULL,
  `last_handling_event_type` varchar(20) DEFAULT NULL,
  `last_handling_event_location` varchar(20) DEFAULT NULL,
  `last_handling_event_voyage` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2923 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


##Leg Table DDL
	CREATE TABLE `leg` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOAD_TIME` timestamp NULL DEFAULT NULL,
  `UNLOAD_TIME` timestamp NULL DEFAULT NULL,
  `load_location_id` varchar(20) DEFAULT NULL,
  `unload_location_id` varchar(20) DEFAULT NULL,
  `voyage_number` varchar(100) DEFAULT NULL,
  `CARGO_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3095 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;