package com.adminuserdetails.adminuserdetails.service.adminService;

import com.adminuserdetails.adminuserdetails.pojo.GlobleApiResponse;
import com.adminuserdetails.adminuserdetails.pojo.adminPojo.AdminPojo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {
    AdminPojo saveAdmin(AdminPojo adminPojo) throws Exception;
    List<AdminPojo> getAllDetails();
    Page<Map<String,Object>> getAllDetailsWithSort(int pageNumber, int pageSize);

    AdminPojo getAdminById(Integer id);
    GlobleApiResponse deleteById(Integer id);
    ResponseEntity uploadFileByAdmin(AdminPojo adminPojo);
//      AdminPojo updateByAdminId(AdminPojo adminPojo);
//      ResponseEntity uploadFileByAdmin(MultipartFile multipartFile);

}
