package com.project.resuming.resume.domain.repository;

import com.project.resuming.member.domain.Member;
import com.project.resuming.resume.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByMember(Member member);

}
