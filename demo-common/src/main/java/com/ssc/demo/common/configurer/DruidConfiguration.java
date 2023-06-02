package com.ssc.demo.common.configurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.github.pagehelper.PageHelper;
import com.ssc.demo.common.constants.ProjectConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * druid 配置.
 * 不需要添加注解：@ServletComponentScan
 *
 * @author Administrator
 *
 * 注解@MapperScan与下面baseSqlSessionFactory Bean重复扫描mapper
 */
@Configuration
@Slf4j
public class DruidConfiguration {
    /**
     * 注册一个StatViewServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServle() {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1,10.11.0.119,10.11.0.66,10.11.0.77,10.11.0.120,125.118.218.79");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        //servletRegistrationBean.addInitParameter("deny", "192.168.0.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;

    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
        return filterRegistrationBean;
    }

    @ConfigurationProperties(prefix = "spring.datasource.base")
    class IDataSourceProperties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private int initialSize;
        private int minIdle;
        private int maxActive;
        private int maxWait;
        private int timeBetweenEvictionRunsMillis;
        private int minEvictableIdleTimeMillis;
        private String validationQuery;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean poolPreparedStatements;
        private int maxPoolPreparedStatementPerConnectionSize;
        private String filters;
        private String connectionProperties;

        @Bean(name = "baseDataSource")     //声明其为Bean实例
        @Primary  //在同样的DataSource中，首先使用被标注的DataSource
        public DataSource dataSource() {
            DruidDataSource datasource = new DruidDataSource();
            datasource.setUrl(url);
            datasource.setUsername(username);
            datasource.setPassword(password);
            datasource.setDriverClassName(driverClassName);

            //configuration
            datasource.setInitialSize(initialSize);
            datasource.setMinIdle(minIdle);
            datasource.setMaxActive(maxActive);
            datasource.setMaxWait(maxWait);
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            datasource.setValidationQuery(validationQuery);
            datasource.setTestWhileIdle(testWhileIdle);
            datasource.setTestOnBorrow(testOnBorrow);
            datasource.setTestOnReturn(testOnReturn);
            datasource.setPoolPreparedStatements(poolPreparedStatements);
            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
            try {
                datasource.setFilters(filters);
            } catch (SQLException e) {
                log.warn("", e);
                System.err.println("druid configuration initialization filter: " + e);
            }
            datasource.setConnectionProperties(connectionProperties);
            return datasource;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public int getInitialSize() {
            return initialSize;
        }

        public void setInitialSize(int initialSize) {
            this.initialSize = initialSize;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }

        public int getTimeBetweenEvictionRunsMillis() {
            return timeBetweenEvictionRunsMillis;
        }

        public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        }

        public int getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        public String getValidationQuery() {
            return validationQuery;
        }

        public void setValidationQuery(String validationQuery) {
            this.validationQuery = validationQuery;
        }

        public boolean isTestWhileIdle() {
            return testWhileIdle;
        }

        public void setTestWhileIdle(boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
        }

        public boolean isTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public boolean isTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }

        public boolean isPoolPreparedStatements() {
            return poolPreparedStatements;
        }

        public void setPoolPreparedStatements(boolean poolPreparedStatements) {
            this.poolPreparedStatements = poolPreparedStatements;
        }

        public int getMaxPoolPreparedStatementPerConnectionSize() {
            return maxPoolPreparedStatementPerConnectionSize;
        }

        public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
            this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
        }

        public String getFilters() {
            return filters;
        }

        public void setFilters(String filters) {
            this.filters = filters;
        }

        public String getConnectionProperties() {
            return connectionProperties;
        }

        public void setConnectionProperties(String connectionProperties) {
            this.connectionProperties = connectionProperties;
        }

        @Bean(name = "baseTransactionManager")
        @Primary
        public DataSourceTransactionManager setTransactionManager(@Qualifier("baseDataSource") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean(name = "baseSqlSessionFactory")
        @Primary
        public SqlSessionFactory setSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            bean.setTypeAliasesPackage(ProjectConstant.MODEL_PACKAGE);

            //配置分页插件
            PageHelper pageHelper = new PageHelper();
            Properties properties = new Properties();
            //分页尺寸为0时查询所有纪录不再执行分页
            properties.setProperty("pageSizeZero", "true");
            //页码<=0 查询第一页，页码>=总页数查询最后一页
            properties.setProperty("reasonable", "true");
            //支持通过 Mapper 接口参数来传递分页参数
            properties.setProperty("supportMethodsArguments", "true");
            pageHelper.setProperties(properties);

            //添加插件
            bean.setPlugins(new Interceptor[]{pageHelper});

            //添加XML目录
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            bean.setMapperLocations(resolver.getResources("classpath:mapper/**/**.xml"));
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            bean.setConfiguration(configuration);
            return bean.getObject();
        }


        @Bean(name = "baseSqlSessionTemplate")
        @Primary
        public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
            return new SqlSessionTemplate(sqlSessionFactory);
        }


    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("baseSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(ProjectConstant.MAPPER_PACKAGE);

        //配置通用Mapper，详情请查阅官方文档
        Properties properties = new Properties();
        properties.setProperty("mappers", ProjectConstant.MAPPER_INTERFACE_REFERENCE);
        //insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }

}

