package com.topsem.mcc.web;

import com.codahale.metrics.annotation.Timed;
import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.User;
import com.topsem.mcc.repository.UserRepository;
import com.topsem.mcc.security.AuthoritiesConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

/**
 * 系统菜单
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/system/users")
@Slf4j
public class UserController extends CrudController<User, Long> {

    @Inject
    private UserRepository userRepository;

    /**
     * GET  /users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/{login}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    ResponseEntity<User> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return userRepository.findOneByLogin(login)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
