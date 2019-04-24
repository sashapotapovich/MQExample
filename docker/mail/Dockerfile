FROM mailhog/mailhog:v1.0.0
MAINTAINER sashapotpovich@gmail.com

HEALTHCHECK --timeout=1s --retries=60 CMD true || exit 1


ENV MH_API_BIND_ADDR 0.0.0.0:8025
ENV MH_UI_BIND_ADDR 0.0.0.0:8025
ENV MH_SMTP_BIND_ADDR 0.0.0.0:25
ENV MH_AUTH_FILE /home/mailhog/auth.file

COPY setup /setup

RUN echo "sci:$(MailHog bcrypt sci)" > /home/mailhog/auth.file



EXPOSE 25
EXPOSE 8025

USER root