    package com.example.hoiiday.service.impl;

    import com.example.hoiiday.DTO.UserDTO;
    import com.example.hoiiday.mapper.UserMapper;
    import com.example.hoiiday.model.Admin;
    import com.example.hoiiday.model.User;
    import com.example.hoiiday.repository.AdminRepository;
    import com.example.hoiiday.repository.UserRepository;
    import com.example.hoiiday.service.EmailService;
    import com.example.hoiiday.service.UserService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.Set;
    import java.util.stream.Collectors;

    @Service
    public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final AdminRepository adminRepository;
        private final PasswordEncoder passwordEncoder;
        private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
        private final EmailService emailService;

        public UserServiceImpl(UserRepository userRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
            this.userRepository = userRepository;
            this.adminRepository = adminRepository;
            this.passwordEncoder = passwordEncoder;
            this.emailService = emailService;
        }

        @Override
        public UserDTO createUser(UserDTO userDTO) {
            User user = UserMapper.mapToUser(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            String subject = "Welcome to Our Platform!";
            String body = "Dear " + savedUser.getFirstName() + ",\n\n" +
                            "Thank you for signing up for our platform!\n\n" +
                            "We're excited to have you on board.";
            emailService.sendSimpleEmail(savedUser.getEmail(), subject, body);
            if ("ADMIN".equalsIgnoreCase(userDTO.getRole().name())) {
                Admin admin = new Admin();
                admin.setUser(savedUser);
                adminRepository.save(admin);
                savedUser.setAdmin(admin);
                userRepository.save(savedUser);
            }

            return UserMapper.mapToUserDTO(savedUser);
        }

        @Override
        public Set<UserDTO> getAllUsers() {
            Set<User> users = userRepository.findAll().stream().collect(Collectors.toSet());
            return users.stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toSet());
        }

        @Override
        public UserDTO getUserById(Long userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User doesnt not exist"));
            return UserMapper.mapToUserDTO(user);
        }

        @Override
        public User getUserEntityById(Long userId) {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        }

        @Override
        public UserDTO updateUser(Long userId, UserDTO userDTO) {
            User userToUpdate = userRepository.findById(userId)
                    .orElseThrow(()-> new RuntimeException("User doesn't exist"));
            userToUpdate.setEmail(userDTO.getEmail());
            userToUpdate.setFirstName(userDTO.getFirstName());
            userToUpdate.setLastName(userDTO.getLastName());
            userToUpdate.setPhoneNumber(userDTO.getPhoneNumber());
            userToUpdate.setRole(userDTO.getRole());
            userRepository.save(userToUpdate);
            return UserMapper.mapToUserDTO(userToUpdate);

        }

        @Override
        public void deleteUser(Long userId) {
            userRepository.deleteById(userId);
        }

        @Override
        public User findUserByEmail(String email){
            logger.info("Searching for user with email: {}", email);
            return userRepository.findByEmail(email).orElse(null);
        }

        @Override
        public User save(User user) {
            return userRepository.save(user);
        }


    }
