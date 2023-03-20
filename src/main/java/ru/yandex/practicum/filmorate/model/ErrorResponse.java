package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {
    private Integer code;
    private List<ErrorsDescription> errors;
}