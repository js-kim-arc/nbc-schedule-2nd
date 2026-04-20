package nbc.schedule.User.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.schedule.User.application.dto.request.UserCreateRequest;
import nbc.schedule.User.application.dto.request.UserUpdateRequest;
import nbc.schedule.User.application.dto.response.UserResponse;
import nbc.schedule.User.presentaion.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // -----------------------------------------------------------------------
    // POST /api/users — 유저 생성 (회원가입)
    // -----------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @Valid @RequestBody UserCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    // -----------------------------------------------------------------------
    // GET /api/users — 전체 유저 조회
    // -----------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // -----------------------------------------------------------------------
    // GET /api/users/{id} — 단건 유저 조회
    // -----------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.findById(id));
    }

    // -----------------------------------------------------------------------
    // PATCH /api/users/{id} — 유저 수정
    // -----------------------------------------------------------------------

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        request.validate(); // ① @Valid 후 교차 검증 (DTO 책임, Service에 검증 노출 X)
        return ResponseEntity.ok(userService.update(id, request));
    }

    // -----------------------------------------------------------------------
    // DELETE /api/users/{id} — 유저 삭제
    // -----------------------------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
