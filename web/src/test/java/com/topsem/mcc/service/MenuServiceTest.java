package com.topsem.mcc.service;

import com.topsem.Application;
import com.topsem.mcc.domain.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Test class for the UserResource REST controller.
 *
 * @see com.topsem.mcc.service.UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class MenuServiceTest {

    @Inject
    private MenuService menuService;

    @Test
    public void getMenuByParentId() {
        List<Menu> menus = menuService.getMenuByParentId(1L);
        System.out.println(menus);
    }
}
