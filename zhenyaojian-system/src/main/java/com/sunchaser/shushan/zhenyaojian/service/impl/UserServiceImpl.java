package com.sunchaser.shushan.zhenyaojian.service.impl;

import com.sunchaser.shushan.zhenyaojian.repository.entity.UserEntity;
import com.sunchaser.shushan.zhenyaojian.repository.mapper.UserMapper;
import com.sunchaser.shushan.zhenyaojian.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务实现类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
