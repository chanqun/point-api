package com.kakao.test.service

import com.kakao.test.entity.Membership
import com.kakao.test.exception.NotExistMembershipException
import com.kakao.test.repository.MembershipRepository
import org.springframework.stereotype.Service

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
    fun findMemberships(userId: String): List<Membership>

    /**
     * userId와 membershipId가
     * 일치하는 membership을 비활성화
     *
     * 해당하는 membership이 없을 때
     * @exception NotExistMembershipException
     */
    fun disableMembership(userId: String, membershipId: String)

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
     * 해당하는 membership이 없을 때
     * @exception NotExistMembershipException
     */
    fun addPoint(userId: String, membershipId: String, point: Int)
}

@Service
class MembershipServiceImpl(
    private val membershipRepository: MembershipRepository
) : MembershipService {

    override fun create(membership: Membership): Membership {
        membership.create()

        return membershipRepository.save(membership)
    }

    override fun findMemberships(userId: String): List<Membership> {
        return membershipRepository.findByUserId(userId)
    }

    override fun disableMembership(userId: String, membershipId: String) {
        val membership = membershipRepository.findByUserIdAndMembershipId(userId, membershipId)
            ?: throw NotExistMembershipException()

        membership.disable()
    }

    override fun findMembership(userId: String, membershipId: String): Membership {
        return membershipRepository.findByUserIdAndMembershipId(userId, membershipId)
            ?: throw NotExistMembershipException()
    }

    override fun addPoint(userId: String, membershipId: String, point: Int) {
        membershipRepository.findByUserIdAndMembershipId(userId, membershipId)
            ?.addPoint(point) ?: throw NotExistMembershipException()
    }
}