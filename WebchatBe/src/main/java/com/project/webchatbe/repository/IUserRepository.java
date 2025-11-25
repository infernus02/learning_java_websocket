package com.project.webchatbe.repository;

import com.project.webchatbe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("""
select us from User us 
where us.isDelete = false 
and (:keyword is null or us.username = :keyword) 
order by us.createDate desc  
""")
    Page<User> findAll(@Param("keyword") String keyword,
                        Pageable pageable);
}
