package com.vinod.aws.parameter.secret.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.PropertySource;

@Log4j2
public class ParameterStorePropertySource extends PropertySource<ParameterStoreSource> {
    public static final String PARAMETER_STORE_HIERARCHY_SPLIT_CHARACTER = "/";

    public ParameterStorePropertySource(String name, ParameterStoreSource source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
            if (name.startsWith(PARAMETER_STORE_HIERARCHY_SPLIT_CHARACTER)) {
                log.debug("Property found => {}",name.split(":")[0]);
                return source.getProperty(name.split(":")[0]);
            }
            return null;
    }
}
