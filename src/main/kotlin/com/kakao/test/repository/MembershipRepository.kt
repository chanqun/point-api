package com.kakao.test.repository

import com.kakao.test.entity.Membership
import org.springframework.data.jpa.repository.JpaRepository

interface MembershipRepository : JpaRepository<Membership, Long> {
    fun findById(id: String): Membership?
}