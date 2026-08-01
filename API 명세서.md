# 마이페이지 정보 수정

Method: PATCH
URI patterns: /api/users/me/profile
설명: 마이페이지에서 수정한 프로필 정보를 DB에 업데이트합니다.
완료: Yes
필요 정보: 수정할 마이페이지 정보

마이페이지에서 수정한 프로필 정보를 DB에 업데이트합니다.

# URL

`PATCH /api/users/me/profile`

> ex) `/api/users/me/profile`
> 

# 필요 정보

# Request Parameter

없음

# Request Body

```json
{
  "basicSpec": {
    "languageScore": "AL" 
  },
  "certificates": [
    {
      "certId": 1,
      "certName": "정보처리기사"
    },
    {
      "certName": "AWS Solutions Architect" 
    }
  ]
}
```

(보내지 않은 값들은 기존 값을 유지한다.)

| **필드명** | **타입** | **필수 여부** | **설명** |
| --- | --- | --- | --- |
| name | String | X | 사용자 이름 (수정 시에만 포함) |
| basicSpec | Object | X | 유저 기본 스펙 객체 |
| basicSpec.school | String | X | 출신 대학교 이름 |
| basicSpec.major | String | X | 전공 |
| basicSpec.desiredJob | String | X | 희망 직무 |
| basicSpec.languageName | String | X | 어학 시험명 |
| basicSpec.languageScore | String | X | 어학 점수 |
| certificates | Array | X | 자격증 목록 (배열 채로 덮어씌움) |
| certificates[].certId | Long | X | 기존 자격증인 경우 PK 포함 |
| certificates[].certName | String | O (배열 포함 시) | 자격증 이름 |
| internships | Array | X | 인턴 경험 목록 (배열 채로 덮어씌움) |
| internships[].internId | Long | X | 기존 인턴인 경우 PK 포함 |
| internships[].companyName | String | O (배열 포함 시) | 회사명 |
| internships[].role | String | O (배열 포함 시) | 담당 직무/부서 |
| internships[].startDate | String | O (배열 포함 시) | 시작일 (YYYY-MM-DD) |
| internships[].endDate | String | O (배열 포함 시) | 종료일 (YYYY-MM-DD) |

# ✅ Success Response

### HTTP Status code: `200 Ok`

### Content

```json
{
  "isSuccess": true,
  "code": 201,
  "message": "사용자 프로필 정보가 성공적으로 저장되었습니다.",
  "data": null
}
```

# ❌ Fail Response

## 사용자 정보를 찾을 수 없는 경우

### HTTP Status code: `400 Bad Request`

### Content

```json
{
  "timestamp": "2026-07-31T23:15:30.347+09:00",
  "status": 400,
  "error": "Bad Request",
  "code": "INVALID_INPUT",
  "message": "입력값이 올바르지 않습니다.",
  "path": "/api/users/me/profile"
}
```

### HTTP Status code: `500 Internal Server Error`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 500,
  "error": "Internal Server Error",
  "code": "SERVER_ERROR",
  "message": "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
  "path": "/api/users/me/profile"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 400 Bad Request | INVALID_INPUT | Body에 필수 값이 비어있거나 타입이 맞지 않는 경우 |
| 500 Internal Server Error | SERVER_ERROR | 서버 내부 오류가 발생 |

---

# 마이페이지 조회

Method: GET
URI patterns: /api/users/me/profile
설명: 유저의 학력, 어학, 자격증, 인턴 기간 등 모든 기본 스펙을 한 번에 조회
완료: Yes
필요 정보: 없음

유저의 학력, 어학, 자격증, 인턴 기간 등 모든 기본 스펙을 한 번에 조회

# URL

`GET /api/users/me/profile`

> ex) `/api/users/me/profile`
> 

# 필요 정보

# Request Parameter

없음

# Request Body

없음

# ✅ Success Response

### HTTP Status code: `200 OK`

### Content

