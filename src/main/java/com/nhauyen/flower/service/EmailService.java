package com.nhauyen.flower.service;

import com.nhauyen.flower.entity.Order;
import com.nhauyen.flower.entity.OrderDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Gửi OTP 6 số
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(toEmail);
            helper.setSubject("Mã Xác Thực Quên Mật Khẩu - Nhã Uyên Flower");
            
            String content = "<div style='font-family: Arial; padding: 20px; text-align: center; border: 1px solid #fbcfe8; border-radius: 10px;'>"
                    + "<h2 style='color: #db2777;'>Mã OTP Của Bạn</h2>"
                    + "<p>Sử dụng mã bên dưới để đặt lại mật khẩu. Mã này có hiệu lực trong 5 phút.</p>"
                    + "<h1 style='background: #fdf2f8; display: inline-block; padding: 10px 30px; letter-spacing: 5px; color: #db2777; border-radius: 5px;'>" + otp + "</h1>"
                    + "<p style='color: #888; font-size: 12px; margin-top: 20px;'>Không chia sẻ mã này cho bất kỳ ai.</p>"
                    + "</div>";
            
            helper.setText(content, true);
            mailSender.send(message);
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Gửi Hóa đơn (Giữ nguyên logic cũ)
    public void sendOrderConfirmation(Order order) {
        // ... (Giữ nguyên code gửi hóa đơn của anh ở đây) ...
        // Anh copy lại đoạn code gửi hóa đơn cũ vào đây nhé, chỉ thêm hàm sendOtpEmail ở trên thôi
        // Nếu cần em viết lại cả file thì bảo em nhé.
        try {
             MimeMessage message = mailSender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
             helper.setTo("gamebauham79@gmail.com"); // Email của anh
             helper.setSubject("🌸 Đơn Hàng Mới #" + order.getId());
             helper.setText("<h1>Có đơn hàng mới!</h1><p>Tổng tiền: " + order.getTotalAmount() + "</p>", true);
             mailSender.send(message);
        } catch (Exception e) { e.printStackTrace(); }
    }
}