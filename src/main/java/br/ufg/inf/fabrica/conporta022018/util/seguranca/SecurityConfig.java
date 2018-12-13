package br.ufg.inf.fabrica.conporta022018.util.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Colocar as urls liberadas para todos, ou seja, não necessita de autenticação.
        http.authorizeRequests().antMatchers("/javax.faces.resource/**",
                "/resources/**",
                "/index.xhtml",
                "/trocarSenha.xhtml",
                "/login.xhtml",
                "/esqueciSenha.xhtml",
                "/error.xhtml")
                .permitAll()
                .anyRequest().authenticated();
        //Colocar a url de login com sucesso.
        http.formLogin().loginPage("/login.xhtml")
                .permitAll().and().logout()
                .permitAll();
        //Para deslocar.
        http.logout().logoutSuccessUrl("/login.xhtml");
        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
