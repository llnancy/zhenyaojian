package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.SingleResponse;
import com.sunchaser.shushan.mojian.base.util.JsonUtils;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.UserMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreateUserRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionInfo;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.RoleInfo;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfoResponse;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.service.UserService;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * user controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/4
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapstruct userMapstruct;

    @GetMapping("/user/info")
    public SingleResponse<UserInfoResponse> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        UserEntity userEntity = loginUser.getUserEntity();
        UserInfoResponse userInfo = userMapstruct.convert(userEntity);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setName("role");
        roleInfo.setPermissions(Lists.newArrayList(new PermissionInfo("name", 0, "icon", "path", "component")));
        userInfo.setRoleInfo(roleInfo);
        return SingleResponse.success(userInfo);
    }

    @PostMapping("/user")
    public IResponse createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return IResponse.SUCCESS;
    }

    @GetMapping("/user/nav")
    public SingleResponse<Object> navInfo() {
        String json = "{\"path\":\"/\",\"name\":\"index\",\"component\":\"BasicLayout\",\"meta\":{\"title\":\"首页\",\"permission\":\"index\"},\"redirect\":\"/dashboard\",\"children\":[{\"path\":\"/dashboard\",\"name\":\"dashboard\",\"component\":\"RouteView\",\"meta\":{\"title\":\"menu.dashboard\",\"icon\":\"dashboard\",\"permission\":\"dashboard\"},\"redirect\":\"/dashboard/workplace\",\"children\":[{\"path\":\"/dashboard/workplace\",\"name\":\"workplace\",\"component\":\"Workplace\",\"meta\":{\"title\":\"menu.dashboard.workplace\",\"permission\":\"workplace\"}},{\"path\":\"https://www.baidu.com/\",\"name\":\"monitor\",\"component\":null,\"meta\":{\"title\":\"menu.dashboard.monitor\",\"target\":\"_blank\",\"permission\":\"monitor\"}},{\"path\":\"/dashboard/analysis\",\"name\":\"Analysis\",\"component\":\"Analysis\",\"meta\":{\"title\":\"menu.dashboard.analysis\",\"permission\":\"Analysis\"}}]},{\"path\":\"/form\",\"name\":\"form\",\"component\":\"RouteView\",\"meta\":{\"title\":\"menu.form\",\"icon\":\"form\",\"permission\":\"form\"},\"redirect\":\"/form/base-form\",\"children\":[{\"path\":\"/form/basic-form\",\"name\":\"basic-form\",\"component\":\"BasicForm\",\"meta\":{\"title\":\"menu.form.basic-form\",\"permission\":\"basic-form\"}},{\"path\":\"/form/step-form\",\"name\":\"step-form\",\"component\":\"StepForm\",\"meta\":{\"title\":\"menu.form.step-form\",\"permission\":\"step-form\"}},{\"path\":\"/form/advanced-form\",\"name\":\"advanced-form\",\"component\":\"AdvancedForm\",\"meta\":{\"title\":\"menu.form.advanced-form\",\"permission\":\"advanced-form\"}}]},{\"path\":\"/list\",\"name\":\"list\",\"component\":\"RouteView\",\"meta\":{\"title\":\"menu.list\",\"icon\":\"table\",\"permission\":\"list\"},\"redirect\":\"/list/table-list\",\"children\":[{\"path\":\"/list/table-list/:pageNo([1-9]\\\\\\\\\\\\\\\\d*)?\",\"name\":\"table-list\",\"component\":\"TableList\",\"meta\":{\"title\":\"menu.list.table-list\",\"permission\":\"table-list\"}},{\"path\":\"/list/basic-list\",\"name\":\"basic-list\",\"component\":\"StandardList\",\"meta\":{\"title\":\"menu.list.basic-list\",\"permission\":\"basic-list\"}},{\"path\":\"/list/card\",\"name\":\"card\",\"component\":\"CardList\",\"meta\":{\"title\":\"menu.list.card-list\",\"permission\":\"card\"}},{\"path\":\"/list/search\",\"name\":\"search\",\"component\":\"SearchLayout\",\"meta\":{\"title\":\"menu.list.search-list\",\"permission\":\"search\"},\"redirect\":\"/list/search/article\",\"children\":[{\"path\":\"/list/search/article\",\"name\":\"article\",\"component\":\"SearchArticles\",\"meta\":{\"title\":\"menu.list.search-list.articles\",\"permission\":\"article\"}},{\"path\":\"/list/search/project\",\"name\":\"project\",\"component\":\"SearchProjects\",\"meta\":{\"title\":\"menu.list.search-list.projects\",\"permission\":\"project\"}},{\"path\":\"/list/search/application\",\"name\":\"application\",\"component\":\"SearchApplications\",\"meta\":{\"title\":\"menu.list.search-list.applications\",\"permission\":\"application\"}}]}]},{\"path\":\"/profile\",\"name\":\"profile\",\"component\":\"RouteView\",\"meta\":{\"title\":\"menu.profile\",\"icon\":\"profile\",\"permission\":\"profile\"},\"redirect\":\"/profile/basic\",\"children\":[{\"path\":\"/profile/basic\",\"name\":\"basic\",\"component\":\"ProfileBasic\",\"meta\":{\"title\":\"menu.profile.basic\",\"permission\":\"basic\"}},{\"path\":\"/profile/advanced\",\"name\":\"advanced\",\"component\":\"ProfileAdvanced\",\"meta\":{\"title\":\"menu.profile.advanced\",\"permission\":\"advanced\"}}]},{\"path\":\"/result\",\"name\":\"result\",\"component\":\"PageView\",\"meta\":{\"title\":\"menu.result\",\"icon\":\"check-circle-o\",\"permission\":\"result\"},\"redirect\":\"/result/success\",\"children\":[{\"path\":\"/result/success\",\"name\":\"success\",\"component\":\"ResultSuccess\",\"meta\":{\"title\":\"menu.result.success\",\"hiddenHeaderContent\":true,\"permission\":\"success\"}},{\"path\":\"/result/fail\",\"name\":\"fail\",\"component\":\"ResultFail\",\"meta\":{\"title\":\"menu.result.fail\",\"hiddenHeaderContent\":true,\"permission\":\"fail\"}}]},{\"path\":\"/exception\",\"name\":\"exception\",\"component\":\"RouteView\",\"meta\":{\"title\":\"menu.exception\",\"icon\":\"warning\",\"permission\":\"exception\"},\"redirect\":\"/exception/403\",\"children\":[{\"path\":\"/exception/403\",\"name\":\"403\",\"component\":\"Exception403\",\"meta\":{\"title\":\"menu.exception.not-permission\",\"permission\":\"403\"}},{\"path\":\"/exception/404\",\"name\":\"404\",\"component\":\"Exception404\",\"meta\":{\"title\":\"menu.exception.not-find\",\"permission\":\"404\"}},{\"path\":\"/exception/500\",\"name\":\"500\",\"component\":\"Exception500\",\"meta\":{\"title\":\"menu.exception.server-error\",\"permission\":\"500\"}}]},{\"path\":\"/account\",\"name\":\"account\",\"component\":\"RouteView\",\"meta\":{\"title\":\"menu.account\",\"icon\":\"user\",\"permission\":\"account\"},\"redirect\":\"/account/center\",\"children\":[{\"path\":\"/account/center\",\"name\":\"center\",\"component\":\"AccountCenter\",\"meta\":{\"title\":\"menu.account.center\",\"permission\":\"center\"}},{\"path\":\"/account/settings\",\"name\":\"settings\",\"component\":\"AccountSettings\",\"meta\":{\"title\":\"menu.account.settings\",\"permission\":\"settings\"},\"hideChildrenInMenu\":true,\"redirect\":\"/account/settings/basic\",\"children\":[{\"path\":\"/account/settings/basic\",\"name\":\"BasicSettings\",\"component\":\"BasicSetting\",\"meta\":{\"title\":\"account.settings.menuMap.basic\",\"permission\":\"BasicSettings\"},\"hidden\":true},{\"path\":\"/account/settings/security\",\"name\":\"SecuritySettings\",\"component\":\"SecuritySettings\",\"meta\":{\"title\":\"account.settings.menuMap.security\",\"permission\":\"SecuritySettings\"},\"hidden\":true},{\"path\":\"/account/settings/custom\",\"name\":\"CustomSettings\",\"component\":\"CustomSettings\",\"meta\":{\"title\":\"account.settings.menuMap.custom\",\"permission\":\"CustomSettings\"},\"hidden\":true},{\"path\":\"/account/settings/binding\",\"name\":\"BindingSettings\",\"component\":\"BindingSettings\",\"meta\":{\"title\":\"account.settings.menuMap.binding\",\"permission\":\"BindingSettings\"},\"hidden\":true},{\"path\":\"/account/settings/notification\",\"name\":\"NotificationSettings\",\"component\":\"NotificationSettings\",\"meta\":{\"title\":\"account.settings.menuMap.notification\",\"permission\":\"NotificationSettings\"},\"hidden\":true}]}]}]}";
        return SingleResponse.success(JsonUtils.toObject(json, JsonNode.class));
    }
}
