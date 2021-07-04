package com.kakao.test.controller

import MembershipPointReq
import com.fasterxml.jackson.databind.ObjectMapper
import com.kakao.test.controller.dto.MembershipCreateReq
import com.kakao.test.controller.dto.MembershipCreateRes
import com.kakao.test.repository.MembershipRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
internal class MembershipControllerTest @Autowired constructor(
    private val membershipRepository: MembershipRepository
) {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `멤버십 정상 등록`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.error").value(false))
            .andExpect(jsonPath("$.response.membershipId").value("cj"))
            .andExpect(jsonPath("$.response.membershipStatus").value("Y"))
    }

    @Test
    fun `멤버십 등록 실패 - x-user-id 없음 400`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        mockMvc
            .perform(
                post("/api/v1/membership")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(400))
    }

    @Test
    fun `멤버십 잘못된 정보 요청 - 빈 id 400 에러`() {
        val reqObject = MembershipCreateReq("", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(400))
    }

    @Test
    fun `멤버십 잘못된 정보 요청 - 빈 name 400 에러`() {
        val reqObject = MembershipCreateReq("cj", "", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(400))
    }

    @Test
    fun `멤버십 잘못된 정보 요청 - 음수 400 에러`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", -1)
        val req: String = objectMapper.writeValueAsString(reqObject)

        mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(400))
    }

    @Test
    fun `멤버십 전체조회 API 정상 동작`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().isOk)

        mockMvc
            .perform(get("/api/v1/membership").header("X-USER-ID", "test1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.response[0].membershipId").value("cj"))
            .andExpect(jsonPath("$.error").value(false))
    }

    @Test
    fun `x-user-id 없을 때 멤버십 전체조회 - 400 에러`() {
        mockMvc
            .perform(get("/api/v1/membership"))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(400))
    }

    @Test
    fun `userId, membershipId로 삭제 API - 성공`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        val result = mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().isOk)
            .andReturn()

        val res = objectMapper.readValue(result.response.contentAsString, MembershipCreateRes::class.java)
        val membershipId = res.response.membershipId

        mockMvc
            .perform(delete("/api/v1/membership/${membershipId}").header("X-USER-ID", "test1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.response").value(true))
            .andExpect(jsonPath("$.error").value(false))
    }

    @Test
    fun `userId, membershipId로 삭제 실패 - 해당 멤버십 없음 500`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        val result = mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().isOk)
            .andReturn()

        val res = objectMapper.readValue(result.response.contentAsString, MembershipCreateRes::class.java)
        val membershipId = res.response.membershipId

        mockMvc
            .perform(delete("/api/v1/membership/${membershipId}").header("X-USER-ID", "test2"))
            .andExpect(status().is5xxServerError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(500))
    }

    @Test
    fun `userId, membershipId로 멤버십 조회 API - 성공`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        val result = mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().isOk)
            .andReturn()

        val res = objectMapper.readValue(result.response.contentAsString, MembershipCreateRes::class.java)
        val membershipId = res.response.membershipId

        mockMvc
            .perform(get("/api/v1/membership/${membershipId}").header("X-USER-ID", "test1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.response.membershipName").value("happypoint"))
            .andExpect(jsonPath("$.error").value(false))
    }

    @Test
    fun `userId, membershipId로 멤버십 조회 API - 실패 아이디 틀림`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        val result = mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().isOk)
            .andReturn()

        val res = objectMapper.readValue(result.response.contentAsString, MembershipCreateRes::class.java)
        val membershipId = res.response.membershipId

        mockMvc
            .perform(get("/api/v1/membership/${membershipId}").header("X-USER-ID", "test2"))
            .andExpect(status().is5xxServerError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(500))
    }

    @Test
    fun `멤버십 포인트 등록 성공`() {
        val reqObject = MembershipCreateReq("cj", "happypoint", 20)
        val req: String = objectMapper.writeValueAsString(reqObject)

        mockMvc
            .perform(
                post("/api/v1/membership").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(req)
            )
            .andExpect(status().isOk)
            .andReturn()

        val pointReq = objectMapper.writeValueAsString(MembershipPointReq(reqObject.membershipId, 400))

        mockMvc
            .perform(
                put("/api/v1/membership/point").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(pointReq)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.response").value(true))
            .andExpect(jsonPath("$.error").value(false))

        val membership =
            membershipRepository.findByUserIdAndMembershipId("test1", reqObject.membershipId)

        assertThat(membership!!.point).isEqualTo(24)
    }

    @Test
    fun `멤버십 포인트 등록 실패 - x-user-id 없음 400`() {
        val pointReq = objectMapper.writeValueAsString(MembershipPointReq("cj", 350))

        mockMvc
            .perform(
                put("/api/v1/membership/point")
                    .contentType(APPLICATION_JSON).content(pointReq)
            )
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(400))
    }

    @Test
    fun `멤버십 포인트 등록 실패 - 요청값 validation 오류 400`() {
        val pointReq = objectMapper.writeValueAsString(MembershipPointReq("cj", -1))

        mockMvc
            .perform(
                put("/api/v1/membership/point").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(pointReq)
            )
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(400))

        val pointReq2 = objectMapper.writeValueAsString(MembershipPointReq("", 100))

        mockMvc
            .perform(
                put("/api/v1/membership/point").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(pointReq2)
            )
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(400))
    }

    @Test
    fun `멤버십 포인트 등록 실패 - 해당 멤버십 없음 500`() {
        val pointReq = objectMapper.writeValueAsString(MembershipPointReq("cj", 350))

        mockMvc
            .perform(
                put("/api/v1/membership/point").header("X-USER-ID", "test1")
                    .contentType(APPLICATION_JSON).content(pointReq)
            )
            .andExpect(status().is5xxServerError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.response").isEmpty)
            .andExpect(jsonPath("$.error.status").value(500))
    }

}
