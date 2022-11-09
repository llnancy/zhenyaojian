package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RolePermissionEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.RolePermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * role permission service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements IService<RolePermissionEntity> {
}
