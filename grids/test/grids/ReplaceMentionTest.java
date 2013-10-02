package grids;

import org.junit.Test;

import sage.domain.repository.UserRepository;
import sage.domain.service.TweetPostService;
import sage.entity.User;

public class ReplaceMentionTest {
    @Test
    public void test() {
        String content = "@Admin @Bethia XXX@Admin@Bethia@CentOS社区 ";
        content = "@Admin @Admin@Admin@Admin@Admin@Admin @Admin@Admin @Admin@Admin@Admin @Admi";
        UserRepository ur = new UserRepository() {
            @Override
            public User findByName(String name) {
                User user = new User();
                user.setId(1000);
                user.setName(name);
                return user;
            }
        };
        String output = TweetPostService.replaceMention(content, 0, new StringBuilder(), ur);
        System.out.println(output);
    }
}
