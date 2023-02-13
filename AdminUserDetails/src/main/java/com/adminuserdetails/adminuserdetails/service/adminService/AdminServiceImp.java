package com.adminuserdetails.adminuserdetails.service.adminService;


import com.adminuserdetails.adminuserdetails.model.admin.AdminDetails;
import com.adminuserdetails.adminuserdetails.pojo.GlobleApiResponse;
import com.adminuserdetails.adminuserdetails.pojo.adminPojo.AdminPojo;
import com.adminuserdetails.adminuserdetails.repo.adminRepo.AdminRepo;
import com.adminuserdetails.adminuserdetails.utils.fileUtils.FIleStorageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImp implements AdminService {

    private final   AdminRepo adminRepo;
    private final FIleStorageUtils fIleStorageUtils;
    @Override
    public AdminPojo saveAdmin(AdminPojo adminPojo) throws Exception  {

        AdminDetails adminDetails;
        if(adminPojo.getId()!=null)
            adminDetails = adminRepo.findById(adminPojo.getId()).orElseThrow(() ->{throw new RuntimeException("Admin not found");});
        else
            adminDetails = new AdminDetails();


        String filepath=fIleStorageUtils.storeFile(adminPojo);
        try{
            adminDetails.setId(adminPojo.getId());
            adminDetails.setName(adminPojo.getName());
            adminDetails.setEmail(adminPojo.getEmail());
            adminDetails.setFilepath(filepath);
            adminDetails.setContactNumber(adminPojo.getContactNumber());
            adminDetails.setDate(new SimpleDateFormat("yy-MM-dd").parse(adminPojo.getData()));
            adminDetails.setAddress(adminPojo.getAddress());
            adminRepo.save(adminDetails);
            return adminPojo;
        }catch (ParseException e)
        {
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public GlobleApiResponse deleteById(Integer id) {
        AdminDetails adminDetails=adminRepo.findById(id).orElseThrow(()->new RuntimeException("Admin is not exist!!!"));
        adminRepo.delete(adminDetails);
        return  GlobleApiResponse.builder().status(true).massege("Admin delete successfully").build();
    }
    @Override
    public List<AdminPojo> getAllDetails() {

        return adminRepo.findAll()
                .stream().map(
                        adminDetails ->{

                            return AdminPojo.builder()
                                    .id(adminDetails.getId())
                                    .name(adminDetails.getName())
                                    .address(adminDetails.getAddress())
                                    .email(adminDetails.getEmail())
                                    .contactNumber(adminDetails.getContactNumber())
                                    .data(new SimpleDateFormat("YY-MM-DD").format(adminDetails.getDate()))
                                    .fileLocation(adminDetails.getFilepath())
                                    .build();


                        })
                .collect(Collectors.toList());
    }

    @Override
    public Page<Map<String,Object>> getAllDetailsWithSort(int pageNumber, int pageSize) {
        int page=pageNumber;
        int size=pageSize;
        Pageable pageable= PageRequest.of(page,size);

        return adminRepo.findAllDetailsByPagination(pageable);
//        return adminRepo.findAll(PageRequest.of(pageNumber,pageSize)).stream().map(
//                adminDetails ->{
//
//                        return  AdminPojo.builder()
//                                .id(adminDetails.getId())
//                                .name(adminDetails.getName())
//                                .address(adminDetails.getAddress())
//                                .email(adminDetails.getEmail())
//                                .contactNumber(adminDetails.getContactNumber())
//                                .fileLocation(adminDetails.getFilepath())
//                                .build();
//
//
//                })
//                .collect(Collectors.toList());
    }

    @Override
    public AdminPojo getAdminById(Integer id) {
        AdminDetails adminDetails=adminRepo.findById(id).orElseThrow(()->new RuntimeException("Admin is not recoded!!!"));

        return AdminPojo.builder()
                .id(adminDetails.getId())
                .name(adminDetails.getName())
                .address(adminDetails.getAddress())
                .email(adminDetails.getEmail())
                .contactNumber(adminDetails.getContactNumber())
                .fileLocation(adminDetails.getFilepath())
                .build();

    }

//    @Override
//    public AdminPojo updateByAdminId(AdminPojo adminPojo) {
//         AdminDetails findadminDetails=adminRepo.findById(adminPojo.getId()).orElseThrow(()->new RuntimeException("admin is not found!!! "));
//
//                  findadminDetails.setId(adminPojo.getId());
//                  findadminDetails.setAddress(adminPojo.getAddress());
//                  findadminDetails.setContactNumber(adminPojo.getContactNumber());
//                  findadminDetails.setEmail(adminPojo.getEmail());
//                  findadminDetails.setName(adminPojo.getName());
//            AdminDetails adminDetails=adminRepo.save(findadminDetails);
//
//        return new AdminPojo(adminDetails);
//    }

    //    @Override
//    public ResponseEntity uploadFileByAdmin(MultipartFile multipartFile) {
//      String fileDir=System.getProperty("user.dir")+File.separator+"bikash";
//      File fileDirectory=new File(fileDir);
//      if(!fileDirectory.exists()) {
//            boolean mkdir= fileDirectory.mkdir();
//      }else {
//          log.info("file is already exist!!");
//      }
//      String filepath=fileDir+File.separator+multipartFile.getOriginalFilename();
//       fileDirectory=new File(filepath);
//       try(FileOutputStream outputStream=new FileOutputStream(fileDirectory))
//        {
//            outputStream.write(multipartFile.getBytes());
//            return ResponseEntity.ok(new GlobleApiResponse(true,"file successfully upload",filepath));
//        }catch (Exception e)
//       {
//           return ResponseEntity.badRequest().body(new GlobleApiResponse(false,"file not successfully uploaded",null));
//       }
//
//    }
    @Override
    public ResponseEntity uploadFileByAdmin(AdminPojo adminPojo) {
        MultipartFile multipartFile=adminPojo.getMultipartFile();
        String fileDir=System.getProperty("user.dir")+ File.separator+"bikash";
        File fileDirectory=new File(fileDir);
        if(!fileDirectory.exists()) {
            boolean mkdir= fileDirectory.mkdir();
        }else {
            log.info("file is already exist!!");
        }
        String filepath=fileDir+File.separator+multipartFile.getOriginalFilename();
        fileDirectory=new File(filepath);
        try(FileOutputStream outputStream=new FileOutputStream(fileDirectory))
        {
            outputStream.write(multipartFile.getBytes());
            return ResponseEntity.ok(new GlobleApiResponse(true,"file successfully upload",filepath));
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(new GlobleApiResponse(false,"file not successfully uploaded",null));
        }

    }
}
