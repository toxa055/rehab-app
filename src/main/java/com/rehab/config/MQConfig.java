package com.rehab.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class that declares methods to configure all unnecessary beans
 * which will be used for communicating with messaging broker.
 * RabbitMQ is particular implementation of messaging broker that is used in the application.
 */
@Configuration
public class MQConfig {

    /**
     * Method that initializes a new instance of Queue with specific name, 'events_queue' in this case.
     *
     * @return new instance of Queue;
     */
    @Bean
    public Queue queue() {
        return new Queue("events_queue");
    }

    /**
     * Method that initializes a new instance of MessageConverter's implementation.
     *
     * @return new instance of Jackson2JsonMessageConverter;
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Method that initializes a new instance of AmqpTemplate's implementation.
     * It's used for sending messages to queue.
     *
     * @param connectionFactory interface that creates AMQ connections.
     * @return new instance of RabbitTemplate;
     */
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
