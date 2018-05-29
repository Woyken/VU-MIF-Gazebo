package lt.vu.mif.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "CustomUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable(); //temporary disabled to allow h2-console

        http.authorizeRequests()
                .antMatchers("/payment.xhtml")
                .access("hasAnyRole('USER', 'ADMIN')")

                .and()
                .authorizeRequests()
                .antMatchers("/user*")
                .hasRole("USER")

                .and()
                .authorizeRequests()
                .antMatchers("/admin*")
                .hasRole("ADMIN")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied.xhtml")

                .and()
                .formLogin()
                .loginPage("/login.xhtml")
                .successHandler(successHandler())
                .failureUrl("/login.xhtml?error=true")
                .permitAll()

                .and()
                .logout()
                .logoutSuccessUrl("/main.xhtml")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()

                .and().csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/main.xhtml");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {

        web.ignoring().antMatchers("/*.css");
        web.ignoring().antMatchers("/*.js");
    }

}
