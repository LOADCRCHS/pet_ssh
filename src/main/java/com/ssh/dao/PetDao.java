package com.ssh.dao;

import com.ssh.pojo.ConsumeRecordTO;
import com.ssh.pojo.MyPageInfo;
import com.ssh.pojo.PageInfo;
import com.ssh.pojo.PetTO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;
import java.util.Map;

public class PetDao extends HibernateDaoSupport {


    @Autowired
    private SessionFactory sessionFactory;

    //no use
    List<PetTO> getAllPetTO() {
        Session session = super.getSessionFactory().openSession();
        String hql = "from PetTO";
        Query query = super.getSessionFactory().openSession().createQuery(hql);
        return query.list();
    }
    //no use
    PageInfo<PetTO> getPetPage(int pageSize, int pageNum, PetTO petTO) {
        if (pageSize == 0) {
            pageSize = 1;
        }
        if (pageNum == 0) {
            pageNum = 5;
        }

        String hql = "from PetTO";


        Query query = super.getSessionFactory().openSession().createQuery(hql);

        query.setFirstResult((pageSize - 1) * pageNum);
        query.setMaxResults(pageNum);

        return new PageInfo<PetTO>(pageSize, pageNum, (int) getPetCount(), query.list());
    }
    //no use
    private <T> Query selectStatement(int pageSize, int pageNum, Class<T> className, Map<String, String> varables, Session session) {
        if (pageSize == 0) {
            pageSize = 1;
        }
        if (pageNum == 0) {
            pageNum = 5;
        }

        StringBuilder stringBuilder = new StringBuilder();
        /*
         * 通过className得到该实体类的字符串形式,
         */
        stringBuilder.append("from " + className.getName());
        stringBuilder.append(" where 1=1 ");
        /*
         * 动态的拼接sql语句,如果一个属性的值为"", 则不往条件中添加.
         */
        for (Map.Entry<String, String> entry : varables.entrySet()) {
            if (!entry.getValue().equals("")) {
                //todo 根据name用like查询
                stringBuilder.append(" and " + entry.getKey() + "=:" + entry.getKey());
            }
        }

        Query query = session.createQuery(stringBuilder.toString());
        /*
         * 动态的给条件赋值
         */
        for (Map.Entry<String, String> entry : varables.entrySet()) {
            if (!entry.getValue().equals("")) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.setFirstResult((pageSize - 1) * pageNum);
        query.setMaxResults(pageNum);
        return query;
    }

    //带条件的分页查询
    public MyPageInfo<PetTO> getPetByPage(int pageNum, int pageSize, PetTO petTO) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PetTO.class);

        if (petTO.getName() != null && !"".equals(petTO.getName())) {
            detachedCriteria.add(Restrictions.like("name", "%" + petTO.getName() + "%"));
        }
        if (petTO.getBreederPhone() != null && !"".equals(petTO.getBreederPhone())) {
            detachedCriteria.add(Restrictions.eq("breederPhone", petTO.getBreederPhone()));
        }


        Criteria cri = detachedCriteria.getExecutableCriteria(sessionFactory.openSession());
        List list = cri.list();
        cri.setFirstResult((pageNum - 1) * pageSize);
        cri.setMaxResults(pageSize);

        return new MyPageInfo<PetTO>(list.size(), cri.list());
    }

    public long getPetCount() {
        final String hql = "select count(*) from PetTO ";
        return (long) sessionFactory.openSession().createQuery(hql).uniqueResult();
        /*return (long) super.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session arg0)
                    throws HibernateException {
                Query query = arg0.createQuery(hql);
                return query.uniqueResult();
            }

        });*/
    }

    public MyPageInfo<ConsumeRecordTO> getConsumeRecordTOByPetTOId(int pageSize, int pageNum, ConsumeRecordTO consumeRecordTO) {
        if (pageSize == 0) {
            pageSize = 1;
        }
        if (pageNum == 0) {
            pageNum = 5;
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConsumeRecordTO.class);
        if (consumeRecordTO.getPetId() != null) {
            detachedCriteria.add(Restrictions.eq("petId", consumeRecordTO.getPetId()));
        }
        if (consumeRecordTO.getStartTime() != null) {
            detachedCriteria.add(Restrictions.gt("consumeDate", consumeRecordTO.getStartTime()));
        }
        if (consumeRecordTO.getEndTime() != null) {
            detachedCriteria.add(Restrictions.lt("consumeDate", consumeRecordTO.getEndTime()));
        }
        Criteria cri = detachedCriteria.getExecutableCriteria(sessionFactory.openSession());
        cri.setFirstResult((pageNum - 1) * pageSize);
        cri.setMaxResults(pageSize);
        return new MyPageInfo<ConsumeRecordTO>(cri.list().size(), cri.list());
    }

    public double getBalanceByPetTOId(Integer petTOId) {
        return sessionFactory.openSession().get(PetTO.class, petTOId).getBalance();
    }

    public void changeBalance(Double money, Integer petId) {
        PetTO petTO = new PetTO();
        petTO.setId(petId);
        petTO.setBalance(money);
        sessionFactory.openSession().save(petTO);
    }

    public void addConsumeRecordTO(ConsumeRecordTO consumeRecordTO) {
        sessionFactory.openSession().save(consumeRecordTO);
    }

    public void addOrUpdatePet(PetTO pet) {
        sessionFactory.openSession().saveOrUpdate(pet);
    }
}
