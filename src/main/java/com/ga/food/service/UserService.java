package com.ga.food.service;

import com.ga.food.exception.InformationExistException;
import com.ga.food.exception.InformationNotFoundException;
import com.ga.food.mailing.AccountPasswordResetEmailContext;
import com.ga.food.mailing.AccountVerificationEmailContext;
import com.ga.food.mailing.EmailService;
import com.ga.food.model.SecureToken;
import com.ga.food.model.User;
import com.ga.food.model.request.LoginRequest;
import com.ga.food.model.response.LoginResponse;
import com.ga.food.repository.UserRepository;
import com.ga.food.security.JWTUtils;
import com.ga.food.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import com.ga.food.security.MyUserDetailsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${site.base.url.https}")
    private String baseurl;
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;

    @Autowired
    EmailService emailService;

    @Autowired
    private SecureTokenService secureTokenService;
//    @Autowired
//    private SecureTokenService secureTokenService;


    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtUtils=jwtUtils;
        this.myUserDetails=myUserDetails;
    }

    public User createUser(User userObj){
        System.out.println("service calling create user =======>");
        if(!userRepository.existsByEmailAddress(userObj.getEmailAddress())){
            userObj.setPassword(passwordEncoder.encode(userObj.getPassword()));
            User result = userRepository.save(userObj);
            sendConfirmationEmail(userObj);
            return result;
        }
        else{
            throw new InformationExistException("a user with this email already exists "
                    +userObj.getEmailAddress() );
        }

    }
    public User findUserByEmail(String email){
        return userRepository.findUserByEmailAddress(email);
    }
    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest
                            .getEmail(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails=(MyUserDetails) authentication.getPrincipal();
            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        }
        catch (Exception e){
            return ResponseEntity.ok(new LoginResponse("username or password are incorrect"));
        }
    }
    public void sendConfirmationEmail(User user){
        SecureToken secureToken=secureTokenService.createToken();
        secureToken.setUser(user);
        secureTokenService.saveSecureToken(secureToken);
        AccountVerificationEmailContext context = new AccountVerificationEmailContext();
        context.init(user);
        context.setToken(secureToken.getToken());
        context.buildVerificationUrl(baseurl, secureToken.getToken());

        System.out.println("sending email to " + user.getEmailAddress());
        emailService.sendMail(context);
    }

    public void resetPassword(String email){
        SecureToken secureToken=secureTokenService.createToken();
        User user = userRepository.findUserByEmailAddress(email);
        System.out.println("service found user ====> " + user.getUserName());
        secureToken.setUser(user);
        secureTokenService.saveSecureToken(secureToken);
        AccountPasswordResetEmailContext context = new AccountPasswordResetEmailContext();
        context.init(user);
        context.setToken(secureToken.getToken());
        context.buildResetUrl(baseurl, secureToken.getToken());

        System.out.println("sending email to " + user.getEmailAddress());
        emailService.sendMail(context);
    }
    public void resetPasswordActivator(String token,User userObj){
        SecureToken secureToken = secureTokenService.findByToken(token);
        User user=secureToken.getUser();
        user.setPassword(passwordEncoder.encode(userObj.getPassword()));
        userRepository.save(user);
        //secureTokenService.removeToken(secureToken);

    }
}
