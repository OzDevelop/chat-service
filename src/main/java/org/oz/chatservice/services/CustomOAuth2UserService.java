package org.oz.chatservice.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.oz.chatservice.entities.Member;
import org.oz.chatservice.enums.Gender;
import org.oz.chatservice.repositories.MemberRepository;
import org.oz.chatservice.vos.CustomOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 카카오톡으로부터 가져온 정보로 (attributeMap)
//        Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
        String email = oAuth2User.getAttribute("email");
        // email로 디비를  조회 , 사용자가 없으면 맴버 entity를 만들고 ( registerMember(attributeMap))
        Member member = memberRepository.findByEmail(email).orElseGet(() -> {
            Member newMember = MemberFactory.create(userRequest, oAuth2User);

            return memberRepository.save(newMember);
        });

        // 회원가입을 시키는 코드
        return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }

}
