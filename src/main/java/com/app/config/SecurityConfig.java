package com.app.config;

import com.app.config.filter.JwtTokenValidator;
import com.app.service.UserDetailServiceImpl;
import com.app.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired  //HAY QUE INYECTARLO, ES UN OBJETO DE SPRING SECURITY
    AuthenticationConfiguration authenticationConfiguration; //TAMBIEN SE PUEDE ENVIAR DIRECTAMENTE COMO ARGUMENTO EN LA FUNCION DE AUTHENTICATIONMANAGER

    //    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(crsf -> crsf.disable()) //cross side request foralgo SE UTILIZA SIEMPRE QUE HAY FORMULARIOS, EN REST SE UTILIZA PERO MAS "DEFAULT"
//                .httpBasic(Customizer.withDefaults())//CUANDO SOLO VAMOS A USAR USER Y PASSWORD, SI SE UTILIZA TOKEN JWT, ES OTRO MODO
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //STATELESS, GUARDA EL OBJETO QUE SE CREA CUANDO ALGUIEN INICIA SESION EN UNA PAG(QUE QUEDE TU SESION ABIERTA), COMO ACA NO QUIERO GUARDARLA SE USA STATELESS
    //               .authorizeHttpRequests(http -> {
//
    //                   //Configurar EndPoints Publicos primero
//                    http.requestMatchers(HttpMethod.GET, "/auth/hello").permitAll();//permite que todos los que envien ese request tengan acceso
//
//                    //Configurar los EndPoints Privados
//                    http.requestMatchers(HttpMethod.GET, "/auth/helloSecured").hasAuthority("READ");//asi se definen el control de roles segun la request
//
//                    //Configurar el resto de endpoints - NO ESPECIFICADOS
//                    http.anyRequest().denyAll(); //denyAll para cuando quiero rechazar el acceso a los endpoints no especificados
//                    //http.anyRequest().authenticated();//authenticated para cuando quiero dejar el acceso a los endpoints libre si se logean, los endpoints no especificados
//                })
//                .build();
    //   }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(crsf -> crsf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(HttpMethod.POST,"/auth/*").permitAll();


                    http.requestMatchers(HttpMethod.GET, "/method/get").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/method/post").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.PUT, "/method/post").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.DELETE, "/method/delete").hasAuthority("DELETE");
                    http.requestMatchers(HttpMethod.PATCH, "/method/patch").hasAuthority("REFACTOR");

                    http.anyRequest().denyAll();

                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    //EL AuthenticationManager funciona con el AuthenticationConfiguration por este motivo hay que instanciarlo
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // return NoOpPasswordEncoder.getInstance(); //ESTE METODO ESTA DEPRECADO ES VIEJO Y NO ENCRIPTA, SE UTILIZA PARA PRUEBAS, LUEGO SE CAMBIA
    }

    //Con este main, llamo a una instancia del bcrypt para encriptar una contraseña, por si harcodeo un user
   // public static void main(String[] args) {
     //   System.out.println(new BCryptPasswordEncoder().encode("1234"));}

}
