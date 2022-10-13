package ruitis.isdbcv1.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ruitis.isdbcv1.dao.DishDAO;
import ruitis.isdbcv1.models.Dish;
import ruitis.isdbcv1.util.SessionDataHolder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            session = request.getSession(true);
        }

        DishDAO dishDAO = (DishDAO)session.getAttribute("dishDAO");

        List<Dish> dishes = dishDAO.findAll();

        session.setAttribute("dishes", dishes);
        request.getRequestDispatcher("/jsp/order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("/order");
            return;
        }

        SessionDataHolder dataHolder = (SessionDataHolder)session.getAttribute("dataHolder");
        DishDAO dishDAO = (DishDAO)session.getAttribute("dishDAO");

        Optional<Dish> maybeDish = dishDAO.findByName(request.getParameter("dish"));

        if (maybeDish.isEmpty()) {
            response.sendRedirect("/order?error");
            return;
        }

        Dish dish = maybeDish.get();

        Integer numberOrdered = dataHolder.getOrderInfo().getOrDefault(dish, 0);
        dataHolder.getOrderInfo().put(dish, numberOrdered+1);

        response.sendRedirect("/order");
    }
}
