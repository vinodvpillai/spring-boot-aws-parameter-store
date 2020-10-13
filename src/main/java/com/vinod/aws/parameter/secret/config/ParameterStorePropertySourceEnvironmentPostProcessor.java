package com.vinod.aws.parameter.secret.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import static com.vinod.aws.parameter.secret.config.ParameterStorePropertySourceConfigurationProperties.*;

public class ParameterStorePropertySourceEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Autowired
    private Environment environment;
    static boolean isInitialized;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!isInitialized && isParameterStorePropertySourceEnabled(environment)) {
            //AWSSecretsManager simpleSystemsManagementClient = AWSSecretsManagerClientBuilder.defaultClient();

            //Remove the below line and uncomment the above one; if EC2 instance is having necessary permission.
            AWSSimpleSystemsManagement awsSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder.standard().withCredentials(new StaticCredentialsProvider(new BasicAWSCredentials(environment.getProperty("ACCESS_KEY"), environment.getProperty("SECRET_KEY")))).withRegion("ap-south-1").build();

            Boolean haltBootConfigParam = environment.getProperty(HALT_BOOT_CONFIGURATION_PROPERTY, Boolean.class);
            ParameterStoreSource parameterStore = new ParameterStoreSource(awsSimpleSystemsManagement, haltBootConfigParam);
            ParameterStorePropertySource propertySource = new ParameterStorePropertySource(PROPERTY_SOURCE_NAME, parameterStore);
            environment.getPropertySources().addFirst(propertySource);

            isInitialized = true;
        }
    }

    private boolean isParameterStorePropertySourceEnabled(ConfigurableEnvironment environment) {
        return environment.getProperty(ENABLED, Boolean.class, Boolean.FALSE);
    }
}
