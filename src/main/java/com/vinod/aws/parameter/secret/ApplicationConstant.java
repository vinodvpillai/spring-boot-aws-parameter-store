package com.vinod.aws.parameter.secret;

public class ApplicationConstant {

    //AWS Parameter store.
    public static final String PARAMETER_STORE_ACCEPTED_PROFILE = "awsParameterStorePropertySourceEnabled";
    public static final String PARAMETER_STORE_ACCEPTED_PROFILES_CONFIGURATION_PROPERTY = "awsParameterStorePropertySource.enabledProfiles";
    public static final String PARAMETER_STORE_ENABLED_CONFIGURATION_PROPERTY = "awsParameterStorePropertySource.enabled";
    public static final String PARAMETER_STORE_HALT_BOOT_CONFIGURATION_PROPERTY = "awsParameterStorePropertySource.haltBoot";
    public static final String PARAMETER_STORE_PROPERTY_SOURCE_NAME = "AWSParameterStorePropertySource";

    //AWS Secret store.
    public static final String SECRET_STORE_ACCEPTED_PROFILE = "awsSecretStorePropertySourceEnabled";
    public static final String SECRET_STORE_ACCEPTED_PROFILES_CONFIGURATION_PROPERTY = "awsSecretStorePropertySource.enabledProfiles";
    public static final String SECRET_STORE_ENABLED_CONFIGURATION_PROPERTY = "awsSecretStorePropertySource.enabled";
    public static final String SECRET_STORE_HALT_BOOT_CONFIGURATION_PROPERTY = "awsSecretStorePropertySource.haltBoot";
    public static final String SECRET_STORE_PROPERTY_SOURCE_NAME = "AWSSecretStorePropertySource";

}
