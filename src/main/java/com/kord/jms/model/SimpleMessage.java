package com.kord.jms.model;


import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMessage implements Serializable {

    static final long serialVersionUID = -6943025009652258417L;

    private UUID id;
    private String message;
}
