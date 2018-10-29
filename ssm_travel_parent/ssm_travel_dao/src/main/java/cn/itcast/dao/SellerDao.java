package cn.itcast.dao;

import cn.itcast.domain.Seller;

public interface SellerDao {

    /**
     * 根据sid查询seller对象
     * @param sid
     * @return
     */
    Seller findOneBySid(int sid);
}
