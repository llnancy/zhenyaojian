package io.github.llnancy.zhenyaojian.system.repository.entity;

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
 * @since JDK8 2022/12/02
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
     * 用户代理 UA
     */
    private String userAgent;

    /**
     * 设备类型
     */
    private String deviceType;

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
    private String region;

    /**
     * 请求地址
     */
    private String requestUri;

    /**
     * HTTP 请求方式
     */
    private String requestMethod;

    /**
     * 请求类名
     */
    private String className;

    /**
     * 请求方法名
     */
    private String methodName;

    /**
     * 请求参数
     */
    private String parameters;

    /**
     * 返回值
     */
    private String response;

    /**
     * 异常描述
     */
    private String exception;

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
    @TableField("`description`")
    private String description;

    /**
     * 请求状态（0：成功，1：异常）
     */
    @TableField("`status`")
    private Integer status;

    /**
     * 日志类型
     */
    @TableField("`type`")
    private String type;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 消耗时间（单位：ms）
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
