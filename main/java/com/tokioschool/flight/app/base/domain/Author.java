package com.tokioschool.flight.app.base.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value

public class Author {
    int id;
    String name;

}
