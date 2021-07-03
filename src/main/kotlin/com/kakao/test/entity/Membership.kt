package com.kakao.test.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Membership(
    @Id
    var id: String,

    var userId: String,

    var name: String,

    var point: Int,

    var status: MembershipStatus,

    var startDate: LocalDateTime
)