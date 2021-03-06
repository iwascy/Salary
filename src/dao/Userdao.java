package dao;
import domain.Department;
import domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

/**
 * 操作数据库中user
 */
public class Userdao {

    //声明JDBCTemplate对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    public  User login(User loginUser){
        try {
            //sql语句
            String sql = "select * from user where username = ? and password = ?";
            //调用query方法
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>
                    (User.class), loginUser.getUsername(), loginUser.getPassword(),loginUser.getId());

            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public void update(User user,String eno) {
        //1.定义sql
        String sql = "update user set password = ? where username = ?";
        //2.执行sql
        template.update(sql,user.getPassword(),eno);
    }
}
