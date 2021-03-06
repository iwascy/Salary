package service.impl;

import dao.Impl.EmployeeDaoImpl;
import dao.dao;
import domain.Employee;
import domain.User;
import operate.PageBean;
import service.Service;

import java.util.List;
import java.util.Map;

public class EmployeeUpdateServiceImpls implements Service {
    private dao dao = new EmployeeDaoImpl();
    @Override
    public List<Employee> findAll() {
        return dao.findALL();
    }

    @Override
    public User login(User user) {
        return null;
    }

    @Override
    public boolean repeat(Employee employee) {
        return false;
    }


    @Override
    public void addUser(Employee employee) {
        dao.add(employee);
    }

    @Override
    public void deleteUser(String eno) {
        dao.delete(eno);
    }

    @Override
    public Employee findUserById(String eno) {
        return dao.findById(eno);
    }

    @Override
    public void updateUser(Employee employee) {
        dao.update(employee);
    }

    @Override
    public void delSelectedUser(String[] enos) {
        if(enos != null && enos.length > 0){
            //1.遍历数组
            for (String id : enos) {
                //2.调用dao删除
                dao.delete(id);
            }
        }

    }

    @Override
    public PageBean<Employee> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {

        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        if(currentPage <=0) {
            currentPage = 1;
        }
        //1.创建空的PageBean对象
        PageBean<Employee> pb = new PageBean<Employee>();
        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        //3.调用dao查询总记录数
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //4.调用dao查询List集合
        //计算开始的记录索引
        int start = (currentPage - 1) * rows;
        List<Employee> list = dao.findByPage(start,rows,condition);
        pb.setList(list);

        //5.计算总页码
        int totalPage = (totalCount % rows)  == 0 ? totalCount/rows : (totalCount/rows) + 1;
        pb.setTotalPage(totalPage);


        return pb;
    }
}
