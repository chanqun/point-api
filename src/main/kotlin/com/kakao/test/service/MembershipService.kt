package com.kakao.test.service

import com.kakao.test.entity.Membership
import com.kakao.test.repository.MembershipRepository
import org.springframework.stereotype.Service

interface MembershipService{

    /**
     *  membership을 등록
     */
    fun create(membership: Membership) : Membership
}

@Service
class MembershipServiceImpl(
    private val membershipRepository: MembershipRepository
) : MembershipService {

    override fun create(membership: Membership): Membership {
        membership.create()

        return membershipRepository.save(membership)
    }

}