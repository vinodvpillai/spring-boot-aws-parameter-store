package com.vinod.aws.parameter.secret.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ObjectUtils;

import static com.vinod.aws.parameter.secret.config.ParameterStorePropertySourceConfigurationProperties.*;

public class ParameterStorePropertySourceEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private String accessKey="accessKey";
    private String secretKey="secretKey";


    static boolean isInitialized;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!isInitialized && isParameterStorePropertySourceEnabled(environment)) {
            //AWSSecretsManager simpleSystemsManagementClient = AWSSecretsManagerClientBuilder.defaultClient();

            //Remove the below line and uncomment the above one; if EC2 instance is having necessary permission.
            AWSSimpleSystemsManagement awsSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder.standard().withCredentials(new StaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))).withRegion("ap-south-1").build();

            Boolean haltBootConfigParam = environment.getProperty(HALT_BOOT_CONFIGURATION_PROPERTY, Boolean.class, Boolean.FALSE);
            ParameterStoreSource parameterStore = new ParameterStoreSource(awsSimpleSystemsManagement, haltBootConfigParam);
            ParameterStorePropertySource propertySource = new ParameterStorePropertySource(PROPERTY_SOURCE_NAME, parameterStore);
            environment.getPropertySources().addFirst(propertySource);

            isInitialized = true;
        }
    }

    private boolean isParameterStorePropertySourceEnabled(ConfigurableEnvironment environment) {
        String[] userDefinedEnabledProfiles = environment.getProperty(ACCEPTED_PROFILES, String[].class);
        return environment.getProperty(ENABLED, Boolean.class, Boolean.FALSE) || environment.acceptsProfiles(PARAMETER_STORE_ACCEPTED_PROFILE) || (!ObjectUtils.isEmpty(userDefinedEnabledProfiles) && environment.acceptsProfiles(userDefinedEnabledProfiles));
    }
}
