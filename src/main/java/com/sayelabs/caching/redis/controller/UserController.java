package com.sayelabs.caching.redis.controller;


import com.sayelabs.caching.redis.model.User;
import com.sayelabs.caching.redis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "users")
public class UserController  {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users",key = "#userId",unless = "#result.followers<12000")
    @GetMapping(path = "/{userId}")
    public User getUser(@PathVariable String userId){
        logger.info("Getting user with id {}", userId);
        User user;
        user = (User)userRepository.findById(Long.parseLong(userId)).get();
        return user;
    }

    @CachePut(value = "users", key = "#user.id")
    @PutMapping(path = "/")
    public User updateUser(@RequestBody User user){
        userRepository.save(user);
        return user;
    }

    @CacheEvict(value = "users",allEntries = true)
    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userRepository.deleteById(userId);
    }
}
