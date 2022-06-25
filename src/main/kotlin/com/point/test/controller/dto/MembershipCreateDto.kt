package com.point.test.controller.dto

import com.point.test.entity.Membership
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class MembershipCreateReq(
    @field: NotBlank
    val membershipId: String,

    @field: NotBlank
    val membershipName: String,

    @field: Min(value = 0)
    val point: Int
) {
    fun toMembership(userId: String): Membership {
        return Membership(null, membershipId, userId, membershipName, point)
    }
}

class MembershipCreateRes(
    val success: Boolean = true,
    val response: Membership,
    val error: Boolean = false
)