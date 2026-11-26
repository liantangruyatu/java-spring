package nuc.edu.cn.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import nuc.edu.cn.bookmanager.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

