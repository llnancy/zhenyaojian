package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.UserOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfo;
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
     * convert UserOpsCommand to UserEntity
     *
     * @param command UserOpsCommand
     * @return UserEntity
     */
    UserEntity convert(UserOpsCommand command);

    /**
     * convert UserEntity to UserInfoResponse
     *
     * @param userEntity UserEntity
     * @return UserInfoResponse
     */
    UserInfoResponse convert(UserEntity userEntity);

    UserInfo convertToUserInfo(UserEntity userEntity);
}
