-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 02, 2024 at 03:30 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `file`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL,
  `created_date_time` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `modified_date_time` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `created_date_time`, `created_by`, `status`, `modified_date_time`, `updated_by`, `address`, `dob`, `full_name`, `gender`, `phone`, `user_id`) VALUES
(1, NULL, 'SUPER_ADMIN', 1, '2024-06-30 22:30:01.000000', 'hvv@gmail.com', 'Hanoi 1', '2024-06-13', 'Nguyen Van A', 'MALE', '0987651234', 2),
(2, NULL, 'SUPER_ADMIN', 1, NULL, 'SUPER_ADMIN', 'Hanoi', NULL, 'Lê Thị Lan', 'MALE', NULL, 3),
(3, NULL, 'SUPER_ADMIN', 1, NULL, 'SUPER_ADMIN', 'HCM', NULL, 'Pham Minh Hiep', 'MALE', NULL, 4);

-- --------------------------------------------------------

--
-- Table structure for table `files`
--

CREATE TABLE `files` (
  `id` bigint(20) NOT NULL,
  `created_date_time` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `modified_date_time` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `original_filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `file_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `files`
--

INSERT INTO `files` (`id`, `created_date_time`, `created_by`, `status`, `modified_date_time`, `updated_by`, `description`, `link`, `user_id`, `original_filename`, `file_type_id`) VALUES
(7, '2024-07-02 22:14:19.000000', 'superadmin@gmail.com', 1, '2024-07-02 22:14:19.000000', 'superadmin@gmail.com', 'Html And JS', '94f89a14-cef1-4084-994e-bc51264f7d9e', 1, '01_ html va_javascript.pdf', 2),
(8, '2024-07-02 22:17:42.000000', 'superadmin@gmail.com', 1, '2024-07-02 22:17:42.000000', 'superadmin@gmail.com', 'Đề thi', '4fac7d42-a374-494d-a561-ab274f6fed6a', 1, 'dethi.pdf', 2);

-- --------------------------------------------------------

--
-- Table structure for table `file_type`
--

CREATE TABLE `file_type` (
  `id` bigint(20) NOT NULL,
  `created_date_time` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `modified_date_time` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `file_type`
--

INSERT INTO `file_type` (`id`, `created_date_time`, `created_by`, `status`, `modified_date_time`, `updated_by`, `name`) VALUES
(1, '2024-07-02 21:59:13.000000', 'superadmin@gmail.com', NULL, '2024-07-02 21:59:13.000000', 'superadmin@gmail.com', 'C++'),
(2, '2024-07-02 21:59:18.000000', 'superadmin@gmail.com', NULL, '2024-07-02 21:59:18.000000', 'superadmin@gmail.com', 'PDF'),
(5, '2024-07-02 22:17:18.000000', 'superadmin@gmail.com', NULL, '2024-07-02 22:17:18.000000', 'superadmin@gmail.com', 'pdf');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `created_date_time` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `modified_date_time` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `created_date_time`, `created_by`, `status`, `modified_date_time`, `updated_by`, `name`) VALUES
(1, NULL, NULL, 1, NULL, NULL, 'SUPER_ADMIN'),
(2, NULL, NULL, 1, NULL, NULL, 'ADMIN'),
(3, NULL, NULL, 1, NULL, NULL, 'CLIENT');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_date_time` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `modified_date_time` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(120) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created_date_time`, `created_by`, `status`, `modified_date_time`, `updated_by`, `email`, `password`, `phone_number`) VALUES
(1, NULL, NULL, 1, '2024-06-30 17:47:01.000000', 'superadmin@gmail.com', 'superadmin@gmail.com', '$2a$10$qWQ3vjmYIP31wYkmMHMFXO6agJ7qDrvrNJigUx1wFeDtJDfPUb/we', NULL),
(2, NULL, 'SUPER_ADMIN', 1, NULL, 'SUPER_ADMIN', 'hvv@gmail.com', '$2a$10$D9b4xIeFBlhOUVFJMt2dcOkVDn9putQfLXnd0rN0Gour3tWDUHdAy', '0987651234'),
(3, NULL, 'SUPER_ADMIN', 1, NULL, 'SUPER_ADMIN', 'nvt@gmail.com', '$2a$10$N.5jdYdmTzbQJfNJvF4t0eFSdlIPXNuyvd8VohfVPgwD1wj.CXcNC', '0987651234'),
(4, NULL, 'SUPER_ADMIN', 1, NULL, 'SUPER_ADMIN', 'pmh@gmail.com', '$2a$10$7KYpM1cSmZ9RdGOePV/.bO1Mw6FUp8iMSszzouHLU91rru4DD6qli', '0911134567');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 3),
(4, 1),
(4, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKeuat1oase6eqv195jvb71a93s` (`user_id`);

--
-- Indexes for table `files`
--
ALTER TABLE `files`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdgr5hx49828s5vhjo1s8q3wdp` (`user_id`),
  ADD KEY `FK56a80nt923011julqtlr4v3qa` (`file_type_id`);

--
-- Indexes for table `file_type`
--
ALTER TABLE `file_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `files`
--
ALTER TABLE `files`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `file_type`
--
ALTER TABLE `file_type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customers`
--
ALTER TABLE `customers`
  ADD CONSTRAINT `FKrh1g1a20omjmn6kurd35o3eit` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `files`
--
ALTER TABLE `files`
  ADD CONSTRAINT `FK56a80nt923011julqtlr4v3qa` FOREIGN KEY (`file_type_id`) REFERENCES `file_type` (`id`),
  ADD CONSTRAINT `FKdgr5hx49828s5vhjo1s8q3wdp` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
