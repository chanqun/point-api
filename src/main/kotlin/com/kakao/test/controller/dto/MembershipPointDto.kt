import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class MembershipPointReq(
    @field: NotBlank
    val membershipId: String,

    @field: Min(value = 0)
    val point: Int
)

class MembershipPointRes(
    val success: Boolean = true,
    val response: Boolean = true,
    val error: Boolean = false
)