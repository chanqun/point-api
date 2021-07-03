package com.kakao.test.controller

import com.kakao.test.controller.dto.MembershipCreateReq
import com.kakao.test.controller.dto.MembershipCreateRes
import com.kakao.test.exception.NoUserIdException
import com.kakao.test.service.MembershipService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class MembershipController @Autowired constructor(
    private val membershipService: MembershipService
) {

    @PostMapping("/membership")
    fun createMembership(
        request: HttpServletRequest,
        @RequestBody @Valid req: MembershipCreateReq
    ): MembershipCreateRes {
        val userId = getUserId(request)

        val createMembership = membershipService.create(req.toMembership(userId))

        return MembershipCreateRes(response = createMembership)
    }

    private fun getUserId(request: HttpServletRequest): String {
        return request.getHeader("X-USER-ID") ?: throw NoUserIdException()
    }
}