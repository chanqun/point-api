package com.point.test.service

import com.point.test.entity.Membership
import com.point.test.exception.NotExistMembershipException
import com.point.test.repository.MembershipRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface MembershipService {

    /**
     *  membership을 등록
     */
    fun create(membership: Membership): Membership

    /**
     * userId에 해당하는
     * membership 목록을 가져옴
     * 없으면 size 0인 리스트를 반환
     */
    fun findBy(userId: String): List<Membership>

    /**
     * userId와 membershipId가
     * 일치하는 membership을 비활성화
     *
     * 해당하는 membership이 없을 때
     * @exception NotExistMembershipException
     */
    fun disable(userId: String, membershipId: String)

    /**
     * userId와 membershipId가
     * 일치하는 membership 조회
     * 해당하는 membership이 없을 때
     * @exception NotExistMembershipException
     */
    fun findMembership(userId: String, membershipId: String): Membership

    /**
     * userId와 membershipId가
     * 일치하는 membership 조회해서 포인트 추가
     * amount 에 1% 적립
     * 해당하는 membership이 없을 때
     * @exception NotExistMembershipException
     */
    fun addPoint(userId: String, membershipId: String, amount: Int)
}

@Service
@Transactional
class MembershipServiceImpl(
    private val membershipRepository: MembershipRepository
) : MembershipService {

    override fun create(membership: Membership): Membership {
        membership.create()

        return membershipRepository.save(membership)
    }

    override fun findBy(userId: String): List<Membership> {
        return membershipRepository.findByUserId(userId)
    }

    override fun disable(userId: String, membershipId: String) {
        val membership = membershipRepository.findByUserIdAndMembershipId(userId, membershipId)
            ?: throw NotExistMembershipException()

        membership.disable()
    }

    override fun findMembership(userId: String, membershipId: String): Membership {
        return membershipRepository.findByUserIdAndMembershipId(userId, membershipId)
            ?: throw NotExistMembershipException()
    }

    override fun addPoint(userId: String, membershipId: String, amount: Int) {
        membershipRepository.findByUserIdAndMembershipId(userId, membershipId)
            ?.addPoint(amount) ?: throw NotExistMembershipException()
    }
}