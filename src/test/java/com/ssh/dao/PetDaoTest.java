package com.ssh.dao;

import com.ssh.pojo.PageInfo;
import com.ssh.pojo.PetTO;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * PetDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 23, 2018</pre>
 */
public class PetDaoTest {

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
    private PetDao petDao = (PetDao) applicationContext.getBean("petDao");

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getAllPetTO(DetachedCriteria criteria)
     */
    @Test
    public void testGetAllPetTO() throws Exception {
        PetTO pet = new PetTO();
        List<PetTO> allPetTO = petDao.getAllPetTO();
        for (PetTO petTO : allPetTO) {
            System.out.println(petTO);
        }
    }


    @Test
    public void testGetPetPage() {
        PetTO petTO = new PetTO();

        PageInfo<PetTO> petPage = petDao.getPetPage(1, 5, petTO);
        List<PetTO> list = petPage.getList();
        for (PetTO to : list) {
            System.out.println(to);
        }
    }

    @Test
    public void testGetPetByPage() {
        PetTO petTO = new PetTO();
        petTO.setName("二");
//        PageInfo<PetTO> petByPage = petDao.getPetByPage(2 , 2, petTO);
//        for (PetTO to : petByPage.getList()) {
//            System.out.println(to);
//        }
    }

    /**
     * Method: getConsumeRecordTOByPetTOId(ConsumeRecordTO consumeRecordTO)
     */
    @Test
    public void testGetConsumeRecordTOByPetTOId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getBalanceByPetTOId(Integer petTOId)
     */
    @Test
    public void testGetBalanceByPetTOId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: increaseBalance(Double money, Integer petTOId)
     */
    @Test
    public void testIncreaseBalance() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: decreaseBalance(Double money, Integer petTOId)
     */
    @Test
    public void testDecreaseBalance() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addConsumeRecordTO(ConsumeRecordTO consumeRecordTO)
     */
    @Test
    public void testAddConsumeRecordTO() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addPetTO(PetTO PetTO)
     */
    @Test
    public void testAddPetTO() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updatePetTO(PetTO PetTO)
     */
    @Test
    public void testUpdatePetTO() throws Exception {
//TODO: Test goes here... 
    }


} 
