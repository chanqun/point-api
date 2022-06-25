package com.point.test.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MembershipTest {

    @Test
    fun `point 적립 테스트`() {
        val membership =
            Membership(1L, "cj", "teat1", "happypoint", 120, MembershipStatus.Y, LocalDateTime.now())

        membership.addPoint(200)

        assertThat(membership.point).isEqualTo(122)
    }
}