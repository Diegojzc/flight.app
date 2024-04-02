package com.tokioschool.flight.app.email.service;

import com.tokioschool.flight.app.email.dto.EmailDTO;

public interface EmailService {

    void sendEmail(EmailDTO emailDTO);
}
