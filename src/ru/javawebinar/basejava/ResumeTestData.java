package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ResumeTestData {
    private final Resume resume = new Resume("Григорий Кислин");

    public Resume getResume() {
        return resume;
    }

    public static void main(String[] args) {
        ResumeTestData testData = new ResumeTestData();
        Resume resume = testData.getResume();
        testData.setContact(resume);
        testData.setSection(resume);
        System.out.println(resume.getFullName());
        outputData(resume);
    }

    public static void outputData(Resume resume) {
        for (ContactType type : ContactType.values()) {
            System.out.print(type.getTitle());
            System.out.println(resume.getContact(type));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(resume.getSection(type) + " ");
        }
    }

    public void setContact(Resume resume) {
        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "grigory.kislin");
        resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LINKEDINPROFILE, "https://www.linkedin.com/in/gkislin");
        resume.setContact(ContactType.GITHUBPROFILE, "https://github.com/gkislin");
        resume.setContact(ContactType.STACKOVERFLOWPROFILE, "https://stackoverflow.com/users/548473");
        resume.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
    }

    public void setSection(Resume resume) {
        ArrayList<String> objective = new ArrayList<>();
        objective.add("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        ListSection objectiveSection = new ListSection(objective);
        resume.setSection(SectionType.OBJECTIVE, objectiveSection);

        ArrayList<String> personal = new ArrayList<>();
        personal.add("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        ListSection personalSection = new ListSection(personal);
        resume.setSection(SectionType.PERSONAL, personalSection);

        ArrayList<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        ListSection achievementSection = new ListSection(achievements);
        resume.setSection(SectionType.ACHIEVEMENT, achievementSection);

        ArrayList<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        ListSection qualificationSection = new ListSection(qualifications);
        resume.setSection(SectionType.QUALIFICATIONS, qualificationSection);

        ArrayList<Institution> workPlaces = new ArrayList<>();
        workPlaces.add(new Institution("http://javaops.ru/", "Java Online Projects",
                LocalDate.of(2013, 10, 1), null, "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        workPlaces.add(new Institution("https://www.wrike.com", "Wrike",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        workPlaces.add(new Institution(null, "RIT Center", LocalDate.of(2012, 04, 01), LocalDate.of(2014, 10, 1), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        InstitutionSection experience = new InstitutionSection(workPlaces);
        resume.setSection(SectionType.EXPERIENCE, experience);

        ArrayList<Institution> studyPlaces = new ArrayList<>();
        studyPlaces.add(new Institution("https://www.coursera.org/course/progfun", "Coursera", LocalDate.of(2013, 3,
                1), LocalDate.of(2013, 5, 1), null, "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        studyPlaces.add(new Institution("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                "Luxoft", LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1), null, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        InstitutionSection education = new InstitutionSection(studyPlaces);
        resume.setSection(SectionType.EDUCATION, education);
    }
}



