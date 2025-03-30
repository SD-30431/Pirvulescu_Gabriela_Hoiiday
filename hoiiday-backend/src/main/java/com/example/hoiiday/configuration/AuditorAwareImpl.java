//package com.example.hoiiday.configuration;
//
//import lombok.NonNull;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Optional;
//
//public class AuditorAwareImpl implements AuditorAware<String> {
//    @Override
//    @NonNull
//    public Optional<String> getCurrentAuditor() {
//        if (SecurityContextHolder.getContext().getAuthentication() == null) {
//            return Optional.empty();
//        }
//        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
//    }
//}
