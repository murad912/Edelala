package com.edelala.mur.service;


import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException; // Import MailException
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor // Inject JavaMailSender automatically
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String senderEmail;
//
//    /**
//     * Sends a rent request notification to the property owner.
//     * @param owner The owner of the property.
//     * @param renter The renter who made the request.
//     * @param property The property involved in the request.
//     */
//    public void sendRentRequestNotification(User owner, User renter, Property property) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(owner.getEmail());
//            message.setFrom(senderEmail);
//            message.setSubject("New Rent Request for Property: " + property.getTitle());
//            message.setText(String.format("Dear %s,\n\n%s (%s) has requested to rent your property at %s.\n\nPlease log in to your dashboard to view the request.",
//                    owner.getFirstName(), renter.getFirstName() + " " + renter.getLastName(), renter.getEmail(), property.getAddress()));
//            mailSender.send(message);
//            System.out.println("Email sent: Rent Request Notification to " + owner.getEmail());
//        } catch (MailException e) {
//            System.err.println("Failed to send rent request notification email to " + owner.getEmail() + ": " + e.getMessage());
//            // In production, you might log this error more formally or notify an admin.
//        }
//    }
//
//    /**
//     * Sends a rent request approval notification to the renter.
//     * @param owner The owner who approved the request.
//     * @param renter The renter whose request was approved.
//     * @param property The property involved.
//     */
//    public void sendRentRequestApprovalNotification(User owner, User renter, Property property) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(renter.getEmail());
//            message.setFrom(senderEmail);
//            message.setSubject("Your Rent Request Approved for: " + property.getTitle());
//            message.setText(String.format("Dear %s,\n\nYour rent request for the property at %s has been approved by %s.\n\nPlease proceed with the payment for the broker service.",
//                    renter.getFirstName(), property.getAddress(), owner.getFirstName() + " " + owner.getLastName()));
//            mailSender.send(message);
//            System.out.println("Email sent: Rent Request Approval to " + renter.getEmail());
//            // Optionally, send a notification to the owner as well
//        } catch (MailException e) {
//            System.err.println("Failed to send rent request approval email to " + renter.getEmail() + ": " + e.getMessage());
//        }
//    }
//
//    /**
//     * Sends a password reset email using JavaMailSender.
//     * @param toEmail The recipient's email address.
//     * @param resetLink The password reset link.
//     */
//    public void sendPasswordResetEmail(String toEmail, String resetLink) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(toEmail);
//            message.setFrom(senderEmail); // Use the configured sender email
//            message.setSubject("Password Reset Request");
//            message.setText(String.format("Dear user,\n\nYou requested a password reset. Please click the following link to reset your password:\n%s\n\nThis link is valid for 15 minutes.", resetLink));
//            mailSender.send(message);
//            System.out.println("Email sent: Password Reset Request to " + toEmail);
//        } catch (MailException e) {
//            System.err.println("Failed to send password reset email to " + toEmail + ": " + e.getMessage());
//        }
//    }
//} jun 7 work


//@Service
//@RequiredArgsConstructor // Inject JavaMailSender automatically
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String senderEmail;
//
//    /**
//     * Sends a password reset email using JavaMailSender.
//     * @param toEmail The recipient's email address.
//     * @param resetLink The password reset link.
//     */
//    public void sendPasswordResetEmail(String toEmail, String resetLink) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(toEmail);
//            message.setFrom(senderEmail); // Use the configured sender email
//            message.setSubject("Password Reset Request");
//            message.setText(String.format("Dear user,\n\nYou requested a password reset. Please click the following link to reset your password:\n%s\n\nThis link is valid for 15 minutes.", resetLink));
//            mailSender.send(message);
//            System.out.println("Email sent successfully: Password Reset Request to " + toEmail);
//        } catch (MailException e) {
//            System.err.println("Failed to send password reset email to " + toEmail + ": " + e.getMessage());
//            // In a real application, you might log this error more formally
//        }
//    }
//
//    /**
//     * Sends a rent request notification to the property owner.
//     * @param owner The owner of the property.
//     * @param renter The renter who made the request.
//     * @param property The property involved in the request.
//     */
//    public void sendRentRequestNotification(User owner, User renter, Property property) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(owner.getEmail());
//            message.setFrom(senderEmail);
//            message.setSubject("New Rent Request for Your Property: " + property.getTitle());
//            message.setText(String.format("Dear %s,\n\n%s (%s) has requested to rent your property at %s.\n\nPlease log in to your dashboard to view the request.",
//                    owner.getFirstName(), renter.getFirstName() + " " + renter.getLastName(), renter.getEmail(), property.getAddress()));
//            mailSender.send(message);
//            System.out.println("Email sent successfully: Rent Request Notification to " + owner.getEmail());
//        } catch (MailException e) {
//            System.err.println("Failed to send rent request notification email to " + owner.getEmail() + ": " + e.getMessage());
//            // In production, you might log this error more formally or notify an admin.
//        }
//    }
//
//    /**
//     * Sends a rent request approval notification to the renter.
//     * @param owner The owner who approved the request.
//     * @param renter The renter whose request was approved.
//     * @param property The property involved.
//     */
//    public void sendRentRequestApprovalNotification(User owner, User renter, Property property) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(renter.getEmail());
//            message.setFrom(senderEmail);
//            message.setSubject("Your Rent Request Approved for: " + property.getTitle());
//            message.setText(String.format("Dear %s,\n\nYour rent request for the property at %s has been approved by %s.\n\nPlease proceed with the payment for the broker service.",
//                    renter.getFirstName(), property.getAddress(), owner.getFirstName() + " " + owner.getLastName()));
//            mailSender.send(message);
//            System.out.println("Email sent successfully: Rent Request Approval to " + renter.getEmail());
//            // Optionally, send a notification to the owner as well
//        } catch (MailException e) {
//            System.err.println("Failed to send rent request approval email to " + renter.getEmail() + ": " + e.getMessage());
//        }
//    }
//
//    /**
//     * Sends a rent request rejection notification to the renter.
//     * @param owner The owner who rejected the request.
//     * @param renter The renter whose request was rejected.
//     * @param property The property involved.
//     */
//    public void sendRentRequestRejectionNotification(User owner, User renter, Property property) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(renter.getEmail());
//            message.setFrom(senderEmail);
//            message.setSubject("Your Rent Request for " + property.getTitle() + " Has Been Rejected.");
//            message.setText(String.format("Dear %s,\n\nUnfortunately, your rent request for '" + property.getTitle() + "' has been rejected by the owner (" + owner.getEmail() + ").\n\nYou may explore other available properties on our platform.",
//                    renter.getFirstName(), property.getTitle(), owner.getEmail()));
//            mailSender.send(message);
//            System.out.println("Email sent successfully: Rent Request Rejection to " + renter.getEmail());
//        } catch (MailException e) {
//            System.err.println("Failed to send rent request rejection email to " + renter.getEmail() + ": " + e.getMessage());
//        }
//    }
//}
// july 14