```json
{
  "isSuccess": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "data": [
		{
		  "name": "이현경",
		  "basicSpec": {
		    "school": "이화여자대학교",
		    "major": "컴퓨터공학과",
		    "desiredJob": "인프라 엔지니어",
		    "languageName": "OPIc",
		    "languageScore": "IM2"
		  },
		  "certificates": [
		    {
		      "certId": 1,
		      "certName": "정보처리기사"
		    },
		    {
		      "certId": 2,
		      "certName": "SQLD"
		    }
		  ],
		  "internships": [
		    {
		      "internId": 1,
		      "companyName": "KT Cloud",
		      "role": "인프라 엔지니어링 인턴",
		      "startDate": "2025-07-01",
		      "endDate": "2025-08-31"
		    }
		  ]
		}
  ]
}
```

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
| isSuccess | Boolean | 요청 성공 여부 (true) |
| code | Integer | 응답 상태 코드 (200) |
| message | String | 응답 결과 메시지 |
| data | Array | 마이페이지 프로필 데이터 목록 (배열) |
| data[].name | String | 사용자 이름 |
| data[].basicSpec | Object | 유저의 기본 스펙(학력/어학 등) 객체 |
| data[].basicSpec.school | String | 출신 대학교 이름 |
| data[].basicSpec.major | String | 전공 |
| data[].basicSpec.desiredJob | String | 사용자가 설정한 희망 직무 |
| data[].basicSpec.languageName | String | 최신 어학 시험명 (예: OPIc, TOEIC) |
| data[].basicSpec.languageScore | String | 최신 어학 점수 |
| data[].certificates | Array | 보유 자격증 목록 (배열) |
| data[].certificates[].certId | Long | 자격증 고유 ID (PK) |
| data[].certificates[].certName | String | 자격증 이름 (예: 정보처리기사) |
| data[].internships | Array | 인턴십 경험 목록 (배열) |
| data[].internships[].internId | Long | 인턴십 고유 ID (PK) |
| data[].internships[].companyName | String | 인턴 근무 회사명 |
| data[].internships[].role | String | 담당 직무 및 부서명 |
| data[].internships[].startDate | String | 근무 시작일 (형식: YYYY-MM-DD) |
| data[].internships[].endDate | String | 근무 종료일 (형식: YYYY-MM-DD) |

# ❌ Fail Response

## 사용자 정보를 찾을 수 없는 경우

### HTTP Status code: `404 Not Found`

### Content

