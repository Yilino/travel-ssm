package cn.itcast.dao;

import cn.itcast.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 根据rid查询所有相关图片路径
     * @param rid
     * @return
     */
    List<RouteImg> findAllByRid(int rid);
}
