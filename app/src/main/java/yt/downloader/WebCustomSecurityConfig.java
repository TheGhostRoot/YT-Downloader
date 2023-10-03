package yt.downloader;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;

import static org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class WebCustomSecurityConfig {

      //  http.headers().contentSecurityPolicy("script-src 'self' http://localhosts:25533; report-uri http://localhosts:25533;");

    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.headers(headers -> headers
                        .contentTypeOptions(ServerHttpSecurity.HeaderSpec.ContentTypeOptionsSpec::disable)
                        .frameOptions(frameOptions -> frameOptions.mode(SAMEORIGIN))
                        .contentSecurityPolicy(contentSecurityPolicy -> contentSecurityPolicy.policyDirectives("script-src 'self' http://localhosts:25533; report-uri http://localhosts:25533;"))
                        .referrerPolicy(referrerPolicy -> referrerPolicy.policy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.SAME_ORIGIN))

        );

        http.authorizeExchange(exchanges -> exchanges.anyExchange().permitAll());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/*");
    }
}