```json
{
  "timestamp": "2026-07-31T23:15:30.347+09:00",
  "status": 404,
  "error": "Not Found",
  "code": "USER_NOT_FOUND",
  "message": "사용자 정보를 찾을 수 없습니다.",
  "path": "/api/users/me/profile"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 404 Not Found | USER_NOT_FOUND | 해당하는 User가 존재하지 않는 경우 |

---

# 사용자 기본 정보 입력

Method: POST
URI patterns: /api/users/me/profile
설명: 회원가입 직후 온보딩 화면에서 입력받은 사용자의 기본 인적 사항, 스펙, 자격증, 인턴 경험을 DB에 한 번에 저장합니다.
완료: Yes
필요 정보: 사용자 기본 정보

회원가입 직후 온보딩 화면에서 입력받은 사용자의 기본 인적 사항, 스펙, 자격증, 인턴 경험을 DB에 한 번에 저장합니다.

# URL

`POST /api/users/me/profile`

> ex) `/api/users/me/profile`
> 

# 필요 정보

# Request Parameter

없음

# Request Body

```json
{
  "name": "이현경",
  "basicSpec": {
    "school": "이화여자대학교",
    "major": "컴퓨터공학과",
    "desiredJob": "MLOps/인프라 엔지니어",
    "languageName": "OPIc",
    "languageScore": "IM2"
  },
  "certificates": [
    {
      "certName": "정보처리기사"
    },
    {
      "certName": "SQLD"
    }
  ],
  "internships": [
    {
      "companyName": "KT Cloud",
      "role": "인프라 엔지니어링 인턴",
      "startDate": "2025-07-01",
      "endDate": "2025-08-31"
    }
  ]
}
```

(참고: 자격증이나 인턴 경험이 없는 경우, 빈 배열 `[]` 로 보내면 됩니다.)

| **필드명** | **타입** | **필수 여부** | **설명** |
| --- | --- | --- | --- |
| name | String | O | 사용자 이름 (Users 테이블 업데이트) |
| basicSpec | Object | O | 유저 기본 스펙 객체 |
| basicSpec.school | String | O | 출신 대학교 이름 |
| basicSpec.major | String | O | 전공 |
| basicSpec.desiredJob | String | O | 희망 직무 |
| basicSpec.languageName | String | X | 어학 시험명 (없으면 null 또는 빈칸) |
| basicSpec.languageScore | String | X | 어학 점수 |
| certificates | Array | O | 자격증 목록 (배열, 없으면 빈 배열) |
| certificates[].certName | String | O (배열 내) | 자격증 이름 |
| internships | Array | O | 인턴 경험 목록 (배열, 없으면 빈 배열) |
| internships[].companyName | String | O (배열 내) | 회사명 |
| internships[].role | String | O (배열 내) | 담당 직무/부서 |
| internships[].startDate | String | O (배열 내) | 시작일 (YYYY-MM-DD) |
| internships[].endDate | String | O (배열 내) | 종료일 (YYYY-MM-DD) |

# ✅ Success Response

### HTTP Status code: `201 Created`

### Content

```json
{
  "isSuccess": true,
  "code": 201,
  "message": "사용자 프로필 정보가 성공적으로 저장되었습니다.",
  "data": null
}
```

# ❌ Fail Response

## 사용자 정보를 찾을 수 없는 경우

### HTTP Status code: `400 Bad Request`

### Content

```json
{
  "timestamp": "2026-07-31T23:15:30.347+09:00",
  "status": 400,
  "error": "Bad Request",
  "code": "INVALID_INPUT",
  "message": "입력값이 올바르지 않습니다.",
  "path": "/api/users/me/profile"
}
```

### HTTP Status code: `500 Internal Server Error`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 500,
  "error": "Internal Server Error",
  "code": "SERVER_ERROR",
  "message": "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
  "path": "/api/users/me/profile"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 400 Bad Request | INVALID_INPUT | Body에 필수 값이 비어있거나 타입이 맞지 않는 경우 |
| 500 Internal Server Error | SERVER_ERROR | 서버 내부 오류가 발생 |

---

# 입력한 답변 전체 조회

Method: GET
URI patterns: /api/experiences
설명: 사용자가 이전에 작성해 둔 모든 질문 번호와 답변 내용을 배열 형태로 한 번에 내려줍니다.
완료: Yes
필요 정보: 없음

사용자가 이전에 작성해 둔 모든 질문 번호와 답변 내용을 배열 형태로 한 번에 내려줍니다.

# URL

`GET /api/experiences`

> ex) `/api/experiences`
> 

# 필요 정보

# Request Parameter

없음

# Request Body

```json
없음.
```

# ✅ Success Response

### HTTP Status code: `200 OK`

### Content

```json
{
  "isSuccess": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "data": [
    {
      "experienceId": 12,
      "questionIndex": 1,
      "answerContent": "이화여자대학교 캡스톤 프로젝트에서 Spring Boot를 활용해..."
    },
    {
      "experienceId": 15,
      "questionIndex": 3,
      "answerContent": "협업 과정에서 깃허브 충돌이 발생했을 때..."
    }
  ]
}
```

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
| isSuccess | Boolean | 요청 성공 여부 (true) |
| code | Integer | 응답 상태 코드 (200) |
| message | String | 응답 결과 메시지 |
| data | Array | 작성된 경험(답변) 데이터 목록 배열 |
| data[].experienceId | Long | 서술형 경험 테이블의 고유 PK |
| data[].questionIndex | Integer | 프론트엔드와 약속한 질문 인덱스 번호 (예: 1, 2, 3, 4) |
| data[].answerContent | String | 유저가 텍스트 에어리어에 작성한 실제 답변 내용 |

# ❌ Fail Response

### HTTP Status code: `500 Internal Server Error`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 500,
  "error": "Internal Server Error",
  "code": "SERVER_ERROR",
  "message": "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
  "path": "/api/experiences"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 500 Internal Server Error | SERVER_ERROR | 서버 내부 오류가 발생 |

---

# 질문 답변 작성

Method: PUT
URI patterns: /api/experiences/{questionIndex}
설명: 특정 질문(questionIndex)에 대한 사용자의 답변을 개별적으로 저장하거나 수정합니다.
완료: Yes
필요 정보: questionIndex

특정 질문(questionIndex)에 대한 사용자의 답변을 개별적으로 저장하거나 수정합니다.

# URL

`PUT /api/experiences/{questionIndex}`

> ex) `/api//experiences/1`
> 

