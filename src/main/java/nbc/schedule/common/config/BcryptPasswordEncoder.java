package nbc.schedule.common.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoder {

    private static final int COST = 12; // BCrypt 비용 인수. 높을수록 느려짐 (권장: 10~14)

    /**
     * 원문 비밀번호를 BCrypt 해시로 인코딩한다.
     * 결과는 {@code $2a$12$...} 형태의 60자 문자열이다.
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults()
                     .hashToString(COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer()
                                     .verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}