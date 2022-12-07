package io.github.llnancy.zhenyaojian.admin.web.controller.observability;

import io.github.llnancy.mojian.base.entity.response.MultiPageResponse;
import io.github.llnancy.zhenyaojian.framework.model.request.SystemLogPageRequest;
import io.github.llnancy.zhenyaojian.framework.model.response.SystemLogResponse;
import io.github.llnancy.zhenyaojian.framework.service.observability.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * system log controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/2
 */
@RestController
@RequiredArgsConstructor
public class LogController {

    private final SystemLogService systemLogService;

    @GetMapping("/logs")
    public MultiPageResponse<SystemLogResponse> logs(SystemLogPageRequest request) {
        return systemLogService.logs(request);
    }
}
