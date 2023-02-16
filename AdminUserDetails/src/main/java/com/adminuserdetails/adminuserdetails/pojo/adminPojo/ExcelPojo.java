package com.adminuserdetails.adminuserdetails.pojo.adminPojo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelPojo {

    private Integer id;
    private Object name;
    private Object email;
    private Object address;
    private Object contactNumber;
    private Object date;
    private Object filepath;


}
