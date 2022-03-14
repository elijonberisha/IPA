package ch.cs.eb.ipa.config;

import ch.cs.eb.ipa.model.UserAuthority;
import ch.cs.eb.ipa.repository.CUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = UserAuthority.ADMIN.name();
    private static final String EMPLOYEE = UserAuthority.EMPLOYEE.name();
    private static final String INACTIVE = UserAuthority.INACTIVE.name();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(new CUserRepository());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/logout").hasAnyAuthority(ADMIN, EMPLOYEE, INACTIVE)
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.sessionManagement().maximumSessions(1).expiredUrl("/expired").and().invalidSessionUrl("/expired");
    }
}
