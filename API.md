#### 카카오페이 멤버십 적립 서비스

멤버십
해피포인트, 신세계포인트, CJ ONE

적립비율 
1%

X-USER-ID
JSON
멤버십 연결하기, 나의 멤버십 조회, 멤버십 연결끊기, 포인트 적립


#### 평가 기준
- 프로젝트 구성 방법 및 시스템 아키텍쳐 설계 방법이
- 요구사항을 잘 이해?
- RESTAPI의 응답 포맷이 일정한다
- DB 테이블 설계 및 쿼리 효율적
- 예외처리
- 적절한 범위 테스트

#### 요청공통
Content-Type : JSON
X-USER-ID : test1 사용자 식별값


#### API
1 나의 멤버십 전체조회
GET /api/v1/membership

2 멤버십 등록하기
POST /api/v1/membership

3 멤버십 삭제(비활성화) 하기
사용자 식별 값과 membershipId 없을 떄 
500 - NotExistMembershipException()

4 멤버십 상세조회

5 포인트 적립


// TODO ;;

--> kakao pay exception 으로 바꾸기?
--> 이미 있는 포인트면 등록 오류?
--> 이미 정지되어 있는 멤버십인 경우는 똑같이 N 처리
 membership을 다른 곳에 담아서 response에 담아줄 것이냐 ;; ㅋ