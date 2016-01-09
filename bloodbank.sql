-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2015 at 07:29 AM
-- Server version: 5.6.25
-- PHP Version: 5.5.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bloodbank`
--

-- --------------------------------------------------------

--
-- Table structure for table `donor`
--

CREATE TABLE IF NOT EXISTS `donor` (
  `DID` int(10) unsigned NOT NULL,
  `DNAME` varchar(20) NOT NULL,
  `DBGROUP` varchar(3) NOT NULL,
  `DCONTACT` varchar(20) NOT NULL,
  `DLASTDATE` varchar(10) NOT NULL,
  `DREADY` varchar(3) NOT NULL,
  `DLOCATION` varchar(20) NOT NULL,
  `DUSER` varchar(20) NOT NULL,
  `DPASS` varchar(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `donor`
--

INSERT INTO `donor` (`DID`, `DNAME`, `DBGROUP`, `DCONTACT`, `DLASTDATE`, `DREADY`, `DLOCATION`, `DUSER`, `DPASS`) VALUES
(1, 'Shubho', 'O+', '01929xxxx', '2015-12-02', 'NO', 'Dhanmondi', 'shubho1', 'password'),
(2, 'Rahat', 'A+', '01888888', '2015-09-10', 'YES', 'Khilkhet', 'rahat1', 'password'),
(3, 'Mahbub', 'O+', '8801xxxxx', '1994-05-05', 'YES', 'Mirpur', 'mahbub1', 'mahbubpass'),
(4, 'Prottoy', 'AB+', '018xxxxxx', '2015-10-11', 'YES', 'Banani', 'prottoy1', 'password'),
(5, 'sumaya', 'A-', '0167xxxxx', '2015-09-10', 'YES', 'Farmgate', 'sumaya1', 'password'),
(6, 'zubair', 'O+', '0173xxxxx', '2015-10-08', 'NO', 'Dhanmondi', 'zubair1', 'password'),
(7, 'Imran', 'B+', '0168xxxxx', '2015-10-08', 'YES', 'Khilkhet', 'imran1', 'password'),
(8, 'Shafik', 'O-', '0185xxxxx', '2015-10-14', 'YES', 'Khilkhet', 'shafik1', 'password'),
(9, 'kaium', 'AB-', '0176xxxxx', '2015-11-11', 'NO', 'Nikunja', 'kaium1', 'password'),
(11, 'Rajib', 'A+', '0194xxxxx', '2015-11-03', 'No', 'Farmgate', 'rajib1', 'password'),
(12, 'Junaed', 'A-', '0183xxxxx', '2015-09-15', 'Yes', 'Dhanmondi', 'junaed1', 'password'),
(20, 'Che', 'B-', '016xxxxx', '2015-12-16', 'YES', 'Banani', 'che1', 'password');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE IF NOT EXISTS `patient` (
  `PID` int(10) unsigned NOT NULL,
  `PNAME` varchar(20) NOT NULL,
  `PBGROUP` varchar(3) NOT NULL,
  `PCONTACT` varchar(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`PID`, `PNAME`, `PBGROUP`, `PCONTACT`) VALUES
(1, 'Dip', 'A+', '0173xxx'),
(2, 'sagar', 'O+', '016xxx'),
(3, 'zubraj', 'A-', '0172xxx'),
(4, 'kamal', 'B+', '0183xxx'),
(5, 'Saimon', 'O-', '0178xxx'),
(6, 'Mahmudul', 'B-', '0193xxx'),
(7, 'Samia', 'AB+', '0175xxx'),
(8, 'Tonmoy', 'AB-', '0156xxx'),
(9, 'Salman', 'B+', '0156xxx'),
(10, 'Ariyan', 'A+', '0197xxx'),
(11, 'Tahsina', 'A-', '0178xxx'),
(12, 'Pronoy', 'B-', '0183xxx'),
(13, 'Amrita', 'O-', '0172xxx'),
(14, 'Ankita', 'O+', '0193xxx'),
(15, 'Arnob', 'AB+', '0156xxx'),
(16, 'Shahriar', 'AB-', '0197xxx'),
(18, 'Shamim', 'A-', '017xxxxx'),
(23, 'runa', 'B-', '015xxxxx');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `donor`
--
ALTER TABLE `donor`
  ADD UNIQUE KEY `DID` (`DID`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD UNIQUE KEY `PID` (`PID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `donor`
--
ALTER TABLE `donor`
  MODIFY `DID` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `PID` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
