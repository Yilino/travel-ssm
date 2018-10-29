package cn.itcast.service.impl;

import cn.itcast.dao.FavoriteDao;
import cn.itcast.dao.RouteDao;
import cn.itcast.domain.Favorite;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Route;
import cn.itcast.service.FavoriteService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteDao favoriteDao;
    @Autowired
    private RouteDao routeDao;

    /**
     * 根据rid和uid查询是否收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(int rid, int uid) {
        System.out.println(rid);
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        return favorite != null;
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    /**
     * 根据用户uid分页查询用户收藏的旅游线路
     * @param uid
     * @param currentPage
     * @param rows
     * @return
     */
    @Override
    public PageBean<Route> findFavoriteByUid(int uid, int currentPage,int rows ) {

        PageBean<Route> routePageBean = new PageBean<>();
        Page<Integer> page = PageHelper.startPage(currentPage, rows);

//        int totalCount = favoriteDao.findCountByUid(uid);

//        int start = (currentPage - 1) * rows;
        List<Route> list = null;
        List<Integer> ridList = favoriteDao.findRidByUid(uid);
        List<Integer> result = page.getResult();
       /* if (ridList != null&&ridList.size() > 0){
            list = new ArrayList<Route>();
            for (int rid : ridList) {
                Route route = routeDao.findOne(rid);
                list.add(route);
            }
        }*/
        if (result != null&&result.size() > 0){
            list = new ArrayList<Route>();
            for (int rid : result) {
                Route route = routeDao.findOne(rid);
                list.add(route);
            }
        }

//        int totalPage = totalCount % rows == 0 ? totalCount / rows : (totalCount / rows) + 1;
        int totalPage = page.getPages();
        long totalCount =page.getTotal();
        routePageBean.setCurrentPage(currentPage);
        routePageBean.setRows(rows);
        routePageBean.setTotalCount((int)totalCount);
        routePageBean.setTotalPage(totalPage);
        routePageBean.setList(list);
        return routePageBean;
    }

    /**
     * 条件查询收藏排行榜
     * @param currentPage
     * @param rows
     * @param rname
     * @param lprice
     * @param hprice
     * @return
     */
    @Override
    public PageBean<Route> findAllFavorite(int currentPage, int rows,String rname,int lprice,int hprice) {
        PageBean<Route> routePageBean = new PageBean<>();
        //先获取的所有被收藏的旅游线路rid和收藏次数
        List<Route> route_list = favoriteDao.findRidAndCount();
        //用上面的rid查新每个线路的具体信息，（我在这加了条件查询，所以没能分页，不加条件查询分页可直接做）
        List<Route> list_all = null;
        if (route_list != null&&route_list.size() > 0){
            list_all = new ArrayList<Route>();
            for (Route rid_route : route_list) {
                Route route = routeDao.findByMsg(rid_route.getRid(),rname,lprice,hprice);
                if (route!=null){
                    route.setCount(rid_route.getCount());
                    list_all.add(route);
                }
            }
        }
        //totalCountmei没加条件查询用route_list就可以
        int totalCount = list_all.size();
        //这是加了条件查询后我在外面进行的分页操作
        List<Route> list = null;
        int start =(currentPage-1)*rows;
        int end = (start+rows) > list_all.size() ? list_all.size():(start+rows);
        if (list_all != null&&list_all.size() > 0){
            list = new ArrayList<Route>();
            for (int i = start;i < end;i++){
                list.add(list_all.get(i));
            }
        }

        int totalPage = totalCount % rows == 0 ? totalCount / rows : (totalCount / rows) + 1;

        routePageBean.setCurrentPage(currentPage);
        routePageBean.setRows(rows);
        routePageBean.setTotalCount(totalCount);
        routePageBean.setTotalPage(totalPage);
        routePageBean.setList(list);

        return routePageBean;
    }

    /**
     * 查询最有人气的旅游路线
     * @return
     */
    @Override
    public List<Route> findPopularity() {
        List<Integer> list = favoriteDao.findRidByCount();
        List<Route> routeList = new ArrayList<>();
        if (list!=null&& list.size()>0){
            for (int rid : list) {
                Route route = routeDao.findOne(rid);
                routeList.add(route);
            }
        }
        return routeList;
    }
}
