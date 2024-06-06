package sd.project.usermanagementservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sd.project.usermanagementservice.entities.UserType;
import sd.project.usermanagementservice.security.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers("/users/read/**").hasAnyAuthority(UserType.ADMIN.name(), UserType.CLIENT.name())
                                .requestMatchers("/users/delete/**").hasAnyAuthority(UserType.ADMIN.name())
                                .requestMatchers("/users/create").hasAnyAuthority(UserType.ADMIN.name())
                                .requestMatchers("/users/register").permitAll()
                                .requestMatchers("/users/login").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(httpSecurityCorsConfigurer -> {httpSecurityCorsConfigurer.configure(http);});

        return http.build();
    }
}
