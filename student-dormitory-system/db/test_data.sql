USE dorm_management;

-- 楼栋
INSERT INTO building (name, `type`, address) VALUES
('A栋','男生楼','北区A栋'),
('B栋','女生楼','北区B栋');

-- 宿舍
INSERT INTO dorm (building_id, room_no, floor, room_type, capacity, note) VALUES
(1,'101',1,'四人间',4,''),
(1,'102',1,'四人间',4,''),
(2,'201',2,'六人间',6,'靠近洗手间');

-- 学生
INSERT INTO student (student_no, name, `class`, phone, gender) VALUES
('2021001','张三','计算机1班','13800000001','男'),
('2021002','李四','计算机1班','13800000002','男'),
('2021003','王五','软件2班','13800000003','女');

-- 入住记录（张三住 A栋101 床位1）
INSERT INTO stay (student_id, dorm_id, bed_no, checkin_date, active) VALUES
(1,1,1,'2023-09-01',1),
(2,1,2,'2023-09-02',1),
(3,3,1,'2023-09-05',1);

-- 缴费记录（张三已缴，李四未缴）
INSERT INTO payment (student_id, amount, due_date, paid, paid_date) VALUES
(1,1,500.00,'2023-09-10',1,'2023-09-09'),
(2,2,500.00,'2023-09-10',0,NULL);

-- 违纪记录（王五未处理）
INSERT INTO violation (student_id, description, occ_date, processed) VALUES
(3,'晚归未报备','2023-10-01',0);
