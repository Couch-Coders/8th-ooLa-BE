package com.couchcoding.oola.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.ElementCollection;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String uid;

    @Column(name = "display_name")
    @NotBlank(message = "displayName은 필수 값입니다")
    private String displayName;

    @Column(name = "email")
    @NotBlank(message = "email은 필수 값입니다")
    private String email;

    @Column(name = "blog_url")
    private String blogUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "photo_url")
    @NotBlank(message = "photoUrl은 필수 값입니다")
    private String photoUrl;

    @Column(name = "nick_name")
    @NotBlank(message = "nickName은 필수 값입니다")
    private String nickName;

    @Column(name = "introduce")
    @NotBlank(message = "introduce는 필수 값입니다")
    private String introduce;

    @Column(name = "tech_stack")
    @NotNull(message = "기술스택은 필수 값 입니다")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> techStack = new ArrayList<>();

    @Builder
    public Member(Long id, String uid, @NotBlank(message = "displayName은 필수 값입니다") String displayName, @NotBlank(message = "email은 필수 값입니다") String email, String blogUrl, String githubUrl, @NotBlank(message = "photoUrl은 필수 값입니다") String photoUrl, @NotBlank(message = "nickName은 필수 값입니다") String nickName, @NotBlank(message = "introduce는 필수 값입니다") String introduce, @NotNull(message = "기술스택은 필수 값 입니다") List<String> techStack) {
        this.id = id;
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.blogUrl = blogUrl;
        this.githubUrl = githubUrl;
        this.photoUrl = photoUrl;
        this.nickName = nickName;
        this.introduce = introduce;
        this.techStack = techStack;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Member profileUpdate(String uid, Long id, MemberSaveRequestDto memberSaveRequestDto) {
        this.id = id;
        this.techStack = memberSaveRequestDto.getTechStack();
        this.displayName = memberSaveRequestDto.getDisplayName();
        this.nickName = memberSaveRequestDto.getNickName();
        this.blogUrl  = memberSaveRequestDto.getBlogUrl();
        this.githubUrl = memberSaveRequestDto.getGithubUrl();
        this.introduce = memberSaveRequestDto.getIntroduce();
        this.email = memberSaveRequestDto.getEmail();
        this.photoUrl = memberSaveRequestDto.getPhotoUrl();
        return this;
    }
}