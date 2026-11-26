package com.graduation.init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.*;
import com.graduation.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 数据初始化类，在应用启动时自动执行
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    @Autowired
    private StudentMapper studentMapper;
    
    @Autowired
    private TopicMapper topicMapper;
    
    @Autowired
    private SelectionMapper selectionMapper;
    
    @Autowired
    private ProgressMapper progressMapper;

    @Override
    public void run(String... args) throws Exception {
        // 检查是否已有数据
        if (userMapper.selectCount(null) == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        LocalDateTime now = LocalDateTime.now();
        
        // 1. 插入用户数据
        User admin = User.builder()
                .username("admin")
                .password("123456")
                .email("admin@example.com")
                .role(0)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        userMapper.insert(admin);

        User teacher1 = User.builder()
                .username("teacher1")
                .password("123456")
                .email("teacher1@example.com")
                .role(1)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        userMapper.insert(teacher1);

        User teacher2 = User.builder()
                .username("teacher2")
                .password("123456")
                .email("teacher2@example.com")
                .role(1)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        userMapper.insert(teacher2);

        User student1 = User.builder()
                .username("student1")
                .password("123456")
                .email("student1@example.com")
                .role(2)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        userMapper.insert(student1);

        User student2 = User.builder()
                .username("student2")
                .password("123456")
                .email("student2@example.com")
                .role(2)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        userMapper.insert(student2);

        User student3 = User.builder()
                .username("student3")
                .password("123456")
                .email("student3@example.com")
                .role(2)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        userMapper.insert(student3);

        User student4 = User.builder()
                .username("student4")
                .password("123456")
                .email("student4@example.com")
                .role(2)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        userMapper.insert(student4);

        User student5 = User.builder()
                .username("student5")
                .password("123456")
                .email("student5@example.com")
                .role(2)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        userMapper.insert(student5);

        // 2. 插入教师数据
        Teacher t1 = Teacher.builder()
                .userId(teacher1.getUserId())
                .realName("张教授")
                .phone("13800138001")
                .department("计算机科学与技术")
                .researchArea("人工智能")
                .maxStudents(5)
                .currentCount(0)
                .createTime(now)
                .updateTime(now)
                .build();
        teacherMapper.insert(t1);

        Teacher t2 = Teacher.builder()
                .userId(teacher2.getUserId())
                .realName("李副教授")
                .phone("13800138002")
                .department("软件工程")
                .researchArea("软件开发")
                .maxStudents(5)
                .currentCount(0)
                .createTime(now)
                .updateTime(now)
                .build();
        teacherMapper.insert(t2);

        // 3. 插入学生数据
        Student s1 = Student.builder()
                .userId(student1.getUserId())
                .realName("学生1")
                .studentNo("2022001")
                .major("计算机科学与技术")
                .className("计科1班")
                .phone("13900139001")
                .createTime(now)
                .updateTime(now)
                .build();
        studentMapper.insert(s1);

        Student s2 = Student.builder()
                .userId(student2.getUserId())
                .realName("学生2")
                .studentNo("2022002")
                .major("计算机科学与技术")
                .className("计科1班")
                .phone("13900139002")
                .createTime(now)
                .updateTime(now)
                .build();
        studentMapper.insert(s2);

        Student s3 = Student.builder()
                .userId(student3.getUserId())
                .realName("学生3")
                .studentNo("2022003")
                .major("软件工程")
                .className("软工1班")
                .phone("13900139003")
                .createTime(now)
                .updateTime(now)
                .build();
        studentMapper.insert(s3);

        Student s4 = Student.builder()
                .userId(student4.getUserId())
                .realName("学生4")
                .studentNo("2022004")
                .major("软件工程")
                .className("软工1班")
                .phone("13900139004")
                .createTime(now)
                .updateTime(now)
                .build();
        studentMapper.insert(s4);

        Student s5 = Student.builder()
                .userId(student5.getUserId())
                .realName("学生5")
                .studentNo("2022005")
                .major("软件工程")
                .className("软工1班")
                .phone("13900139005")
                .createTime(now)
                .updateTime(now)
                .build();
        studentMapper.insert(s5);

        // 4. 插入课题数据
        Topic topic1 = Topic.builder()
                .teacherId(t1.getTeacherId())
                .title("基于深度学习的图像识别系统")
                .description("使用卷积神经网络实现图像分类和识别功能")
                .type(1)
                .difficulty(3)
                .requiredSkills("Python, TensorFlow, OpenCV")
                .maxStudents(2)
                .selectedCount(0)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        topicMapper.insert(topic1);

        Topic topic2 = Topic.builder()
                .teacherId(t1.getTeacherId())
                .title("智能推荐系统设计与实现")
                .description("基于协同过滤算法开发个性化推荐系统")
                .type(1)
                .difficulty(2)
                .requiredSkills("Java, Spring Boot, MySQL")
                .maxStudents(2)
                .selectedCount(0)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        topicMapper.insert(topic2);

        Topic topic3 = Topic.builder()
                .teacherId(t1.getTeacherId())
                .title("自然语言处理在文本分类中的应用")
                .description("研究文本分类算法并实现新闻分类系统")
                .type(1)
                .difficulty(3)
                .requiredSkills("Python, NLTK, scikit-learn")
                .maxStudents(1)
                .selectedCount(0)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        topicMapper.insert(topic3);

        Topic topic4 = Topic.builder()
                .teacherId(t2.getTeacherId())
                .title("Web前端框架性能比较研究")
                .description("对比Vue、React、Angular等前端框架的性能差异")
                .type(2)
                .difficulty(2)
                .requiredSkills("JavaScript, Vue, React, Angular")
                .maxStudents(3)
                .selectedCount(0)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        topicMapper.insert(topic4);

        Topic topic5 = Topic.builder()
                .teacherId(t2.getTeacherId())
                .title("移动应用开发实践")
                .description("开发一款基于Flutter的移动应用")
                .type(1)
                .difficulty(2)
                .requiredSkills("Dart, Flutter, Firebase")
                .maxStudents(2)
                .selectedCount(0)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        topicMapper.insert(topic5);

        Topic topic6 = Topic.builder()
                .teacherId(t2.getTeacherId())
                .title("数据库优化技术研究")
                .description("研究数据库索引、查询优化等技术并进行实践")
                .type(2)
                .difficulty(3)
                .requiredSkills("MySQL, SQL, 数据库原理")
                .maxStudents(2)
                .selectedCount(0)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
        topicMapper.insert(topic6);

        // 5. 插入选题数据
        Selection selection1 = Selection.builder()
                .studentId(s1.getStudentId())
                .topicId(topic1.getTopicId())
                .selectionType(1)
                .status(1)
                .applyTime(now)
                .updateTime(now)
                .build();
        selectionMapper.insert(selection1);

        Selection selection2 = Selection.builder()
                .studentId(s2.getStudentId())
                .topicId(topic2.getTopicId())
                .selectionType(1)
                .status(1)
                .applyTime(now)
                .updateTime(now)
                .build();
        selectionMapper.insert(selection2);

        Selection selection3 = Selection.builder()
                .studentId(s3.getStudentId())
                .topicId(topic4.getTopicId())
                .selectionType(1)
                .status(1)
                .applyTime(now)
                .updateTime(now)
                .build();
        selectionMapper.insert(selection3);

        // 6. 插入进度数据
        Progress progress1 = Progress.builder()
                .selectionId(selection1.getSelectionId())
                .stage(1)
                .status(2)
                .submitFile("/upload/progress/1/开题报告.docx")
                .submitTime(now)
                .updateTime(now)
                .build();
        progressMapper.insert(progress1);

        Progress progress2 = Progress.builder()
                .selectionId(selection1.getSelectionId())
                .stage(2)
                .status(1)
                .submitFile("/upload/progress/1/中期检查.ppt")
                .submitTime(now)
                .updateTime(now)
                .build();
        progressMapper.insert(progress2);

        Progress progress3 = Progress.builder()
                .selectionId(selection2.getSelectionId())
                .stage(1)
                .status(2)
                .submitFile("/upload/progress/2/开题报告.docx")
                .submitTime(now)
                .updateTime(now)
                .build();
        progressMapper.insert(progress3);

        System.out.println("数据初始化完成！");
    }
}
