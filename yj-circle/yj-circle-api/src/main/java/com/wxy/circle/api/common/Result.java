package com.wxy.circle.api.common;

import com.wxy.circle.api.enums.ResultCodeEnum;
import lombok.Data;

/**
 * @program: YunJuClub-Flex
 * @description: 统一结果返回类
 * @author: 32115
 * @create: 2024-05-14 17:10
 */
@Data
public class Result<T> {

    // 是否成功
    private Boolean success;

    // 状态码
    private Integer code;

    // 返回信息
    private String message;

    // 返回数据
    private T data;

    /**
     * @author: 32115
     * @description: 请求成功
     * @date: 2024/5/14
     * @return: Result<T>
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * @author: 32115
     * @description: 请求成功 返回结果带有数据
     * @date: 2024/5/14
     * @return: Result<T>
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * @author: 32115
     * @description: 请求失败
     * @date: 2024/5/14
     * @return: Result<T>
     */
    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(ResultCodeEnum.FAIL.getMessage());
        return result;
    }

    /**
     * @author: 32115
     * @description: 请求失败 返回结果带有数据
     * @date: 2024/5/14
     * @return: Result<T>
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(msg);
        return result;
    }
}
