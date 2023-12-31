# 📮 멋사SNS (개인 프로젝트)
> 여러분들이 많이 사용하고 있는 인스타그램, 페이스북 등을 착안하여 여러분들만의 플랫폼을 만들어보는 미니 프로젝트입니다.  
> 사용자가 게시글 자유롭게 올리고 댓글, 팔로우, 친구, 좋아요 등을 통해 소통하는 SNS 플랫폼의 백엔드를 만들어봅시다.  

## 🌟 작업 기간 (2023.08.03 ~ 2023.08.08) 

## ⭐ local test를 위해 꼭 Redis를 실행해야됩니다.  

### 설치  
#### docker pull redis

### 실행
#### docker run --name some-redis -p 6379:6379 -d redis

### Redis-cli 접속
#### docker exec -it some-redis redis-cli

## 🎯 멋사SNS ERD
![ERD](https://private-user-images.githubusercontent.com/109706328/258985660-725150df-ada6-49ec-ad19-46e55fa5df2c.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE2OTE0Njk3MDEsIm5iZiI6MTY5MTQ2OTQwMSwicGF0aCI6Ii8xMDk3MDYzMjgvMjU4OTg1NjYwLTcyNTE1MGRmLWFkYTYtNDllYy1hZDE5LTQ2ZTU1ZmE1ZGYyYy5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBSVdOSllBWDRDU1ZFSDUzQSUyRjIwMjMwODA4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDIzMDgwOFQwNDM2NDFaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT05NGYwNzA5YjJmOWIxMzkwMTA1YjIyOTExNDNiZGE4ZmY5MDQyMjFmOTM4YmIwNGVlN2JiOTQ4MDIyYTVlZWYzJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.JrOu3lvFokJlH1wVWgw9YrwL6Rh5dwKPBBi3e6GmJTk)  

## ⚙️ 기술스택  

Language | Java 17

Framework | Spring 6.0.11 (Spring Boot 3.1.2)

Build Tool | Gradle 8.2.1

Test | Junit 5.9.3, Mockito 5.3.1

Docs | Spring REST Docs 3.0.0

## 💡 API 설계
[API 문서 - Postman](https://documenter.getpostman.com/view/25715996/2s9XxzusjQ)  

## ⚡️주요 기능
- 인증 / 인가
  - 회원가입
  - JWT (AccessToken, RefreshToken) - Redis
  - Spring Security
- 유저
  - 프로필 수정, 이미지 등록
- 게시판
  - 게시판 CRUD
  - 게시글 좋아요
- 댓글
  - 댓글 CRUD
- 팔로우
  - 팔로우한 유저의 게시글만 조회
- 친구
  - 친구인 유저의 게시글만 조회
  
## 📌 학습
[Redis) 싱글벙글 Refresh Token을 Redis에 저장하고 사용해보자](https://byungil.tistory.com/309)  
  
Response 값 통일  
  
예외 처리 (@RestControllerAdvice)  
  
soft delete 적용  

이미지 저장 삭제 쿼리 개선  

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

## 3️⃣ DAY 3 / 댓글, 좋아요 구현하기
<details>  
<summary>3일차 기능 요구 사항</summary>

### 기능 요구 사항

> 💡 **댓글이란?**  
> 댓글이란 피드에 대하여 의견을 제시하는 비교적 적은 문구를 의미한다.  
> 
> 📝 DAY 3️⃣ **댓글, 좋아요 구현** **8/7**
> 1. 댓글 작성은 로그인 한 사람만 쓸 수 있다.  
>   - 댓글에는 작성자 아이디, 댓글 내용이 포함된다.  
> 2. 자신이 작성한 댓글은 수정 및 삭제가 가능하다.  
>   - 댓글이 삭제될때는 실제로 데이터베이스에서 삭제하는 것이 아닌, 삭제 되었다는 표시를 남기도록 한다.  
> 3. 댓글의 조회는 피드의 단독 조회와 함께 이뤄진다.  
> 4. 다른 사용자의 피드는 좋아요를 할 수 있다.  
>   - 자신의 피드의 좋아요는 할 수 없다(권한 없음).  
>   - 좋아요 요청을 보낼 때 이미 좋아요 한 상태라면, 좋아요는 취소된다.  
</details> 

## 4️⃣ DAY 4 / 사용자 정보 구현하기

<details>  
<summary>4일차 기능 요구 사항</summary>

### 기능 요구 사항

> 📝 DAY 4️⃣ **팔로우, 친구 구현** **8/8**  
> 
> 1. 사용자의 정보는 조회가 가능하다.
>   - 이때 조회되는 정보는 아이디와 프로필 사진이다.  
> 2. 로그인 한 사용자는 다른 사용자를 팔로우 할 수 있다.
>   - 팔로우는 일방적 관계이다. A 사용자가 B를 팔로우 하는 것이 B 사용자가 A를 팔로우 하는것을 의미하지 않는다.
> 3. 로그인 한 사용자는 팔로우 한 사용자의 팔로우를 해제할 수 있다.
> 4. 로그인 한 사용자는 다른 사용자와 친구 관계를 맺을 수 있다.
>   - 친구 관계는 양방적 관계이다. A 사용자가 B와 친구라면, B 사용자와 A 도 친구이다.
>   - A 사용자는 B 사용자에게 친구 요청을 보낸다.
>   - B 사용자는 자신의 친구 요청 목록을 확인할 수 있다.
>   - B 사용자는 친구 요청을 수락 혹은 거절할 수 있다.
> 5. 사용자의 팔로우한 모든 사용자의 피드 목록을 조회할 수 있다.
>   - 이때 작성한 사용자와 무관하게 작성된 순서의 역순으로 조회한다.
>   - 그 외 조회되는 데이터는 피드 목록 조회와 동일하다.
> 6. 사용자와 친구관계의 모든 사용자의 피드 목록을 조회할 수 있다.
>   - 이때 작성한 사용자와 무관하게 작성된 순서의 역순으로 조회한다.
>   - 그 외 조회되는 데이터는 피드 목록 조회와 동일하다.
</details> 