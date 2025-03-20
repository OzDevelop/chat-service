package org.oz.chatservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oz.chatservice.enums.Gender;

/**
 * Entity 는 JPA 관점에서 테이블에 대응하는 클래스들을 의미
 * 이를 통해 테이블에 매핑되고, 테이블의 데이터를 조회, 저장, 삭제 등을 하기 위해선 repository가 필요함.
 *
 * @Entity 어노테이션이 붙은 클래스는 JPA가 관리하는 클래스로 인식, Id 속성이 필수임.
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Id
    Long id;

    String email;
    String nickName;
    String name;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String phoneNumber;
    LocalDate birthday;
    String role;




}
