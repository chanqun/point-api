package com.kakao.test.controller.dto

import com.kakao.test.entity.Membership

class MembershipDeleteRes(
    val success: Boolean = true,
    val response: Boolean = true,
    val error: Boolean = false
)