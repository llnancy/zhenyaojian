package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserRoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * user role service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/7
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements IService<UserRoleEntity> {
}
