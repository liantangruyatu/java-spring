package com.example.volunteer.config;

import com.example.volunteer.model.*;
import com.example.volunteer.repository.*;
import com.example.volunteer.service.RecruitmentApplicationService;
import com.example.volunteer.service.RecruitmentService;
import com.example.volunteer.service.ServiceRecordService;
import com.example.volunteer.service.TrainingRecordService;
import com.example.volunteer.service.UserService;
import com.example.volunteer.service.VolunteerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final VolunteerService volunteerService;
    private final VolunteerSkillRepository volunteerSkillRepository;
    private final VolunteerPreferenceRepository volunteerPreferenceRepository;
    private final RecruitmentService recruitmentService;
    private final RecruitmentApplicationService applicationService;
    private final ServiceRecordService serviceRecordService;
    private final TrainingRecordService trainingRecordService;

    public DataInitializer(UserService userService, RoleRepository roleRepository,
                          VolunteerService volunteerService,
                          VolunteerSkillRepository volunteerSkillRepository,
                          VolunteerPreferenceRepository volunteerPreferenceRepository,
                          RecruitmentService recruitmentService,
                          RecruitmentApplicationService applicationService,
                          ServiceRecordService serviceRecordService,
                          TrainingRecordService trainingRecordService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.volunteerService = volunteerService;
        this.volunteerSkillRepository = volunteerSkillRepository;
        this.volunteerPreferenceRepository = volunteerPreferenceRepository;
        this.recruitmentService = recruitmentService;
        this.applicationService = applicationService;
        this.serviceRecordService = serviceRecordService;
        this.trainingRecordService = trainingRecordService;
    }

    @Override
    public void run(String... args) {
        // Ensure base roles exist
        roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role r = new Role();
            r.setName("ADMIN");
            r.setDescription("系统管理员");
            return roleRepository.save(r);
        });
        Role volunteerRole = roleRepository.findByName("VOLUNTEER").orElseGet(() -> {
            Role r = new Role();
            r.setName("VOLUNTEER");
            r.setDescription("志愿者");
            return roleRepository.save(r);
        });

        // Ensure default admin account
        User admin = userService.createAdminIfNotExists();

        // 创建测试志愿者用户
        createTestVolunteers(volunteerRole, admin);
    }

    private void createTestVolunteers(Role volunteerRole, User admin) {
        // 志愿者1：张三 - 已审核通过
        User user1 = createUserIfNotExists("zhangsan", "123456", volunteerRole, "张三", "13800000001", "zhangsan@example.com");
        Volunteer vol1 = createVolunteerIfNotExists(user1, "M", LocalDate.of(1995, 5, 15), "440101199505150011", "广州市天河区", 1, "审核通过");
        if (vol1 != null) {
            createVolunteerSkill(vol1, "急救", "通过红十字急救培训，持有急救证书");
            createVolunteerSkill(vol1, "计算机", "熟悉办公软件，会使用PS、PR等设计软件");
            createVolunteerPreference(vol1, "助老服务", "周末", "市中心");
            createVolunteerPreference(vol1, "环境保护", "节假日", "郊区");
            createServiceRecord(vol1, LocalDate.of(2025, 12, 10), 4.0, "敬老院陪伴老人", "表现优秀");
            createServiceRecord(vol1, LocalDate.of(2025, 11, 20), 3.5, "社区环保宣传", "积极参与");
            createTrainingRecord(vol1, "志愿者基础培训", LocalDate.of(2025, 10, 15), "优秀");
        }

        // 志愿者2：李四 - 已审核通过
        User user2 = createUserIfNotExists("lisi", "123456", volunteerRole, "李四", "13800000002", "lisi@example.com");
        Volunteer vol2 = createVolunteerIfNotExists(user2, "F", LocalDate.of(1998, 8, 22), "440101199808220022", "广州市越秀区", 1, "审核通过");
        if (vol2 != null) {
            createVolunteerSkill(vol2, "教育", "师范专业毕业，有教学经验");
            createVolunteerSkill(vol2, "心理咨询", "持有心理咨询师证书");
            createVolunteerPreference(vol2, "支教服务", "寒暑假", "山区");
            createVolunteerPreference(vol2, "心理辅导", "工作日晚上", "社区");
            createServiceRecord(vol2, LocalDate.of(2025, 12, 5), 5.0, "山区支教活动", "认真负责");
            createTrainingRecord(vol2, "志愿者心理辅导培训", LocalDate.of(2025, 9, 20), "合格");
        }

        // 志愿者3：王五 - 待审核
        User user3 = createUserIfNotExists("wangwu", "123456", volunteerRole, "王五", "13800000003", "wangwu@example.com");
        Volunteer vol3 = createVolunteerIfNotExists(user3, "M", LocalDate.of(2000, 3, 10), "440101200003100033", "广州市海珠区", 0, null);
        if (vol3 != null) {
            createVolunteerSkill(vol3, "体育", "擅长篮球、足球等运动");
            createVolunteerPreference(vol3, "体育指导", "周末", "学校");
        }

        // 志愿者4：赵六 - 已审核通过
        User user4 = createUserIfNotExists("zhaoliu", "123456", volunteerRole, "赵六", "13800000004", "zhaoliu@example.com");
        Volunteer vol4 = createVolunteerIfNotExists(user4, "F", LocalDate.of(1996, 11, 5), "440101199611050044", "广州市白云区", 1, "审核通过");
        if (vol4 != null) {
            createVolunteerSkill(vol4, "医疗护理", "护理专业，有医院实习经验");
            createVolunteerPreference(vol4, "医疗协助", "周末", "医院");
            createServiceRecord(vol4, LocalDate.of(2025, 12, 1), 6.0, "社区义诊活动", "专业细致");
        }

        // 创建招募信息
        Recruitment rec1 = createRecruitmentIfNotExists("敬老院周末陪伴活动", 
                "陪同老人聊天、散步，协助简单照料，为老人带去温暖和关怀。", 
                "XX敬老院", 
                LocalDateTime.of(2026, 2, 1, 9, 0), 
                LocalDateTime.of(2026, 2, 1, 17, 0),
                "身体健康，有耐心，优先有助老经验者", 
                20, 1, admin);
        
        Recruitment rec2 = createRecruitmentIfNotExists("社区环保宣传活动",
                "在社区开展环保知识宣传，组织垃圾分类活动，提高居民环保意识。",
                "XX社区活动中心",
                LocalDateTime.of(2026, 2, 15, 14, 0),
                LocalDateTime.of(2026, 2, 15, 18, 0),
                "热爱环保，有宣传经验优先",
                15, 1, admin);

        Recruitment rec3 = createRecruitmentIfNotExists("山区支教活动",
                "前往山区小学进行支教，为孩子们提供教育支持，传递知识的力量。",
                "XX山区小学",
                LocalDateTime.of(2026, 3, 1, 8, 0),
                LocalDateTime.of(2026, 3, 5, 18, 0),
                "有教学经验，能适应山区环境",
                10, 1, admin);

        Recruitment rec4 = createRecruitmentIfNotExists("社区义诊活动",
                "协助医生开展社区义诊，为居民提供健康咨询和基础检查服务。",
                "XX社区卫生服务中心",
                LocalDateTime.of(2026, 2, 20, 9, 0),
                LocalDateTime.of(2026, 2, 20, 16, 0),
                "有医疗或护理背景优先",
                12, 0, admin); // 草稿状态

        // 创建报名记录
        if (vol1 != null && rec1 != null) {
            createApplicationIfNotExists(rec1, vol1, 1); // 已通过
        }
        if (vol1 != null && rec2 != null) {
            createApplicationIfNotExists(rec2, vol1, 0); // 待审核
        }
        if (vol2 != null && rec3 != null) {
            createApplicationIfNotExists(rec3, vol2, 0); // 待审核
        }
        if (vol4 != null && rec4 != null) {
            createApplicationIfNotExists(rec4, vol4, 0); // 待审核
        }
    }

    private User createUserIfNotExists(String username, String password, Role role, String fullName, String phone, String email) {
        Optional<User> existing = userService.findByUsername(username);
        if (existing.isPresent()) {
            return existing.get();
        }
        return userService.createUser(username, password, role.getName(), fullName, phone, email);
    }

    private Volunteer createVolunteerIfNotExists(User user, String gender, LocalDate birthday, String idCard, String address, Integer auditStatus, String auditRemark) {
        Optional<Volunteer> existing = volunteerService.findByUserId(user.getId());
        if (existing.isPresent()) {
            return existing.get();
        }
        Volunteer volunteer = new Volunteer();
        volunteer.setUser(user);
        volunteer.setGender(gender);
        volunteer.setBirthday(birthday);
        volunteer.setIdCard(idCard);
        volunteer.setAddress(address);
        volunteer.setAuditStatus(auditStatus);
        volunteer.setAuditRemark(auditRemark);
        return volunteerService.save(volunteer);
    }

    private void createVolunteerSkill(Volunteer volunteer, String skillName, String description) {
        VolunteerSkill skill = new VolunteerSkill();
        skill.setVolunteer(volunteer);
        skill.setSkillName(skillName);
        skill.setDescription(description);
        volunteerSkillRepository.save(skill);
    }

    private void createVolunteerPreference(Volunteer volunteer, String serviceType, String preferredTime, String preferredLocation) {
        VolunteerPreference preference = new VolunteerPreference();
        preference.setVolunteer(volunteer);
        preference.setServiceType(serviceType);
        preference.setPreferredTime(preferredTime);
        preference.setPreferredLocation(preferredLocation);
        volunteerPreferenceRepository.save(preference);
    }

    private Recruitment createRecruitmentIfNotExists(String title, String description, String location,
                                                     LocalDateTime startTime, LocalDateTime endTime,
                                                     String requirement, Integer maxVolunteers,
                                                     Integer status, User createdBy) {
        // 简单检查：如果已有相同标题的招募，就不创建
        if (recruitmentService.findAll().stream().anyMatch(r -> r.getTitle().equals(title))) {
            return null;
        }
        Recruitment recruitment = new Recruitment();
        recruitment.setTitle(title);
        recruitment.setDescription(description);
        recruitment.setLocation(location);
        recruitment.setStartTime(startTime);
        recruitment.setEndTime(endTime);
        recruitment.setRequirement(requirement);
        recruitment.setMaxVolunteers(maxVolunteers);
        recruitment.setStatus(status);
        recruitment.setCreatedBy(createdBy);
        return recruitmentService.save(recruitment);
    }

    private void createApplicationIfNotExists(Recruitment recruitment, Volunteer volunteer, Integer status) {
        // 检查是否已存在报名记录
        if (applicationService.findByRecruitmentId(recruitment.getId()).stream()
                .anyMatch(app -> app.getVolunteer().getId().equals(volunteer.getId()))) {
            return;
        }
        RecruitmentApplication application = new RecruitmentApplication();
        application.setRecruitment(recruitment);
        application.setVolunteer(volunteer);
        application.setStatus(status);
        applicationService.save(application);
    }

    private void createServiceRecord(Volunteer volunteer, LocalDate serviceDate, Double hours, String content, String evaluation) {
        ServiceRecord record = new ServiceRecord();
        record.setVolunteer(volunteer);
        record.setServiceDate(serviceDate);
        record.setHours(hours);
        record.setContent(content);
        record.setEvaluation(evaluation);
        serviceRecordService.save(record);
    }

    private void createTrainingRecord(Volunteer volunteer, String topic, LocalDate trainingDate, String result) {
        TrainingRecord record = new TrainingRecord();
        record.setVolunteer(volunteer);
        record.setTopic(topic);
        record.setTrainingDate(trainingDate);
        record.setResult(result);
        trainingRecordService.save(record);
    }
}


