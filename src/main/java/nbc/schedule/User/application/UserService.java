package nbc.schedule.User.application;

import lombok.RequiredArgsConstructor;
import nbc.schedule.User.presentaion.dto.request.UserCreateRequest;
import nbc.schedule.User.presentaion.dto.request.UserUpdateRequest;
import nbc.schedule.User.presentaion.dto.response.UserResponse;
import nbc.schedule.User.domain.Exception.UserDomainException;
import nbc.schedule.User.domain.User;
import nbc.schedule.User.infrastructure.persistence.UserRepository;
import nbc.schedule.common.config.BcryptPasswordEncoder;
import nbc.schedule.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository        userRepository;
    private final BcryptPasswordEncoder passwordEncoder;

    // -----------------------------------------------------------------------
    // 생성
    // -----------------------------------------------------------------------

    @Transactional
    public UserResponse create(UserCreateRequest request) {

//        초기 dto 구현 로직
//        public void checkEmailDuplicate(UserRepository userRepository) {
//            if (userRepository.existsByEmail(this.email)) {
//                throw UserDomainException.of(ErrorCode.USER_EMAIL_DUPLICATE);
//            }
//        }
//      DTO에 검증 로직을 넣으면 단위 테스트 시 user리포지토리를 의존(리포지토리 Mock을 만들어야함),
//      외부 의존성이 필요한 검증은 Service에서 처리하겠습니다. - 과제 피드백 반영
        checkEmailDuplicate(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return UserResponse.from(userRepository.save(request.toUser(encodedPassword)));
    }

    // -----------------------------------------------------------------------
    // 조회
    // -----------------------------------------------------------------------

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                             .stream()
                             .map(UserResponse::from)
                             .toList();
    }

    public UserResponse findById(Long id) {
        return UserResponse.from(getUserOrThrow(id));
    }

    // -----------------------------------------------------------------------
    // 수정
    // -----------------------------------------------------------------------

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = getUserOrThrow(id);

        if (request.hasEmailChange()) {
            checkEmailDuplicate(request.getEmail());
        }

        request.applyTo(user);
        return UserResponse.from(user);
    }

    // -----------------------------------------------------------------------
    // 삭제
    // -----------------------------------------------------------------------

    @Transactional
    public void delete(Long id) {
        User user = getUserOrThrow(id);
        userRepository.delete(user);
    }

    // -----------------------------------------------------------------------
    // 내부 헬퍼
    // -----------------------------------------------------------------------

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> UserDomainException.of(
                                     ErrorCode.USER_NOT_FOUND, "id=" + id));
    }

    /** 이메일 중복 — DB 조회가 필요한 집합 규칙. Service 책임 */
    private void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw UserDomainException.of(ErrorCode.USER_EMAIL_DUPLICATE);
        }
    }
}