package sage;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

public class HibernateTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        Configuration cfg = new Configuration().configure();
        ServiceRegistryBuilder srb = new ServiceRegistryBuilder().applySettings(cfg.getProperties());
        SessionFactory sf = cfg.buildSessionFactory(srb.buildServiceRegistry());
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        //
        tx.commit();
        session.close();
    }

}
