package com.kakao.test.repository

import com.kakao.test.entity.Membership
import org.springframework.data.jpa.repository.JpaRepository

interface MembershipRepository : JpaRepository<Membership, Long> {
    fun findByMembershipId(membershipId: String): Membership?

    fun findByUserId(userId: String): List<Membership>

    fun findByUserIdAndMembershipId(userId: String, membershipId: String): Membership?
}