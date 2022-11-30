package com.sunchaser.shushan.zhenyaojian.system.repository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统日志记录表
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("zyj_system_log")
public class SystemLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 浏览器 UA
     */
    private String userAgent;

    /**
     * 浏览器名称
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 地理位置
     */
    private String geoLocation;

    /**
     * 日志类型
     */
    @TableField("`type`")
    private String type;

    /**
     * 操作者 ID
     */
    private String userId;

    /**
     * 操作者账户
     */
    private String userAccount;

    /**
     * 操作描述
     */
    private String operatorDesc;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求地址
     */
    private String requestMapping;

    /**
     * HTTP 请求方式
     */
    private String httpMethod;

    /**
     * 请求参数
     */
    private String requestParameters;

    /**
     * 返回值
     */
    private String returnValue;

    /**
     * 异常描述
     */
    private String exceptionDesc;

    /**
     * 异常详情
     */
    private String exceptionDetail;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 消耗时间
     */
    private Integer rt;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
