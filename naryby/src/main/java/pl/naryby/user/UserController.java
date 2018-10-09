package pl.naryby.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String STARTING_ROLE = "BLOCKED";

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AcceptUserService acceptUserService;

    public UserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          AcceptUserService acceptUserService) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.acceptUserService = acceptUserService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(STARTING_ROLE);
        applicationUserRepository.save(user);
    }

    @PutMapping("/accept/{userId}")
    public void acceptUser(@PathVariable("userId") String userId, Authentication authentication) throws NoPermissionException, AcceptUserException {

        final String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        acceptUserService.acceptUser(userId, role);

    }
}