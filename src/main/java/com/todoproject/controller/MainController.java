package com.todoproject.controller;

import com.todoproject.entity.User;
import com.todoproject.entity.Todo;
import com.todoproject.service.TodoService;
import com.todoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Controller
public class MainController {


    @Autowired
    TodoService todoService;

    @Autowired
    UserService userService;


    @RequestMapping("/")
    public String defaultAfterLogin(HttpServletRequest request) {
        Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();
        String admin = "ADMIN";
        if (myRole.equals(admin)) {
            return "redirect:/admin";
        }
        return "redirect:/user";
    }
    @RequestMapping(value = "/save_todo", method = RequestMethod.POST)
    public String saveTodo(@ModelAttribute("todo") Todo todo) {
        Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = auth.getName();
        Long id=userService.getCurrentUserId(currentusername);
        todo.setUser_id(id);
        todoService.save(todo);
        return "redirect:/";
    }
    @RequestMapping(value = "/save_user", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/list_user";
    }
    @RequestMapping("/delete_todo/{tid}")
    public String deleteTodoPage(@PathVariable (name="tid") Long todo_id) {
        todoService.delete(todo_id);
        return "redirect:/";
    }
    @RequestMapping("/delete_user/{id}")
    public String deleteUserPage(@PathVariable (name="id") Long id) {
        userService.delete(id);
        return "redirect:/list_user";
    }
    @GetMapping("/login")
    public String showLoginPage()
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null||authentication instanceof AnonymousAuthenticationToken)
        {
            return "/login";
        }
        return "redirect:/";
    }

    @RequestMapping("/user")
    public String viewHomePage(Model model) {

        Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = auth.getName();
        Long id=userService.getCurrentUserId(currentusername);
        List<Todo> listTodo = todoService.listById(id);
        model.addAttribute("listTodo", listTodo);
        return "index";
    }
    @RequestMapping("/admin")
    public String viewHomeAdminPage(Model model) {
        List<Todo> listTodo = todoService.listAll();
        model.addAttribute("listTodo", listTodo);
        return "admin";
    }
    @RequestMapping("/asc")
    public String viewAscUserHomePage(Model model) {

        Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = auth.getName();
        Long id=userService.getCurrentUserId(currentusername);
        List<Todo> listTodo= todoService.listById_DateAsc(id);
        model.addAttribute("listTodo", listTodo);

        return "index";
    }


    @RequestMapping("/admin_asc")
    public String viewAscAdminHomePage(Model model) {

        List<Todo> listTodo = todoService.listAllByDateAsc();
        model.addAttribute("listTodo", listTodo);
        return "admin";
    }




    @RequestMapping("/list_user")
    public String viewListUserPage(Model model) {
        List<User> listUser = userService.listAll();
        model.addAttribute("listUser", listUser);
        return "list_user";
    }

    @RequestMapping("/add_todo")
    public String newTodoPage(Model model) {
        Todo todo =new Todo();
        model.addAttribute(todo);
        return "add_todo";
    }
    @RequestMapping("/add_user")
    public String newUserPage(Model model) {
        User user =new User();
        model.addAttribute(user);
        return "add_user";
    }


    @RequestMapping("/update_todo/{tid}")
    public ModelAndView showEditTodoPage(@PathVariable(name="tid") Long todo_id) {
        ModelAndView mav=new ModelAndView("update_todo");
        Todo todo = todoService.get(todo_id);
        Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = auth.getName();
        Long id=userService.getCurrentUserId(currentusername);
        todo.setUser_id(id);
        mav.addObject("todo", todo);
        return mav;
    }
    @RequestMapping("/update_user/{id}")
    public ModelAndView showEditUserPage(@PathVariable(name="id") Long id) {

        ModelAndView mav=new ModelAndView("update_user");
        User user = userService.get(id);
        mav.addObject("user", user);
        return mav;
    }



}
