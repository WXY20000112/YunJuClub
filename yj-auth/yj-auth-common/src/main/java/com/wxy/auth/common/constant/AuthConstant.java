package com.wxy.auth.common.constant;

/**
 * @program: YunJuClub
 * @description: 分页查询相关属性常量类
 * @author: 32115
 * @create: 2024-03-21 15:45
 */
public class AuthConstant {

    // 公钥 非对称加密算法
    // public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGfP9vtFmUPQLdeekZTWEADDx32cctbOZ2KsMs6ybjnG0AZLF4NWJPcuIQDsnyTw5Faw1dgcTZsxbkGrBmBJ55SCFMw1wcrhCTyo8Nxt7Pt8fOvVhrNKWGUdPzlbKNEH/m1YfH7k0dAbeSKo/hRcM3EaHGwukxbdip2pfs2uGE9wIDAQAB";

    // 私钥 非对称加密算法
    // public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMZ8/2+0WZQ9At156RlNYQAMPHfZxy1s5nYqwyzrJuOcbQBksXg1Yk9y4hAOyfJPDkVrDV2BxNmzFuQasGYEnnlIIUzDXByuEJPKjw3G3s+3x869WGs0pYZR0/OVso0Qf+bVh8fuTR0Bt5Iqj+FFwzcRocbC6TFt2Knal+za4YT3AgMBAAECgYAElX8NBHO4PVG0ixqvWCOtqJqvXF1uJZkVkO9Bm9o1dGjp4bg2S7kESGW0c7HAujlwnKuxbNYcOZn7lRciIY/yaXQyihVgjBYSeAY1VOyy523cLZ9ld17ZR/Te0Zwn+KErKlL/F9jo3gcePZ/Mx5afKvZ1vy/3kDFMvo706OVi4QJBANoPtSHa3VVhSYN7JJSYdO3I6WMrssZ0xj5uSV7ZDpOqJH8JLpVgXy7lXGjOJvQ7wINgLcxtnUDGDVPYLV2BhasCQQDpBYT6W0eHpyfVt6tcFhZvT7uzgCS7hnWLQLaY6oFE52+6J90YSWnZfDB6ogsWt7WSdOVlTgtT6DGnYOlOJdnlAkBogdishglHrx2dXvo0ITEA+SYpejru8+5C43ZUd7CNwqBFJfBmdM6JWjFEpXBz4uMr039/lxUjgwfGJDfxtW6LAkEAhc2ljhzb9gkF1rKgsz/7oe8m2Ou8K/IJjOyb7TK7B9XdUgeR7WA6Z6foLoLd2y2iFXcKQiGOX5/04yfC0BzsAQJAfn1Hal0695KIMgbeGD2oF0CvGgZrX77RA70/SB0kLCixm0m8ydYgv2pQzEZF6xdD6WukBi2fuQKaz2lSr+2pnA==";

    // 密钥 对称加密算法
    public static final String ENCRYPT_KEY = "YunJuClub";

    // 用户注册后的初始角色
    public static final String NORMAL_USER = "normal_user";

    // 用户权限前缀
    public static final String AUTH_PERMISSION_PREFIX = "auth.permission";

    // 用户角色前缀
    public static final String AUTH_ROLE_PREFIX = "auth.role";

    // 微信验证码前缀
    public static final String WECHAT_CODE_PREFIX = "wechat_code";

    // 初始密码
    public static final String INITIAL_PASSWORD = "YunJuClub";

    // 初始邮箱
    public static final String INITIAL_EMAIL = "YunJuClub@email.com";

    // 初始手机号
    public static final String INITIAL_PHONE = "YunJuClub";

    // 初始性别
    public static final String INITIAL_SEX = "男";

    // 初始昵称
    public static final String INITIAL_NICKNAME = "用户" + System.currentTimeMillis();

    // 初始头像
    public static final String INITIAL_AVATAR = "initial_avatar.png";
}
