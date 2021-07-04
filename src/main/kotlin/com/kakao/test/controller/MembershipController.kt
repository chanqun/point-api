package com.kakao.test.controller

import MembershipPointReq
import MembershipPointRes
import com.kakao.test.controller.dto.*
import com.kakao.test.exception.NoUserIdException
import com.kakao.test.service.MembershipService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/membership")
class MembershipController @Autowired constructor(
    private val membershipService: MembershipService
) {

    @GetMapping
    fun membershipList(request: HttpServletRequest): MembershipsRes {
        val userId = getUserId(request)

        val memberships = membershipService.findMemberships(userId)

        return MembershipsRes(response = memberships)
    }

    @PostMapping
    fun createMembership(
        request: HttpServletRequest, @RequestBody @Valid req: MembershipCreateReq
    ): MembershipCreateRes {
        val userId = getUserId(request)

        val createMembership = membershipService.create(req.toMembership(userId))

        return MembershipCreateRes(response = createMembership)
    }

    @DeleteMapping("/{membershipId}")
    fun deleteMembership(
        request: HttpServletRequest, @PathVariable membershipId: String
    ): MembershipDeleteRes {
        val userId = getUserId(request)

        membershipService.disableMembership(userId, membershipId)

        return MembershipDeleteRes()
    }

    @GetMapping("/{membershipId}")
    fun findMembership(
        request: HttpServletRequest, @PathVariable membershipId: String
    ): MembershipRes {
        val userId = getUserId(request)

        val membership = membershipService.findMembership(userId, membershipId)

        return MembershipRes(response = membership)
    }

    @PutMapping("/point")
    fun addMembershipPoint(
        request: HttpServletRequest, @RequestBody @Valid req: MembershipPointReq
    ): MembershipPointRes {
        val userId = getUserId(request)

        membershipService.addPoint(userId, req.membershipId, req.point)

        return MembershipPointRes()
    }

    private fun getUserId(request: HttpServletRequest): String {
        return request.getHeader("X-USER-ID") ?: throw NoUserIdException()
    }
}