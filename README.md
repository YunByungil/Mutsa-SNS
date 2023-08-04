# 📮 멋사SNS
> 여러분들이 많이 사용하고 있는 인스타그램, 페이스북 등을 착안하여 여러분들만의 플랫폼을 만들어보는 미니 프로젝트입니다.  
> 사용자가 게시글 자유롭게 올리고 댓글, 팔로우, 친구, 좋아요 등을 통해 소통하는 SNS 플랫폼의 백엔드를 만들어봅시다.  
   
## ⭐ locatest를 위해 꼭 Redis를 실행해야됩니다.  

### 설치  
#### docker pull redis

### 실행
#### docker run --name some-redis -p 6379:6379 -d redis

### Redis-cli 접속
#### docker exec -it some-redis redis-cli

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
> 📝 DAY 1️⃣ **사용자 인증** **8/3**
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
- [x] Security, Jwt Setting
- [x] 회원가입
- [x] 로그인
- [x] accessToken, refreshToken 
- [x] 로그인 상태에서 프로필 사진 업로드
</details>

<details>
<summary>1일차 예외 목록</summary>  

### ✨ 예외 목록
- [x] 회원가입 - 아이디 중복 체크
- [x] 로그인 - ID, PASSWORD 체크
</details>

<details>
<summary>1일차 테스트 목록</summary>

### ✅ 테스트 목록

#### Service
- [x] 회원가입#createUser()
- [x] 아이디 중복검사#validateDuplicateUsername()

#### controller
- [x] 회원가입

#### Repository
- [x] 아이디로 UserEntity 찾기#findByUsername()


</details>

<hr>

## 2️⃣ DAY 2 / 피드 구현하기

<details>  
<summary>2일차 기능 요구 사항</summary>

### 기능 요구 사항
> 💡 **피드란?**  
> 사용자가 나누고 싶은 이야기를 작성하는, 사용자 개인의 공간을 의미합니다.  
>   
> 📝 DAY 2️⃣ **피드 구현** **8/4**
> 1. 피드는 제목과 내용을 붙일 수 있다.
>   - 피드에는 복수의 이미지를 넣을 수 있다.
> 2. 피드를 작성하고자 한다면 로그인 된 상태여야 한다.
>   - 사용자가 피드를 작성하면, 특별한 설정 없이 자신이 작성한 피드로 등록된다.
> 3. 피드는 작성한 사용자 기준으로, 목록 형태의 조회가 가능하다.  
>   - 조회를 위해 대상 사용자의 정보가 제공되어야 한다.
>   - 피드 목록 조회시, 작성자 아이디, 제목과 대표 이미지에 관한 정보가 포함되어야 한다.
>   - 이때 대표 이미지란 피드에 등록된 첫번째 이미지를 의미한다.
>   - 만약 피드에 등록된 이미지가 없다면, 지정된 기본 이미지를 보여준다.
> 4. 피드는 단독 조회가 가능하다.
>   - 피드 단독 조회시, 피드에 연관된 모든 정보가 포함되어야 한다. 이는 등록된 모든 이미지를 확인할 수 있는 각각의 URL과, 댓글 목록, 좋아요의 숫자를 포함한다.
>   - 피드를 단독 조회할 시, 로그인이 된 상태여야 한다.
> 5. 피드는 수정이 가능하다.
>   - 피드에 등록된 이미지의 경우, 삭제 및 추가만 가능하다.
>   - 피드의 이미지가 삭제될 경우 서버에서도 해당 이미지를 삭제하도록 한다.
> 6. 피드는 삭제가 가능하다.
>   - 피드가 삭제될때는 실제로 데이터베이스에서 삭제하는 것이 아닌, 삭제 되었다는 표시를 남기도록 한다.
</details>   

<details>
<summary>2일차 기능 목록</summary>  

### ✨ 기능 목록
- [ ] Post 작성 및 복수의 이미지 저장 
- [ ] 로그인이 된 상태에서만 게시글 작성 가능
- [ ] 피드 목록 조회 기능 (작성자 아이디, 제목과 대표 이미지 정보 포함)
- [ ] 이미지가 없으면 지정된 기본 이미지 보여준다.
- [ ] soft delete 적용
</details>

<details>
<summary>2일차 예외 목록</summary>  

### ✨ 예외 목록
- [ ] 로그인 상태가 아니면 예외 발생
</details>

<details>
<summary>2일차 테스트 목록</summary>

### ✅ 테스트 목록

#### Service

#### controller

#### Repository

</details>

<hr>