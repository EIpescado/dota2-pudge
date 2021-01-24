package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.system.entity.SystemUser;
import pers.yurwisher.dota2.pudge.system.pojo.fo.ChangeAccountInfoFo;
import pers.yurwisher.dota2.pudge.system.pojo.fo.ChangeMailFo;
import pers.yurwisher.dota2.pudge.system.pojo.fo.ChangePhoneFo;
import pers.yurwisher.dota2.pudge.system.pojo.fo.ResetPasswordFo;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemUserFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemUserQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemUserTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemUserVo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

/**
 * @author yq
 * @date 2020/09/21 11:36
 * @description 用户service
 * @since V1.0.0
 */
public interface ISystemUserService extends BaseService<SystemUser> {


    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    CurrentUser findUserByUsername(String username);

    /**
     * 根据帐号获取用户
     *
     * @param username 帐号
     * @return 用户
     */
    SystemUser getUserByUsername(String username);

    /**
     * 用户列表
     *
     * @param qo 查询对象
     * @return 分页结果
     */
    PageR<SystemUserTo> list(SystemUserQo qo);

    /**
     * 创建用户
     *
     * @param fo 用户表单
     */
    void create(SystemUserFo fo);

    /**
     * 更新用户
     *
     * @param id 用户ID
     * @param fo 用户表单
     */
    void update(Long id, SystemUserFo fo);

    /**
     * 用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    SystemUserVo get(Long id);

    /**
     * 重置密码
     *
     * @param id 用户ID
     */
    void resetPassword(Long id);

    /**
     * 修改密码
     * @param resetPasswordFo 参数
     */
    void changePassword(ResetPasswordFo resetPasswordFo);

    /**
     * 发送变更邮箱验证邮件
     * @param mail 邮箱
     */
    void sendChangeMailCode(String mail);

    /**
     * 变更邮箱
     * @param changeMailFo 参数
     */
    void changeMail(ChangeMailFo changeMailFo);

    /**
     * 变更帐号信息
     * @param changeAccountInfoFo 帐号信息
     */
    void changeAccountInfo(ChangeAccountInfoFo changeAccountInfoFo);

    /**
     * 发送变更手机验证短信
     * @param phone 手机
     */
    void sendChangePhoneCode(String phone);

    /**
     * 变更绑定手机
     * @param changePhoneFo 参数
     */
    void changePhone(ChangePhoneFo changePhoneFo);

    /**
     * 变更用户状态
     *
     * @param id 用户id
     * @param state 状态
     */
    void switchState(Long id,Integer state);
}
