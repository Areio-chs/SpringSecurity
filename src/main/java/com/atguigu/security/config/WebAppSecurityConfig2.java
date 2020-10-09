//package com.atguigu.security.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//
//import javax.sql.DataSource;
//
//@Configurable//声明当前类是一个配置类，相当于xml配置文件作用
//@EnableWebSecurity//声明式配置，启用springsecurity安全机制
//public class WebAppSecurityConfig2 extends WebSecurityConfigurerAdapter {
//    @Autowired
//    DataSource dataSource;
//    //这个与数据库版本的认证有关
//    @Autowired
//    UserDetailsService userDetailsService;
////    @Autowired
////    PasswordEncoder passwordEncoder;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//
//        //基于内存的认证方式
////        builder
////                .inMemoryAuthentication()
////                .withUser("tom")			// 指定登录系统的账号
////                .password("123123")			// 指定账号对应的密码
////                // 必须设置角色或权限，否则会出现Cannot pass a null GrantedAuthority collection错误
////                .roles("学徒")//设置角色
////                .and()
////                .withUser("lisi").password("123123").authorities("罗汉拳","武当长拳");//设置权限
//
//        /*基于数据库的认证方案*/
//        /*默认的密码校验是按照明文的*/
////        builder.userDetailsService(userDetailsService);
//        /*md5加密 需要配合passwordEncodingImpl*/
//        // builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        //BCrypt加密    不需要配合其他
//        //userDetailsService方法写着什么人有什么样的权限
//        builder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity security) throws Exception {
//
//        //哪些资源谁可以访问
//        security
//                .authorizeRequests()    // 对请求进行授权
//                .antMatchers("/index.jsp", "/layui/**")    // 设置要进行授权的请求地址 *匹配下一级 **代表匹配所有子路径
//                .permitAll()            // 这些资源不需要登录就可以访问
//
//                .antMatchers("/level1/**").hasRole("学徒")//这些资源就算登录后也只能学徒来访问
//                //相当于调用hasAuthority("ROLE_学徒")，因为其无法区分是角色还是权限
//                .antMatchers("/level2/**").hasRole("大师")//这些资源只有大师可以访问该资源
//
//
//                .antMatchers("/level3/**").hasAuthority("葵花宝典")//只有这个权限可以访问level3
//                .anyRequest()            // 剩余的资源只要认证就可以访问，即登录后就可以访问，必须放在最后
//                .authenticated()        // 需要“认证”后才可以访问
//                .and()
//                .formLogin()            // 指定使用表单作为登录方式
//                .loginPage("/index.jsp")    // 指定登录页地址
//                .usernameParameter("loginacct") //设置表单提交参数名，不设置默认是loginacct
//                .passwordParameter("userpswd")
//                .loginProcessingUrl("/doLogin")//设置提交路径 默认/loginin
//                .defaultSuccessUrl("/main.html")//登录成功后的页面
//                .permitAll();
////        security.csrf().disable();  禁用csrf
//        //如果csrf开启，必须以post方式提交，表单需要添加csrf token的隐藏域
//        security
//                .logout()
//                .logoutUrl("/logout")   //不写这个默认请求路径是/logout
//                .logoutSuccessUrl("/index.jsp");//注销成功后去的页面
//        /*自定义访问拒绝处理页面*/
//        security.exceptionHandling().accessDeniedPage("/to/no/auth/page.html");//在AdminController里定义的路径
//        /*开启记住我功能----cookie版本,用这个其实已经可以了*/
//        //security.rememberMe();//只需要在index页面表单那里参数名写成remember-me即可
//        /*开启记住我功能----数据库版本，需要先在数据库建立表*/
//        JdbcTokenRepositoryImpl ptr = new JdbcTokenRepositoryImpl();
//        ptr.setDataSource(dataSource);
//        security.rememberMe().tokenRepository(ptr);//此时登录后会把token登数据库了
//    }
//
//    public static void main(String[] args) {
//        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
//        String encode = bcpe.encode("123456");
//        System.out.println(encode);
//    }
//}