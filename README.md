# one

## 🛠 개발 컨벤션
### 네이밍 규칙
- 클래스명: PascalCase (예: `MemberService`)
- 변수/메서드명: camelCase (예: `getMemberInfo`)
- DB 테이블/컬럼명: snake_case (예: `member_id`)
- 상수: UPPER_SNAKE_CASE (예: `MAX_LOGIN_ATTEMPT`)

### 커밋 컨벤션
| 타입       | 설명      |
|----------|---------|
| feat     | 새 기능 추가 |
| fix      | 버그 수정   |
| docs     | 문서 수정   |
| refactor | 코드 리팩토링 |
| test     | 	테스트 코드 추가/수정|
| chore | 빌드, 설정 등 기타 작업|
|ci| CI/CD 파이프라인 관련|



예시:
```
feat: 게시글 좋아요 기능 구현

- member_post_like 테이블 생성
- 좋아요 토글 API 구현
- 중복 좋아요 방지 유니크 제약조건 추가

#23
```


### 브랜치 전략
- `main`: 배포 브랜치
- `develop`: 개발 통합 브랜치
- `feat/기능명`: 기능 개발 브랜치


## 📁 프로젝트 구조

```
com.project.firstboard
├── common/       # 공통 예외, 응답 포맷
├── config/       # 전역 설정 (Swagger, CORS 등)
├── security/     # 인증/인가 (JWT, OAuth)
├── ai/           # AI 채팅 기능
├── external/     # 외부 API 연동 (S3, 결제 등)
├── member/       # 회원 도메인
├── board/        # 게시판 도메인
└── comment/      # 댓글 도메인
```


각 도메인 패키지는 다음 구조를 따릅니다:
```
domain/
├── api/          # Controller, Request/Response DTO
├── application/  # Service (비즈니스 로직)
└── domain/       # Entity, Repository
```