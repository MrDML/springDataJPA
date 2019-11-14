package top.objccn.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import top.objccn.jpa.dao.CustomerDao;
import top.objccn.jpa.entity.Customer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Auter MrDML
 * @Date 2019-11-13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JpaSecond {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据id查询
     */
    @Test
    public void testFindOne(){
        Customer customer = customerDao.findOne(4l);
        System.out.println("============================");
        System.out.println(customer);
    }

    /**
     * 根据id从数据库查询
     *      @Transactional : 保证getOne正常运行
     *
     *  findOne：
     *      em.find()           :立即加载
     *  getOne：
     *      em.getReference     :延迟加载
     *      * 返回的是一个客户的动态代理对象
     *      * 什么时候用，什么时候查询
     */

    /**
     * 延迟获取
     */
    @Test
    @Transactional
    public void testGetOne(){
        // 注意调用该方法需要添加事务
        // getOne 延迟加载 相当于  em.getReference     :延迟加载
        Customer customer = customerDao.getOne(3l);
        System.out.println("============================");
        System.out.println(customer);
    }

    /**
     * 查询全部
     */
    @Test
    public void findAll(){

        List<Customer> customerList = customerDao.findAll();

        for (Customer customer : customerList) {

            System.out.println(customer);
        }

    }

    /**
     * 分页
     */
    @Test
    public void testFindAllWithPage(){
        //参数1: 页面从0开始
        // 参数2: 每页的行数

        Pageable pageable = new PageRequest(0, 5);
        Page<Customer> customerPage = customerDao.findAll(pageable);

        long totalElements = customerPage.getTotalElements();
        System.out.println("总记录数:"+totalElements);

        int totalPages = customerPage.getTotalPages();
        System.out.println("总页数:" + totalPages);

        List<Customer> customerList = customerPage.getContent();
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

    /**
     * 排序
     */
    @Test
    public void testFindAllWithSort(){
        // 创建一个sort对象
        // 参数1: 排序方式
        // 参数2: 排序的字段,应该是实体类的属性名
        Sort sort = new Sort(Sort.Direction.ASC, "custId");
        List<Customer> customerList = customerDao.findAll(sort);

        for (Customer customer : customerList) {
            System.out.println(customer);
        }

    }


    /**
     * 查询记录数
     */
    @Test
    public void testcount(){
        long count = customerDao.count();
        System.out.println(count);
    }

    /**
     * 查询是否存在该条记录
     */
    @Test
    public void testExists(){
        boolean exists = customerDao.exists(2l);

        System.out.println(exists);
    }

    // ==============================================

    /**
     * jpql
     */
    @Test
    public void testgetAllCustomer(){
        List<Customer> customerList = customerDao.getAllCustomer();

        for (Customer customer : customerList) {

            System.out.println(customer);
        }


    }

    @Test
    public void testgetAllCustomerByPage(){
        Pageable pageable = new PageRequest(0, 5);
        List<Customer> customerList = customerDao.getAllCustomerByPage(pageable);
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

    @Test
    public void testgetCustomerById(){

        Customer customer = customerDao.getCustomerById(2l);

        System.out.println(customer);

    }

    @Test
    public void testgetCustList(){
        List<Customer> customerList = customerDao.getCustList("%上海%", "%李白%");
        for (Customer customer : customerList) {
            System.out.println(customer);
        }

    }

    @Test
//    @Transactional
//    @Commit
    public void  updateSource(){
        customerDao.updateSource("Java",3l);
    }

    @Test
    public void testgetCustomerListByNative(){
        List<Customer> customerList = customerDao.getCustomerListByNative("%李白%");

        for (Customer customer : customerList) {
            System.out.println(customer);
        }

    }

    // =================================================================
    // 方法命名规则查询
    @Test
    public void testfindByCustId(){
        Customer customer = customerDao.findByCustId(4l);
        System.out.println(customer);
    }

    @Test
    public void testfindByCustNameLikeAndCustAddressLike(){
        List<Customer> customerList = customerDao.findByCustNameLikeAndCustAddressLike("%李白%", "%上海%");

        for (Customer customer : customerList) {
            System.out.println(customer);
        }

    }

    @Test
    public void testfindByCustAddressLike(){

        Page<Customer> customerPage = customerDao.findByCustAddressLike("%上海%", new PageRequest(0, 4));

        System.out.println(customerPage.getTotalElements());
        System.out.println(customerPage.getTotalPages());
        List<Customer> customerList = customerPage.getContent();

        for (Customer customer : customerList) {
            System.out.println(customer);
        }

    }


    // ==================================

    @Test
    public void testFindByIdSpecification(){
        Customer customer = customerDao.findOne(new Specification<Customer>() {

            /**
             * CriteriaQuery：sql语句中的各种关键字。例如wherer、order by、group by、having等
             * Root：设置from
             * CriteriaBuilder：设置具体查询条件
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                // 参数1: 字段名称
                // 参数2: 字段的值
                Predicate predicate = criteriaBuilder.equal(root.get("custId"), 3l);

                return predicate;
            }
        });

        System.out.println(customer);
    }

    @Test
    public void testFindByNameAndAddress(){
        customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                // 创建根据名称模糊查询的条件
                Predicate predicateA = criteriaBuilder.like(root.get("custName"), "%李%");

                // 创建一个根据地质模糊查询的条件
                Predicate predicateB = criteriaBuilder.like(root.get("custAddress"), "%上海%");
                // 组合两个条件
                Predicate predicate = criteriaBuilder.and(predicateA, predicateB);

                // 返回组合后的条件
                return  predicate;
            }
        }).forEach(System.out::println);


    }

    @Test
    public void testFindByNameAndAddressWithPage(){

        Page<Customer> page = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                Predicate predicateA = criteriaBuilder.like(root.get("custName"), "%李%");
                Predicate predicateB = criteriaBuilder.like(root.get("custAddress"), "%上海%");

                // 组合查询条件
                Predicate predicate = criteriaBuilder.and(predicateA, predicateB);

                return predicate;
            }
        }, new PageRequest(0, 3));

        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        List<Customer> customerList = page.getContent();

        for (Customer customer : customerList) {
            System.out.println(customer);
        }

    }

    @Test
    public void testFindCustomerWithSort(){

        List<Customer> list = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.like(root.get("custName"), "%李%");

                return predicate;
            }
        }, new Sort(Sort.Direction.DESC, "custId"));

        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test
    public void testFindCustomerWithSort2(){
        List<Customer> customerList = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("custName"), "%李%");


                Predicate result =
                        criteriaQuery.where(predicate).orderBy(criteriaBuilder.desc(root.get("custId"))).getRestriction();
                return result;
            }
        });

        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }





}
