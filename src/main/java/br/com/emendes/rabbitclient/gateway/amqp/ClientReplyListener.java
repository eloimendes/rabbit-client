package br.com.emendes.rabbitclient.gateway.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.tutorials.tut7.FibResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientReplyListener {

    @RabbitListener(queues = "tut.rep.responses", concurrency = "100")
    public void fibonacci(FibResponse response) {
        log.info(" [x] Received response: " + response);
    }
}
