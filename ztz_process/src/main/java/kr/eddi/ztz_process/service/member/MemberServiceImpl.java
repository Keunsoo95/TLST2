package kr.eddi.ztz_process.service.member;

import kr.eddi.ztz_process.entity.member.Authentication;
import kr.eddi.ztz_process.entity.member.BasicAuthentication;
import kr.eddi.ztz_process.entity.member.Member;
import kr.eddi.ztz_process.repository.member.AuthenticationRepository;
import kr.eddi.ztz_process.repository.member.MemberRepository;
import kr.eddi.ztz_process.service.member.request.MemberRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;


    @Override
    public Boolean emailValidation(String email) {
        Optional<Member> maybeMember = memberRepository.findByEmail(email);
        if (maybeMember.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean signUp(MemberRegisterRequest request) {
        final Member member = request.toMember();
        memberRepository.save(member);

        final BasicAuthentication auth = new BasicAuthentication(member,
                Authentication.BASIC_AUTH, request.getPassword());

        authenticationRepository.save(auth);

        return true;
    }
}
