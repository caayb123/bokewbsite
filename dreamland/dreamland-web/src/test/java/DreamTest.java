import com.dream.dao.CommentDao;
import com.dream.dao.UserContentDao;
import com.dream.domain.User;
import com.dream.domain.UserContent;
import com.dream.service.UserContentService;
import com.dream.service.UserService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DreamTest{
    /**
     * @Description: 项目测试类
     * @Param: 方法参数
     * @return: 返回值
     * @Author: cyb
     */
    @Autowired
   private UserService userService;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserContentDao userContentDao;
    @Autowired
    private SolrClient solrServer;

    @Autowired
    private UserContentService userContentService;


    @Test
    public void test() throws Exception{
        List<UserContent> list = userContentService.findAll();
        if(list!=null && list.size()>0){
            for (UserContent cont : list){
                SolrInputDocument inputDocument = new SolrInputDocument();
                inputDocument.addField( "comment_num", cont.getCommentNum() );
                inputDocument.addField( "downvote", cont.getDownvote() );
                inputDocument.addField( "upvote", cont.getUpvote() );
                inputDocument.addField( "rpt_time", cont.getRptTime() );
                inputDocument.addField( "content", cont.getContent() );
                inputDocument.addField( "category", cont.getCategory());
                inputDocument.addField( "title", cont.getTitle() );
                inputDocument.addField( "u_id", cont.getuId() );
                inputDocument.addField( "id", cont.getId());
                inputDocument.addField( "personal", cont.getPersonal());
                solrServer.add( inputDocument );
            }
        }

        solrServer.commit();
    }
    @Test
    public void test2() throws Exception{
        String password = new Md5PasswordEncoder().encodePassword("124578963", "784503325@qq.com");
        System.out.println(password);

    }
}
