package com.learn.admin.common.config.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * SQL 默认注入器
 *
 * @author lijun
 * @date 2022/4/25 11:28
 */
public class DefaultSqlInjector extends AbstractSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        Stream.Builder<AbstractMethod> builder = Stream.<AbstractMethod>builder()
                .add(new Insert())
                .add(new Delete())
                .add(new DeleteByMap())
                .add(new Update())
                .add(new SelectByMap())
                .add(new SelectCount())
                .add(new SelectMaps())
                .add(new SelectMapsPage())
                .add(new SelectObjs())
                .add(new SelectList())
                .add(new SelectPage());
        if (tableInfo.havePK()) {
            builder.add(new DeleteById())
                    .add(new DeleteBatchByIds())
                    .add(new UpdateById())
                    .add(new SelectById())
                    .add(new SelectBatchByIds());
        } else {
            logger.warn(String.format("%s ,Not found @TableId annotation, Cannot use Mybatis-Plus 'xxById' Method.",
                    tableInfo.getEntityType()));
        }
        return builder.build().collect(toList());
    }
}
