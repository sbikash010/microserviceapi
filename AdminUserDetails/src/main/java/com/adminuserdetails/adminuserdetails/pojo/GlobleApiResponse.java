package com.adminuserdetails.adminuserdetails.pojo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GlobleApiResponse implements Serializable {
    private boolean status;
    private String massege;
    private Object data;

}
