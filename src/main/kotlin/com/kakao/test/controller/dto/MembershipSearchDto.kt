package com.kakao.test.controller.dto

import com.kakao.test.entity.Membership

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