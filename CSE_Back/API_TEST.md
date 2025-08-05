# API 테스트 가이드

## 서버 실행
```bash
./gradlew bootRun
```

## 1. 회원가입 테스트
첫 번째 사용자로 회원가입하면 기본 사용자 데이터가 자동으로 추가됩니다.

```bash
curl -X POST "http://localhost:8080/api/users/signup" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=테스트사용자&email=test@example.com&password=testpass&role=OPERATOR"
```

## 2. 로그인 테스트

### 운영자 로그인 (김철수)
```bash
curl -X POST "http://localhost:8080/api/users/login" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=chulsoo@example.com&password=password1"
```

### 운영자 로그인 (이영희)
```bash
curl -X POST "http://localhost:8080/api/users/login" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=younghee@example.com&password=password2"
```

### 시민 로그인 (박민수)
```bash
curl -X POST "http://localhost:8080/api/users/login" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=minsu@example.com&password=password3"
```

### 시민 로그인 (정다혜)
```bash
curl -X POST "http://localhost:8080/api/users/login" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=dahye@example.com&password=password4"
```

### 관리자 로그인
```bash
curl -X POST "http://localhost:8080/api/users/login" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=admin@example.com&password=adminpass"
```

## 3. 오늘의 판매 현황 조회
```bash
curl -X GET "http://localhost:8080/api/users/today-sales"
```

## 응답 예시

### 로그인 응답 (운영자)
```json
{
  "user": {
    "id": 2,
    "name": "김철수",
    "email": "chulsoo@example.com",
    "role": "OPERATOR",
    "createdAt": "2024-01-01T10:00:00"
  },
  "foodTruck": {
    "id": 1,
    "name": "김철수의 맛있는 떡볶이",
    "description": "매콤달콤한 떡볶이 전문점",
    "status": "APPROVED"
  },
  "menus": [
    {
      "id": 1,
      "name": "떡볶이",
      "price": 5000,
      "category": "분식"
    },
    {
      "id": 2,
      "name": "어묵",
      "price": 3000,
      "category": "분식"
    }
  ],
  "todaySales": {
    "orderCount": 0,
    "totalRevenue": 0,
    "topMenu": null
  }
}
```

### 오늘의 판매 현황 응답
```json
{
  "orderCount": 5,
  "totalRevenue": 25000,
  "topMenu": "떡볶이"
}
```

## 사용자 역할별 정보 제공

1. **OPERATOR (운영자)**: 자신의 푸드트럭 정보와 메뉴 정보 제공
2. **CITIZEN (시민)**: 기본 사용자 정보만 제공 (푸드트럭/메뉴 정보 없음)
3. **ADMIN (관리자)**: 모든 메뉴 정보 제공 (전체 관리자 권한)

## 오늘의 판매 현황
- 새로고침할 때마다 실시간으로 업데이트됩니다
- 주문 수, 총 매출, 인기 메뉴 정보를 제공합니다 