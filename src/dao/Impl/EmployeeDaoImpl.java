package dao.Impl;

import dao.dao;
import domain.Employee;
import domain.Salary;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmployeeDaoImpl implements dao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Employee> findALL() {
        String sql = "select * from employee where eno != '1'";
        List<Employee> employees = template.query(sql, new BeanPropertyRowMapper<>(Employee.class));
        return employees;
    }



    @Override
    public void add(Employee employee) {


        //1.定义sql
        String sql = "insert into employee values(?,?,?,?,?,?,?,?)";
        //2.执行sql
        template.update(sql,employee.getEno(),employee.getEname(), employee.getEdept(), employee.getEsex(), employee.getEgrade(), employee.getErank(), employee.getEage(),employee.getEwelfare());
    }

    @Override
    public boolean re(String eno) {
        String sql = "select eno from employee where eno = ?";

        return false;

    }


    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Salary deptsalary(Salary salary,String dept) {
        String sql = "select sum(allsalary) from salary,employee where salary.sno = employee.eno and edept = ? group by ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Salary>(Salary.class),dept,dept);
    }

    @Override
    public void delete(String eno) {
        //1.定义sql
        String sql = "delete from employee where eno = ?";
        //2.执行sql
        template.update(sql,eno);
    }

    @Override
    public Employee findById(String eno) {
        try {
            String sql = "select * from employee where eno = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<Employee>(Employee.class), eno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Employee employee) {
        String oldeno = employee.getEno();
        System.out.println("oldeno,update:"+oldeno);
        String sql = "update employee set eno = ?,ename = ? ,edept = ? , esex = ? , egrade = ?, erank = ?,eage = ?,ewelfare = ? where eno = ?";
        template.update(sql,employee.getEno(),employee.getEname(), employee.getEdept(), employee.getEsex(), employee.getEgrade(), employee.getErank(), employee.getEage(), employee.getEwelfare(),employee.getId());
        System.out.println("update:"+employee);

    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //1.定义模板初始化sql
        String sql = "select count(*) from employee where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);

        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }



    @Override
    public List<Employee> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from employee  where 1 = 1 and eno != '1'";

        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);

        return template.query(sql,new BeanPropertyRowMapper<Employee>(Employee.class),params.toArray());
    }

    @Override
    public void refresh(String eno) {
        String sql = "update employee set ";
    }


    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
}
