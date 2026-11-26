package nuc.edu.cn.bookmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import nuc.edu.cn.bookmanager.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户登录
     */
    User login(String userName, String password);
    
    /**
     * 注册用户
     */
    boolean register(User user);
    
    /**
     * 更新用户信息
     */
    boolean updateUserInfo(User user);
    
    /**
     * 获取所有用户（管理员）
     */
    List<User> getAllUsers();
}

