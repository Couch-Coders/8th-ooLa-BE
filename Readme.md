# 온라인 스터디 모집 플랫폼 - OOLa

## 💡 배포 주소 [OOLa](https://studyoola.herokuapp.com/)

## 📚 프로젝트 기획 의도
### 개발자들이 온라인을 통해 스터디를 모집하고 효율적인 운영을 돕기 위한 프로젝트

## 📌 핵심 기능

### 1. 온라인 스터디 모임 만들기!

- 스터디의 분야, 일정 및 시간과 모집 인원등의 스터디의 정보 입력하여 스터디 등록 및 모집 시작

### 2. 스터디 타입, 스터디 일정 및 시간으로 필터링!

- 필터링을 통해 자신의 조건에 맞는 스터디를 빠르게 검색

### 3. 관심 스터디 저장하기!

- 하트버튼을 눌러서 관심있는 스터디 저장
- 저장된 스터디는 마이스터디 페이지에서 확인 및 효율적인 스터디 신청

### 4. 스터디멤버 확인하기!

- 스터디 멤버의 테크 스택과 간단한 자기소개, 깃과 블로그 주소 기반 스터디 멤버 확인

### 5. 스터디 멤버와 공유하고 싶은 참조자료 포스팅하기!

- 참여하고 있는 스터디의 공유로그 탭에 외부 참조자료의 URL과 간단한 소개 포스팅


## 👨‍💻 팀원 소개

**FrontEnd**

- 황유진 , 이미미

**Backend**

- 최미영

## ⏰ 프로젝트 개발 기간

2022.05.09 ~ 2022.05.19 프로젝트 기획

2022.05.19 ~ 2022.06.27 프로젝트 개발

## 🛠️ 개발 환경

**FrontEnd**

<p>
    <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=white"/>
    <img src="https://img.shields.io/badge/react-61DAFB?style=flat-square&logo=react&logoColor=white"/>
    <img src="https://img.shields.io/badge/React Router-CA4245?style=flat-square&logo=React Router&logoColor=white"/>
    <img src="https://img.shields.io/badge/styled-components-DB7093?style=flat-square&logo=styled-components&logoColor=white"/>
    <img src="https://img.shields.io/badge/Ant Design -0170FE?style=flat-square&logo=Ant Design&logoColor=white"/>
    <img src="https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=Firebase&logoColor=white"/>
</p>

**Backend**

<p>
 <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Boot -6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white"/>
  <img src="https://img.shields.io/badge/Data JPA-6DB33F?style=flat-square&logo=&logoColor=white"/>
  <img src="https://img.shields.io/badge/Query DSL-0769AD?style=flat-square&logo=&logoColor=white"/>
  <img src="https://img.shields.io/badge/PostgreSQL -4479A1?style=flat-square&logo=PostgreSQL&logoColor=white"/>
  <img src="https://img.shields.io/badge/Oauth-4285F4?style=flat-square&logo=Google&logoColor=white"/>
</p>
 

**Infra**
<p>
 <img src="https://img.shields.io/badge/Heroku -4479A1?style=flat-square&logo=Heroku&logoColor=white"/>
<img src="https://img.shields.io/badge/Github Actions-4285F4?style=flat-square&logo=Google&logoColor=white"/>
</p>
 

## ⚙️ 시스템 아키텍처

![시스템 아키텍처](https://user-images.githubusercontent.com/42866800/176840686-f0665ef6-b7e9-4dac-969d-108e065b7c12.png)

## ⚙ ERD 설계

![온라인 스터디 모집 플랫폼 ERD](https://user-images.githubusercontent.com/42866800/176939980-746476bb-b5cf-434c-b376-1db8114e56b7.png)

## 🛠 기획 및 설계

[기능 명세서](https://www.notion.so/ooLa-1389c563c730413583f7b612d9235bee)

[UI 기획서](https://whimsical.com/project-oola-TNbwpCqE3crQ1BD3k5pTKn)

[Figma 디자인](https://www.figma.com/file/MvD49HcDMRc3kuGUIwezXx/project-ooLa?node-id=0%3A1)

[DB ERD 명세서](https://www.notion.so/ERD-21da4ff688b044578199a3675014ec0f)

[API 명세서](https://unique-wandflower-4cc.notion.site/ooLa-API-16f4146dab7946eb8770ed6804d122d3)

## 📂 프론트 깃 레포지터리

[https://github.com/Couch-Coders/8th-ooLa-fe](https://github.com/Couch-Coders/8th-ooLa-fe)

## ✔️프로젝트 이슈

### 백에서 배열을 JSON 파싱하여 보냈는데 자바스크립트에서 인식하지 못하는 문제

- 원인 : 백에서 ArrayList를 파싱할때 toString()을 사용했더니 제대로 파싱되지 못해 프론트에서 배열로 인식하지 못하는 문제 발생
- 해결과정 : 
- ArrayList를 문자열화 하여 파싱하지 않고 구조를 변경하여 ArrayList 자체를 DTO에 담아 JSON화 시키는 방향으로 코드 수정
- tech_stack ArrayList 관련 문제였는데 이를 @ElementCollection 어노테이션을 사용하여 Member 엔티티를 기준으로 관리되도록 수정
- MemberTechStack 이라는 새로운 값 타입을 직접 정의 하여 사용하기 위해 @Embeddable 어노테이션을 붙임

### 구글 로그인시 JwtFilter 인증객체 생성하는 부분에서 null이 발생하여 403 예외 발생

- 원인 : 구글 로그인시 헤더에서 토큰을 꺼내 검증할때 토큰의 형식이 올바르지 않음
- 우리 사이트에 로그인시 백으로 전달해주는 토큰과 구글 로그인시 반환받은 토큰 형식이 일치하지 않아 구글 로그인후 필터를 통해 회원 조회시 "인증되지 않은 회원이입니다"라는 403 예외가 발생
- 해결과정 : 
- 프론트에서 토큰을 백으로 보낼때 json 객체로 파싱하지 않도록 수정
- 백에서는 구글 로그인시 전달받은 토큰을 사용하여 우리쪽 회원인지 검증할때 데이터베이스에 일치하는 회원 정보가 없는 경우 “존재하지 않는 회원입니다” 라는 예외를 띄워 회원가입을 유도하도록 코드 수정

## 🔖 레퍼런스

[[JPA] 임베디드 타입(embedded type)](https://velog.io/@conatuseus/JPA-%EC%9E%84%EB%B2%A0%EB%94%94%EB%93%9C-%ED%83%80%EC%9E%85embedded-type-8ak3ygq8wo)

[JPA @ElementCollection](https://prohannah.tistory.com/133)

[https://insanelysimple.tistory.com/m/350](https://insanelysimple.tistory.com/m/350)

[https://unique-wandflower-4cc.notion.site/OAuth-2ec4394c64df48b4b0719386632011a8](https://www.notion.so/OAuth-2ec4394c64df48b4b0719386632011a8)