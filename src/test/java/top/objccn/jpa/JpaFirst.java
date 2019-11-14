package top.objccn.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.objccn.jpa.dao.CustomerDao;
import top.objccn.jpa.entity.Customer;

/**
 * @Auter MrDML
 * @Date 2019-11-13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class JpaFirst {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void addCustomer() {
        //1、初始化spring容器
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext
        // .xml");
        //2、从容器中获得Dao的代理对象。
        //CustomerDao customerDao = applicationContext.getBean(CustomerDao.class);
        //3、使用dao实现增删改查
        Customer customer = new Customer();
        customer.setCustName("李清照");
        customer.setCustAddress("上海");
        customer.setCustLevel("VIP");
        customer.setCustIndustry("UI");

        //插入数据
        customerDao.save(customer);
    }

    @Test
    public void deleteCustmoer(){
        customerDao.delete(1l);
    }

    @Test
    public void updateCustomer(){
        // 根据id查询一条数据,返回一个对象
        Customer customer = customerDao.findOne(2l);
        customer.setCustPhone("13807261673");
        customer.setCustSource("iOS");
        // 将修改的字段更新到数据库
        customerDao.save(customer);
    }


    @Test
    public void findById(){

        // 通过ID查询
        Customer customer = customerDao.findOne(2l);

        System.out.println("============================");

        System.out.println(customer + "......");


    }






}
