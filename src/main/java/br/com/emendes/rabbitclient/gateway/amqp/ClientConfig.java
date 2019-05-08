package br.com.emendes.rabbitclient.gateway.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

	private final RabbitAdmin rabbitAdmin;

	@Qualifier("rabbitAdminCommon")
	private final RabbitAdmin rabbitAdminCommon;

	public DirectExchange exchange(String name) {
		return new DirectExchange(name);
	}

	public Queue queue(String name) {
		return new Queue(name);
	}

	@PostConstruct
	public void binding() {
		bind("tut.rep", "tut.rep.responses", "responseA");
	}

	private void bind(String exchangeName, String queueName, String routingKey) {
		final Exchange exchange = exchange(exchangeName);
		rabbitAdminCommon.declareExchange(exchange);

		final Queue queue = queue(queueName);
		rabbitAdminCommon.declareQueue(queue);

		final BindingBuilder.GenericArgumentsConfigurer binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
		rabbitAdminCommon.declareBinding(binding.noargs());

		System.out.println("bind executed");
	}

}