@Service
@RequiredArgsConstructor // Inject JavaMailSender automatically
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * Sends a password reset email using JavaMailSender.
     * @param toEmail The recipient's email address.
     * @param resetLink The password reset link.
     */
    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setFrom(senderEmail); // Use the configured sender email
            message.setSubject("Password Reset Request");
            message.setText(String.format("Dear user,\n\nYou requested a password reset. Please click the following link to reset your password:\n%s\n\nThis link is valid for 15 minutes.", resetLink));
            mailSender.send(message);
            logger.info("Email sent successfully: Password Reset Request to {}", toEmail);
        } catch (MailException e) {
            logger.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage(), e); // Log with stack trace
            // Re-throw if you want the calling method to handle it, otherwise, just log.
            // For email sending, often just logging is sufficient as it might be a transient issue.
        }
    }

    /**
     * Sends a rent request notification to the property owner.
     * @param owner The owner of the property.
     * @param renter The renter who made the request.
     * @param property The property involved in the request.
     */
    public void sendRentRequestNotification(User owner, User renter, Property property) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(owner.getEmail());
            message.setFrom(senderEmail);
            message.setSubject("New Rent Request for Your Property: " + property.getTitle());
            message.setText(String.format("Dear %s,\n\n%s (%s) has requested to rent your property at %s.\n\nPlease log in to your dashboard to view the request.",
                    owner.getFirstName(), renter.getFirstName() + " " + renter.getLastName(), renter.getEmail(), property.getAddress()));
            mailSender.send(message);
            logger.info("Email sent successfully: Rent Request Notification to {}", owner.getEmail());
        } catch (MailException e) {
            logger.error("Failed to send rent request notification email to {}: {}", owner.getEmail(), e.getMessage(), e); // Log with stack trace
        }
    }

    /**
     * Sends a rent request approval notification to the renter.
     * @param owner The owner who approved the request.
     * @param renter The renter whose request was approved.
     * @param property The property involved.
     */
    public void sendRentRequestApprovalNotification(User owner, User renter, Property property) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(renter.getEmail());
            message.setFrom(senderEmail);
            message.setSubject("Your Rent Request Approved for: " + property.getTitle());
            message.setText(String.format("Dear %s,\n\nYour rent request for the property at %s has been approved by %s.\n\nPlease proceed with the payment for the broker service.",
                    renter.getFirstName(), property.getAddress(), owner.getFirstName() + " " + owner.getLastName()));
            mailSender.send(message);
            logger.info("Email sent successfully: Rent Request Approval to {}", renter.getEmail());
        } catch (MailException e) {
            logger.error("Failed to send rent request approval email to {}: {}", renter.getEmail(), e.getMessage(), e); // Log with stack trace
            throw e; // Re-throw to propagate the error up to the controller/service that called it
        }
    }

    /**
     * Sends a rent request rejection notification to the renter.
     * @param owner The owner who rejected the request.
     * @param renter The renter whose request was rejected.
     * @param property The property involved.
     */
    public void sendRentRequestRejectionNotification(User owner, User renter, Property property) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(renter.getEmail());
            message.setFrom(senderEmail);
            message.setSubject("Your Rent Request for " + property.getTitle() + " Has Been Rejected.");
            message.setText(String.format("Dear %s,\n\nUnfortunately, your rent request for '" + property.getTitle() + "' has been rejected by the owner (" + owner.getEmail() + ").\n\nYou may explore other available properties on our platform.",
                    renter.getFirstName(), property.getTitle(), owner.getEmail()));
            mailSender.send(message);
            logger.info("Email sent successfully: Rent Request Rejection to {}", renter.getEmail());
        } catch (MailException e) {
            logger.error("Failed to send rent request rejection email to {}: {}", renter.getEmail(), e.getMessage(), e); // Log with stack trace
            throw e; // Re-throw to propagate the error
        }
    }
}

