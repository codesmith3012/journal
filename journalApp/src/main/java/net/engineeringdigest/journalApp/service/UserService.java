package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Component
public class UserService {

    @Autowired
    public UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public boolean saveNewEntry(User user) {
        try {
            System.out.println("Saving new entry");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(user.getRole() != null && !user.getRole().isEmpty() ? user.getRole() : "USER");
            userRepository.save(user);
            return true;
        } catch (Exception e) {
             log.error("Error occurred for {}",user.getUserName(),e);
             log.warn("Error saving duplicate user");
             log.info("Error saving duplicate user");
             log.debug("Error saving duplicate user");
             log.trace("Error saving duplicate user");
            return false;
        }
    }
    public void saveAdmin(User user) {
        System.out.println("Saving new entry");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole() != null && !user.getRole().isEmpty() ? user.getRole() : "ADMIN");
        userRepository.save(user);
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
        // Optional data return value means that the data can be present in the data structure or not....
    }
    public void deleteById(long id) {
         userRepository.deleteById(id);

    }
    public User findByUsername(String username) {
        User user = userRepository.findUsersByUserName(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user;
    }


}

// controller -> service -> repository