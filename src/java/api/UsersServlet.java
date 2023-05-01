package api;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import model.User;
import org.json.JSONObject;
import org.json.JSONArray;



@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    
    
private JSONObject getJSONBody(BufferedReader reader) throws Exception{
    StringBuilder buffer = new StringBuilder();
    String line = null;
    while ((line = reader.readLine())!=null) {
        buffer.append(line);
     
    }
    return new JSONObject(buffer.toString());    
}

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        response.setContentType("application/json;charset=UTF-8");
        JSONObject object = new JSONObject();
        try {
          object.put("list", new JSONArray(User.list));
            
        }catch(Exception ex){
            response.setStatus(500);
            object.put("error", ex.getLocalizedMessage());
        }
           response.getWriter().print(object.toString());
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject object = new JSONObject();
        try {
            
            JSONObject body = getJSONBody (request.getReader());
            String nome = body.getString("nome");
            String email = body.getString("email");
            String senha = body.getString("senha");
            if( nome !=null && email !=null && senha != null) {
                User u = new User(nome, email,senha);
                User.list.add(u);
            }
            object.put("Users", new JSONArray(User.list));
            
        }catch(Exception ex){
            response.setStatus(500);
            object.put("error", ex.getLocalizedMessage());             
        }
        response.getWriter().print(object.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject object = new JSONObject();
        try {
            String email = request.getParameter("email");
            int i = -1;
            for(User u :User.list) {
                if(u.getEmail().equals(email)){
                    i = User.list.indexOf(u);
                    break;
                }
                
            }
            User.list.remove(i);
            object.put("list", new JSONArray(User.list));
            
        }catch(Exception ex){
            response.setStatus(500);
            object.put("error", ex.getLocalizedMessage());   
        }
         response.getWriter().print(object.toString());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}
