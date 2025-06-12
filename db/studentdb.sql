-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
-- Host: 127.0.0.1
-- Generation Time: May 18, 2025 at 12:49 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studentdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`id`, `title`, `description`) VALUES
(1, 'Introduction to Java', 'Learn Java basics.'),
(2, 'Web Development', 'Learn HTML, CSS and JavaScript.'),
(3, 'Data Structure & Algorithms', 'Learn data structure and algorithms in Java.'),
(5, 'Math Discreet', 'Learn Math');

-- --------------------------------------------------------

--
-- Table structure for table `enrollments`
--

CREATE TABLE `enrollments` (
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrollments`
--

INSERT INTO `enrollments` (`student_id`, `course_id`) VALUES
(1, 1),
(1, 2),
(3, 1),
(3, 2),
(4, 1),
(4, 3),
(5, 1),
(5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `student_number` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `user_id`, `student_number`) VALUES
(1, 2, 'S1234567'),
(2, 5, 'S2025001'),
(3, 7, 'S2025002'),
(4, 8, 'S2025008'),
(5, 9, 'S2025012');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `role`) VALUES
(2, 'John Student', 'john@student.com', '$argon2id$v=19$m=65536,t=2,p=1$ZGVmYXVsdA$XD34d1vxCR7At1fjvMjnaGUFLAKN+rQ6tVubnK9KZz0', 'student'),
(5, 'Admin', 'admin@admin.com', '$argon2i$v=19$m=65536,t=2,p=1$OtfvUHmtOZOFtBAk6S4DYQ$9dxecfyMy8WXxjwasJ2DVDK5nPg7kqyzlUvyyQAqYEY', 'admin'),
(6, 'Azrul', 'azrul@student.com', '$argon2i$v=19$m=65536,t=2,p=1$GZYBwKL5bvkQaOV0QUKqJQ$iLPLX7JJsw2blu1rUDnTrO3J5tNZ7l9CD2/Ue17nv/U', 'student'),
(7, 'Azman', 'azman@admin.com', '$argon2i$v=19$m=65536,t=2,p=1$/gsaEEodMB3U3kJkCZPVfw$oG+zmW2aF2YOg9KFMUJPxyyXAzFc1R97eJxushEcq1A', 'student'),
(8, 'Azroy', 'azroy@student.com', '$argon2i$v=19$m=65536,t=2,p=1$I+XuV5gxpgmPYooWJXe3Gg$vgHI39yZ9duh2ABHuWRlFWwT0BX0c7gCGtNQfwo9bqg', 'student'),
(9, 'Ali', 'ali@student.com', '$argon2i$v=19$m=65536,t=2,p=1$3G7p2YGUkUDHI9IiyGBTAQ$ebUVlNb3dqycJiEb5MwxVeKAvzxBOMVLfKDBMFnqVv8', 'student');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `enrollments`
--
ALTER TABLE `enrollments`
  ADD PRIMARY KEY (`student_id`,`course_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `enrollments`
--
ALTER TABLE `enrollments`
  ADD CONSTRAINT `enrollments_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `enrollments_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
