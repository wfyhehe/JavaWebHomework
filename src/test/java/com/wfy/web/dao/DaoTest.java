package com.wfy.web.dao;

import com.wfy.web.model.User;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:hibernate-config.xml"})
@Rollback
@Transactional(transactionManager = "transactionManager")
public class DaoTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    public void upTest() {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Menu menu = menuDao.getMenuByName("菜单管理");
//        System.out.println(menuDao.getPre(menu));

//        session.getTransaction().commit();
//        session.close();
    }

    @Test
    public void isRelatedTest() {
//        User user = userDao.getUserByName("admin");
//        System.out.println(user);
//        System.out.println(employeeDao.isRelated(user));
    }

    @Test
    public void hqlTest() {
        User user = userDao.getUserByName("admin");
        String hql = "select count(*) from Employee e where e.user.id = ?";
        System.out.println(hibernateTemplate.find(hql, user.getId()));
    }

    @Test
    public void ActionTest() {
//        User user = userDao.getUserByName("admin");
//        List<String> actions =  actionDao.getActionsByUser(user);
//        System.out.println(actions);
    }

}
