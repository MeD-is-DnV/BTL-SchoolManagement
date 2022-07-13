-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th7 05, 2022 lúc 10:02 AM
-- Phiên bản máy phục vụ: 10.4.17-MariaDB
-- Phiên bản PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `school_management`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class`
--

CREATE TABLE `class` (
  `class_id` varchar(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `class`
--

INSERT INTO `class` (`class_id`, `name`, `status`) VALUES
('3EsedPyXgF', 'K35B1DL', 1),
('7MkdBdy0rq', 'K33B1DL', 0),
('BOSCxNxuro', 'K34B3DL', 1),
('C9dbWCzDpa', 'K34B2DL', 1),
('V5qwa7wkYk', 'K34B1DL', 1),
('vfZepuAexE', 'K35B2DL', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class_details`
--

CREATE TABLE `class_details` (
  `class_id` varchar(11) NOT NULL,
  `subject_id` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `class_details`
--

INSERT INTO `class_details` (`class_id`, `subject_id`) VALUES
('3EsedPyXgF', 'IAstLFsqGd'),
('7MkdBdy0rq', '2nkh4bWOmu'),
('7MkdBdy0rq', '2NZResTTow'),
('7MkdBdy0rq', 'JyADk3bcfy'),
('7MkdBdy0rq', 'qHAly8tCRV'),
('BOSCxNxuro', '2nkh4bWOmu'),
('BOSCxNxuro', 'Qu4lhSj14s'),
('C9dbWCzDpa', '2nkh4bWOmu'),
('C9dbWCzDpa', 'Qu4lhSj14s'),
('C9dbWCzDpa', 't3jJNXx7Kp'),
('V5qwa7wkYk', '2nkh4bWOmu'),
('V5qwa7wkYk', 'qHAly8tCRV'),
('V5qwa7wkYk', 'Qu4lhSj14s'),
('vfZepuAexE', 'IAstLFsqGd'),
('vfZepuAexE', 't3jJNXx7Kp');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `result`
--

CREATE TABLE `result` (
  `result_id` varchar(20) NOT NULL,
  `student_id` varchar(11) NOT NULL,
  `subject_id` varchar(11) NOT NULL,
  `point` float NOT NULL,
  `start_day` date NOT NULL,
  `end_date` date NOT NULL,
  `status` tinyint(1) NOT NULL,
  `subject_not_active` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `result`
--

INSERT INTO `result` (`result_id`, `student_id`, `subject_id`, `point`, `start_day`, `end_date`, `status`, `subject_not_active`) VALUES
('13IGv', '1y6Ta7HDBK', 't3jJNXx7Kp', 7, '2022-04-24', '2022-04-27', 1, b'0'),
('5IuRL', '4w3CEQZdMq', 'Qu4lhSj14s', 9, '2022-07-01', '2022-07-03', 1, b'0'),
('5JiKV', 't64XepzGSu', 'qHAly8tCRV', 6, '2022-07-01', '2022-07-03', 1, b'0'),
('9MkN2', 'KjTYfPmoB6', 't3jJNXx7Kp', 8, '2022-04-24', '2022-04-27', 1, b'0'),
('b1WpJ', '1y6Ta7HDBK', 'Qu4lhSj14s', 7.7, '2022-04-24', '2022-04-27', 1, b'0'),
('DshdX', 'U66v4biAYM', 'Qu4lhSj14s', 8.9, '2022-07-01', '2022-07-03', 1, b'0'),
('E9GJx', 'U66v4biAYM', '2nkh4bWOmu', 6.5, '2022-07-01', '2022-07-03', 1, b'0'),
('eSSAS', 'q10gqCnPkH', '2nkh4bWOmu', 7, '2022-07-01', '2022-07-03', 1, b'0'),
('GmL3i', 'QLjxMFI31f', 'Qu4lhSj14s', 8.5, '2022-07-01', '2022-07-03', 1, b'0'),
('HSqQX', '1y6Ta7HDBK', 'Qu4lhSj14s', 7, '2022-06-28', '2022-06-30', 1, b'0'),
('IDYgv', '1y6Ta7HDBK', '2nkh4bWOmu', 7, '2022-04-24', '2022-04-27', 1, b'0'),
('IhIdC', 'KjTYfPmoB6', 't3jJNXx7Kp', 7, '2022-06-28', '2022-06-30', 1, b'0'),
('iKYsI', 'KjTYfPmoB6', '2nkh4bWOmu', 8.5, '2022-04-24', '2022-04-27', 1, b'0'),
('IVXMf', 't64XepzGSu', '2nkh4bWOmu', 6, '2022-07-01', '2022-07-03', 1, b'0'),
('JP3mK', '4w3CEQZdMq', '2nkh4bWOmu', 9, '2022-07-01', '2022-07-03', 1, b'0'),
('k6e17', 'GlndRC0ID6', 'Qu4lhSj14s', 8, '2022-04-24', '2022-04-27', 1, b'0'),
('K8BJb', 'GlndRC0ID6', '2nkh4bWOmu', 8.5, '2022-04-24', '2022-04-27', 1, b'0'),
('KAjVX', 'ZLcvaBieRj', 'qHAly8tCRV', 5, '2022-07-01', '2022-07-03', 1, b'0'),
('kBsPE', 'QLjxMFI31f', '2nkh4bWOmu', 8.5, '2022-07-01', '2022-07-03', 1, b'0'),
('KQD80', 'qUvb8v7IhC', 'qHAly8tCRV', 7.8, '2022-07-01', '2022-07-03', 1, b'0'),
('KRnDI', 'U66v4biAYM', 'qHAly8tCRV', 5, '2022-07-01', '2022-07-03', 1, b'0'),
('KuepF', '4w3CEQZdMq', 'qHAly8tCRV', 9, '2022-07-01', '2022-07-03', 1, b'0'),
('l3zPt', 'GlndRC0ID6', 't3jJNXx7Kp', 8, '2022-04-24', '2022-04-27', 1, b'0'),
('L5B2w', 'JkkZY43PWI', 'Qu4lhSj14s', 8, '2022-07-01', '2022-07-03', 1, b'0'),
('M7CRo', 'q10gqCnPkH', 'qHAly8tCRV', 7, '2022-07-01', '2022-07-03', 1, b'0'),
('mwpmq', 't64XepzGSu', 'Qu4lhSj14s', 6, '2022-07-01', '2022-07-03', 1, b'0'),
('N1y84', 'JkkZY43PWI', 'qHAly8tCRV', 8, '2022-07-01', '2022-07-03', 1, b'0'),
('n2PBP', 'wxefce8K9x', 'Qu4lhSj14s', 7, '2022-07-01', '2022-07-03', 1, b'0'),
('nwG5a', 'qUvb8v7IhC', 'Qu4lhSj14s', 7.9, '2022-07-01', '2022-07-03', 1, b'0'),
('oh6gC', 'KjTYfPmoB6', 'Qu4lhSj14s', 8, '2022-06-28', '2022-06-30', 1, b'0'),
('OHykb', '1y6Ta7HDBK', '2nkh4bWOmu', 7, '2022-06-28', '2022-06-30', 1, b'0'),
('OnMB7', 'qUvb8v7IhC', '2nkh4bWOmu', 7.7, '2022-07-01', '2022-07-03', 1, b'0'),
('oWsCf', 'GlndRC0ID6', 'Qu4lhSj14s', 8, '2022-06-28', '2022-06-30', 1, b'0'),
('p5r4J', 'wxefce8K9x', '2nkh4bWOmu', 9, '2022-07-01', '2022-07-03', 1, b'0'),
('pHwlA', 'KjTYfPmoB6', '2nkh4bWOmu', 8.7, '2022-06-28', '2022-06-30', 0, b'0'),
('pjrgQ', 'q10gqCnPkH', 'Qu4lhSj14s', 7, '2022-07-01', '2022-07-03', 1, b'0'),
('qB4ns', 'ZLcvaBieRj', '2nkh4bWOmu', 5, '2022-07-01', '2022-07-03', 1, b'0'),
('QYycH', 'JkkZY43PWI', '2nkh4bWOmu', 8, '2022-07-01', '2022-07-03', 1, b'0'),
('tN9ko', 'wxefce8K9x', 'qHAly8tCRV', 5.5, '2022-07-01', '2022-07-03', 1, b'0'),
('TssYJ', 'ZLcvaBieRj', 'Qu4lhSj14s', 5, '2022-07-01', '2022-07-03', 1, b'0'),
('uJ0an', 'QLjxMFI31f', 'qHAly8tCRV', 8.5, '2022-07-01', '2022-07-03', 1, b'0'),
('UZnIW', 'GlndRC0ID6', '2nkh4bWOmu', 8, '2022-06-28', '2022-06-30', 1, b'0'),
('vjJiz', 'GlndRC0ID6', 't3jJNXx7Kp', 8, '2022-06-28', '2022-06-30', 1, b'0'),
('wiR7l', '1y6Ta7HDBK', 't3jJNXx7Kp', 7, '2022-06-28', '2022-06-30', 1, b'0'),
('zMsuF', 'KjTYfPmoB6', 'Qu4lhSj14s', 8.5, '2022-04-24', '2022-04-27', 1, b'0');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `student`
--

CREATE TABLE `student` (
  `student_id` varchar(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `card_id` varchar(12) NOT NULL,
  `gender` tinyint(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `address` varchar(1000) NOT NULL,
  `class_id` varchar(11) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `student`
--

INSERT INTO `student` (`student_id`, `name`, `dob`, `card_id`, `gender`, `email`, `phone_number`, `address`, `class_id`, `status`) VALUES
('1y6Ta7HDBK', 'Nguyen Thi Bich', '2002-06-30', '011011011011', 0, 'bichkachu@gmail.com', '0855589512', 'Quang Ninh', 'C9dbWCzDpa', 1),
('3GNj7t8Ud7', 'Le Hong Thanh', '2003-09-22', '016016016016', 0, 'hongthanh@gmail.com', '0952231158', 'Phu Tho', '3EsedPyXgF', 0),
('4w3CEQZdMq', 'Dang Duy Khanh', '2002-11-11', '005005005005', 1, 'khanhdang@gmail.com', '0963325784', 'Ha Noi', 'V5qwa7wkYk', 1),
('5cnEl6pAsV', 'Dinh Van Manh', '2002-01-06', '014014014014', 1, 'manhdinh@gmail.com', '0367727951', 'Quang Ninh', 'C9dbWCzDpa', 0),
('atsBrZJL1r', 'Trinh Xuan Hieu', '2001-11-20', '015015015015', 1, 'xuanhieu@gmail.com', '0986661020', 'Hoang Mai, Ha Noi', 'V5qwa7wkYk', 0),
('GlndRC0ID6', 'Le Phuong Thao', '2002-02-20', '013013013013', 0, 'phuongthao@gmail.com', '0359921458', 'Ha Giang', 'C9dbWCzDpa', 1),
('JkkZY43PWI', 'Nguyen Tien Dung', '2002-02-25', '008008008008', 1, 'tiendung__@gmail.com', '0389925564', 'Ha Noi', 'V5qwa7wkYk', 1),
('KjTYfPmoB6', 'Le Dang Binh', '2002-08-15', '010010010010', 1, 'dangbinh@gmail.com', '0361121131', 'Hai Phong', 'C9dbWCzDpa', 1),
('q10gqCnPkH', 'Nguyen Hong Nhung', '2002-08-07', '009009009009', 0, 'hongnhung@gmail.com', '0978990254', 'Ha Nam', 'V5qwa7wkYk', 1),
('QLjxMFI31f', 'Pham Quang Linh', '2002-05-06', '007007007007', 1, 'quanglinh@gmail.com', '0821199986', 'Ha Noi', 'V5qwa7wkYk', 1),
('qUvb8v7IhC', 'Nguyen Mai Linh', '2002-02-10', '003003003003', 0, 'linhlinh123@gmail.com', '0879921458', 'Hai Duong', 'V5qwa7wkYk', 1),
('s5el5TvXGn', 'To Minh Tam', '2002-06-02', '012012012012', 0, 'minhtam@gmail.com', '0998821569', 'Cao Bang', 'V5qwa7wkYk', 0),
('t64XepzGSu', 'Nguyen Van Duy', '2002-09-27', '001001001001', 1, 'abc123@gmail.com', '0367727999', 'Ha Noi', 'V5qwa7wkYk', 1),
('U66v4biAYM', 'Kieu Tri Lang', '2002-07-04', '002002002002', 1, 'lang123@gmail.com', '0896657514', 'Ha Noi', 'V5qwa7wkYk', 1),
('wxefce8K9x', 'Vu Tuan Kiet', '2002-05-05', '004004004004', 1, 'kietvu@gmail.com', '0847751982', 'Ha Noi', 'V5qwa7wkYk', 1),
('ZLcvaBieRj', 'La Thi Huong', '2002-10-21', '006006006006', 0, 'huongla@gmail.com', '0875521110', 'Thanh Hoa', 'V5qwa7wkYk', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `subject`
--

CREATE TABLE `subject` (
  `subject_id` varchar(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `subject`
--

INSERT INTO `subject` (`subject_id`, `name`, `status`) VALUES
('2nkh4bWOmu', 'Java Spring MVC', 1),
('2NZResTTow', 'Java Android', 1),
('8c2bXnjVxI', 'Asp.Net', 0),
('IAstLFsqGd', 'SQL', 1),
('JyADk3bcfy', 'API', 1),
('l43SLKkd9n', 'Java Core', 0),
('LJKHwLKCuv', 'C#', 0),
('nAOwUGjre9', 'PHP MVC', 0),
('qHAly8tCRV', 'Java Spring Boot', 1),
('Qu4lhSj14s', 'Java Web Basic', 1),
('t3jJNXx7Kp', 'MySQL', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`class_id`);

--
-- Chỉ mục cho bảng `class_details`
--
ALTER TABLE `class_details`
  ADD PRIMARY KEY (`class_id`,`subject_id`);

--
-- Chỉ mục cho bảng `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`result_id`);

--
-- Chỉ mục cho bảng `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `class_id` (`class_id`);

--
-- Chỉ mục cho bảng `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`subject_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
