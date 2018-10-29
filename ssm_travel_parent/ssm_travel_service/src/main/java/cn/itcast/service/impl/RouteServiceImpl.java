package cn.itcast.service.impl;

import cn.itcast.dao.FavoriteDao;
import cn.itcast.dao.RouteDao;
import cn.itcast.dao.RouteImgDao;
import cn.itcast.dao.SellerDao;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Route;
import cn.itcast.domain.RouteImg;
import cn.itcast.domain.Seller;
import cn.itcast.service.RouteService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;
    @Autowired
    private RouteImgDao routeImgDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private FavoriteDao favoriteDao;

    /**
     * 分页查询数据，封装为pageBean对象
     * @param cid
     * @param currentPage
     * @param rows
     * @param
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int rows) {

        PageBean<Route> routePageBean = new PageBean<>();
        Page<Route> page = PageHelper.startPage(currentPage, rows);
        List<Route> routeDaoAll = routeDao.findByCid(cid);
        long totalCount = page.getTotal();
        int totalPage = page.getPages();
        List<Route> result = page.getResult();
//        int start = (currentPage - 1) * rows;
//        List<Route> list = routeDao.findByCid(cid);
        routePageBean.setCurrentPage(currentPage);
        routePageBean.setRows(rows);
        routePageBean.setTotalCount((int)totalCount);
        routePageBean.setTotalPage(totalPage);
        routePageBean.setList(result);
        return routePageBean;
    }

    /**
     * 根据rid查询route对象所有信息
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //获取route基本信息
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //获取route相关图片信息，封装进route对象内
        List<RouteImg> list = routeImgDao.findAllByRid(route.getRid());
        route.setRouteImgList(list);
        //获取route对应商家信息，封装进route对象内
        Seller seller = sellerDao.findOneBySid(route.getSid());
        route.setSeller(seller);

        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        //返回route对象
        return route;
    }

    /**
     * 查询最新的旅游路线
     * @return
     */
    @Override
    public List<Route> findRouteByDate() {
        List<Route> list = routeDao.findByDate();
        return list;
    }
}
