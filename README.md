# 카카오페이 뿌리기 기능 구현하기

## 뿌리기 API

* 사용자의 식별값 "X-USER-ID"는 자바 Random 클래스를 활용해서 시간을 Seed로 해서 난수를 생성.

* 대화방의 식별값 "X-ROOM-ID"도 시간을 기반으로 UUID를 생성.

* 뿌리기 요청건에 대한 고유 token은 아스키 코드의 문자 3개를 골라 랜덤하게 생성.

## 받기 API

* 뿌린지 10분이 지난 요청에 대한 처리는 LocalDateTime의 now(), isAfter(), plusMinutes() 등을 사용해 처리함.

* @Lock(LockModeType.PESSIMISTIC_WRITE)을 이용해서 테이블 로우에 락을 걸어서 다수의 서버에 다수의 인스턴스로 동작하더라도 문제가 없도록 구현. 

## 조회 API

* Spring Data JPA 쿼리 메서드를 활용해서 뿌린건에 대한 조회가 7일 동안만 가능 하도록 구현.

