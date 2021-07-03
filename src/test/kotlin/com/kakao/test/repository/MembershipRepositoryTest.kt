package com.kakao.test.repository

import com.kakao.test.entity.Membership
import com.kakao.test.entity.MembershipStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class MembershipRepositoryTest @Autowired constructor(
    private val membershipRepository: MembershipRepository
) {

    @Test
    fun `Membership 엔티티 등록 테스트`() {
        val membership = Membership(null, "cj", "test1", "happypoint", 120, MembershipStatus.Y, LocalDateTime.now())

        assertThat(membership.seq).isNull()

        membershipRepository.save(membership)

        val findMembership = membershipRepository.findByMembershipId("cj")

        assertThat(membership.seq).isNotNull

        assertThat(membership.userId).isEqualTo(findMembership!!.userId)
        assertThat(membership.membershipName).isEqualTo(findMembership.membershipName)
        assertThat(membership.point).isEqualTo(120)
        assertThat(membership.membershipStatus).isEqualTo(MembershipStatus.Y)
        assertThat(membership.startDate).isEqualTo(findMembership.startDate)
    }
}