# 필요 정보

# Request Parameter

| **파라미터명** | **타입** | **필수 여부** | **설명** |
| --- | --- | --- | --- |
| `questionIndex` | `Integer` | O | 프론트엔드에서 지정한 질문 번호 (예: 1, 2, 3, 4) |

# Request Body

```json
{
  "answerContent": "이화여자대학교 캡스톤 프로젝트에서 Spring Boot를 활용해..."
}
```

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
| answerContent | string | 질문 답변 내용 |

아무 내용도 입력하지 않는다면 `"answerContent": "”` 으로 보낸다.

# ✅ Success Response

### HTTP Status code: `200 OK`

### Content

```json
{
  "isSuccess": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "data": {
    "experienceId": 12,
    "questionIndex": 1
  }
}
```

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
| isSuccess | Boolean | 요청 성공 여부 (true) |
| code | Integer | 응답 상태 코드 (200) |
| message | String | 응답 결과 메시지 |
| data | Array | 작성된 경험(답변) 데이터 목록 배열 |
| data[].experienceId | Long | 서술형 경험 테이블의 고유 PK |
| data[].questionIndex | Integer | 프론트엔드와 약속한 질문 인덱스 번호 (예: 1, 2, 3, 4) |

# ❌ Fail Response

### HTTP Status code: `500 Internal Server Error`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 500,
  "error": "Internal Server Error",
  "code": "SERVER_ERROR",
  "message": "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
  "path": "/api/experiences/1"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 500 Internal Server Error | SERVER_ERROR | 서버 내부 오류가 발생 |

---

# 타겟 기업/계열사/직무 목록 조회

Method: GET
URI patterns: /api/jds
설명: 홈 화면의 드롭다운을 목록을 조회한다. 타겟 기업, 계열사, 직무 목록을 한 번에 불러온다.
완료: Yes
필요 정보: 없음

홈 화면의 드롭다운을 목록을 조회한다. 타겟 기업, 계열사, 직무 목록을 한 번에 불러온다.

# URL

`GET /api/jds`

> ex) `/api/jds`
> 

# 필요 정보

# Request Parameter

없음.

# Request Body

```json
없음.
```

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
|  |  |  |

# ✅ Success Response

### HTTP Status code: `200 OK`

### Content

```json
{
  "isSuccess": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "data": [
    {
      "jdId": 1,
      "companyName": "카카오",
      "targetAffiliate": "카카오페이",
      "jobName": "백엔드 개발자"
    },
    {
      "jdId": 2,
      "companyName": "토스",
      "targetAffiliate": "토스뱅크",
      "jobName": "백엔드 개발자"
    }
  ]
}
```

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
| isSuccess | Boolean | 요청 성공 여부 (true) |
| code | Integer | 응답 상태 코드 (200) |
| message | String | 응답 결과 메시지 |
| data | Array | 기업/직무 리스트 (배열) |
| data[].jdId | Long | Company_JD 테이블의 고유 PK (자소서 생성 요청 시 사용됨) |
| data[].companyName | String | 타겟 기업명 (예: 카카오, 토스) |
| data[].targetAffiliate | String | 타겟 계열사명 (계열사가 없는 본사 채용일 경우 null 반환) |
| data[].jobName | String | 타겟 직무명 (예: 백엔드 개발자) |

# ❌ Fail Response

### HTTP Status code: `500 Internal Server Error`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 500,
  "error": "Internal Server Error",
  "code": "SERVER_ERROR",
  "message": "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
  "path": "/api/jds"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 500 Internal Server Error | SERVER_ERROR | 서버 내부 오류가 발생 |

---

# AI 자소서 생성

Method: POST
URI patterns: /api/applications/generate
설명: 선택한 기업/직무와 유저가 저장해 둔 기존 데이터(스펙, 경험)를 바탕으로 AI 자소서 생성을 서버에 요청합니다.
완료: Yes
필요 정보: jdId

선택한 기업/직무와 유저가 저장해 둔 기존 데이터(스펙, 경험)를 바탕으로 AI 자소서 생성을 서버에 요청합니다.

# URL

