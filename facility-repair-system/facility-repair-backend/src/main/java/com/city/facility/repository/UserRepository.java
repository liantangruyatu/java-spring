package com.city.facility.repository;

import com.city.facility.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

/**
 * 用户数据访问层
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据手机号查询用户
     */
    Optional<User> findByPhone(String phone);
    
    /**
     * 根据用户类型查询用户列表
     */
    List<User> findByUserType(User.UserType userType);
    
    /**
     * 查询所有激活的用户
     */
    List<User> findByStatus(User.UserStatus status);
    
    /**
     * 批量更新所有用户密码
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET password = ?1, update_time = NOW()", nativeQuery = true)
    int updateAllPasswords(String password);
    
    /**
     * 检查手机号是否存在
     */
    boolean existsByPhone(String phone);
}
