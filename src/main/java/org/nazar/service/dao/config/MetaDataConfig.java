package org.nazar.service.dao.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dao.type")
public class MetaDataConfig {
    private String daoType;

    public String getDaoType() {
        return daoType;
    }

    public void setDaoType(String daoType) {
        this.daoType = daoType;
    }
}
