package com.topsem.config;

import com.topsem.common.repository.jpa.RepositoryHelper;
import com.topsem.common.repository.jpa.support.SimpleBaseRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * @author Chen on 14-11-26.
 */
@Configuration
@AutoConfigureAfter(CacheConfiguration.class)
@EnableJpaRepositories(basePackages = {"com.topsem.*.repository"}, repositoryFactoryBeanClass = SimpleBaseRepositoryFactoryBean.class)
public class JPAConfiguration {

    @Inject
    EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        RepositoryHelper.setEntityManagerFactory(entityManagerFactory);
    }
}
