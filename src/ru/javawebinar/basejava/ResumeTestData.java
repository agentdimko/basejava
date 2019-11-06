package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;

public class ResumeTestData {
    private final Resume resume = new Resume("Григорий Кислин");

    public Resume getResume() {
        return resume;
    }

    public static void main(String[] args) {
        ResumeTestData testData = new ResumeTestData();
        Resume resume = testData.getResume();
        System.out.println(resume.getFullName());
        testData.fillContacts(resume);
        testData.outputContacts(resume);
        testData.fillSections(resume);
        testData.outputSections(resume);
    }

    private void outputSections(Resume resume) {
        for (Section section : resume.getAllSections()) {
            System.out.println(section.toString());
        }
    }

    private void fillSections(Resume resume) {
        resume.addSectionElement(SectionType.OBJECTIVE,
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSectionElement(SectionType.PERSONAL,
                "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSectionElement(SectionType.ACHIEVEMENT,
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                        "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                        " Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                        "Более 1000 выпускников..");
        resume.addSectionElement(SectionType.ACHIEVEMENT,
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция " +
                        "с Twilio, DuoSecurity, Google Authenticator,Jira, Zendesk.");
        resume.addSectionElement(SectionType.ACHIEVEMENT,
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                        "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                        "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                        "интеграция CIFS/SMB java сервера.");
        resume.addSectionElement(SectionType.ACHIEVEMENT,
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, " +
                        "GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        resume.addSectionElement(SectionType.ACHIEVEMENT,
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                        "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации " +
                        "о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для " +
                        "администрирования и мониторинга системы по JMX (Jython/ Django).");
        resume.addSectionElement(SectionType.ACHIEVEMENT,
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, " +
                        "Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "MySQL, SQLite, MS SQL, HSQLDB");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, " +
                        "Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), " +
                        "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        resume.addSectionElement(SectionType.QUALIFICATIONS, "Python: Django.");
        resume.addSectionElement(SectionType.QUALIFICATIONS, "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resume.addSectionElement(SectionType.QUALIFICATIONS, "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        resume.addSectionElement(SectionType.QUALIFICATIONS, "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, " +
                "JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, " +
                "HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, " +
                        "OpenCmis, Bonita, pgBouncer.");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных " +
                        "шаблонов, UML, функционального программирования");
        resume.addSectionElement(SectionType.QUALIFICATIONS,
                "Родной русский, английский \"upper intermediate\"");
        resume.addSectionElement(SectionType.EXPERIENCE,
                new Institution(
                        "Java Online Projects",
                        "http://javaops.ru/",
                        LocalDate.of(2013, 10, 1),
                        null,
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));
        resume.addSectionElement(SectionType.EXPERIENCE,
                new Institution(
                        "Wrike",
                        "https://www.wrike.com",
                        LocalDate.of(2014, 10, 1),
                        LocalDate.of(2016, 1, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        resume.addSectionElement(SectionType.EXPERIENCE,
                new Institution(
                        "Rit Center",
                        null,
                        LocalDate.of(2012, 4, 1),
                        LocalDate.of(2014, 10, 1),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для " +
                                "разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
                                "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура " +
                                "БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, " +
                                "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat, WSO2, " +
                                "xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, " +
                                "PL/Python"));
        resume.addSectionElement(SectionType.EDUCATION,
                new Institution(
                        "Coursera",
                        "https://www.coursera.org/course/progfun",
                        LocalDate.of(2013, 3, 1),
                        LocalDate.of(2013, 5, 1),
                        null,
                        "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        resume.addSectionElement(SectionType.EDUCATION,
                new Institution(
                        "Luxoft",
                        "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        LocalDate.of(2011, 3, 1),
                        LocalDate.of(2011, 4, 1),
                        null,
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
    }

    public void fillContacts(Resume resume) {
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDINPROFILE, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUBPROFILE, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOWPROFILE, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
    }

    public void outputContacts(Resume resume) {
        for (String s : resume.getAllContact()) {
            System.out.println(s);
        }

    }
}
