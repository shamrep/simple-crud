package org.simplecrud.repository.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class TagEntity {

    private Long id;
    private String name;
}
