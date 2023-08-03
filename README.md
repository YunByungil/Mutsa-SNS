<details>
<summary>미션 설명</summary>  

</details>

# 📮 멋사SNS
> 여러분들이 많이 사용하고 있는 인스타그램, 페이스북 등을 착안하여 여러분들만의 플랫폼을 만들어보는 미니 프로젝트입니다.  
> 사용자가 게시글 자유롭게 올리고 댓글, 팔로우, 친구, 좋아요 등을 통해 소통하는 SNS 플랫폼의 백엔드를 만들어봅시다.  

## 🎯 멋사SNS ERD

## ⚙️ 기술스택  

Language | Java 17

Framework | Spring 6.0.11 (Spring Boot 3.1.2)

Build Tool | Gradle 8.2.1

Test | Junit 5.9.3, Mockito 5.3.1

Docs | Spring REST Docs 3.0.0

## 💡 API 설계
추가 예정  

## 1️⃣ DAY 1 / 사용자 인증하기  

<details>  
<summary>1일차 기능 요구 사항</summary>

### 기능 요구 사항
> 📝 DAY 1️⃣ **중고 물품 관리** **6/29**
> 1. 사용자 **회원가입**이 가능하다.  
>    - 회원가입에 필수로 필요한 정보는 아이디와 비밀번호 이다.  
>    - 부수적으로 이메일, 전화번호를 기입할 수 있다.  
> 2. 등록된 물품 정보는 누구든지 열람할 수 있다.
>    - 인증 방식은 JWT를 이용한 토큰 인증 방식을 택한다.  
> 3. **로그인** 한 상태에서, 자신을 대표하는 사진, 프로필 사진을 **업로드** 할 수 있다.
</details>   

<details>
<summary>1일차 기능 목록</summary>  

### ✨ 기능 목록
- [ ] Security, Jwt Setting
- [ ] 회원가입
- [ ] 로그인
- [ ] accessToken, refreshToken 
- [ ] 로그인 상태에서 프로필 사진 업로드
</details>

<details>
<summary>1일차 예외 목록</summary>  

### ✨ 예외 목록
- [ ] 회원가입 - 아이디 중복 체크
- [ ] 로그인 - ID, PASSWORD 체크
</details>

<details>
<summary>1일차 테스트 목록</summary>

### ✅ 테스트 목록

#### Service

#### controller

#### Repository

</details>

<hr>