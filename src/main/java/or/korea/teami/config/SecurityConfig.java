package or.korea.teami.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import or.korea.teami.config.security.CAccessDeniedHandler;
import or.korea.teami.config.security.CAuthenticationEntryPoint;
import or.korea.teami.config.security.JwtAuthenticationFilter;
import or.korea.teami.config.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtTokenProvider jwtTokenProvider;
	private final CAuthenticationEntryPoint CAuthenticationEntryPoint;
	private final CAccessDeniedHandler CAccessDeniedHandler;


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/v2/api-docs",
				"/configuration/ui",
				"/swagger-resources/**",
				"/configuration/security",
				"/swagger-ui.html",
				"/webjars/**",
				"/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable()    // security에서 기본으로 생성하는 login페이지 사용 안 함
			.csrf().disable()    // REST API 사용하기 때문에 csrf 사용 안 함
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)    // JWT인증사용하므로 세션 생성 안함
			.and()
			.authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
			.antMatchers( "/v1/**", "/h2-console/**").permitAll() // 가입 및 인증 주소는 누구나 접근가능
			.anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(CAuthenticationEntryPoint)
			.accessDeniedHandler(CAccessDeniedHandler)
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	}
}
