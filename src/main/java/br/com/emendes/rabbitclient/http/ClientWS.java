package br.com.emendes.rabbitclient.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/send")
@Slf4j
public class ClientWS {

    @Autowired
    private RabbitTemplate template;

    @GetMapping
    public void send(@RequestParam(defaultValue = "5") int fib){
        log.info(" [x] Requesting fib(" + fib + ")");
        template.convertAndSend("tut.rep", "request", fib, message -> {
            message.getMessageProperties().setReplyToAddress(new Address("tut.rep", "response"));
            return message;
        });
    }
}
