package com.kakao.test.service

import com.kakao.test.entity.Membership
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = NONE)
@Transactional
class MembershipServiceTest @Autowired constructor(
    private val membershipService: MembershipService
) {

    @Test
    fun `Membership create service 정상 등록`() {
        val userId = "test1"
        val membership = Membership(null, "cj", userId, "cjone", 5210)

        val createMember = membershipService.create(membership)

        assertThat(createMember.userId).isEqualTo(userId)
        assertThat(createMember.startDate).isNotNull
        assertThat(createMember.seq).isNotNull
        assertThat(createMember.membershipName).isEqualTo(membership.membershipName)
    }
}