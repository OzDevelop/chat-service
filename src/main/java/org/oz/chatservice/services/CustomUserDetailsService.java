package org.oz.chatservice.services;

import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oz.chatservice.dtos.MemberDto;
import org.oz.chatservice.entities.Member;
import org.oz.chatservice.enums.Role;
import org.oz.chatservice.repositories.MemberRepository;
import org.oz.chatservice.vos.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username).get();
        if (Role.fromCode(member.getRole()) != Role.CONSULTANT) {
            throw new UsernameNotFoundException("상담사가 아닙니다.");
        }
        return new CustomUserDetails(member);
    }

    // 스프링 시큐리티를 통해서 멤버룰 제어할 때, 패스워드는 패스워드 인코더라는 클래스를 통해 인코딩이 된 값이 들ㅇ어가야 함.
    // 스프링 시큐리티에서 무조건 디코딩을 진행하기 떄문ㅇ.  (SecurityConfiguration에 passwordincoder 추가)
    public MemberDto saveMember(MemberDto memberDto) {
        Member member = MemberDto.to(memberDto);
        member.updatePassword(memberDto.password(), memberDto.confirmedPassword(), passwordEncoder);

        member = memberRepository.save(member);

        return MemberDto.from(member);
    }

}
