# 게시판 프로젝트

## 내용

- 회원, 게시글, 댓글, 댓글 좋아요로 구성된 간단한 토이 프로젝트
- 기본적인 CRUD 구현
- 관리자/회원 권한 구분 기능 구현
- 게시글 등록 상태 구현 
  - 작성중 - 등록 대기 - 등록 완료(금기어 포함시 관리자가 확인후 완료 처리)
- 게시글 검색 기능
- 알람 기능
- 팔로우 기능
- 페이지네이션 구현
- ~~간단한 화면 구현~~
- 앞으로 추가될 예정

## 목적

- 스프링에 대한 실질적인 공부를 위해 간단하게 구현한 코드
- 앞으로 스프링등 개발 전반적인 공부를 하고 실제 코드로 구현해보기 위한 프로젝트
- 기본적으로 백엔드 프로젝트지만 아주 기초적인 화면도 구현되어 있음
- 현재 상태는 이상하더라도 점점 발전하는 것을 목표

## 사용 기술

- Spring, SpringBoot, Spring Data JPA, Spring Validation
- Lombok, thymeleaf, QueryDsl
- MySql

## 추가 예정 (계속 수정 됨)

- Slice<> 알아보기
  - Page<> 는 게시판처럼 총 데이터 수가 필요할 때
  - Slice<> 는 모바일처럼 총 데이터가 필요 없을 때 -> 무한 스크롤
  - Page 는 토탈 카운트가 필요, Slice 는 불러오는 데이터 +1개로 호출해서, 다음이 있는지만 판단 
- @EventListener 알아보기
- 쿼리 디테일 수정 -> 특히 페이지/ 카운트 쿼리 (querydsl 71페이지)
- 페이징 조건 로직
- querydsl 다양한 기능 사용 고려
   - 집합함수, groupBy, on, subQuery
- oauth 구현 와 자체 로그인 기능 동시 지원 구현 

---
2023/04/08
- #46 : aop를 이용해서 생성관련 트랜잭션 실행시 자동 로그기록 구현
  - 자체 어노테이션 활용

2023/03/30
- #43 : @ControllerAdvisor / @ControllerAdvisor 공부 및 적용
- #44 : 예외 처리 세분화
- #45 : @Valid 관련 예외 처리

2023/03/25
- #42 : final 파라미터 적용

2023/03/24
- #39 : 검증 파트 만들다가 문제가 생겨 일시중단
- #40 : @Query 및 @EntityGraph 적용
- #41 : @Modifying 적용 가능성 알아보기
2023/03/23
- #37 : 테스트 db 분리 및 테스트 코드 다시 짜기
- #38 : 정적 팩토리 생성자 고려

2023/03/22
- #34 : 금기어 목록을 객체로 분리하기
- #35 : 금기어 목록과 관련된 로직 개발하기 + 금기어 캐싱
- #36 : 본인 팔로우 검증 로직

2023/03/21
- #32 : 대댓글, 대댓글 알람 구현
- #33 : 게시글 등록 프로세스 조정 (작성중 -> 등록 대기 -> 등록 완료)
  - 금기어 포함시 등록 대기로 설정
  - 금기어 미 포함시 등록 완료로 설정
  - 관리자가 등록 대기 게시글을 확인후 등록 완료 처리 가능

2023/03/20
- #31 : 검색 조건 / 검색 추가

2023/03/18
- #29 : 전체 코드 리팩토링
- #30 : 알람 연관관계 단방향으로 변경 

2023/03/17
- #28 : 연관관계에 따른 페치 조인 전략 다르게 적용
- 현재 기능을 한번 정리해서 파악하는 과정이 필요하다. 너무 구현 위주의 코딩을 해서 정리가 안되는 느낌이 있다.

2023/03/15
- #26 : fetch join 을 통해서 LAZY 상태에서도 N+1 문제 발생하는 경우 수정
- #27 : DTO 로 변환할 때, 페치조인 사용해서 쿼리 최적화

2023/03/14
- #24 : 알람 공통/개별 부분 분리
- #25 : 댓글과 회원의 연관 관계 수정 (차후 댓글에서 작성자를 찾아보기 위함, 아이디만 남기면 될지 객체를 남길지에 대한 의문은 아직 있다.)

2023/03/13
- #22 : #21 문제 해결 -> 알람을 먼저 삭제후 좋아요 삭제 (아직 만족 안됨)
- #23 : 알람 관련 테스트 코드 작성

2023/03/12
- #20 : 테스트 코드 다시 작성
- #21 : 알람 - 댓글 좋아요 간 문제 있음 발견

2023/03/11
- #18 : 전체 코드 정리
- #19 : 테스트 코드 다시 작성

2023/03/10
- #15 : 테스트 코드 추가 (Notification) -> 다시 공부해서 제대로 재정비
- #16 : 멤버별 알람 조회 기능 추가
- #17 : 권한 분리 (삭제 - 자신과 관리자만, 업데이트 - 자신만)

2023/03/09
- #14 : 테스트 코드 추가 (Member, Post, Comment)

2023/03/08
- #13 : BeanValidation 적용

2023/03/07
- #11 : #10의 알람 기능 완성 -> N+1 문제 발생
- #12 : 페치 조인으로 쿼리 성능 상승 -> 아직은 일부분

2023/03/06
- #9 : 팔로워 구현
- #10 : 팔로우 하는 멤버가 게시글을 작성하면 팔로워들에게 알람 생성 -> 알람은 아직 미구현
  - 팔로우를 구현하는 과정이 생각보다 복잡했음.

2023/03/04
- #7 : 댓글과 알람 관계 매핑
- #8 : 댓글 좋아요가 달릴 때도 알람 생성 구현

2023/03/03
- #6 : Notification 기본 구현 및 의존관계 매핑

2023/03/02 
- #4 : ADMIN 권한 실패시 HTTPStatus 조정
- #5 #3 문제를 exceptionHandler를 통해서 해결

2023/03/01 
- #2 : cascade, orphanRemoval 적용 / 오타 수정 
- #3 : 컨트롤러에서 예외상황 리턴값 처리 (올바른 방법인지는 아직 의문) - 해결

2023/02/22 ~ 2023/02/28 
- #1 기초 구현 완료 

