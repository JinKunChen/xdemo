package com.topsem.mcc.repository;


import com.topsem.common.repository.jpa.BaseRepository;
import com.topsem.mcc.domain.Menu;

import java.util.List;


public interface MenuRepository extends BaseRepository<Menu, Long> {

    List<Menu> getMenuByParentId(Long pid);


    List<Menu> findByParentIdsLike(String s);
}
