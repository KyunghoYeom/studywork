package remind.inflearn.member.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import remind.inflearn.member.entity.Member;
import remind.inflearn.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    private Member newM;



    @BeforeEach
    public void setUp(){
        newM = Member.builder().name("mike").build();

    }


    @Test
    public void 회원가입() throws Exception{
        Long joinId = memberService.join(newM);

        Member findMember = memberRepository.findById(joinId).orElseThrow();

        assertThat(newM).isEqualTo(findMember);
        assertThat(newM.getId()).isEqualTo(findMember.getId());



    }

    @Test
    void 중복_회원_예외() throws Exception {
        Long joinId = memberService.join(newM);
        Member sameM = Member.builder().name("mike").build();
        assertThatThrownBy(() -> memberService.join(sameM))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("중복된 회원입니다.");


    }


}