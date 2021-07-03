package com.kakao.test.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakao.test.controller.dto.MembershipCreateReq
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
internal class MembershipControllerTest {
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
}
