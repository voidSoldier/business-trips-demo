package com.cmpn.tripsdemo.config;

import com.cmpn.tripsdemo.datasource.TripConsumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  public static final String FANOUT_EXCHANGE_NAME = "trips-fanout-ex";

  public static final String TRIPS_QUEUE = "trips-queue";

  @Bean
  Queue queue() {
    return new Queue(TRIPS_QUEUE, true);
  }

  @Bean
  FanoutExchange fanoutExchange() {
    return new FanoutExchange(FANOUT_EXCHANGE_NAME);
  }

  @Bean
  Binding binding(Queue queue, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(queue).to(fanoutExchange);
  }

  @Bean
  MessageListenerAdapter listenerAdapter(TripConsumer tripConsumer) {
    return new MessageListenerAdapter(tripConsumer, "receiveMessage");
  }

  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                           MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setMessageListener(listenerAdapter);
    return container;
  }
}
