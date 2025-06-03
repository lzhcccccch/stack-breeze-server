package cn.lzhch;

import cn.hutool.core.text.CharSequenceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class StackBreezeServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(StackBreezeServerApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        String launchInfo = CharSequenceUtil.format("""
                        
                        ----------------------------------------------------------
                        \t\
                        Application '{}' is running! Access URLs:
                        \t\
                        Local: \t\thttp://localhost:{}
                        
                        ----------------------------------------------------------""",
                env.getProperty("spring.application.name"),
                CharSequenceUtil.isBlank(env.getProperty("server.port")) ? "8080" : env.getProperty("server.port"));

        System.out.println(launchInfo);
    }

}
