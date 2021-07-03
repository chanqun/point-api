package com.kakao.test.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Membership(

    @Id @GeneratedValue
    var seq: Long? = null,

    var membershipId: String? = null,

    var userId: String? = null,

    var membershipName: String? = null,

    var point: Int? = null,

    @Enumerated(EnumType.STRING)
    var membershipStatus: MembershipStatus? = null,

    var startDate: LocalDateTime? = null

) {
    fun create() {
        this.startDate = LocalDateTime.now()
        this.membershipStatus = MembershipStatus.Y
    }


}
