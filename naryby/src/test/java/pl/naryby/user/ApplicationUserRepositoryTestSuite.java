package pl.naryby.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationUserRepositoryTestSuite {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Test
    public void test() {

        //given
        ApplicationUser applicationUser = new ApplicationUser("testLogin", "testPass");

        //when
        applicationUserRepository.save(applicationUser);
        long id = applicationUser.getId();

        //then
        Assert.assertNotEquals(0, id);

        //clean up
        applicationUserRepository.delete(applicationUser);


    }

}
