package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sunchaser.shushan.zhenyaojian.framework.enums.ResponseEnum;
import com.sunchaser.shushan.zhenyaojian.framework.exception.ZyjBizException;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.UserMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreateUserRequest;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserRoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.service.IRoleService;
import com.sunchaser.shushan.zhenyaojian.system.service.IUserRoleService;
import com.sunchaser.shushan.zhenyaojian.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * user service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserService userService;

    private final IRoleService roleService;

    private final IUserRoleService userRoleService;

    private final UserMapstruct userMapstruct;

    @Transactional(rollbackFor = Exception.class)
    public void createUser(CreateUserRequest request) {
        String roleCode = request.getRoleCode();
        LambdaQueryWrapper<RoleEntity> queryWrapper = Wrappers.<RoleEntity>lambdaQuery()
                .eq(RoleEntity::getCode, roleCode);
        RoleEntity role = roleService.getOne(queryWrapper);
        if (Objects.isNull(role)) {
            throw new ZyjBizException(ResponseEnum.ILLEGAL_ROLE);
        }
        UserEntity user = userMapstruct.convert(request);
        userService.save(user);
        UserRoleEntity userRole = UserRoleEntity.builder()
                .userId(user.getId())
                .roleId(role.getId())
                .build();
        userRoleService.save(userRole);
    }
}
