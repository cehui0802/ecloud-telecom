package com.telecom.ecloud.base.samples.config;

import com.telecom.ecloudframework.base.core.util.BeanUtils;
import com.telecom.ecloudframework.base.core.util.StringUtil;
import com.telecom.ecloudframework.base.db.datasource.DataSourceUtil;
import com.telecom.ecloudframework.sys.api.model.ISysDataSource;
import com.telecom.ecloudframework.sys.api.model.ISysDataSourceDefAttribute;
import com.telecom.ecloudframework.sys.api.platform.ISysDataSourcePlatFormService;
import cn.hutool.core.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class DataSourceInitialization implements CommandLineRunner {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("${ecloud.log.dataSource.dbAlias}")
    String dbAlias;

    @Autowired
    ISysDataSourcePlatFormService iSysDataSourcePlatFormService ;

    @Override
    public void run(String... args) {
        if(StringUtil.isNotEmpty(dbAlias)){
            try{
                List<? extends ISysDataSource> list = iSysDataSourcePlatFormService.dataSourceList(dbAlias);
                if(list != null && list.size() > 0){
                    for(ISysDataSource iSysDataSource : list){
                        DataSource dataSource = tranform2DataSource(iSysDataSource);
                        DataSourceUtil.addDataSource(iSysDataSource.getKey(), dataSource, iSysDataSource.getDbType(), false);
                        LOG.info("{}数据源已被加载",iSysDataSource.getKey());
                    }
                }
            }catch(Exception e){
                LOG.error("数据源加载失败",e);
            }
        }

    }

    private DataSource tranform2DataSource(ISysDataSource sysDataSource) {
        try {
            Class<?> cls = Class.forName(sysDataSource.getClassPath());// 拿到类路径
            DataSource dataSource = (DataSource) cls.newInstance();// 初始化一个对象
            // 设置值
            for (ISysDataSourceDefAttribute attribute : sysDataSource.getAttributes()) {
                if (StringUtil.isEmpty(attribute.getValue())) {
                    continue;
                }
                Object value = BeanUtils.getValue(attribute.getType(), attribute.getValue());
                String setMethodName = "set" + StringUtil.toFirst(attribute.getName(), true);
                ReflectUtil.invoke(dataSource, setMethodName, value);
            }
            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
