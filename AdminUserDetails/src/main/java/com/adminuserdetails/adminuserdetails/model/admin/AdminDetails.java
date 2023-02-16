package com.adminuserdetails.adminuserdetails.model.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="admin_details",uniqueConstraints ={
        @UniqueConstraint(name="UK_Admin_email",columnNames = "email")
})
public class AdminDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Admin not blank")
    private String name;
    @Column(name="email")
    private String email;
    private String address;
    @Column(name = "contaact_number")
    @Size(max = 10)

    private String contactNumber;
    @JsonFormat(pattern = "yy-mm-dd")
    private Date date;

    @Column(columnDefinition = "TEXT")
    private String  filepath;

}
