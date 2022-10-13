package ruitis.isdbcv1.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ruitis.isdbcv1.dao.OrderDAO;
import ruitis.isdbcv1.models.Order;
import ruitis.isdbcv1.util.SessionDataHolder;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderListServlet", value = "/order-list")
public class OrderListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            session = request.getSession(true);
        }

        OrderDAO orderDAO = (OrderDAO)session.getAttribute("orderDAO");

        List<Order> orders = orderDAO.findAll();

        session.setAttribute("orders", orders);
        request.getRequestDispatcher("/jsp/order-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                           IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("/order-list");
            return;
        }

        SessionDataHolder dataHolder = (SessionDataHolder)session.getAttribute("dataHolder");
        OrderDAO orderDAO = (OrderDAO)session.getAttribute("orderDAO");

        Order order = Order.builder().build();

        orderDAO.save(order);

        response.sendRedirect("/order-list");
    }
}
