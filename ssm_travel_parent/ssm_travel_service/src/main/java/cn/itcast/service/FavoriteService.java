package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.Route;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Route;

import java.util.List;

public interface FavoriteService {

    /**
     * 根据rid和uid查询是否收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(int rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     * 根据用户uid分页查询用户收藏的旅游线路
     * @param uid
     * @param currentPage
     * @param rows
     * @return
     */
    PageBean<Route> findFavoriteByUid(int uid, int currentPage, int rows);
    /**
     * 条件查询收藏排行榜
     * @param currentPage
     * @param rows
     * @param rname
     * @param lprice
     * @param hprice
     * @return
     */
    PageBean<Route> findAllFavorite(int currentPage, int rows, String rname, int lprice, int hprice);

    List<Route> findPopularity();

}
