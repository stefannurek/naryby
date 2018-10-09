package pl.naryby.user;

import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;

@Service
public class AcceptUserService {

    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";

    private final ApplicationUserRepository applicationUserRepository;

    AcceptUserService(final ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public void acceptUser(String userId, String role) throws NoPermissionException, AcceptUserException {

        if(!role.equals(ADMIN_ROLE)) {
            throw new NoPermissionException("No permission exception");
        }

        ApplicationUser applicationUser = applicationUserRepository.findById(Long.parseLong(userId));

        if(applicationUser.getRole().equals(USER_ROLE)) {
            throw new AcceptUserException("User already accepted");
        }

        if(applicationUser.getRole().equals(ADMIN_ROLE)) {
            throw new AcceptUserException("User have admin role");
        }

        applicationUser.setRole(USER_ROLE);
        applicationUserRepository.save(applicationUser);

    }

}
