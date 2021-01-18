package filip.zg.rznuback.service;

import filip.zg.rznuback.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;

@Service
@RequiredArgsConstructor
public class ChatService {

    private static String adminMessage = null;
    private static String userMessage = null;

    public static void saveMessage(String message) {
        if (isAdmin()) adminMessage = message;
        else userMessage = message;
    }

    public static String getMessage() {
        if (isAdmin()) {
            String msg = userMessage;
            userMessage = null;
            return msg;
        } else {
            String msg = adminMessage;
            adminMessage = null;
            return msg;
        }
    }

    private static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        return user.getUsername().equals("admin");
    }
}
