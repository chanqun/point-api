package com.kakao.test.service

import com.kakao.test.entity.Membership
import com.kakao.test.entity.MembershipStatus
import com.kakao.test.exception.NotExistMembershipException
import com.kakao.test.repository.MembershipRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest(webEnvironment = NONE)
@Transactional
class MembershipServiceImplTest @Autowired constructor(
    private val membershipService: MembershipService,
    private val membershipRepository: MembershipRepository,
    private val em: EntityManager
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

    @Test
    fun `Membership list 정상 조회 확인`() {
        val userId = "test1"
        val membership = Membership(null, "cj", userId, "cjone", 5210)
        val membership2 = Membership(null, "shinsegae", userId, "shinsegaepoint", 3500)

        membershipService.create(membership)
        membershipService.create(membership2)

        val memberships = membershipService.findMemberships(userId)

        assertThat(memberships.size).isEqualTo(2)
    }

    @Test
    fun `Membership list 없을 때 빈 리스트 반환`() {
        val userId = "test1"

        val memberships = membershipService.findMemberships(userId)

        assertThat(memberships.size).isEqualTo(0)
    }

    @Test
    fun `disable membership 성공 - status N으로 바뀜`() {
        val userId = "test1"
        val membership = Membership(null, "cj", userId, "cjone", 5210)

        membershipService.create(membership)

        membershipService.disableMembership(userId, membership.membershipId!!)

        em.flush()
        em.clear()

        val findById = membershipRepository.findById(membership.seq!!).get()

        assertThat(findById.membershipStatus).isEqualTo(MembershipStatus.N)
    }

    @Test
    fun `disable membership 실패 - 해당 멤버십 없음 NotExistMembershipException`() {
        val userId = "test1"
        val membershipId = "cj"

        assertThatThrownBy { membershipService.disableMembership(userId, membershipId) }
            .isInstanceOf(NotExistMembershipException::class.java)
    }
}