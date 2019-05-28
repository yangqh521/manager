package com.manager.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(basePackages="com.manager.dao.dsp",sqlSessionFactoryRef="dspDataSqlSessionFactory")
public class DspDataSourceConfig {


    @Primary
    @Bean(name = "dspDataSource")
    @ConfigurationProperties(prefix="spring.datasource.dsp")
    public DataSource dspDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Primary
    @Bean(name = "dspDataSqlSessionFactory")
    public SqlSessionFactory  dspSqlSessionFactory(@Qualifier("dspDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }


    @Primary
    @Bean(name = "dspDataTransactionManager")
    public DataSourceTransactionManager dspTransactionManager(@Qualifier("dspDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Primary
    @Bean(name = "dspDataSqlSessionTemplate")
    public SqlSessionTemplate dspSqlSessionTemplate(
            @Qualifier("dspDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}

