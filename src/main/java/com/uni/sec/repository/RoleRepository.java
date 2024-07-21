package com.uni.sec.repository;

import com.uni.sec.model.ERole;
import com.uni.sec.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(ERole role);
}
