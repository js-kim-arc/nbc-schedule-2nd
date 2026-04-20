package nbc.schedule.User.presentaion;

import lombok.RequiredArgsConstructor;
import nbc.schedule.User.application.dto.request.UserCreateRequest;
import nbc.schedule.User.application.dto.request.UserUpdateRequest;
import nbc.schedule.User.application.dto.response.UserResponse;
import nbc.schedule.User.domain.Exception.UserDomainException;
import nbc.schedule.User.domain.User;
import nbc.schedule.User.infrastructure.persistence.UserRepository;
import nbc.schedule.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본: 읽기 전용. 쓰기는 메서드 레벨에서 재선언
public class UserService {

    private final UserRepository userRepository;

    // -----------------------------------------------------------------------
    // 생성
    // -----------------------------------------------------------------------

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        checkEmailDuplicate(request.getEmail());
        return UserResponse.from(userRepository.save(request.toUser()));
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

        if (request.getEmail() != null) {
            checkEmailDuplicate(request.getEmail());
        }

        request.applyTo(user); // dirty checking → @Transactional 종료 시 UPDATE 자동 실행
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

    private void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw UserDomainException.of(ErrorCode.USER_EMAIL_DUPLICATE);
        }
    }
}
