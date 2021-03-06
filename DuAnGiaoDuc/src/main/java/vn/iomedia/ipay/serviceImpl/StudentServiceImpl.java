package vn.iomedia.ipay.serviceImpl;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import vn.iomedia.ipay.connection.SQLConnection;
import vn.iomedia.ipay.contanst.CommonContanst;
import vn.iomedia.ipay.entity.Student;
import vn.iomedia.ipay.service.StudentService;
import vn.iomedia.ipay.utils.ObjectUtils;
import vn.iomedia.ipay.utils.StringUtillStudy;

public class StudentServiceImpl implements StudentService {

    private Logger log = Logger.getLogger(StudentServiceImpl.class);

    @SuppressWarnings("unused")
    @Override
    public void updatePass(Student student, String newPass) {
        EntityManager em = SQLConnection.getConnection();
        try {
            log.debug("update password Student.");
            em.getTransaction().begin();
            int id = em.createQuery("UPDATE Student SET password = :newPass WHERE id = :id")
                    .setParameter("newPass", newPass).setParameter("id", student.getId()).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception exp) {
            log.error(exp);
        } finally {
            SQLConnection.closeConnection();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void updateNumberAndDateRegis(Student student, int number) {
        EntityManager em = SQLConnection.getConnection();
        if (number >= 0) {
            log.debug("if number Registration > 0,then continue update number Registration.");
            log.debug("numerRegis >= 0,update student");
            try {
                String date = StringUtillStudy.getDateRegis();
                log.debug("Date for new registration =" + date);
                em.getTransaction().begin();
                em.createQuery("UPDATE Student SET numberRegis = :number , dateRegis = :dateRegis WHERE id = :id")
                        .setParameter("number", number).setParameter("dateRegis", date)
                        .setParameter("id", student.getId()).executeUpdate();
                em.getTransaction().commit();
                Student stu = (Student) em.createQuery("SELECT stu FROM Student stu WHERE stu.id = :id")
                        .setParameter("id", student.getId()).getSingleResult();
                ObjectUtils.putObjectContext(CommonContanst.STUDENT, stu);
            } catch (Exception exp) {
                log.error(exp);
            } finally {
                SQLConnection.closeConnection();
            }
        }
    }

}
