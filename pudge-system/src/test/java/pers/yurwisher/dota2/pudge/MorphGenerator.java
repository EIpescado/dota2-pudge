package pers.yurwisher.dota2.pudge;

import pers.yurwisher.morph.support.Morph;

/**
 * @author yq
 * @date 2019/07/10 16:19
 * @description
 * @since V1.0.0
 */
public class MorphGenerator {

    public static void main(String[] args) {
        Morph morph = new Morph();
        morph.modelBuilder().setIdType("Long")
                .setAuthor("yq")
                .setModule("pudge-system")
                .setBasePackage("pers.yurwisher.dota2.pudge")
                .setEntityClass("pers.yurwisher.dota2.pudge.system.entity.SystemLog")
                .setDescription("系统日志");
        morph.configBuilder().setServiceSuperClass("pers.yurwisher.dota2.pudge.base.BaseService")
                .setSo(false).setVo(true).setFo(false).setTo(true).setQo(true)
                .setQoSuperClass("pers.yurwisher.dota2.pudge.base.BasePageQo")
                .setMapperSuperClass("pers.yurwisher.dota2.pudge.base.CommonMapper")
                .setMapperLocation("mapper")
                .setServiceImplSuperClass("pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl")
                .setControllerSuperClass("pers.yurwisher.dota2.pudge.base.BaseController");
        morph.wave();
    }
}
