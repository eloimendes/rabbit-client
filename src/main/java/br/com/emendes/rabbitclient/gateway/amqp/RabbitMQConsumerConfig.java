package br.com.emendes.rabbitclient.gateway.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumerConfig {

//    private final ConnectionFactory connectionFactory;

    @Bean
    @Primary
    public RabbitAdmin rabbitAdmin() {
        final ConnectionFactory logConnectionFactory = getLogConnectionFactory("app-a", "secret", "localhost:5672", "app-a");
        return new RabbitAdmin(logConnectionFactory);
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(RabbitAdmin rabbitAdmin){
        return rabbitAdmin.getRabbitTemplate();
    }

    @Bean(name = "rabbitAdminCommon")
    public RabbitAdmin rabbitAdminCommon() {
        final ConnectionFactory commonConnectionFactory = getLogConnectionFactory("app-a", "secret", "localhost:5672", "common");
        return new RabbitAdmin(commonConnectionFactory);
    }

    @Bean("rabbitTemplateCommon")
    public RabbitTemplate rabbitTemplateCommon(@Qualifier("rabbitAdminCommon") RabbitAdmin rabbitAdmin){
        return rabbitAdmin.getRabbitTemplate();
    }

    private ConnectionFactory getLogConnectionFactory(String user, String pass, String addresses, String virtualHost){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(user);
        connectionFactory.setAddresses(addresses);
        connectionFactory.setPassword(pass);
        connectionFactory.setVirtualHost(virtualHost);
//        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        return connectionFactory;
    }

    @Bean(name="rabbitListenerCommonContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerCommonContainerFactory() {
        final ConnectionFactory commonConnectionFactory = getLogConnectionFactory("app-a", "secret", "localhost:5672", "common");

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(commonConnectionFactory);

        return factory;
    }
}