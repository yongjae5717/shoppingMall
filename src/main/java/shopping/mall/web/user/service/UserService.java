package shopping.mall.web.user.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mall.common.exception.ApiException;
import shopping.mall.common.exception.ExceptionEnum;
import shopping.mall.common.security.jwt.JwtFilter;
import shopping.mall.common.security.jwt.SecurityUtil;
import shopping.mall.common.security.jwt.TokenProvider;
import shopping.mall.domain.entity.Authority;
import shopping.mall.domain.entity.User;
import shopping.mall.domain.repository.UserRepository;
import shopping.mall.web.user.dto.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public UserCreateResponse signup(UserDto userDto) {
        if (userRepository.findOneByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_EMAIL);
        }

        if (userRepository.findByNickname(userDto.getNickname()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_NICKNAME);
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .birth(userDto.getBirth())
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(user);
        user.setUsername(user.getUsername() + "#" + user.getUserId());

        return new UserCreateResponse(true, "회원 가입 성공");
    }

    public String getUsernameWithEmail(String email){
        UserDto userDto = UserDto.from(userRepository.findOneByEmail(email).orElse(null));
        return userDto != null ? userDto.getUsername() : null;
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER))
        );
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest){
        String username = getUsernameWithEmail(userLoginRequest.getEmail());

        if(userLoginRequest.getEmail().isEmpty() || userLoginRequest.getPassword().isEmpty()) {
            throw new ApiException(ExceptionEnum.MISSING_REQUIRED_ITEMS);
        }

        if(userRepository.findOneWithAuthoritiesByUsername(username).isEmpty()){
            throw new ApiException(ExceptionEnum.NOT_FOUND_EMAIL);
        }

        UserDto userDto = getUserWithAuthorities(username);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, userLoginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        ResponseEntity<TokenDto> tokenDtoResponseEntity = new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);

        if (tokenDtoResponseEntity.getBody().getToken().isEmpty()) {
            throw new ApiException(ExceptionEnum.TOKEN_EMPTY);
        }

        return new UserLoginResponse(true, "로그인 성공", tokenDtoResponseEntity.getBody(), userDto);
    }

    @Transactional
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest){
        User user = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));

        if (updateUserRequest.getNickname() == null){
            user.updateUser(passwordEncoder.encode(updateUserRequest.getPassword()));
        }
        else{
            if (userRepository.findByNickname(updateUserRequest.getNickname()).orElse(null) != null) {
                throw new ApiException(ExceptionEnum.DUPLICATE_NICKNAME);
            }
            user.updateUser(passwordEncoder.encode(updateUserRequest.getPassword()),updateUserRequest.getNickname());
        }

        return new UpdateUserResponse(true, "회원수정 성공");
    }

    @Transactional
    public DeleteUserResponse deleteUser(){
        User user = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));

        userRepository.delete(user);

        return new DeleteUserResponse(true, "회원탈퇴 성공");
    }


    //회원 전체 조회
    public List<User> findMembers() {
        return userRepository.findAll();
    }
}