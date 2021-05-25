import com.gcsj.Service.CertificateYueService;
import com.gcsj.Service.StuCertificateService;
import com.gcsj.Service.StudentService;
import com.gcsj.pojo.Certificate_YUE;
import com.gcsj.pojo.StuCertificate;
import com.gcsj.pojo.student_y;
import org.junit.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class test {

    @Autowired
    private StudentService studentService;
    @Resource
    private StuCertificateService stuCertificateService;
    @Resource
    private CertificateYueService certificateYueService;



    @Test
    public void certificateDataShow()
    {
        Integer value  = 0;
        //获取所有学生信息
        final HashMap<String, Integer> maps = new HashMap<String, Integer>();
        List<student_y> list = studentService.list(null);
        List<Long> ls1 = new ArrayList<>();
        list.forEach(n->ls1.add(n.getStuID()));
        System.out.println(ls1);

        //获取证书ID
        List<StuCertificate> certificates = stuCertificateService.listByIds(ls1);
        List<Long> ls2 = new ArrayList<>();
        certificates.forEach(n->ls2.add(n.getCertificateID()));
        System.out.println(ls2);

        List<Certificate_YUE> certificateYueList = certificateYueService.listByIds(ls2);
        for (Certificate_YUE c:certificateYueList
        ) {
            if (maps.containsKey(c.getCertificateName()))
            {
                value = maps.get(c.getCertificateName())+1;
                maps.put(c.getCertificateName(),value);
            }
            else
            {
                maps.put(c.getCertificateName(),0);
            }
        }
        System.out.println(maps);

    }
}
