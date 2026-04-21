![ERD](https://github.com/js-kim-arc/nbc-schedule-2nd/blob/main/%EC%8A%A4%EC%BC%80%EC%A5%B41.png?raw=true)

<details>
<summary>📋 Schedule Management API 명세서</summary>

# Schedule Management API 명세서

---

## 공통 사항

- **Base URL**: `/api`
- **Content-Type**: `application/json`
- **응답 공통 규칙**: 모든 응답에서 `password` 필드는 제외
---

## 1. 일정 생성

### `POST /api/schedules`

**Request Body**

```json
{
  "title": "스프링 공부",
  "content": "JPA Auditing 적용 방법 학습",
  "author": "김철수",
  "password": "1234"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| `title` | String | ✅ | 할일 제목. blank 불가 |
| `content` | String | ✅ | 할일 내용. blank 불가 |
| `author` | String | ✅ | 작성 유저명. blank 불가 |
| `password` | String | ✅ | 수정·삭제 시 사용할 비밀번호. blank 불가 |

**Response Body** `201 Created`

```json
{
  "id": 1,
  "title": "스프링 공부",
  "content": "JPA Auditing 적용 방법 학습",
  "author": "김철수",
  "createdAt": "2026-04-20T10:00:00",
  "updatedAt": "2026-04-20T10:00:00"
}
```

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 필수 필드 누락 | `400 Bad Request` | `"필수 입력값이 누락되었습니다."` |
| 필수 필드가 blank | `400 Bad Request` | `"입력값은 비어 있을 수 없습니다."` |

---

## 2. 일정 목록 조회 (페이징)

### `GET /api/schedules`

**Query Parameter**

| 파라미터 | 타입 | 필수 | 기본값 | 설명 |
| --- | --- | --- | --- | --- |
| `page` | int | ❌ | `0` | 페이지 번호 (0-based) |
| `size` | int | ❌ | `10` | 페이지 크기 |
| `sort` | String | ❌ | `updatedAt,desc` | 정렬 기준 |
| `author` | String | ❌ | - | 작성자명 필터. 미전달 시 전체 조회 |

**Response Body** `200 OK`

```json
{
  "content": [
    {
      "id": 2,
      "title": "QueryDSL 공부",
      "content": "동적 쿼리 작성 방법 학습",
      "author": "김철수",
      "createdAt": "2026-04-20T11:00:00",
      "updatedAt": "2026-04-20T12:30:00"
    }
  ],
  "totalElements": 2,
  "totalPages": 1,
  "number": 0,
  "size": 10,
  "first": true,
  "last": true,
  "numberOfElements": 2,
  "empty": false
}
```

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 조회 결과 0건 | `200 OK` | `content: []`로 빈 페이지 반환 |
| 음수 `page` 또는 `size` | `400 Bad Request` | `"유효하지 않은 페이징 파라미터입니다."` |

---

## 3. 단건 일정 조회

### `GET /api/schedules/{id}`

**Response Body** `200 OK`

```json
{
  "id": 1,
  "title": "스프링 공부",
  "content": "JPA Auditing 적용 방법 학습",
  "author": "김철수",
  "createdAt": "2026-04-20T10:00:00",
  "updatedAt": "2026-04-20T10:00:00"
}
```

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 존재하지 않는 ID | `404 Not Found` | `"해당 일정을 찾을 수 없습니다."` |

---

## 4. 일정 수정

### `PATCH /api/schedules/{id}`

**Request Body**

```json
{
  "title": "스프링 심화 공부",
  "content": "트랜잭션 격리 수준 심화 학습",
  "password": "1234"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| `title` | String | ❌ | 수정할 제목. 미전달 시 기존 값 유지 |
| `content` | String | ❌ | 수정할 내용. 미전달 시 기존 값 유지 |
| `password` | String | ✅ | 본인 확인용 비밀번호 |

**Response Body** `200 OK`

```json
{
  "id": 1,
  "title": "스프링 심화 공부",
  "content": "트랜잭션 격리 수준 심화 학습",
  "author": "김철수",
  "createdAt": "2026-04-20T10:00:00",
  "updatedAt": "2026-04-20T13:00:00"
}
```

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 존재하지 않는 ID | `404 Not Found` | `"해당 일정을 찾을 수 없습니다."` |
| 비밀번호 불일치 | `403 Forbidden` | `"비밀번호가 일치하지 않습니다."` |
| `password` 미전달 또는 blank | `400 Bad Request` | `"비밀번호는 필수입니다."` |
| `title`·`content` 모두 미전달 | `400 Bad Request` | `"수정할 필드가 없습니다."` |

---

## 5. 일정 삭제

### `DELETE /api/schedules/{id}`

**Request Body**

```json
{
  "password": "1234"
}
```

**Response** `204 No Content`

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 존재하지 않는 ID | `404 Not Found` | `"해당 일정을 찾을 수 없습니다."` |
| 비밀번호 불일치 | `403 Forbidden` | `"비밀번호가 일치하지 않습니다."` |
| `password` 미전달 또는 blank | `400 Bad Request` | `"비밀번호는 필수입니다."` |

---

## API 요약표

| 기능 | 메서드 | URL | 인증 | 페이징 |
| --- | --- | --- | --- | --- |
| 일정 생성 | `POST` | `/api/schedules` | 없음 | - |
| 일정 목록 조회 | `GET` | `/api/schedules` | 없음 | ✅ |
| 단건 일정 조회 | `GET` | `/api/schedules/{id}` | 없음 | - |
| 일정 수정 | `PATCH` | `/api/schedules/{id}` | password 검증 | - |
| 일정 삭제 | `DELETE` | `/api/schedules/{id}` | password 검증 | - |

</details>

---

<details>
<summary>👤 User Management API 명세서</summary>

# User Management API 명세서

> **버전**: Story 3-3 반영 (BCrypt 암호화 적용)

---

## 공통 사항

- **Base URL**: `/api`
- **Content-Type**: `application/json`
- **응답 공통 규칙**: 모든 응답에서 `password` 필드는 제외

---

## 1. 유저 생성 (회원가입)

### `POST /api/users`

**Request Body**

```json
{
  "username": "김철수",
  "email": "chulsoo@example.com",
  "password": "mypassword123"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| `username` | String | ✅ | 유저명. blank 불가 |
| `email` | String | ✅ | 이메일 주소. 유효한 형식 필수. 서비스 전체 유일 |
| `password` | String | ✅ | 계정 비밀번호. 8자 이상 필수. 응답에 포함되지 않음 |

**Response Body** `201 Created`

```json
{
  "id": 1,
  "username": "김철수",
  "email": "chulsoo@example.com",
  "createdAt": "2026-04-20T10:00:00",
  "updatedAt": "2026-04-20T10:00:00"
}
```

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 필수 필드 누락 | `400 Bad Request` | `"필수 입력값이 누락되었습니다."` |
| 필수 필드가 blank | `400 Bad Request` | `"입력값은 비어 있을 수 없습니다."` |
| 유효하지 않은 이메일 형식 | `400 Bad Request` | `"유효하지 않은 이메일 형식입니다."` |
| `password`가 8자 미만 | `400 Bad Request` | `"비밀번호는 8자 이상이어야 합니다."` |
| 이미 가입된 이메일 | `409 Conflict` | `"이미 사용 중인 이메일입니다."` |

---

## 2. 전체 유저 조회

### `GET /api/users`

**Response Body** `200 OK`

```json
[
  {
    "id": 1,
    "username": "김철수",
    "email": "chulsoo@example.com",
    "createdAt": "2026-04-20T10:00:00",
    "updatedAt": "2026-04-20T10:00:00"
  }
]
```

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 조회 결과 0건 | `200 OK` | 빈 배열 `[]` 반환 |

---

## 3. 단건 유저 조회

### `GET /api/users/{id}`

**Response Body** `200 OK`

```json
{
  "id": 1,
  "username": "김철수",
  "email": "chulsoo@example.com",
  "createdAt": "2026-04-20T10:00:00",
  "updatedAt": "2026-04-20T10:00:00"
}
```

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 존재하지 않는 ID | `404 Not Found` | `"해당 유저를 찾을 수 없습니다."` |

---

## 4. 유저 수정

### `PATCH /api/users/{id}`

**Request Body**

```json
{
  "username": "김철수(수정)",
  "email": "chulsoo_new@example.com"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| `username` | String | ❌ | 수정할 유저명. 미전달 시 기존 값 유지 |
| `email` | String | ❌ | 수정할 이메일. 미전달 시 기존 값 유지 |

**Response Body** `200 OK`

```json
{
  "id": 1,
  "username": "김철수(수정)",
  "email": "chulsoo_new@example.com",
  "createdAt": "2026-04-20T10:00:00",
  "updatedAt": "2026-04-20T13:00:00"
}
```

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 존재하지 않는 ID | `404 Not Found` | `"해당 유저를 찾을 수 없습니다."` |
| `username`·`email` 모두 미전달 | `400 Bad Request` | `"수정할 필드가 없습니다."` |
| blank로 명시적 전달 | `400 Bad Request` | `"입력값은 비어 있을 수 없습니다."` |
| 유효하지 않은 이메일 형식 | `400 Bad Request` | `"유효하지 않은 이메일 형식입니다."` |
| 이미 사용 중인 이메일 | `409 Conflict` | `"이미 사용 중인 이메일입니다."` |

---

## 5. 유저 삭제

### `DELETE /api/users/{id}`

**Response** `204 No Content`

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| 존재하지 않는 ID | `404 Not Found` | `"해당 유저를 찾을 수 없습니다."` |

---

## API 요약표

| 기능 | 메서드 | URL | 인증 |
| --- | --- | --- | --- |
| 유저 생성 (회원가입) | `POST` | `/api/users` | 없음 |
| 전체 유저 조회 | `GET` | `/api/users` | 없음 |
| 단건 유저 조회 | `GET` | `/api/users/{id}` | 없음 |
| 유저 수정 | `PATCH` | `/api/users/{id}` | 없음 (Epic 4에서 추가) |
| 유저 삭제 | `DELETE` | `/api/users/{id}` | 없음 (Epic 4에서 추가) |

</details>

---

<details>
<summary>🔐 Auth Management API 명세서</summary>

# Auth Management API 명세서

> **버전**: Story 4-1 (이메일·비밀번호 로그인 + 세션 기반 접근 제어)

---

## 공통 사항

- **Base URL**: `/auth`
- **Content-Type**: `application/json`
- **인증 방식**: Cookie/Session (`JSESSIONID`). 서블릿 컨테이너(Tomcat)가 발급·관리.
- **세션 만료**: 기본 30분 비활성.
- **에러 통일 원칙**: 로그인 실패 시 이메일 없음·비밀번호 불일치를 동일한 401로 반환.

---

## 1. 로그인

### `POST /auth/login`

**Request Body**

```json
{
  "email": "chulsoo@example.com",
  "password": "mypassword123"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| `email` | String | ✅ | 가입 시 등록한 이메일 주소. blank 불가 |
| `password` | String | ✅ | 계정 비밀번호. blank 불가 |

**Response Body** `200 OK`

```json
{
  "id": 1,
  "username": "김철수",
  "email": "chulsoo@example.com"
}
```

> 응답 헤더에 `Set-Cookie: JSESSIONID=xxx; Path=/; HttpOnly`가 자동으로 추가된다.

**에러 응답**

| 상황 | 상태 코드 | 메시지 |
| --- | --- | --- |
| `email` 또는 `password` 누락·blank | `400 Bad Request` | `"필수 입력값이 누락되었습니다."` |
| 존재하지 않는 이메일 | `401 Unauthorized` | `"이메일 또는 비밀번호가 올바르지 않습니다."` |
| 비밀번호 불일치 | `401 Unauthorized` | `"이메일 또는 비밀번호가 올바르지 않습니다."` |

---

## 2. 로그아웃 (v2 예정)

### `POST /auth/logout`

> 현재 Story 4-1 범위에 포함되지 않는다. v2에서 구현 예정.

---

## 인증 필요 / 공개 URL 분류

| 분류 | URL 패턴 | 비고 |
| --- | --- | --- |
| 공개 | `POST /auth/login` | 로그인 |
| 공개 | `POST /api/users` | 회원가입 |
| 공개 | `GET /api/schedules` | 목록 조회 |
| 공개 | `GET /api/schedules/{id}` | 단건 조회 |
| 공개 | `GET /api/users/**` | 유저 조회 |
| **인증 필요** | `POST /api/schedules` | 일정 생성 |
| **인증 필요** | `PATCH /api/schedules/{id}` | 일정 수정 |
| **인증 필요** | `DELETE /api/schedules/{id}` | 일정 삭제 |
| **인증 필요** | `PATCH /api/users/{id}` | 유저 수정 |
| **인증 필요** | `DELETE /api/users/{id}` | 유저 삭제 |

---

## ErrorCode 추가 목록

| 코드 | 상수명 | 메시지 | HTTP |
| --- | --- | --- | --- |
| `AUTH001` | `AUTH_INVALID_CREDENTIALS` | 이메일 또는 비밀번호가 올바르지 않습니다. | `401` |
| `AUTH002` | `AUTH_REQUIRED` | 로그인이 필요합니다. | `401` |
| `SCHEDULE007` | `SCHEDULE_FORBIDDEN` | 본인의 일정이 아닙니다. | `403` |
| `COMMENT007` | `COMMENT_FORBIDDEN` | 본인의 댓글이 아닙니다. | `403` |
| `USER008` | `USER_FORBIDDEN` | 본인의 계정이 아닙니다. | `403` |

---

## 공통 에러 응답 포맷

```json
{
  "code": "AUTH001",
  "message": "이메일 또는 비밀번호가 올바르지 않습니다.",
  "path": "/auth/login",
  "timestamp": "2026-04-20T13:00:00+09:00"
}
```

</details>
