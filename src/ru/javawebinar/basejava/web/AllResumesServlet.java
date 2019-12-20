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

public class AllResumesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset-UTF-8");
        Storage storage = Config.getInstance().getStorage();
        List<Resume> resumes = storage.getAllSorted();
        resp.getWriter().write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "<title>All Resumes</title>\n" +
                "</head>\n" +
                "<body>");
        resp.getWriter().write("<table>\n" +
                "<caption>All Resumes</caption>" +
                "<tr>\n" +
                "<th>UUID</th>\n" +
                "<th>Full Name</th>\n" +
                "</tr>");
        for (Resume resume : resumes) {
            resp.getWriter().write("<tr><td>" + resume.getUuid() + "</td>" + "<td>" + resume.getFullName()
                    + "</td>></tr>");
        }
        resp.getWriter().write("</table>");
        resp.getWriter().write("</body>\n" +
                "</html>");
    }
}
