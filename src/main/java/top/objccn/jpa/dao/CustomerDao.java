package top.objccn.jpa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import top.objccn.jpa.entity.Customer;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @Auter MrDML
 * @Date 2019-11-13
 */


/**
 * Customer管理的Dao
 * 只需要创建一个接口即可，需要继承JpaRepository接口。
 * 泛型：
 *  dao关联的Entity类
 *  对应表的主键的数据类型
 */
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    @Query(value = "from Customer")
    List<Customer> getAllCustomer();

    @Query("from Customer")
    List<Customer> getAllCustomerByPage(Pageable pageable);

    // ?1 代表参数的占位, 其中1对象方法中的参数索引
    @Query("from Customer where custId = ?1")
    Customer getCustomerById(Long id);


    @Query("from Customer  where custName like ?2 and custAddress like ?1")
    List<Customer> getCustList(String address,String name);


    /***
     * 更新操作
     * 需要添加@Modifying 注解
     * 对数据进行修改需要添加事务
     * @param source
     * @param id
     */
    @Query("update Customer set custSource = ?1 where custId = ?2")
    @Modifying
    @Transactional
    void updateSource(String source, Long id);

    /**
     * nativeQuery:使用本地的sql的方式查询
     * @return
     */
    @Query(value = "select  * from cst_customer_other  where cust_name like ?1", nativeQuery = true)
    List<Customer>getCustomerListByNative(String name);

    /**方法命名规则查询
     *
     * findBy+属性+And+属性
     *
     * @param id
     * @return
     */

     Customer findByCustId(Long id);

    /**
     * 方法命名规则查询
     *
     * @param name
     * @param adress
     * @return
     */

     List<Customer> findByCustNameLikeAndCustAddressLike(String name,String adress);



     Page<Customer> findByCustAddressLike(String adress, Pageable pageable);



}