package com.topsem.mcc.service;


import com.topsem.common.service.BaseService;
import com.topsem.mcc.domain.Menu;
import com.topsem.mcc.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MenuService extends BaseService<Menu, Long> {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenuByParentId(Long pid) {
        return menuRepository.getMenuByParentId(pid);
    }

    @Override
    @Transactional
    public Menu save(Menu menu) {
        if (menu.getParent() == null || menu.getParent().getId() == null) {
            menu.setParent(new Menu(1L));
        }
        menu.setParent(menuRepository.getOne(menu.getParent().getId()));
        String oldParentIds = menu.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
        menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");


        menuRepository.save(menu);
        // 更新子节点 parentIds
        List<Menu> list = menuRepository.findByParentIdsLike("%," + menu.getId() + ",%");
        for (Menu e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
        }
        menuRepository.save(list);

        //更新当前节点，是否为叶子节点
        menu.setLeaf(list.isEmpty());
        menuRepository.save(menu);

        return menu;
    }
}
