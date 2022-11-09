package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreateUserRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfoResponse;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * user mapstruct
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapstruct {

    /**
     * convert CreateUserRequest to UserEntity
     *
     * @param request CreateUserRequest
     * @return UserEntity
     */
    UserEntity convert(CreateUserRequest request);

    /**
     * convert UserEntity to UserInfoResponse
     *
     * @param userEntity UserEntity
     * @return UserInfoResponse
     */
    UserInfoResponse convert(UserEntity userEntity);
}
