package com.point.test.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.math.roundToInt

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

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone="Asia/Seoul")
    var startDate: LocalDateTime? = null

) {
    fun create() {
        this.startDate = LocalDateTime.now()
        this.membershipStatus = MembershipStatus.Y
    }

    fun disable() {
        this.membershipStatus = MembershipStatus.N
    }

    fun addPoint(amount: Int) {
        this.point = this.point?.plus((amount * 0.01).roundToInt())
    }
}
