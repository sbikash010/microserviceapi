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
public class AdminPojo {
    private Integer id;
    @NotBlank(message = "Admin is not blank")
    @NotNull(message = "Admin Name is not null!!!")
    private String name;
    @NotNull(message = "Email is not null!!!")
    private String email;

    @NotNull(message = "Address is required !!!")
    private String address;
    @NotNull(message = "contactNumber is required!!")
    @Size(min = 10,max = 10)
    private String contactNumber;

    private MultipartFile multipartFile;
    private String fileLocation;

    private String data;


}
