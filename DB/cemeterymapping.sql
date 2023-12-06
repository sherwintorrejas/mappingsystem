-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 06, 2023 at 01:34 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cemeterymapping`
--

-- --------------------------------------------------------

--
-- Table structure for table `grave`
--

CREATE TABLE `grave` (
  `GRAVE_ID` int(255) NOT NULL,
  `SECTION_NO` varchar(150) NOT NULL,
  `SPACE` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `grave`
--

INSERT INTO `grave` (`GRAVE_ID`, `SECTION_NO`, `SPACE`) VALUES
(1, 'AREA 1', 11),
(2, 'AREA 2', 59);

-- --------------------------------------------------------

--
-- Table structure for table `registered_deceased`
--

CREATE TABLE `registered_deceased` (
  `RECORD_ID` int(255) NOT NULL,
  `GRAVE_ID` int(255) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `LASTNAME` varchar(255) NOT NULL,
  `BORN_DATE` varchar(255) NOT NULL,
  `DEATH_DATE` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `registered_deceased`
--

INSERT INTO `registered_deceased` (`RECORD_ID`, `GRAVE_ID`, `NAME`, `LASTNAME`, `BORN_DATE`, `DEATH_DATE`) VALUES
(1, 2, 'LOPSA', 'DWAD', '2023-12-05', '2023-12-06');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contact` int(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `email`, `contact`, `password`) VALUES
(5, 'sherwin', 'sherwin@gmail.com', 0, 'ADMIN'),
(6, 'ADMIN', 'TEST@TEST.TEST', 694848, '$2a$10$DUsLHWaxae3bU4ihdYIQUOZtVZhu1W8tMQVTVc3TAOWrJytsRu8y2'),
(7, 'A', 'A@A.COM', 11111, '$2a$10$jVwH9ByZO1Kua7d/KazoAuTttFNdQVyYtJR5z1pDrF9imbA0QoFj2'),
(8, '1sherwin', 'sa@d.c', 16868168, '$2a$10$l9oa0jL5AfEuwkTRSRMcLOf4YRGkrNJdojUkOSOOKoXWiOpnE8xEa');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `grave`
--
ALTER TABLE `grave`
  ADD PRIMARY KEY (`GRAVE_ID`);

--
-- Indexes for table `registered_deceased`
--
ALTER TABLE `registered_deceased`
  ADD PRIMARY KEY (`RECORD_ID`),
  ADD KEY `GRAVE_ID` (`GRAVE_ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `grave`
--
ALTER TABLE `grave`
  MODIFY `GRAVE_ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `registered_deceased`
--
ALTER TABLE `registered_deceased`
  MODIFY `RECORD_ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `registered_deceased`
--
ALTER TABLE `registered_deceased`
  ADD CONSTRAINT `registered_deceased_ibfk_1` FOREIGN KEY (`GRAVE_ID`) REFERENCES `grave` (`GRAVE_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
