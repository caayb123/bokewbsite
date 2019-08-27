package com.dream.service.impl;

import com.dream.dao.UserContentDao;
import com.dream.domain.User;
import com.dream.domain.UserContent;
import com.dream.service.SolrService;
import com.dream.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SolrServiceImpl implements SolrService {

    @Autowired
    private HttpSolrClient solrClient;
    @Autowired
    private UserService userService;

    @Override
    public PageInfo<UserContent> findByKeyWords(String keyword, Integer pageNum) {
        SolrQuery solrQuery=new SolrQuery();
        //设置查询条件
        solrQuery.setQuery("title:"+keyword);
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField( "title" );
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost( "</span>" );
        if (pageNum==null||pageNum<1){
            pageNum=1;
        }
        solrQuery.setStart((pageNum-1)*7);
        solrQuery.setRows(7);
        solrQuery.addSort("rpt_time", SolrQuery.ORDER.desc);

        //开始查询

        try {
            QueryResponse response = solrClient.query(solrQuery);
            //获得高亮数据集
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //获得结果集
            SolrDocumentList results = response.getResults();
            //获得总数量
            long totalNum = results.getNumFound();
            List<UserContent> list = new ArrayList<>();
            for (SolrDocument solrDocument : results) {
                //创建文章对象
                UserContent content = new UserContent();
                //文章id
                String id = (String) solrDocument.get("id");
                Object content1 = solrDocument.get("content");
                Object commentNum = solrDocument.get("comment_num");
                Object downvote = solrDocument.get("downvote");
                Object upvote = solrDocument.get("upvote");
                Object uid =solrDocument.get("u_id");
                Object rpt_time = solrDocument.get("rpt_time");
                Object category = solrDocument.get("category");
                Object personal = solrDocument.get("personal");

                //获取高亮数据集合中的文章标题
                Map<String, List<String>> stringListMap = highlighting.get(id);
                String title = stringListMap.get("title").get(0);

                content.setId(Long.parseLong(id));
                content.setCommentNum(Integer.parseInt(commentNum.toString()));
                content.setDownvote(Integer.parseInt(downvote.toString()));
                content.setUpvote(Integer.parseInt(upvote.toString()));
                content.setuId(Long.parseLong(uid.toString()));
                content.setTitle(title);
                //无法将javabean直接写入solr索引库中，因此需要多一次查询user并注入到cont对象中
                User user = userService.findById(Long.parseLong(uid.toString()));
                content.setUser(user);
                content.setPersonal(personal.toString());
                Date date = (Date) rpt_time;
                content.setRptTime(date);
                List<String> clist = (ArrayList) content1;
                content.setContent(clist.get(0).toString());
                content.setCategory(category.toString());
                if (personal.toString().equals("0")) {
                    list.add(content);
                }
            }
            PageInfo<UserContent> pageInfo=new PageInfo<>(list);
            pageInfo.setPageNum(pageNum);
            Long longPage=totalNum%7==0?totalNum/7:(totalNum/7)+1;
            int pages = longPage.intValue();
            pageInfo.setPages(pages);
            pageInfo.setTotal(totalNum);
            return pageInfo;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         return null;

    }

    @Override
    public void addUserContent(UserContent userContent) {
                if (userContent!=null){
                    addDocument(userContent);
                }
    }

    @Override
    public void updateUserContent(UserContent userContent) {
        if (userContent!=null){
            addDocument(userContent);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            solrClient.deleteById(id.toString());
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDocument(UserContent cont){
        try {
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
            solrClient.add( inputDocument );
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
