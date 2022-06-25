package com.point.test.controller.dto

import com.point.test.entity.Membership

class MembershipsRes(
    val success: Boolean = true,
    val response: List<Membership>,
    val error: Boolean = false
)

class MembershipRes(
    val success: Boolean = true,
    val response: Membership,
    val error: Boolean = false
)
