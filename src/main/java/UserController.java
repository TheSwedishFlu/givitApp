import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;

public class UserController {


    @Controller
    public class ItemController {

        Logger logger = LoggerFactory.getLogger(UserController.class);

        @Autowired
        private DataSource dataSource;

        @GetMapping("/")
        String start() {
            logger.info("Logging is running");
            return "startpage";
        }

    }
}