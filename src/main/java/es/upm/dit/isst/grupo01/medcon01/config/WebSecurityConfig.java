package es.upm.dit.isst.grupo01.medcon01.config; 
import java.security.Principal;

import javax.sql.DataSource;
import es.upm.dit.isst.grupo01.medcon01.config.MySimpleUrlAuthenticationSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

   // Configuración de autenticación para que se use la bbdd
    @Autowired
    DataSource ds;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(ds)
        // Para usar citas_api
           .usersByUsernameQuery("select dni, password, enabled from medico where dni=?")
           .authoritiesByUsernameQuery("select dni, role from medico where dni=?");

    }
    // Permiso de acceso a la consola h2
    @Override
    public void configure(WebSecurity web) throws Exception{
        web
            .ignoring()
            .antMatchers("h2_console/**"); // Comprobar h2 a través del interfaz web
    }
     
    // Configuración de control de accesos
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http 
            .csrf().disable()
            .authorizeRequests() // Define quien puede acceder a los recursos
                .antMatchers("/css/**", "/images/**", "/audio/**","/index","/error","/").permitAll()
                .antMatchers("/medico/**","/aplicaciones_externas/**").hasAnyRole("MEDICO")
                .antMatchers("/kiosko/**","/sala_espera/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
        .and() 
            .formLogin()
                .loginPage("/login_medico").successHandler(myAuthenticationSuccessHandler()).permitAll()
                .loginProcessingUrl("/login_medico")
                .failureUrl("/login_medico?error=true")          
        .and()
            .logout().logoutSuccessUrl("/login_medico").permitAll()
        .and()
            .httpBasic();
    }   
    /*
     * 
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
            .withUser("11111111A").password("{noop}password").roles("MEDICO").and()
            .withUser("1A").password("{noop}password").roles("MEDICO").and()
            .withUser("22222222B").password("{noop}password").roles("MEDICO").and()
            .withUser("33333333C").password("{noop}password").roles("MEDICO").and()
            .withUser("44444444D").password("{noop}password").roles("MEDICO").and()
            .withUser("55555555E").password("{noop}password").roles("MEDICO").and()
            .withUser("admin").password("{noop}admin").roles("ADMIN").and()
            .withUser("paciente").password("{noop}paciente").roles("PACIENTE");
    }
     */
    // Protección de contraseñas


    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
    
     private JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> usersByUsernameQuery(String string) {
        return null;
    }
    
    
}
