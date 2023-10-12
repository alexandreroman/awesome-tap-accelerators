package com.vmware.tanzu.tap.accelerators.springboot.contributors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface ContributorRepository extends JpaRepository<Contributor, String> {
    @Query("select c from Contributor c order by c.login")
    List<Contributor> findContributors();
}
