package org.springframework.amqp.tutorials.tut7;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class FibResponse implements Serializable {
    public static final long serialVersionUID = 1L;

    int request;
    int response;
}
