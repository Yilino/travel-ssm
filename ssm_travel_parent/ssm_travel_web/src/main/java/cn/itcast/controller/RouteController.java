package cn.itcast.controller;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.Route;
import cn.itcast.domain.User;
import cn.itcast.service.FavoriteService;
import cn.itcast.service.RouteService;
import cn.itcast.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService;
    @Autowired
    private FavoriteService favoriteService;
    @RequestMapping("/pageQuery")
    public PageBean<Route> pageQuery(String cid, String rname, String currentPage, String rows) throws UnsupportedEncodingException {
        //获取参数
        /*String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String rowsStr = request.getParameter("rows");
        String rname = request.getParameter("rname");*/

        //处理参数
        //将get方式发送的中文rname转码
        if (!StringUtils.isEmpty(rname)){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }

        int cid_int = 0;
        if (!StringUtils.isEmpty(cid) && !"null".equalsIgnoreCase(cid)){
            cid_int =Integer.parseInt(cid);
        }
        int currentPage_int = 0;
        if (!StringUtils.isEmpty(currentPage)){
            currentPage_int =Integer.parseInt(currentPage);
        }else {
            currentPage_int = 1;
        }
        int rows_int = 0;
        if (!StringUtils.isEmpty(rows)){
            rows_int =Integer.parseInt(rows);
        }else {
            rows_int = 5;
        }
        return  routeService.pageQuery(cid_int,currentPage_int,rows_int);
    }
    @RequestMapping("/findOne")
    public Route findOne(String rid)  {

        //调用service层
        Route route = routeService.findOne(rid);
        //以json格式响应
        return route;
    }
    @RequestMapping("/isFavorite")
    public Boolean isFavorite(String rid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        int uid;
        int rid_int=Integer.parseInt(rid);
        if (user==null){
            uid=0;
        }else {
            uid=user.getUid();
        }
        //调用service层
        System.out.println(rid);
        boolean flag = favoriteService.isFavorite(rid_int,uid);
        //以json格式响应
        return flag;
    }
    @RequestMapping("/addFavorite")
    public void addFavorite(String rid,HttpSession session) {
        User user = (User) session.getAttribute("user");
        int uid;
        if (user==null){
            return;
        }else {
            uid=user.getUid();
        }
        //调用service层
        favoriteService.add(rid,uid);
    }
    /**
     * 根据uid查询用户收藏旅游线路信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/myFavorite")
    public PageBean myFavorite(String currentPage,String rows,HttpSession session){



        int currentPage_int = 0;
        if (!StringUtils.isEmpty(currentPage)){
            currentPage_int =Integer.parseInt(currentPage);
        }else {
            currentPage_int = 1;
        }
        int rows_int = 0;
        if (!StringUtils.isEmpty(rows)){
            rows_int =Integer.parseInt(rows);
        }else {
            rows_int = 12;
        }

        User user = (User) session.getAttribute("user");

        int uid;
        if (user==null){
            uid = 0;
        }else {
            uid=user.getUid();
        }

        //调用service层
        PageBean<Route> pageBean = favoriteService.findFavoriteByUid(uid, currentPage_int,rows_int);

        return pageBean;
    }
}
