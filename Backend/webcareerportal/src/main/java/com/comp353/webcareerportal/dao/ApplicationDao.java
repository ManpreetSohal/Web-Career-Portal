package com.comp353.webcareerportal.dao;

import com.comp353.webcareerportal.models.Application;
import com.comp353.webcareerportal.models.Employer;
import com.comp353.webcareerportal.models.Job;
import com.comp353.webcareerportal.models.JobSeeker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import javax.transaction.Transactional;

public interface ApplicationDao extends JpaRepository<Application, Long> {

	@Query("select case when COUNT (a.applicationId) > 0 then true else false end from application a where a.applicationId = :application_id")
    boolean applicationExistsWithId(@Value("applicationId") int application_id);
	
	@Transactional
    @Modifying
    @Query("delete from application a where a.applicationId= :application_id")
    void deleteApplicationWithApplicationId(@Value("applicationId") int application_id);
	
	@Transactional
    @Modifying
    @Query("delete from application a where a.job= :job")
    void deleteApplicationWithJob(@Value("job") Job job);
	
	@Query(nativeQuery = true, value = "select a.applicationId from application a where a.jobseeker_email= :jobSeeker group by a.applicationId")
    List<Integer> getApplicationIdsWithJobSeeker(@Value("jobSeeker_email") JobSeeker jobSeeker);
	
	@Query(nativeQuery = true, value = "select * from application a where a.jobseeker_email= :jobSeeker group by a.applicationId")
    List<Application> getApplicationsWithJobSeeker(@Value("jobSeeker_email") JobSeeker jobSeeker);
}
