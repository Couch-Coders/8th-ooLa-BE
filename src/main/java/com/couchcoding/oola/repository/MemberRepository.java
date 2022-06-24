package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.validation.MemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>,MemberRepositoryCustom {

    Optional<Member> findByUid(String uid);

}
