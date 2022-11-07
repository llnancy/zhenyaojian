package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * role service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/7
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService extends ServiceImpl<RoleMapper, RoleEntity> implements IService<RoleEntity> {
}
