//package com.fiap.pedidos.facade;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.context.event.ApplicationPreparedEvent;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.core.env.PropertiesPropertySource;
//import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
//import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
//import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
//
//import java.util.Properties;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class ListenerOfSecretsTest {
//
//    @Test
//    void onApplicationEvent() {
//        // Mock do ConfigurableApplicationContext
//        ConfigurableApplicationContext applicationContext = mock(ConfigurableApplicationContext.class);
//
//        // Mock do SpringApplication
//        SpringApplication springApplication = mock(SpringApplication.class);
//        when(springApplication.run()).thenReturn(applicationContext);
//
//        // Mock do ConfigurableEnvironment
//        ConfigurableEnvironment environment = mock(ConfigurableEnvironment.class);
//        MutablePropertySources propertySources = mock(MutablePropertySources.class);
//        when(environment.getPropertySources()).thenReturn(propertySources);
//
//        // Mock do ApplicationPreparedEvent
//        ApplicationPreparedEvent event = new ApplicationPreparedEvent(springApplication, new String[]{}, applicationContext);
//        when(event.getApplicationContext().getEnvironment()).thenReturn(environment);
//
//        // Mock do SecretsManagerClient
//        SecretsManagerClient secretsManagerClient = mock(SecretsManagerClient.class);
//
//        // Mock do GetSecretValueResponse
//        GetSecretValueResponse getSecretValueResponse = mock(GetSecretValueResponse.class);
//        when(getSecretValueResponse.secretString()).thenReturn("{\"username\": \"testUser\", \"password\": \"testPassword\", \"host\": \"localhost\", \"port\": \"5432\", \"dbname\": \"testDB\"}");
//        when(secretsManagerClient.getSecretValue(Mockito.any(GetSecretValueRequest.class))).thenReturn(getSecretValueResponse);
//
//        ListenerOfSecrets listener = new ListenerOfSecrets();
//        listener.onApplicationEvent(event);
//
//        Properties expectedProperties = new Properties();
//        expectedProperties.setProperty("spring.datasource.username", "testUser");
//        expectedProperties.setProperty("spring.datasource.password", "testPassword");
//        expectedProperties.setProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/testDB?ssl=true&sslmode=require&rejectUnauthorized=false");
//        Mockito.verify(propertySources).addFirst(new PropertiesPropertySource("aws.secret.manager", expectedProperties));
//    }
//}
