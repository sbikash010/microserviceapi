package com.adminuserdetails.adminuserdetails.controller.adminController;

import com.adminuserdetails.adminuserdetails.model.admin.AdminDetails;
import com.adminuserdetails.adminuserdetails.pojo.GlobleApiResponse;
import com.adminuserdetails.adminuserdetails.pojo.adminPojo.AdminPojo;
import com.adminuserdetails.adminuserdetails.service.adminService.AdminService;
import com.adminuserdetails.adminuserdetails.utils.excelGenerator.ExcelGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1")
@Validated
public class AdminController {
    private final AdminService adminService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GlobleApiResponse> saveAdminDetail(@ModelAttribute @Valid AdminPojo adminPojo) throws Exception {
        AdminPojo adminPojo1 = adminService.saveAdmin(adminPojo);
        return ResponseEntity.ok(new GlobleApiResponse(true, "Admin is add successfully",null));

    }

    //    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<GlobleApiResponse> saveAdminDetail(@ModelAttribute @Valid AdminPojo adminPojo,BindingResult bindingResult) throws Exception {
//
//        if(bindingResult.hasErrors()) {
//            String errror=bindingResult.getFieldError().getDefaultMessage();
//            return ResponseEntity.badRequest().body(new GlobleApiResponse(false,errror,null));
//        }
//        AdminPojo adminPojo1 = adminService.saveAdmin(adminPojo);
//        return ResponseEntity.ok(new GlobleApiResponse(true, "Admin is add successfully", adminPojo1));
//
//    }
    @GetMapping()
    public ResponseEntity<List<AdminPojo>> getAllByAdminDetails()
    {
        List<AdminPojo> allDetailsAdmin=adminService.getAllDetails();
        return new ResponseEntity<List<AdminPojo>>(allDetailsAdmin, HttpStatus.OK);
    }
    @GetMapping("/pagination/{pageNumber}/{pageSize}")
    public ResponseEntity<GlobleApiResponse>getAllByAdminDetailsWithSort(@PathVariable int pageNumber,@PathVariable int pageSize)
    {
        return  ResponseEntity.ok(new GlobleApiResponse(true,"admin detail fetched successfully",adminService.getAllDetailsWithSort(pageNumber,pageSize)));
    }
//    @GetMapping("/{pageNumber}/{pageSize}")
//    public ResponseEntity<List<AdminPojo>> getAllByAdminDetailsWithSort(@PathVariable int pageNumber,@PathVariable int pageSize)
//    {
//        List<AdminPojo> allDetailsAdmin=adminService.getAllDetailsWithSort(pageNumber,pageSize);
//        return new ResponseEntity<List<AdminPojo>>(allDetailsAdmin,HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobleApiResponse> getAdminDetailById(@PathVariable("id") Integer id)throws Exception
    {
        return ResponseEntity.ok(new GlobleApiResponse(true,"Admin fetch successfully",adminService.getAdminById(id)));
    }


    @DeleteMapping("/{adminid}")
    public ResponseEntity deleteAdminById(@PathVariable("adminid") Integer id )
    {
        return new ResponseEntity(adminService.deleteById(id), HttpStatus.OK);
    }
    @GetMapping("/export-to-excel")
    public ResponseEntity<GlobleApiResponse> exportIntoExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());

//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=records_" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);

        List<AdminPojo> listOfRecords = adminService.getAllDetails();
        ExcelGenerator excelGenerator=new ExcelGenerator(listOfRecords);
        excelGenerator.generate(response);

        return ResponseEntity.ok(new GlobleApiResponse(true,"Admin details fetch",null));
    }




//    @PutMapping()
//    public ResponseEntity<GlobleApiResponse> updateAdminDetaitById(@RequestBody  AdminPojo adminPojo)
//    {
//        return ResponseEntity.ok(new GlobleApiResponse(true,"update is Successfully",adminService.updateByAdminId(adminPojo)));
//
//    }
//
@PostMapping("/uploadFile")
public ResponseEntity<GlobleApiResponse> fileUpload(MultipartFile multipartFile) throws Exception
{
    if(multipartFile.isEmpty())
    {
        return ResponseEntity.badRequest().body(new GlobleApiResponse(false,"file is not upload",null));
    }
    return ResponseEntity.ok(new GlobleApiResponse(true,"file is upload successfully",adminService.uploadFileByAdmin(multipartFile)));
}

//    @PostMapping("/uploadFile")
//    public ResponseEntity<GlobleApiResponse> fileUpload( AdminPojo adminPojo) throws Exception
//    {
//        return adminService.uploadFileByAdmin(adminPojo);
////        if(multipartFile.isEmpty())
////        {
////            return ResponseEntity.badRequest().body(new GlobleApiResponse(false,"file is not upload",null));
////        }
////        return ResponseEntity.ok(new GlobleApiResponse(true,"file is upload successfully",adminService.uploadFileByAdmin(multipartFile)));
//    }

}
