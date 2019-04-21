package br.com.emendes.rabbitclient.gateway.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange("tut.rep");
	}

	@Bean
	public Queue queue() {
		return new Queue("tut.rep.responses");
	}

	@Bean
	public Binding binding(DirectExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with("response");
	}

}