`POST /api/applications/generate`

> ex) `/api/applications/generate`
> 

# 필요 정보

# Request Parameter

없음.

# Request Body

```json
{
  "jdId": 1
}
```

| **필드명** | **타입** | **필수 여부** | **설명** |
| --- | --- | --- | --- |
| jdId | Long | O | 드롭다운 목록 조회 API에서 선택한 기업/직무의 고유 ID |

# ✅ Success Response

### HTTP Status code: `201 Created`

### Content

```json
{
  "isSuccess": true,
  "code": 201,
  "message": "AI 맞춤형 자소서 생성을 시작합니다.",
  "data": {
    "applicationId": 15,
    "status": "PENDING"
  }
}
```

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
| isSuccess | Boolean | 요청 성공 여부 (true) |
| code | Integer | 응답 상태 코드 |
| message | String | 응답 결과 메시지 |
| data | Object | 생성 요청 결과 데이터 객체 |
| data.applicationId | Long | 방금 생성된 지원서(Application)의 고유 PK |
| data.status | String | 현재 생성 진행 상태 (`PENDING`: 대기/시작됨) |

**💡 프론트엔드 개발 팁 (흐름 처리):**

이 API의 응답을 받으면, 프론트엔드는 화면에 로딩 애니메이션(예: "기업이 원하는 키워드를 추출 중입니다...")을 띄워줍니다. 이후 반환받은 `applicationId`를 가지고 '**생성된 자소서 상세 내용 조회 API**'를 2~3초 간격으로 호출(Polling)하여 상태가 `COMPLETED`로 바뀔 때까지 기다리거나, WebSocket 등을 이용해 결과 화면으로 넘어가면 됩니다.

# ❌ Fail Response

