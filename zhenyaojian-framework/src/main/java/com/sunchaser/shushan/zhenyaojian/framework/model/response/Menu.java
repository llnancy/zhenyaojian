package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * menu
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {

    private String path;

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long parentId;

    @JsonIgnore
    private Integer type;

    private String component;

    private Meta meta;

    private String redirect;

    private List<Menu> children;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Meta {

        private String title;

        private String icon;

        private String permission;
    }
}
