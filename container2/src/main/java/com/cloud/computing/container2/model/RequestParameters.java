package com.cloud.computing.container2.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RequestParameters {
    String file;
    String product;
}
