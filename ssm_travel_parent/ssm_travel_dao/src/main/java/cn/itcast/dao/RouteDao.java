package cn.itcast.dao;

import cn.itcast.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     *
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    int findtotalCount(int cid, String rname);

    /**
     * 分页条件查询每页具体信息
     * @param cid
     * @param start
     * @param rows
     * @param rname
     * @return
     */
    List<Route> findByPage(int cid, int start, int rows, String rname);

    /**
     * 根据cid查询所有
     * @param cid
     * @return
     */
     List<Route> findByCid(int cid);

    /**
     * 根据rid查询route对象
     * @param rid
     * @return
     */
    Route findOne(int rid);

    /**
     * 可根据rid,rname,price查询route对象
     * @param rid
     * @param rname
     * @param lprice
     * @param hprice
     * @return
     */
    Route findByMsg(int rid, String rname, int lprice, int hprice);

    /**
     * 根据日期排序，取最近的四个
     * @return
     */
    List<Route> findByDate();
}
