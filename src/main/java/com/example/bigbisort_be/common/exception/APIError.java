package com.example.bigbisort_be.common.exception;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class APIError {
//    @Builder.Default
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String timestamp;

    private Integer status;
    private Object message;
    private String error;
    private String path;
}

