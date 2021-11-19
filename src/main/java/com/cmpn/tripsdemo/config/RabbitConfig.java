package com.cmpn.tripsdemo.config;

import com.cmpn.tripsdemo.domain.Consumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

  public static final String DIR_EXCHANGE_NAME = "trips-direct-ex";

  public static final String QUEUE_SAVE = "trips-queue_save";
  public static final String QUEUE_UPD = "trips-queue_upd";
  public static final String QUEUE_DEL = "trips-queue_del";

  @Bean
  Queue queueSaveMsg() {
    return new Queue(QUEUE_SAVE, true);
  }

  @Bean
  Queue queueUpdateMsg() {
    return new Queue(QUEUE_UPD, true);
  }

  @Bean
  Queue queueDeleteMsg() {
    return new Queue(QUEUE_DEL, true);
  }

  @Bean
  DirectExchange directExchange() {
    return new DirectExchange(DIR_EXCHANGE_NAME);
  }

  @Bean
  Binding bindingSave(@Qualifier("queueSaveMsg") Queue queue, DirectExchange directExchange) {
    return BindingBuilder.bind(queue).to(directExchange).with("trip.save");
  }

  @Bean
  Binding bindingUpd(@Qualifier("queueUpdateMsg") Queue queue, DirectExchange directExchange) {
    return BindingBuilder.bind(queue).to(directExchange).with("trip.update");
  }

  @Bean
  Binding bindingDel(@Qualifier("queueDeleteMsg") Queue queue, DirectExchange directExchange) {
    return BindingBuilder.bind(queue).to(directExchange).with("trip.delete");
  }

  @Bean
  MessageListenerAdapter listenerAdapter(Consumer consumer) {
    Map<String, String> queueToMethod = new HashMap<>();
    queueToMethod.put(QUEUE_SAVE, "saveOrUpdate");
    queueToMethod.put(QUEUE_UPD, "saveOrUpdate");
    queueToMethod.put(QUEUE_DEL, "delete");
    MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(consumer, "getMessage");
    listenerAdapter.setQueueOrTagToMethodName(queueToMethod);
    return listenerAdapter;
  }

  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                           MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(QUEUE_SAVE, QUEUE_UPD, QUEUE_DEL);
    container.setMessageListener(listenerAdapter);

    return container;
  }
}
