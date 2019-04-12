package com.donnie.interceptor;


import com.donnie.config.MultiTenantHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

@Intercepts(value = {
        @Signature(type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class,Integer.class})})
@Slf4j
public class MultiTenantInterceptor implements Interceptor {

    private static final String preState="/*!mycat:datanode=";
    private static final String afterState="*/";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler=(StatementHandler)invocation.getTarget();
        MetaObject metaStatementHandler= SystemMetaObject.forObject(statementHandler);

        //拦截要执行的sql
        String sql=(String)metaStatementHandler.getValue("delegate.boundSql.sql");

        //获取租户使用节点
        String node = MultiTenantHolder.getCurrentNode();

        if(node!=null) {
            sql = preState + node + afterState + sql;
        }

        log.info("添加mycat注释的sql =" + sql);
        metaStatementHandler.setValue("delegate.boundSql.sql",sql);
        Object result = invocation.proceed();

        return result;
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
