package boot.my.first.boot.controller.config;

import boot.my.first.boot.controller.Model.Permission;
import boot.my.first.boot.controller.Model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //глобально секьюрити в преложении прописано в методах
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()       //механизм защиты от csrf угрозы
                .authorizeRequests()
                .antMatchers("/").permitAll()       //на эту страницу имеют доступ все

//  Удаляем методы после добавления аннотации  @EnableGlobalMethodSecurity(prePostEnabled = true)
//                .antMatchers(HttpMethod.GET,"/api/**").hasAuthority(Permission.DEVELOPERS_READ.getPermission())  //hasAnyRole(Role.USER.name(),Role.ADMIN.name())   //одна из ролей
//                .antMatchers(HttpMethod.POST,"/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())      //hasRole(Role.ADMIN.name())   //только одна роль
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())  //доступ согласно авторизации     //hasRole(Role.ADMIN.name())  доступ согласно роли
//                .antMatchers(HttpMethod.PATCH, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())   //доступ согласно авторизации     //hasRole(Role.ADMIN.name())   доступ согласно роли
                .anyRequest()       //каждый запрос
                .authenticated()    //должен быть аутефицирован
                .and()                  //  и
                .formLogin()  //ЗАМЕНЯЕМ После удаления строк выше и добавления аннотации  @EnableGlobalMethodSecurity(prePostEnabled = true) //.httpBasic();  //с помощью base64 (аутифицирован с помощью base64)
                .loginPage("/auth/login").permitAll()   //направлям всех на страницу авторизации
                .defaultSuccessUrl("/auth/success")    //в случае успеха направляем на страницууспеха
                .and()
                .logout()       //обращаемся к logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))  //логаутРеквестМатчер должен быть обработан АнтРеквестМатчером по ссылке /auth/logout только методом POST
                .invalidateHttpSession(true)                //инвалидируй/отмени сессию
                .clearAuthentication(true)                  //уничтожь аутентификацию
                .deleteCookies("JSESSIONID")        //удалить куки ДжетСешнайди
                .logoutSuccessUrl("/auth/login");       //перенаправь на страницу /auth/login


    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(          //в задаче поменять на
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .authorities(Role.ADMIN.getAuthorities()) //меняем роль на авторити и АДМИН на Авторити      //.roles(Role.ADMIN.name())     //roles("ADMIN") изначально
                        .build(),
                User.builder()                      //создай пользователя
                        .username("user")           //с именем  user
                        .password(passwordEncoder().encode("user")) //с паролем user
                        .authorities(Role.USER.getAuthorities())       //  меняем роль на авторити и юзер на авторити  .roles(Role.USER.name())             //роль user
                        .build()           //создать/построить
        );
    }
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

}
