package com.adminuserdetails.adminuserdetails.repo.adminRepo;

import com.adminuserdetails.adminuserdetails.model.admin.AdminDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AdminRepo extends JpaRepository<AdminDetails,Integer> {

    @Query(value="select * from admin_details ad order by ad.id",nativeQuery = true)
    Page<Map<String,Object>> findAllDetailsByPagination(Pageable pageable);

}
