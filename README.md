## 구현할 기능 목록

### 입출력
- [x] 구입 금액 입력 받기 (1,000원 단위, 0 또는 음수/미단위 예외)
- [x] 발행된 로또 개수 및 번호(오름차순) 출력
- [x] 당첨 번호(쉼표 구분) 입력, 보너스 번호 입력
- [x] 당첨 통계/수익률 출력 (소수점 둘째 자리 반올림)

### 도메인/규칙
- [x] 로또: 1~45 범위, 중복 없는 6개
- [x] 당첨: 6개(1등), 5+보너스(2등), 5개(3등), 4개(4등), 3개(5등)
- [x] 가격: 1장 1,000원, 구입 금액/1000 = 매수
- [x] 예외 시 IllegalArgumentException, "[ERROR]"로 시작하는 메시지 후 재입력

### 설계/품질
- [x] JDK 21, Application.main() 시작
- [x] camp.nextstep.edu.missionutils.Randoms/Console 사용
- [x] indent depth ≤ 2, else/switch 금지 (조기 return)
- [x] 메서드 길이 ≤ 15라인
- [x] Enum 활용 (등수/상금)
- [x] UI 로직 제외 단위 테스트 (JUnit5, AssertJ)