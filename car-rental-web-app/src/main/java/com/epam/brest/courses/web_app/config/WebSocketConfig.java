package com.epam.brest.courses.web_app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String topicExchangeName = "car-rental-exchange";
    public static final String queueNameCreate = "car-rental-queue";
    public static final String queueNameUpdate = "car-rental-queue-update";
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }
    @Bean
    Queue queueCreate() {
        return new Queue(queueNameCreate, true);
    }

    @Bean
    Queue queueUpdate() {
        return new Queue(queueNameUpdate, true);
    }

    @Bean
    Binding bindingCreate(Queue queueCreate, TopicExchange exchange) {
        return BindingBuilder.bind(queueCreate).to(exchange).with("#.add.#");
    }

    @Bean
    Binding bindingUpdate(Queue queueUpdate, TopicExchange exchange) {
        return BindingBuilder.bind(queueUpdate).to(exchange).with("#.update.#");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");

        registry
                .enableStompBrokerRelay("/exchange")
                .setRelayHost("localhost")
                .setVirtualHost("/")
//                .setRelayPort(61613)
                .setClientLogin("admin")
                .setClientPasscode("admin");

    }
}
