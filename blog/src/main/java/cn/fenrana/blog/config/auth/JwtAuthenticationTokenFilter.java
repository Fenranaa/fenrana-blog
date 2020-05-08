package cn.fenrana.blog.config.auth;


import cn.fenrana.blog.jwt.JwtTokenUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private AuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(jwtTokenUtil.getHeader());
        if (StrUtil.isNotEmpty(jwtToken)) {
            try {
                authenticationTokenHandle(jwtToken, request);
            } catch (AuthenticationException e) {
                authenticationEntryPoint.commence(request, response, e);
            }
        } else {
            // 带安全头 没有带token
           // authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is not found"));
        }
        filterChain.doFilter(request, response);

    }

    private void authenticationTokenHandle(String jwtToken, HttpServletRequest request) {
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        if (StrUtil.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.validateToken(jwtToken, username)) {

                List<String> roles = new ArrayList<>();
                roles.add("ROLE_admin");
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                        String.join(",", roles));
                User user = new User(username, "", grantedAuthorities);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new BadCredentialsException("token 验证失败");
            }
        } else {

            throw new BadCredentialsException("token 验证失败");
        }
    }
}
