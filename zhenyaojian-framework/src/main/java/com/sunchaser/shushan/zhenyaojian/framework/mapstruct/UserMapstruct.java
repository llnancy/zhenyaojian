package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.UserOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfoResponse;
import com.sunchaser.shushan.zhenyaojian.framework.service.UserService;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * user mapstruct
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {UserService.class, StringUtils.class})
public interface UserMapstruct {

    /**
     * convert {@link UserOpsCommand} to {@link UserEntity}.
     * The expression attribute indicates a call to {@link UserService#encryptPassword(String, UserOpsCommand.UserOpsTypeEnum)} static method
     * that is placed outside the class
     *
     * @param command {@link UserOpsCommand}
     * @return {@link UserEntity}
     */
    @Mapping(target = "password", expression = "java(UserService.encryptPassword(command.getPassword(), command.getUserOpsType()))")
    @Mapping(target = "nickName", defaultExpression = "java(StringUtils.EMPTY)")
    @Mapping(target = "avatar", defaultExpression = "java(java.lang.String.format(\"https://images.nowcoder.com/head/%dt.png\", java.util.concurrent.ThreadLocalRandom.current().nextInt(1000)))")
    UserEntity convert(UserOpsCommand command);

    /**
     * convert {@link UserEntity} to {@link UserInfoResponse}
     *
     * @param userEntity {@link UserEntity}
     * @return {@link UserInfoResponse}
     */
    UserInfoResponse convert(UserEntity userEntity);
}
