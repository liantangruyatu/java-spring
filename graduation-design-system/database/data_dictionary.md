# 毕业设计管理系统 - 数据字典

## 1. 用户表(users)
| 字段名 | 数据类型 | 长度 | 是否null | 说明 |
|-------|--------|------|---------|-----|
| user_id | INT | - | NO | 用户ID，主键，自增 |
| username | VARCHAR | 50 | NO | 用户名，唯一 |
| password | VARCHAR | 255 | NO | 密码（MD5加密） |
| email | VARCHAR | 100 | YES | 邮箱 |
| role | INT | - | NO | 角色(0:系统管理员,1:教师,2:学生) |
| status | INT | - | NO | 状态(0:禁用,1:启用) |
| create_time | DATETIME | - | NO | 创建时间 |
| update_time | DATETIME | - | YES | 更新时间 |

## 2. 教师表(teachers)
| 字段名 | 数据类型 | 长度 | 是否null | 说明 |
|-------|--------|------|---------|-----|
| teacher_id | INT | - | NO | 教师ID，主键，自增 |
| user_id | INT | - | NO | 用户ID，外键关联users |
| real_name | VARCHAR | 50 | NO | 教师姓名 |
| phone | VARCHAR | 20 | YES | 电话 |
| department | VARCHAR | 100 | YES | 部门 |
| research_area | VARCHAR | 255 | YES | 研究方向 |
| max_students | INT | - | NO | 最多指导学生数，默认10 |
| current_count | INT | - | NO | 当前指导学生数，默认0 |
| create_time | DATETIME | - | NO | 创建时间 |
| update_time | DATETIME | - | YES | 更新时间 |

## 3. 学生表(students)
| 字段名 | 数据类型 | 长度 | 是否null | 说明 |
|-------|--------|------|---------|-----|
| student_id | INT | - | NO | 学生ID，主键，自增 |
| user_id | INT | - | NO | 用户ID，外键关联users |
| real_name | VARCHAR | 50 | NO | 学生姓名 |
| student_no | VARCHAR | 20 | NO | 学号，唯一 |
| major | VARCHAR | 100 | YES | 专业 |
| class_name | VARCHAR | 50 | YES | 班级 |
| phone | VARCHAR | 20 | YES | 电话 |
| create_time | DATETIME | - | NO | 创建时间 |
| update_time | DATETIME | - | YES | 更新时间 |

## 4. 毕设题目表(topics)
| 字段名 | 数据类型 | 长度 | 是否null | 说明 |
|-------|--------|------|---------|-----|
| topic_id | INT | - | NO | 题目ID，主键，自增 |
| teacher_id | INT | - | NO | 教师ID，外键关联teachers |
| title | VARCHAR | 200 | NO | 题目标题 |
| description | TEXT | - | NO | 题目描述 |
| type | INT | - | NO | 题目类型(0:工程应用,1:科研创新,2:综合实践) |
| difficulty | INT | - | NO | 难度等级(1:简单,2:中等,3:困难) |
| required_skills | VARCHAR | 255 | YES | 所需技能 |
| max_students | INT | - | NO | 最多学生数 |
| selected_count | INT | - | NO | 已选人数，默认0 |
| status | INT | - | NO | 状态(0:草稿,1:发布中,2:已结束) |
| create_time | DATETIME | - | NO | 创建时间 |
| update_time | DATETIME | - | YES | 更新时间 |

## 5. 学生选题表(selections)
| 字段名 | 数据类型 | 长度 | 是否null | 说明 |
|-------|--------|------|---------|-----|
| selection_id | INT | - | NO | 选题ID，主键，自增 |
| student_id | INT | - | NO | 学生ID，外键关联students |
| topic_id | INT | - | YES | 题目ID，外键关联topics |
| selection_type | INT | - | NO | 选题类型(0:选择已有题目,1:自拟题目) |
| custom_title | VARCHAR | 200 | YES | 自拟题目标题 |
| custom_description | TEXT | YES | 自拟题目描述 |
| teacher_id | INT | - | YES | 分配的教师ID |
| status | INT | - | NO | 申请状态(0:待审批,1:已同意,2:已拒绝) |
| apply_time | DATETIME | - | NO | 申请时间 |
| approval_time | DATETIME | - | YES | 审批时间 |
| approval_comment | TEXT | YES | 审批意见 |
| update_time | DATETIME | - | YES | 更新时间 |

## 6. 毕设进度表(progress)
| 字段名 | 数据类型 | 长度 | 是否null | 说明 |
|-------|--------|------|---------|-----|
| progress_id | INT | - | NO | 进度ID，主键，自增 |
| selection_id | INT | - | NO | 选题ID，外键关联selections |
| stage | INT | - | NO | 阶段(1:任务书,2:中期检查,3:论文完成,4:答辩) |
| status | INT | - | NO | 状态(0:未开始,1:进行中,2:已完成) |
| submit_file | VARCHAR | 255 | YES | 提交文件名 |
| submit_time | DATETIME | - | YES | 提交时间 |
| teacher_comment | TEXT | YES | 教师评价 |
| score | DECIMAL | 5,2 | YES | 成绩 |
| update_time | DATETIME | - | YES | 更新时间 |

## 7. 系统日志表(system_logs)
| 字段名 | 数据类型 | 长度 | 是否null | 说明 |
|-------|--------|------|---------|-----|
| log_id | INT | - | NO | 日志ID，主键，自增 |
| user_id | INT | - | NO | 用户ID，外键关联users |
| operation | VARCHAR | 100 | NO | 操作类型 |
| table_name | VARCHAR | 50 | YES | 操作的表名 |
| record_id | INT | - | YES | 操作的记录ID |
| old_value | TEXT | - | YES | 修改前的值 |
| new_value | TEXT | - | YES | 修改后的值 |
| create_time | DATETIME | - | NO | 操作时间 |
