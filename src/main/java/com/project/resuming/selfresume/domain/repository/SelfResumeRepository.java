package com.project.resuming.selfresume.domain.repository;

import com.project.resuming.member.domain.Member;
import com.project.resuming.selfresume.domain.SelfResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelfResumeRepository extends JpaRepository<SelfResume, Long> {

    List<SelfResume> findByMember(Member member);

}
