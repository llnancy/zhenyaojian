package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.sunchaser.shushan.mojian.base.enums.ResponseEnum;
import com.sunchaser.shushan.zhenyaojian.framework.exception.ZyjBizException;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.PermissionMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreatePermissionRequest;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
import com.sunchaser.shushan.zhenyaojian.system.service.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * permission service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final IPermissionService permissionService;

    private final PermissionMapstruct permissionMapstruct;

    public void createPermission(CreatePermissionRequest request) {
        Long parentId = request.getParentId();
        PermissionEntity parentPermission = permissionService.getById(parentId);
        if (Objects.isNull(parentPermission)) {
            throw new ZyjBizException(ResponseEnum.INVALID_PARAM);
        }
        PermissionEntity permission = permissionMapstruct.convert(request);
        permissionService.save(permission);
    }

    public void permissions() {
    }
}
