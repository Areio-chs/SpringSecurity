//package com.atguigu.security.component;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
////该方法是security自带的
////组件，把普通破解哦实例化到spring容器来管理，泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。
////例如我在实现类中用到了@Autowired注解，被注解的这个类是从Spring容器中取出来的，那调用的实现类也需要被Spring容器管理，加上@Component
//@Component
//public class UserDetailsServiceImpl2 implements UserDetailsService {
//    //在xml配置文件配过了，因为这里简单点没用mybatis所以用了jdbc模板
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //1.使用SQL语句根据用户名查询用户对象
//        String sql = "SELECT * FROM t_admin WHERE loginacct = ?";
//
//        //2.获取查询结果
//        Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, username);
//
//        //3.获取用户名、密码数据
//        String loginacct = resultMap.get("loginacct").toString();
//        String userpswd = resultMap.get("userpswd").toString();
//
//        //4.创建权限列表
//        //角色必须ROLE_开头，权限不用
//        //此时登录的用户具有学徒的角色，且有罗汉拳的权限，可以访问level1的
//        //给当前登录这个人写死一些他具有的角色/权限，其余的权限对应的资源没得访问
//        List<GrantedAuthority> list = AuthorityUtils.createAuthorityList("ROLE_学徒","罗汉拳");//暂时写死，过后数据库查
//
//        //5.创建用户对象
//        User user = new User(loginacct, userpswd, list);
//
//        return user;
//    }
//}
