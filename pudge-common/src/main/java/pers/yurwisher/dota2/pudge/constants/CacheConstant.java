package pers.yurwisher.dota2.pudge.constants;

/**
 * @author yq
 * @date 2020/09/22 11:32
 * @description redis缓存名称常量
 * @since V1.0.0
 */
public interface CacheConstant {

    /**
     * 由spring cache注解类带入
     */
    interface AnName {
        /**
         * 用户基础信息
         */
        String SYSTEM_USER_INFO = "system_user_info";

        /**
         * 配置
         */
        String SYSTEM_CONFIG = "system_config";

        /**
         * 字典
         */
        String SYSTEM_DICT = "system_dict";
    }

    /**
     * 手动带入 manual
     */
    interface MaName {
        /**
         * 登录验证码
         */
        String LOGIN_CODE = "login_code";
        /**
         * 在线用户
         */
        String ONLINE_USER = "online_user";
        /**
         * 用户菜单树
         */
        String SYSTEM_USER_TREE = "system_user_tree";

        /**
         * 变更邮箱
         */
        String CHANGE_MAIL_CODE = "change_mail_code";

        /**
         * 系统字典值 / 描述 MAP
         */
        String SYSTEM_DICT_MAP = "system_dict_map";
    }

    interface Key {
        /**
         * 系统完整菜单及按钮树
         */
        String SYSTEM_WHOLE_TREE = "system_whole_tree";
        /**
         * 角色下拉框
         */
        String ROLE_SELECT = "system_role_select";
    }
}
