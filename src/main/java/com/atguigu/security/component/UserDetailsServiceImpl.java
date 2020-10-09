package com.atguigu.security.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//方法一是给用户写死权限角色，该方法是用数据库的
//该方法是security自带的
//组件，把普通破解哦实例化到spring容器来管理，泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。
//例如我在实现类中用到了@Autowired注解，被注解的这个类是从Spring容器中取出来的，那调用的实现类也需要被Spring容器管理，加上@Component
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    //在xml配置文件配过了，因为这里简单点没用mybatis所以用了jdbc模板
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.使用SQL语句根据用户名查询用户对象
        String queryUser = "SELECT * FROM t_admin WHERE loginacct = ?";
        //2.查询指定用户的信息
        Map<String, Object> map = jdbcTemplate.queryForMap(queryUser, username);
        /*查询用户id拥有的角色集合*/
        String sql1 = "SELECT t_role.* FROM t_role LEFT JOIN t_user_role ON t_user_role.roleid=t_role.id WHERE t_user_role.userid=?";
        List<Map<String ,Object>> roleList = jdbcTemplate.query(sql1,new ColumnMapRowMapper(),map.get("id"));
        System.out.println("roleList="+roleList);
        /*查询用户拥有的权限集合*/
        String sql2="SELECT DISTINCT t_permission.* FROM t_permission LEFT JOIN t_role_permission ON t_role_permission.`permissionid`=t_permission.`id` LEFT JOIN t_user_role ON t_user_role.userid=t_role_permission.`roleid` WHERE t_user_role.`userid`=?";
        List<Map<String,Object>> permissionList = jdbcTemplate.query(sql2,new ColumnMapRowMapper(),map.get("id"));
        System.out.println("permissionList="+permissionList);
        /*用户所有权限=角色+权限，查出当前登录的人拥有的其对应的authorities权限，而securityconfig又写名了哪些资源需要哪些权限，只要该人有了那些资源对应的权限就可以访问*/
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (Map<String,Object> rolemap : roleList){
            String rolename=rolemap.get("name").toString();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+rolename));

        }
        for (Map<String,Object> permissionmap:permissionList){
            String permissionName = permissionmap.get("name").toString();
            if (!StringUtils.isEmpty(permissionName)){
                authorities.add(new SimpleGrantedAuthority(permissionName));
            }
        }
        System.out.println("authorities="+authorities);
        //进行认证的方法，查出当前登录的人它有的所有权限
        return new User(map.get("loginacct").toString(),map.get("userpswd").toString(),authorities);


    }
}
