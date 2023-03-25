package hello.board.controller.member;

import hello.board.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
@DisplayName("멤버 로그인/회원가입 테스트")
@MockBean(JpaMetamodelMappingContext.class)
class LoginControllerTest {

    private MockMvc mvc;

    @MockBean
    private MemberService memberService;
//
//    @BeforeEach
//    public void setUp() {
//        mvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService))
//                .addFilters(new CharacterEncodingFilter("UTF-8", true))
//                .build();
//    }
//
//    @Test
//    @DisplayName("회원가입 테스트")
//    void insertMemberTest() throws Exception {
//        //given
//        BDDMockito.given(memberService.joinMember(ArgumentMatchers.any()))
//                .willReturn(
//                        Member.builder()
//                                .id(1L)
//                                .name("장대영")
//                                .age(26)
//                                .loginId("longstick0")
//                                .password("1101")
//                                .role(MemberRole.USER)
//                                .build()
//                );
//
//        //when
//        final ResultActions actions =
//                mvc.perform(
//                        post("/login/join")
//                                .contentType(MediaType.ALL)
//                                .accept(MediaType.ALL)
//                                .characterEncoding("UTF-8")
//                ).andDo(print());
//
//        //then
//        actions
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//    }
}