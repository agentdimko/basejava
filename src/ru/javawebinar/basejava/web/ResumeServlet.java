package ru.javawebinar.basejava.web;


import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.getInstance().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset-UTF-8");
        response.getWriter().write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "</head>\n" +
                "<body>");
        response.getWriter().write("Hello Resumes!");
        String name = request.getParameter("name");
        switch (name) {
            case "getresumes":
                response.getWriter().write("<table>\n" +
                        "<caption>All Resumes</caption>" +
                        "<tr>\n" +
                        "<th>UUID</th>\n" +
                        "<th>Full Name</th>\n" +
                        "</tr>");
                List<Resume> resumes = storage.getAllSorted();
                for (Resume resume : resumes) {
                    response.getWriter().write("<tr><td>" + resume.getUuid() + "</td>" + "<td>" + resume.getFullName()
                            + "</td></tr>");
                }
                response.getWriter().write("</table>");
                break;
            default:
                response.getWriter().write("Hello " + name + "!");
                break;
        }
        response.getWriter().write("</body>\n" +
                "</html>");
    }
}
