package io.github.llnancy.zhenyaojian.framework.service.observability;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.llnancy.mojian.base.entity.response.MultiPageResponse;
import io.github.llnancy.mojian.log.enums.AccessType;
import io.github.llnancy.zhenyaojian.framework.mapstruct.SystemLogMapstruct;
import io.github.llnancy.zhenyaojian.framework.model.request.SystemLogPageRequest;
import io.github.llnancy.zhenyaojian.framework.model.response.SystemLogResponse;
import io.github.llnancy.zhenyaojian.system.repository.entity.SystemLogEntity;
import io.github.llnancy.zhenyaojian.system.repository.mapper.SystemLogMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * system log service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/5
 */
@Service
@RequiredArgsConstructor
public class SystemLogService extends ServiceImpl<SystemLogMapper, SystemLogEntity> implements IService<SystemLogEntity> {

    private final SystemLogMapstruct systemLogMapstruct;

    /**
     * Paging query logs
     *
     * @param request {@link SystemLogResponse}
     * @return paging data of {@link SystemLogResponse}
     */
    public MultiPageResponse<SystemLogResponse> logs(SystemLogPageRequest request) {
        String type = request.getType();
        LambdaQueryWrapper<SystemLogEntity> wrapper = Wrappers.<SystemLogEntity>lambdaQuery()
                .eq(StringUtils.isNotBlank(type), SystemLogEntity::getType, type)
                .ne(StringUtils.isBlank(type), SystemLogEntity::getType, AccessType.LOGIN.toString())
                .orderByDesc(SystemLogEntity::getId);
        Page<SystemLogEntity> page = Page.of(request.getPageNo(), request.getPageSize());
        page = this.getBaseMapper().selectPage(page, wrapper);
        return MultiPageResponse.success(page, systemLogMapstruct::convert);
    }
}
