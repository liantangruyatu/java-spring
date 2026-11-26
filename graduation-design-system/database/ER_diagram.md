# 毕业设计管理系统 - ER图说明

## 实体关系图（ER Diagram）

```
                           users (用户表)
                             |
                             | (1:1)
                    _________|________
                   |                  |
              teachers         students
              (教师表)           (学生表)
                   |
                   | (1:N)
                   |
                topics (毕设题目表)
                   |
                   | (1:N)
                   |
            selections (学生选题表)
                   |
                   | (1:N)
                   |
             progress (毕设进度表)
```

## 关系说明

### 1. users 与 teachers (1:1)
- 一个教师用户对应一条教师记录
- 通过 user_id 关联

### 2. users 与 students (1:1)
- 一个学生用户对应一条学生记录
- 通过 user_id 关联

### 3. teachers 与 topics (1:N)
- 一个教师可以发布多个毕设题目
- 通过 teacher_id 关联

### 4. students 与 selections (1:N)
- 一个学生可以选择多个题目（历年可能多次）
- 但实际系统中会限制每个学生只能有一个有效选题

### 5. topics 与 selections (1:N)
- 一个题目可以被多个学生选择（在max_students的限制内）
- 通过 topic_id 关联

### 6. selections 与 progress (1:N)
- 一个选题对应多个进度阶段记录
- 通过 selection_id 关联

### 7. teachers 与 selections (1:N)
- 教师可以审批多个学生的选题申请
- 教师可以指导多个学生

### 8. users 与 system_logs (1:N)
- 记录每个用户的系统操作日志

## 主要业务流程

### 学生选题流程
1. 学生查看教师发布的题目列表
2. 学生可以选择现有题目或自拟题目
3. 生成选题申请（status=0:待审批）
4. 教师审批申请（status=1:同意 或 status=2:拒绝）
5. 审批通过后，学生开始做毕设，生成4个进度阶段

### 数据完整性约束
- 一个学生只能有一个有效的选题（status=1）
- 一个题目已选人数不能超过max_students
- 一个教师指导学生数不能超过max_students
- 选题状态只能在规定的状态值内转换
