package pers.yurwisher.dota2.pudge.sqlInjector.mapperMethod;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author yq
 * @date 2020/11/05 10:18
 * @description 切换enabled
 * @since V1.0.0
 */
public class SwitchEnabledById extends AbstractMethod {

    private static final String SQL = "update %1$s set %2$s = ABS(%3$s - b'1'), last_updated = now() where %4$s = #{%5$s}";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        String logicDeleteColumn = tableInfo.getLogicDeleteFieldInfo().getColumn();
        String sql = String.format(SQL,tableName,logicDeleteColumn,logicDeleteColumn,tableInfo.getKeyColumn(),tableInfo.getKeyProperty());
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, modelClass);
        //第三个参数必须与 commonMapper中一致
        return this.addUpdateMappedStatement(mapperClass, modelClass, "switchEnabledById", sqlSource);
    }
}
