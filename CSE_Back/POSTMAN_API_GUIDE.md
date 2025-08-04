# Postman API 테스트 가이드

## 서버 정보
- **Base URL**: `http://localhost:8080`
- **Content-Type**: `application/json`

## 1. 회원가입 API

### Request
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/users/signup`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (JSON):
  ```json
  {
    "name": "테스트사용자",
    "email": "test@example.com",
    "password": "testpass",
    "role": "OPERATOR"
  }
  ```

### Response
```
회원가입 완료
```

## 2. 로그인 API

### Request
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/users/login`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (JSON):
  ```json
  {
    "email": "chulsoo@example.com",
    "password": "password1"
  }
  ```

### Response (운영자 로그인 예시)
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

## 3. 오늘의 판매 현황 API

### Request
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/users/today-sales`
- **Headers**: 없음

### Response
```json
{
  "orderCount": 5,
  "totalRevenue": 25000,
  "topMenu": "떡볶이"
}
```

## 테스트할 사용자 계정

### 운영자 계정
1. **김철수**
   - Email: `chulsoo@example.com`
   - Password: `password1`
   - 푸드트럭: "김철수의 맛있는 떡볶이"

2. **이영희**
   - Email: `younghee@example.com`
   - Password: `password2`
   - 푸드트럭: "이영희의 건강한 샌드위치"

### 시민 계정
1. **박민수**
   - Email: `minsu@example.com`
   - Password: `password3`

2. **정다혜**
   - Email: `dahye@example.com`
   - Password: `password4`

### 관리자 계정
1. **관리자**
   - Email: `admin@example.com`
   - Password: `adminpass`

## Postman Collection 설정

### 1. 회원가입
```
POST {{baseUrl}}/api/users/signup
Content-Type: application/json

{
  "name": "테스트사용자",
  "email": "test@example.com",
  "password": "testpass",
  "role": "OPERATOR"
}
```

### 2. 김철수 로그인
```
POST {{baseUrl}}/api/users/login
Content-Type: application/json

{
  "email": "chulsoo@example.com",
  "password": "password1"
}
```

### 3. 이영희 로그인
```
POST {{baseUrl}}/api/users/login
Content-Type: application/json

{
  "email": "younghee@example.com",
  "password": "password2"
}
```

### 4. 박민수 로그인
```
POST {{baseUrl}}/api/users/login
Content-Type: application/json

{
  "email": "minsu@example.com",
  "password": "password3"
}
```

### 5. 관리자 로그인
```
POST {{baseUrl}}/api/users/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "adminpass"
}
```

### 6. 오늘의 판매 현황
```
GET {{baseUrl}}/api/users/today-sales
```

## 환경 변수 설정
Postman에서 환경 변수를 설정하세요:
- **baseUrl**: `http://localhost:8080`

## 사용자 역할별 응답 차이

1. **OPERATOR (운영자)**: 자신의 푸드트럭과 메뉴 정보 포함
2. **CITIZEN (시민)**: 사용자 정보만 제공 (푸드트럭/메뉴 정보 없음)
3. **ADMIN (관리자)**: 모든 메뉴 정보 제공

## 오늘의 판매 현황 특징
- 새로고침할 때마다 실시간으로 업데이트
- 주문 수, 총 매출, 인기 메뉴 정보 제공

## 보안 개선사항
- POST 요청에서 JSON body 사용으로 개인정보가 URL에 노출되지 않음
- 비밀번호가 URL 파라미터로 전송되지 않아 보안성 향상 