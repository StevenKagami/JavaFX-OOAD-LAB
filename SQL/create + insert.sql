-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 15, 2023 at 11:47 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectooad`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookpcs`
--

CREATE TABLE `bookpcs` (
  `BookId` int(11) NOT NULL,
  `PcId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `BookedDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bookpcs`
--

INSERT INTO `bookpcs` (`BookId`, `PcId`, `UserId`, `BookedDate`) VALUES
(1, 2, 6, '2023-12-13'),
(2, 3, 7, '2023-12-13'),
(3, 14, 10, '2023-12-14'),
(4, 15, 10, '2023-12-14'),
(5, 10, 9, '2023-12-15'),
(6, 11, 9, '2023-12-15'),
(7, 3, 6, '2023-12-16'),
(8, 4, 7, '2023-12-16'),
(9, 9, 9, '2023-12-17'),
(10, 14, 7, '2023-12-17');

-- --------------------------------------------------------

--
-- Table structure for table `jobs`
--

CREATE TABLE `jobs` (
  `JobId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `PcId` int(11) NOT NULL,
  `JobStatus` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jobs`
--

INSERT INTO `jobs` (`JobId`, `UserId`, `PcId`, `JobStatus`) VALUES
(1, 2, 1, 'UnComplete'),
(2, 2, 5, 'UnComplete'),
(3, 3, 8, 'UnComplete'),
(4, 3, 13, 'UnComplete'),
(5, 3, 15, 'Complete');

-- --------------------------------------------------------

--
-- Table structure for table `pcs`
--

CREATE TABLE `pcs` (
  `PcId` int(11) NOT NULL,
  `PcStatus` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pcs`
--

INSERT INTO `pcs` (`PcId`, `PcStatus`) VALUES
(1, 'Maintenance'),
(2, 'Usable'),
(3, 'Usable'),
(4, 'Usable'),
(5, 'Maintenance'),
(6, 'Maintenance'),
(7, 'Broken'),
(8, 'Broken'),
(9, 'Usable'),
(10, 'Usable'),
(11, 'Usable'),
(12, 'Usable'),
(13, 'Broken'),
(14, 'Usable'),
(15, 'Usable');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `ReportId` int(11) NOT NULL,
  `UserRole` varchar(255) NOT NULL,
  `PcId` int(11) NOT NULL,
  `ReportNote` text NOT NULL,
  `ReportDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`ReportId`, `UserRole`, `PcId`, `ReportNote`, `ReportDate`) VALUES
(1, 'Customer', 1, 'The Pc Is Maintenance', '2023-12-13'),
(2, 'Customer', 1, 'The Pc Is Maintenance', '2023-12-13'),
(3, 'Customer', 7, 'The Pc Is Broken', '2023-12-14'),
(4, 'Customer', 5, 'The Pc Is Maintenance', '2023-12-14'),
(5, 'Customer', 8, 'The Pc Is Broken', '2023-12-14'),
(6, 'Operator', 5, 'The Pc Is Maintenance', '2023-12-15'),
(7, 'Operator', 7, 'The Pc Is Broken', '2023-12-15'),
(8, 'Operator', 5, 'The Pc Is Maintenance', '2023-12-15'),
(9, 'Operator', 7, 'The Pc Is Broken', '2023-12-16'),
(10, 'Operator', 15, 'Pc nya dibook mulu mau ranked valo nih', '2023-12-20');

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetails`
--

CREATE TABLE `transactiondetails` (
  `TransactionId` int(11) NOT NULL,
  `PcId` int(11) NOT NULL,
  `CustomerName` varchar(255) NOT NULL,
  `BookedTime` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactiondetails`
--

INSERT INTO `transactiondetails` (`TransactionId`, `PcId`, `CustomerName`, `BookedTime`) VALUES
(1, 2, 'howardd', '2023-11-25'),
(1, 3, 'howardd', '2023-11-25'),
(1, 4, 'christian', '2023-11-25'),
(2, 9, 'danesckarnadi', '2023-11-26'),
(2, 10, 'howardd', '2023-11-26'),
(2, 11, 'howardd', '2023-11-26'),
(3, 2, 'howardd', '2023-11-27'),
(4, 3, 'theodoric', '2023-11-28'),
(4, 4, 'theodoric', '2023-11-28'),
(4, 9, 'theodoric', '2023-11-28'),
(4, 10, 'theodoric', '2023-11-28'),
(4, 11, 'theodoric', '2023-11-28'),
(5, 2, 'howardd', '2023-11-29'),
(5, 3, 'howardd', '2023-11-29'),
(5, 4, 'howardd', '2023-11-29'),
(5, 9, 'howardd', '2023-11-29'),
(5, 10, 'howardd', '2023-11-29'),
(5, 11, 'christian', '2023-11-29'),
(5, 12, 'howardd', '2023-11-29');

-- --------------------------------------------------------

--
-- Table structure for table `transactionheaders`
--

CREATE TABLE `transactionheaders` (
  `TransactionId` int(11) NOT NULL,
  `StaffID` int(11) NOT NULL,
  `StaffName` varchar(255) NOT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactionheaders`
--

INSERT INTO `transactionheaders` (`TransactionId`, `StaffID`, `StaffName`, `TransactionDate`) VALUES
(1, 4, 'Operator', '2023-12-01'),
(2, 4, 'Operator', '2023-12-01'),
(3, 5, 'Operator2', '2023-12-02'),
(4, 5, 'Operator2', '2023-12-02'),
(5, 5, 'Operator2', '2023-12-03');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserId` int(11) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `UserPassword` varchar(255) NOT NULL,
  `UserAge` int(11) NOT NULL,
  `UserRole` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserId`, `UserName`, `UserPassword`, `UserAge`, `UserRole`) VALUES
(1, 'Admin', 'Admin', 99, 'Admin'),
(2, 'Technician', 'Technician', 99, 'Computer Technician'),
(3, 'Technician2', 'Technician2', 99, 'Computer Technician'),
(4, 'Operator', 'Operator', 99, 'Operator'),
(5, 'Operator2', 'Operator2', 99, 'Operator'),
(6, 'Customer', 'Customer', 99, 'Customer'),
(7, 'howardd', 'howard123', 20, 'Customer'),
(8, 'christian', 'christian123', 20, 'Customer'),
(9, 'danesckarnadi', 'danesckarnadi', 43, 'Customer'),
(10, 'theodoric', 'theo123', 17, 'Customer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookpcs`
--
ALTER TABLE `bookpcs`
  ADD PRIMARY KEY (`BookId`),
  ADD KEY `UserId` (`UserId`),
  ADD KEY `PcId` (`PcId`);

--
-- Indexes for table `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`JobId`),
  ADD KEY `UserId` (`UserId`),
  ADD KEY `PcId` (`PcId`);

--
-- Indexes for table `pcs`
--
ALTER TABLE `pcs`
  ADD PRIMARY KEY (`PcId`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`ReportId`),
  ADD KEY `PcId` (`PcId`);

--
-- Indexes for table `transactiondetails`
--
ALTER TABLE `transactiondetails`
  ADD PRIMARY KEY (`TransactionId`,`PcId`),
  ADD KEY `PcId` (`PcId`);

--
-- Indexes for table `transactionheaders`
--
ALTER TABLE `transactionheaders`
  ADD PRIMARY KEY (`TransactionId`),
  ADD KEY `StaffID` (`StaffID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookpcs`
--
ALTER TABLE `bookpcs`
  MODIFY `BookId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `jobs`
--
ALTER TABLE `jobs`
  MODIFY `JobId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pcs`
--
ALTER TABLE `pcs`
  MODIFY `PcId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `ReportId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `transactionheaders`
--
ALTER TABLE `transactionheaders`
  MODIFY `TransactionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookpcs`
--
ALTER TABLE `bookpcs`
  ADD CONSTRAINT `bookpcs_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `users` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bookpcs_ibfk_2` FOREIGN KEY (`PcId`) REFERENCES `pcs` (`PcId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `jobs`
--
ALTER TABLE `jobs`
  ADD CONSTRAINT `jobs_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `users` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `jobs_ibfk_2` FOREIGN KEY (`PcId`) REFERENCES `pcs` (`PcId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`PcId`) REFERENCES `pcs` (`PcId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactiondetails`
--
ALTER TABLE `transactiondetails`
  ADD CONSTRAINT `transactiondetails_ibfk_1` FOREIGN KEY (`TransactionId`) REFERENCES `transactionheaders` (`TransactionId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transactiondetails_ibfk_2` FOREIGN KEY (`PcId`) REFERENCES `pcs` (`PcId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactionheaders`
--
ALTER TABLE `transactionheaders`
  ADD CONSTRAINT `transactionheaders_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `users` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