### HTTP Status code: `400 Bad Request`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 400,
  "error": "Bad Request",
  "code": "INVALID_INPUT",
  "message": "유효하지 않은 기업/직무 정보이거나, 필수 데이터가 부족합니다.",
  "path": "/api/applications/generate"
}
```

### HTTP Status code: `500 Internal Server Error`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 500,
  "error": "Internal Server Error",
  "code": "SERVER_ERROR",
  "message": "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
  "path": "/api/applications/generate"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 400 Bad Request | INVALID_INPUT | 잘못된 입력값 |
| 500 Internal Server Error | SERVER_ERROR | 서버 내부 오류가 발생 |

---


# 생성된 자소서 목록 조회

Method: GET
URI patterns: /api/applications
설명: 과거에 AI 생성을 요청했던 타겟 기업과 직무, 그리고 현재 생성 상태 목록을 전체 조회합니다.
완료: Yes
필요 정보: 없음.

과거에 AI 생성을 요청했던 타겟 기업과 직무, 그리고 현재 생성 상태 목록을 전체 조회합니다.

# URL

`GET /api/applications`

> ex) `/api/applications`
> 

# 필요 정보

# Request Parameter

없음.

# Request Body

```json
없음.
```

| **필드명** | **타입** | **필수 여부** | **설명** |
| --- | --- | --- | --- |
|  |  |  |  |

# ✅ Success Response

### HTTP Status code: `200 Ok`

### Content

```json
{
  "isSuccess": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "data": [
    {
      "applicationId": 15,
      "companyName": "카카오",
      "targetAffiliate": "카카오페이",
      "jobName": "백엔드 개발자",
      "status": "COMPLETED"
    },
    {
      "applicationId": 14,
      "companyName": "토스",
      "targetAffiliate": "토스뱅크",
      "jobName": "백엔드 개발자",
      "status": "FAILED"
    }
  ]
}
```

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
| isSuccess | Boolean | 요청 성공 여부 (true) |
| code | Integer | 응답 상태 코드 (200) |
| message | String | 응답 결과 메시지 |
| data | Array | 자소서 생성 내역 목록 (배열, 최신순 정렬 권장) |
| data[].applicationId | Long | 지원서(Application)의 고유 PK |
| data[].companyName | String | 타겟 기업명 |
| data[].targetAffiliate | String | 타겟 계열사명 (없을 경우 null) |
| data[].jobName | String | 타겟 직무명 |
| data[].status | String | 생성 진행 상태 (PENDING, ANALYZING, GENERATING, COMPLETED, FAILED) |

# ❌ Fail Response

### HTTP Status code: `500 Internal Server Error`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 500,
  "error": "Internal Server Error",
  "code": "SERVER_ERROR",
  "message": "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
  "path": "/api/applications"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 500 Internal Server Error | SERVER_ERROR | 서버 내부 오류가 발생 |

---

# 생성된 자소서 상세 내용 조회

Method: GET
URI patterns: /api/applications/{applicationId}
설명: 특정 applicationId에 대한 AI 분석 결과와 최종 생성된 자소서 초안을 조회합니다.
완료: Yes
필요 정보: applicationId

특정 applicationId에 대한 AI 분석 결과와 최종 생성된 자소서 초안을 조회합니다.

# URL

`GET /api/applications/{applicationId}`

> ex) `/api/applications/1`
> 

# 필요 정보

# Request Parameter

| **파라미터명** | **타입** | **필수 여부** | **설명** |
| --- | --- | --- | --- |
| applicationId | Long | O | 조회할 생성 내역의 고유 ID (생성 요청 API에서 반환받은 값) |

# Request Body

```json
없음.
```

| **필드명** | **타입** | **필수 여부** | **설명** |
| --- | --- | --- | --- |
|  |  |  |  |

# ✅ Success Response

### HTTP Status code: `200 Ok`

### Content

```json
{
  "isSuccess": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "data": {
    "applicationId": 15,
    "companyName": "카카오",
    "targetAffiliate": "카카오페이",
    "jobName": "백엔드 개발자",
    "status": "COMPLETED",
    "agentAnalysis": "카카오페이 백엔드 직무는 대규모 트래픽 분산 처리와 결제 시스템의 동시성 제어 경험을 가장 중요하게 평가합니다. 지원자의 캡스톤 프로젝트 경험에서 이 부분을 강조하여...",
    "generatedResume": "1. 가장 주도적으로 진행한 프로젝트는 무엇입니까?\n저는 대규모 트래픽 상황을 가정한 티켓팅 서버를 구축하며...\n\n2. 협업 과정에서 겪은 갈등...\n..."
  }
}
```

(참고: `status`가 `PENDING`이나 `ANALYZING`, `GENERATING`일 경우 `agentAnalysis`와 `generatedResume`는 `null`로 반환될 수 있습니다.)

| **필드명** | **타입** | **설명** |
| --- | --- | --- |
| isSuccess | Boolean | 요청 성공 여부 (true) |
| code | Integer | 응답 상태 코드 (200) |
| message | String | 응답 결과 메시지 |
| data | Object | 자소서 생성 상세 데이터 객체 |
| data.applicationId | Long | 지원서(Application)의 고유 PK |
| data.companyName | String | 타겟 기업명 (조인해서 가져옴) |
| data.targetAffiliate | String | 타겟 계열사명 (없을 경우 null) |
| data.jobName | String | 타겟 직무명 |
| data.status | String | 현재 생성 진행 상태 (PENDING, ANALYZING, GENERATING, COMPLETED, FAILED) |
| data.agentAnalysis | String | AI가 분석한 해당 회사의 인재상 및 유저 스펙 매칭 리포트 (완료 시 반환) |
| data.generatedResume | String | AI가 최종 합성하여 생성한 맞춤형 자소서/이력서 초안 텍스트 (완료 시 반환) |

# ❌ Fail Response

### HTTP Status code: `404 Not Found`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 404,
  "error": "Not Found",
  "code": "APPLICATION_NOT_FOUND",
  "message": "해당 지원서 내역을 찾을 수 없습니다.",
  "path": "/api/applications/1"
}
```

### HTTP Status code: `500 Internal Server Error`

### Content

```json
{
  "timestamp": "2026-07-31T23:45:00.000+09:00",
  "status": 500,
  "error": "Internal Server Error",
  "code": "SERVER_ERROR",
  "message": "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
  "path": "/api/applications/1"
}
```

| HTTP Status | code | 발생 조건 |
| --- | --- | --- |
| 404 Not Found | APPLICATION_NOT_FOUND | DB에 해당 applicationId가 존재하지 않는 경우 |
| 500 Internal Server Error | SERVER_ERROR | 서버 내부 오류가 발생 |
