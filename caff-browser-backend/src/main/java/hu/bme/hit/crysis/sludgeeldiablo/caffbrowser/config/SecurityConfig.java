package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.config;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.enums.RoleName;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security.AuthenticationFilter;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security.SecurityVariables.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.cors.enabled}")
    private Boolean corsEnabled;

    @Value("${security.csrf.disabled}")
    private Boolean csrfDisabled;

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationFilter authenticationFilter;
    private final AuthorizationFilter authorizationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        if (Boolean.TRUE.equals(corsEnabled)) {
            http.cors();
        }
        if (Boolean.TRUE.equals(csrfDisabled)) {
            http.csrf().disable();
        }
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(LOGIN_URL, REFRESH_TOKEN_URL, PUBLIC_URL, IMAGES_URL).permitAll();
        http.authorizeRequests().antMatchers(MOD_URL).hasAnyAuthority(RoleName.MODERATOR.toString());
        http.authorizeRequests().antMatchers(ADMIN_URL).hasAnyAuthority(RoleName.ADMINISTRATOR.toString());
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
