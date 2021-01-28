package ma.s.myappws.security;


import ma.s.myappws.services.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecrutiry extends WebSecurityConfigurerAdapter {


    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecrutiry(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*
     *
     * this method we configure all the url can users or application get acces to it
     * antMatchers let us to define which method can application acces to giving url
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, SecurityConstans.SIGN_UP_URL)
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(getAuthenticationFilter()) //.addFilter(new AuthenticationFilter(authenticationManager())); //old method for filer url with /login
                    .addFilter(new AuthorizationFilter(authenticationManager()))
                    .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless if u r working with API

    }


    /*
     * To change the url of login '/login' to something else
     * we use this function
     */
    protected AuthenticationFilter getAuthenticationFilter()throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
